package ku.cs.models;

public interface Filterer<T> {
    boolean filter(T o);
}
