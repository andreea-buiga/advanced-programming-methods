package Model.Statement;

import Model.Container.IStack;
import Model.ProgramState.ProgramState;

public class CompoundStatement implements IStatement {
    IStatement first, second;

    public CompoundStatement(IStatement _first, IStatement _second) {
        first = _first;
        second = _second;
    }

    public IStatement getFirst() {
        return first;
    }

    public IStatement getSecond() {
        return second;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        IStack<IStatement> exeStack = state.getExeStack();
        exeStack.push(second);
        exeStack.push(first);
        return null;
    }

    @Override
    public String toString() {
        return "(" + first.toString() + ";" + second.toString() + ")";
    }
}
