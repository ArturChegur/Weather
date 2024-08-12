package chegur.service;

import chegur.api.impl.GeoCodingAPI;
import chegur.dto.weather.GeocodingResponseDto;
import chegur.dto.weather.WeatherRequestDto;
import chegur.exception.ConnectionErrorException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OpenWeatherService {
    private final static OpenWeatherService INSTANCE = new OpenWeatherService();
    private final GeoCodingAPI geoCodingAPI = GeoCodingAPI.getInstance();

    public List<GeocodingResponseDto> getCityByName(String cityName) {
        try {
            WeatherRequestDto weatherRequestDto = WeatherRequestDto.builder()
                    .cityName(cityName.replaceAll(" ", "+"))
                    .build();

            return geoCodingAPI.makeCall(weatherRequestDto);
        } catch (ConnectionErrorException ignored) {
        }

        return List.of();
    }

    public static OpenWeatherService getInstance() {
        return INSTANCE;
    }
}
