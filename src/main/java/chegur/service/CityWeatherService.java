package chegur.service;

import chegur.api.GeoCodingClientAPI;
import chegur.api.OpenWeatherClientAPI;
import chegur.dao.impl.LocationDao;
import chegur.dao.impl.UserDao;
import chegur.dto.GeocodingResponseDto;
import chegur.dto.OpenWeatherResponseDto;
import chegur.dto.WeatherRequestDto;
import chegur.entity.Location;
import chegur.entity.User;
import chegur.exception.ConnectionErrorException;
import chegur.exception.LocationException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.HibernateException;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CityWeatherService {
    private final static CityWeatherService INSTANCE = new CityWeatherService();

    private final GeoCodingClientAPI geoCodingClientAPI = GeoCodingClientAPI.getInstance();
    private final OpenWeatherClientAPI openWeatherClientAPI = OpenWeatherClientAPI.getInstance();
    private final LocationDao locationDao = LocationDao.getInstance();
    private final SessionService sessionService = SessionService.getInstance();
    private final UserDao userDao = UserDao.getInstance();

    public List<GeocodingResponseDto> getCityByName(String cityName) {
        try {
            WeatherRequestDto weatherRequestDto = WeatherRequestDto.builder()
                    .cityName(URLEncoder.encode(cityName, Charset.defaultCharset()))
                    .build();

            return geoCodingClientAPI.makeCall(weatherRequestDto);
        } catch (ConnectionErrorException e) {
            throw new RuntimeException("Wrong Geocoding API call");
        }
    }

    public List<OpenWeatherResponseDto> getWeatherInCities(String guid) {
        User user = sessionService.getSessionUser(guid);

        List<Location> locations = user.getLocations();
        List<OpenWeatherResponseDto> response = new LinkedList<>();

        try {
            for (Location location : locations) {
                WeatherRequestDto weatherRequestDto = WeatherRequestDto.builder()
                        .cityId(location.getId())
                        .cityName(location.getName())
                        .latitude(location.getLatitude())
                        .longitude(location.getLongitude())
                        .build();

                response.add(openWeatherClientAPI.makeCall(weatherRequestDto));
            }
        } catch (ConnectionErrorException e) {
            throw new RuntimeException("Wrong Weather API call");
        }

        return response;
    }

    public void saveCityToUserFavourites(WeatherRequestDto weatherRequestDto, String guid) {
        User user = sessionService.getSessionUser(guid);

        Location location = addLocation(weatherRequestDto, user);

        try {
            userDao.addFavouriteLocation(user, location);
        } catch (HibernateException e) {
            throw new RuntimeException("Can not save city");
        }
    }

    public void deleteCityFromFavourites(WeatherRequestDto weatherRequestDto, String guid) {
        User user = sessionService.getSessionUser(guid);

        try {
            userDao.deleteFavouriteLocation(user.getId(), weatherRequestDto.getCityId());
        } catch (LocationException e) {
            throw new RuntimeException("Can not delete city");
        }
    }

    private Location addLocation(WeatherRequestDto weatherRequestDto, User user) {
        Location location = Location.builder()
                .name(weatherRequestDto.getCityName())
                .author(user)
                .latitude(weatherRequestDto.getLatitude())
                .longitude(weatherRequestDto.getLongitude())
                .build();

        try {
            locationDao.save(location);
        } catch (LocationException ignored) {
        }

        return location;
    }


    public static CityWeatherService getInstance() {
        return INSTANCE;
    }
}
