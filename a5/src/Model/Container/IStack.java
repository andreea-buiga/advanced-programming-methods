package Model.Container;

import Exception.ContainerException;

public interface IStack<T> {
    T pop() throws ContainerException;
    void push(T elem);
    boolean isEmpty();
}
