package Model.Container;

import java.util.HashMap;
import java.util.Map;

public class MyHeap<Integer, V> implements IHeap<Integer, V> {
    private HashMap<Integer, V> heap;
    int freeAddress;

    public MyHeap() {
        heap = new HashMap<Integer, V>();
        freeAddress = 1;
    }

    @Override
    public int allocate(Integer key, V val) {
        heap.put(key, val);
        freeAddress = freeAddress + 1;
        return freeAddress - 1;
    }

    public void setFreeAddress(int freeAddress) {
        this.freeAddress = freeAddress;
    }

    public int getFreeAddress() {
        return freeAddress;
    }

    @Override
    public Map<Integer, V> getContent() {
        return heap;
    }

    @Override
    public void setContent(Map<Integer, V> content) {
        heap = (HashMap<Integer, V>) content;
    }

    @Override
    public void put(Integer key, V value) {
        this.heap.put(key, value);
    }

    @Override
    public V get(Integer key) {
        return this.heap.get(key);
    }

    @Override
    public String toString() {
        String stringRep ="{\n";
        for(Integer key : this.heap.keySet()){
            stringRep += "\t\t" + key.toString() + " -> " + heap.get(key).toString() + "\n";
        }
        stringRep += "\t}";
        return stringRep;
    }
}
