package Model.Container;

import java.util.List;
import java.util.Vector;
import Exception.ContainerException;

public class MyList<T> implements IList<T> {
    List<T> list;

    public MyList() {
        list = new Vector<T>();
    }

    @Override
    public void add(T elem) {
        list.add(elem);
    }

    @Override
    public T get(int index) throws ContainerException {
        if(index < 0 || index >= list.size())
            throw new ContainerException("invalid index.\n");
        return list.get(index);
    }

    @Override
    public boolean empty() {
        return list.isEmpty();
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public String toString() {
        String stringRep = "{\n";

        for (T el:this.list) {
            stringRep += "\t\t" + el.toString()+ "\n";
        }
        stringRep += "\t}";
        return stringRep;
    }
}
