package chegur.api.impl;

import chegur.api.OpenWeatherAPI;
import chegur.dto.weather.WeatherRequestDto;
import chegur.dto.weather.GeocodingResponseDto;
import chegur.exception.ConnectionErrorException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GeoCodingAPI implements OpenWeatherAPI<GeocodingResponseDto, WeatherRequestDto> {
    private static final GeoCodingAPI INSTANCE = new GeoCodingAPI();

    private static final String BASE_URL = "http://api.openweathermap.org/geo/1.0/direct?q=";
    private static final String LIMIT_AND_TOKEN = "&limit=10&appid=";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<GeocodingResponseDto> makeCall(WeatherRequestDto entity) {
        HttpResponse<String> resp;
        HttpClient client = HttpClient.newHttpClient();

        String token = System.getenv("API_TOKEN");

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + entity.getCityName() + LIMIT_AND_TOKEN + token))
                .GET()
                .build();

        try {
            resp = client.send(req, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException | IOException e) {
            throw new ConnectionErrorException();
        }

        try {
            return objectMapper.readValue(resp.body(), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Internal server error");
        }
    }

    public static GeoCodingAPI getInstance() {
        return INSTANCE;
    }
}
