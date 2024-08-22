package chegur.api;

import chegur.dto.WeatherRequestDto;
import chegur.dto.GeocodingResponseDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GeoCodingClientAPI {
    private static final GeoCodingClientAPI INSTANCE = new GeoCodingClientAPI();

    private static final String BASE_URL = "https://api.openweathermap.org/geo/1.0/direct?q=";
    private static final String LIMIT_AND_TOKEN = "&limit=10&appid=";

    private static final ObjectMapper objectMapper = new ObjectMapper();

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
            return objectMapper.readValue(resp.body(), new TypeReference<>() {});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static GeoCodingClientAPI getInstance() {
        return INSTANCE;
    }
}
