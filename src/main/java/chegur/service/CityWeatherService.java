package chegur.service;

import chegur.api.GeoCodingAPI;
import chegur.api.OneCallAPI;
import chegur.dao.impl.LocationDao;
import chegur.dao.impl.UserDao;
import chegur.dto.GeocodingResponseDto;
import chegur.dto.OneCallResponseDto;
import chegur.dto.WeatherRequestDto;
import chegur.entity.Location;
import chegur.entity.User;
import chegur.exception.ConnectionErrorException;
import chegur.exception.LocationExistsException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.HibernateException;

import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CityWeatherService {
    private final static CityWeatherService INSTANCE = new CityWeatherService();

    private final GeoCodingAPI geoCodingAPI = GeoCodingAPI.getInstance();
    private final OneCallAPI oneCallAPI = OneCallAPI.getInstance();
    private final LocationDao locationDao = LocationDao.getInstance();
    private final SessionService sessionService = SessionService.getInstance();
    private final UserDao userDao = UserDao.getInstance();

    public List<GeocodingResponseDto> getCityByName(String cityName) {
        try {
            WeatherRequestDto weatherRequestDto = WeatherRequestDto.builder()
                    .cityName(cityName.replaceAll(" ", "+"))
                    .build();

            return geoCodingAPI.makeCall(weatherRequestDto);
        } catch (ConnectionErrorException e) {
            throw new RuntimeException(e);
        }
    }

    public List<OneCallResponseDto> getWeatherInCities(String guid) {
        User user = sessionService.getSessionUser(guid);

        List<Location> locations = user.getLocations();
        List<OneCallResponseDto> response = new LinkedList<>();

        try {
            for (Location location : locations) {
                WeatherRequestDto weatherRequestDto = WeatherRequestDto.builder()
                        .cityId(location.getId())
                        .cityName(location.getName())
                        .latitude(location.getLatitude())
                        .longitude(location.getLongitude())
                        .build();

                response.add(oneCallAPI.makeCall(weatherRequestDto));
            }
        } catch (ConnectionErrorException e) {
            throw new RuntimeException();
        }

        response.forEach(city -> {
            city.getMain().setTemp(city.getMain().getTemp() - 273.15);
            city.getMain().setFeelsLike(city.getMain().getFeelsLike() - 273.15);
            city.getMain().setTempMax(city.getMain().getTempMax() - 273.15);
            city.getMain().setTempMin(city.getMain().getTempMin() - 273.15);
        });

        return response;
    }

    public void saveCityToUserFavourites(WeatherRequestDto weatherRequestDto, String guid) {
        User user = sessionService.getSessionUser(guid);

        Location location = addLocation(weatherRequestDto, user);

        try {
            userDao.addFavouriteLocation(user, location);
        } catch (HibernateException e) {
            throw new RuntimeException();
        }
    }

    public void deleteCityFromFavourites(WeatherRequestDto weatherRequestDto, String guid) {
        User user = sessionService.getSessionUser(guid);
        userDao.deleteFavouriteLocation(user.getId(), weatherRequestDto.getCityId());
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
        } catch (LocationExistsException ignored) {
        }

        return location;
    }


    public static CityWeatherService getInstance() {
        return INSTANCE;
    }
}
