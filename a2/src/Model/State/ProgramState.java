package Model.State;

import Model.Container.*;
import Model.Statement.IStatement;
import Model.Value.IValue;

public class ProgramState {
    IDictionary<String, IValue> symTable;
    IList<IValue> out;
    IStack<IStatement> exeStack;
    IStatement originalProgram;

    public ProgramState(IStack<IStatement> stack, IDictionary<String, IValue> symT, IList<IValue> l, IStatement program) {
        exeStack = stack;
        symTable = symT;
        out = l;
        originalProgram = program;
        this.exeStack.push(program);
    }

    public ProgramState(IStatement program) {
        exeStack = new MyStack<IStatement>();
        symTable = new MyDictionary<String, IValue>();
        out = new MyList<IValue>();
        originalProgram = program;
        this.exeStack.push(program);
    }

    public IStack<IStatement> getExeStack() {
        return exeStack;
    }

    public IDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    public IList<IValue> getOut() {
        return out;
    }

    public void setExeStack(IStack<IStatement> s) {
        exeStack = s;
    }

    public void setSymTable(IDictionary<String, IValue> symT) {
        symTable = symT;
    }

    public void setOut(IList<IValue> l) {
        out = l;
    }

    public void setOriginalProgram(IStatement program) {
        originalProgram = program;
    }

    public void addStatement(IStatement statement) {
        exeStack.push(statement);
    }

    @Override
    public String toString() {
        return "ProgramState {\n\t" + "exeStack = " + exeStack.toString() + ",\n\tsymTable = " + symTable.toString() +
                ",\n\tout = " + out.toString() + ",\n\toriginalProgram = " + originalProgram.toString() +
                "\n}\n-----------------------------------------------\n";
    }
}
