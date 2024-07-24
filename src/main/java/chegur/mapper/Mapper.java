package chegur.mapper;

@FunctionalInterface
public interface Mapper<K, T> {
    T mapFrom(K object);
}
