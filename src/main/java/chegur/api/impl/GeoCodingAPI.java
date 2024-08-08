package chegur.api.impl;

import chegur.api.OpenWeatherAPI;
import chegur.dto.weather.WeatherRequestDto;
import chegur.dto.weather.WeatherResponseDto;

public class GeoCodingAPI implements OpenWeatherAPI<WeatherRequestDto, WeatherResponseDto> {

    @Override
    public WeatherRequestDto makeCall(WeatherResponseDto entity) {
        return null;
    }
}
