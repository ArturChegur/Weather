package chegur.controller.page;

import chegur.controller.BaseController;
import chegur.dto.GeocodingResponseDto;
import chegur.dto.WeatherRequestDto;
import chegur.service.CityWeatherService;
import chegur.util.CookieHandler;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.WebContext;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@WebServlet("/found-locations")
public class FoundLocationsController extends BaseController {
    private final CityWeatherService cityWeatherService = CityWeatherService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        WebContext context = createWebContext(req, resp);
        String cityName = req.getParameter("cityName");

        if (cityName == null || cityName.isEmpty() || cityName.isBlank()) {
            processError("Fill up city name!", context, resp);
            return;
        }

        List<GeocodingResponseDto> foundCities = List.of();

        try {
            foundCities = cityWeatherService.getCityByName(cityName);
        } catch (RuntimeException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Wrong Geocoding API call");
        }

        if (foundCities != null && foundCities.isEmpty()) {
            processError("No city was found. Try once more!", context, resp);
            return;
        }

        context.setVariable("foundCities", foundCities);
        processTemplate("found-locations", context, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String cityName;
        BigDecimal latitude;
        BigDecimal longitude;

        try {
            cityName = req.getParameter("cityName");
            latitude = new BigDecimal(req.getParameter("latitude"));
            longitude = new BigDecimal(req.getParameter("longitude"));
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }

        Optional<String> guid = CookieHandler.getSessionCookie(req);

        if (guid.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        WeatherRequestDto weatherRequestDto = WeatherRequestDto.builder()
                .cityName(cityName)
                .latitude(latitude)
                .longitude(longitude)
                .build();
        try {
            cityWeatherService.saveCityToUserFavourites(weatherRequestDto, guid.get());
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private void processError(String error, WebContext context, HttpServletResponse resp) throws IOException {
        context.setVariable("errorMessage", error);
        processTemplate("found-locations", context, resp);
    }
}
