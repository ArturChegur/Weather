package chegur.mapper;

public interface Mapper<K, T> {
    T mapFrom(K object);
}
