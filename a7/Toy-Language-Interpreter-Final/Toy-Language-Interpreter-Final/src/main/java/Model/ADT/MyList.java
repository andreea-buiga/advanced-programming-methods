package Model.ADT;

import Exceptions.EmptyListException;
import Exceptions.ListException;
import Exceptions.OutOfBoundsException;

import java.util.*;

public class MyList<T> implements IList<T> {
    private List<T> list;

    public MyList() {
        list = new ArrayList<T>();
    }

    public MyList(List<T> _list) { list = new ArrayList<T>(_list); }

    @Override
    public void add(T v) {
        this.list.add(v);
    }

    @Override
    public T pop() throws Exception{
        if (this.size() == 0) {
            throw new EmptyListException("The list is empty");
        }

        return this.list.remove(list.size() - 1);
    }

    @Override
    public T peek() throws Exception {
        if (this.size() == 0) {
            throw new EmptyListException("The list is empty");
        }

        return this.list.get(list.size() - 1);
    }

    @Override
    public int size() { return this.list.size(); }

    @Override
    public Iterator<T> getIterator() {
        return list.iterator();
    }

    @Override
    public T getValue(int position) throws Exception {
        if (position > this.size()) {
            throw new ListException("The position does not exists.");
        }
        return this.list.get(position);
    }

    @Override
    public boolean empty() {
        return this.list.isEmpty();
    }

    @Override
    public void clear(){
        this.list.clear();
    }

    @Override
    public String toString() {
        return this.list.toString();
    }

    @Override
    public String toFile() {
        String result = "";
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            result += iterator.next() + "\n";
        }
        return result;
    }

    @Override
    public T get(int index) throws Exception{
        if (index >= this.list.size()) {
            throw new OutOfBoundsException("Trying to access uninitialized memory.");
        }

        return this.list.get(index);
    }

    @Override
    public List<T> getAll() {
        return this.list;
    }
}
