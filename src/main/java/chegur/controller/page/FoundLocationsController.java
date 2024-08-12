package chegur.controller.page;

import chegur.controller.BaseController;
import chegur.dto.weather.GeocodingResponseDto;
import chegur.service.OpenWeatherService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.WebContext;

import java.io.IOException;
import java.util.List;

@WebServlet("/found-locations")
public class FoundLocationsController extends BaseController {
    private final OpenWeatherService openWeatherService = OpenWeatherService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        WebContext context = createWebContext(req, resp);
        String cityName = req.getParameter("cityName");

        if (cityName == null || cityName.isEmpty() || cityName.isBlank()) {
            processError("Fill up city name!", context, resp);
            return;
        }

        List<GeocodingResponseDto> foundCities = openWeatherService.getCityByName(cityName);

        if (foundCities.isEmpty()) {
            processError("No city was found. Try once more!", context, resp);
            return;
        }

        context.setVariable("foundCities", foundCities);
        processTemplate("found-locations", context, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //todo add to db by cookie city to user
    }

    private void processError(String error, WebContext context, HttpServletResponse resp) throws IOException {
        context.setVariable("errorMessage", error);
        processTemplate("found-locations", context, resp);
    }
}
