package chegur.dto.weather;

import lombok.*;

@Getter
@Setter
@Builder
public class WeatherRequestDto {
    private String cityName;
    private Double latitude;
    private Double longitude;
}
