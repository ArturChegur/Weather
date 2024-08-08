package chegur.api;

public interface OpenWeatherAPI<T, V> {

    T makeCall(V entity);

}
