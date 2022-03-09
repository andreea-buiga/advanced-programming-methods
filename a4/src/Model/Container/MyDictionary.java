package Model.Container;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import Exception.ContainerException;

public class MyDictionary<K, V> implements IDictionary<K, V> {
    HashMap<K, V> dictionary;

    public MyDictionary() {
        dictionary = new HashMap<K, V>();
    }

    @Override
    public void update(K id, V value) {
        dictionary.put(id, value);
    }

    @Override
    public V lookup(K id) throws ContainerException {
        if(!dictionary.containsKey(id))
            throw new ContainerException("key doesn't exist.\n");
        return dictionary.get(id);
    }

    @Override
    public boolean isDefined(K id) {
        return dictionary.containsKey(id);
    }

    @Override
    public void remove(K id) {
        dictionary.remove(id);
    }

    @Override
    public Collection<V> values() {
        return dictionary.values();
    }

    @Override
    public Map<K, V> getContent() {
        return dictionary;
    }

    @Override
    public String toString() {
        String stringRep ="{\n";
        for (K key : dictionary.keySet())
            stringRep += "\t\t" + key.toString() + " -> " + dictionary.get(key).toString() + "\n";
        stringRep += "\t}";
        return stringRep;
    }
}
