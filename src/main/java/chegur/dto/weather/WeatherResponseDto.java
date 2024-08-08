package chegur.dto.weather;

import lombok.*;

@Getter
@Setter
@Builder
public class WeatherResponseDto {
    private String cityName;
    private Double latitude;
    private Double longitude;
    private Double temperature;
    private Double feelsLikeTemperature;
    private Double pressure;
    private Double humidity;
    private Double visibility;
    private Double windSpeed;
}
