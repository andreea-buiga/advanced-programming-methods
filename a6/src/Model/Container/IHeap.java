package Model.Container;

import java.util.HashMap;
import java.util.Map;

public interface IHeap<Integer, V> {
    void put(Integer key, V value);
    V get(Integer key);
    int getFreeAddress();
    Map<Integer ,V> getContent();
    void setContent(Map<Integer, V> content);
    int allocate(Integer key, V val);
}
