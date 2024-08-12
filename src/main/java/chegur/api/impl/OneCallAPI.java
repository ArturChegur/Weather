package chegur.api.impl;

import chegur.api.OpenWeatherAPI;
import chegur.dto.weather.WeatherRequestDto;
import chegur.dto.weather.GeocodingResponseDto;

import java.util.List;

public class OneCallAPI implements OpenWeatherAPI<WeatherRequestDto, GeocodingResponseDto> {

    @Override
    public List<WeatherRequestDto> makeCall(GeocodingResponseDto entity) {
        return null;
    }
}
