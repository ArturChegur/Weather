package chegur.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class WeatherRequestDto {
    private int cityId;

    private String cityName;

    private BigDecimal latitude;

    private BigDecimal longitude;
}
