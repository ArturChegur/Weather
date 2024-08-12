package chegur.api;

import java.util.List;

public interface OpenWeatherAPI<T, V> {

    List<T> makeCall(V entity);

}
