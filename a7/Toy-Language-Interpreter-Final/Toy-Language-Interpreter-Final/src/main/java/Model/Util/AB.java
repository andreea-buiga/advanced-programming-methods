package Model.Util;

import Exceptions.StackEmptyException;
import Model.ADT.IStack;
import Model.ADT.MyStack;

public class AB {
    private Integer address = 1;
    private static IStack<Integer> freeAddress = new MyStack<>();

    public Integer getFreeAddress() throws StackEmptyException {
        return freeAddress.isEmpty() ? this.address++ : freeAddress.pop();
    }
}
