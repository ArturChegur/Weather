package chegur.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class OneCallResponseDto {
    @JsonIgnore
    private int cityId;

    @JsonIgnore
    private String cityName;

    @JsonProperty("coord")
    private Coord coord;

    @JsonProperty("wind")
    private Wind wind;

    @JsonProperty("main")
    private Main main;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    @ToString
    public static class Coord {

        @JsonProperty("lon")
        private double lon;

        @JsonProperty("lat")
        private double lat;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    @ToString
    public static class Wind {

        @JsonProperty("speed")
        private double speed;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    @ToString
    public static class Main {

        @JsonProperty("temp")
        private double temp;

        @JsonProperty("feels_like")
        private double feelsLike;

        @JsonProperty("temp_min")
        private double tempMin;

        @JsonProperty("temp_max")
        private double tempMax;

        @JsonProperty("pressure")
        private int pressure;

        @JsonProperty("humidity")
        private int humidity;
    }
}