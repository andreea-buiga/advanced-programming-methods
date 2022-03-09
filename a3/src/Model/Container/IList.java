package Model.Container;

import Exception.ContainerException;

public interface IList<T> {
    void add(T elem);
    T get(int index) throws ContainerException;
    boolean empty();
    void clear();
    int size();
}
