package Model.Container;

import java.util.Stack;
import Exception.ContainerException;
import Model.Statement.CompoundStatement;
import Model.Statement.IStatement;

public class MyStack<T> implements IStack<T> {
    Stack<T> stack;

    public MyStack() {
        stack = new Stack<T>();
    }

    @Override
    public T pop() throws ContainerException {
        return stack.pop();
    }

    @Override
    public void push(T elem) {
        stack.push(elem);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public String inOrder(IStatement stmt) {
        if(stmt instanceof CompoundStatement) {
            String returnString = "";
            CompoundStatement compStmt = (CompoundStatement) stmt;
            IStatement left = compStmt.getFirst();
            IStatement right = compStmt.getSecond();
            if(left instanceof CompoundStatement) {
                returnString += inOrder(left) + "\n\t\t";
            }
            else {
                returnString += left.toString() + "\n\t\t";
            }
            if(right instanceof CompoundStatement) {
                returnString += inOrder(right) + "\n\t\t";
            }
            else {
                returnString += right.toString() + "\n\t\t";
            }

            return returnString;
        }
        else {
            return stmt.toString();
        }
    }

    @Override
    public String toString() {
        //return stack.toString();
        StringBuilder stringRep = new StringBuilder();
        Stack<T> printStack = new Stack<>();
        printStack.addAll(this.stack);

        for(T elem : printStack) {
            if(elem instanceof IStatement) {
                stringRep.append(inOrder((IStatement) elem));
            }
            else {
                stringRep.append(elem.toString());
            }
        }

        return stringRep.toString();
    }
}
