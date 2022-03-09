package Model.Container;

import Exception.ContainerException;

public interface IDictionary<K, V> {
    void update(K id, V value);
    V lookup(K id) throws ContainerException;
    boolean isDefined(K id);
    void remove(K id);
}
