package Model.Container;

import Exception.ContainerException;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface IDictionary<K, V> {
    void update(K id, V value);
    V lookup(K id) throws ContainerException;
    boolean isDefined(K id);
    void remove(K id);
    Map<K,V> getContent();
    Collection<V> values();
    V put(K key, V value);
    Set<K> keySet();
    IDictionary<K, V> clone_dict();
}
