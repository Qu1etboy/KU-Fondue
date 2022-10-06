package ku.cs.services.collection;

public interface Filterer<T> {
    boolean filter(T o);
}
