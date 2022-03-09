package Model.ADT;

import java.util.HashMap;
import java.util.Map;

public class Semaphore<V> implements ISemaphore<V> {
    private int address = 1;
    private HashMap<Integer, V> semaphoreTable;

    public Semaphore() {
        semaphoreTable = new HashMap<>();
    }

    @Override
    public Map getSemaphoreTable() {
        return semaphoreTable;
    }

    @Override
    public void put(int location, V value) {
        semaphoreTable.put(location, value);
    }

    @Override
    public int getLocation() {
        address++;
        return address-1;
    }

    @Override
    public boolean isDefined(int location) {
        return semaphoreTable.containsKey(location);
    }

    @Override
    public void setSemaphoreTable(Map newSemTable) {
        semaphoreTable = (HashMap<Integer, V>) newSemTable;
    }

    public String toString(){
        String s ="{";
        for (Integer key : semaphoreTable.keySet()){
            s += key + "->" + semaphoreTable.get(key).toString() + ";";
        }
        return s+"}";
    }
}
