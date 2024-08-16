package chegur.controller.page;

import chegur.controller.BaseController;
import chegur.dto.OneCallResponseDto;
import chegur.dto.WeatherRequestDto;
import chegur.service.AuthenticationService;
import chegur.service.CityWeatherService;
import chegur.service.SessionService;
import chegur.util.CookieHandler;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.WebContext;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/home")
public class HomeController extends BaseController {
    private final AuthenticationService authenticationService = AuthenticationService.getInstance();
    private final CityWeatherService cityWeatherService = CityWeatherService.getInstance();
    private final SessionService sessionService = SessionService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        WebContext context = createWebContext(req, resp);

        Optional<String> guid = CookieHandler.getSessionCookie(req);
        List<OneCallResponseDto> citiesWeather = List.of();

        try {
            citiesWeather = cityWeatherService.getWeatherInCities(guid.get());
        } catch (RuntimeException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Wrong Weather API call");
        }

        context.setVariable("username", sessionService.getSessionUser(guid.get()).getLogin());
        context.setVariable("weatherCities", citiesWeather);

        processTemplate("home", context, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String cityId;

        try {
            cityId = req.getParameter("cityId");
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }

        Optional<String> guid = CookieHandler.getSessionCookie(req);

        if (guid.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        if (cityId == null) {
            authenticationService.logOut(guid.get());
            resp.sendRedirect("login");
            return;
        }

        WeatherRequestDto weatherRequestDto = WeatherRequestDto.builder()
                .cityId(Integer.parseInt(cityId))
                .build();

        cityWeatherService.deleteCityFromFavourites(weatherRequestDto, guid.get());
    }
}
