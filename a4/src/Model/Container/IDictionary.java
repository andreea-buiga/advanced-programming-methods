package Model.Container;

import Exception.ContainerException;

import java.util.Collection;
import java.util.Map;

public interface IDictionary<K, V> {
    void update(K id, V value);
    V lookup(K id) throws ContainerException;
    boolean isDefined(K id);
    void remove(K id);
    Map<K,V> getContent();
    Collection<V> values();
}
