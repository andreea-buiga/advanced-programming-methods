package Model.ADT;

import java.util.Map;

public interface ISemaphore<V> {
    String toString();
    Map<Integer,V> getSemaphoreTable();
    void setSemaphoreTable(Map<Integer,V> newSemTable);
    void put(int location, V value);
    int getLocation();
    boolean isDefined(int location);
}