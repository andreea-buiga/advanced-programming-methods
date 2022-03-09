package Model.ProgramState;

import Model.Container.*;
import Model.Statement.IStatement;
import Model.Value.IValue;

import java.io.BufferedReader;

public class ProgramState {
    IDictionary<String, IValue> symTable;
    IList<IValue> out;
    IStack<IStatement> exeStack;
    IStatement originalProgram;
    IDictionary<String, BufferedReader> fileTable;

    public ProgramState(IStack<IStatement> stack, IDictionary<String, IValue> symT, IList<IValue> l, IStatement program, IDictionary<String, BufferedReader> fileT) {
        exeStack = stack;
        symTable = symT;
        out = l;
        /// originalProgram = program;
        this.fileTable = fileT;
        this.exeStack.push(program);
    }

    public ProgramState(IStatement program) {
        exeStack = new MyStack<IStatement>();
        symTable = new MyDictionary<String, IValue>();
        out = new MyList<IValue>();
        originalProgram = program;
        fileTable = new MyDictionary<String, BufferedReader>();
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

    public IDictionary<String, BufferedReader> getFileTable() {
        return fileTable;
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

    public void setFileTable(IDictionary<String, BufferedReader> _fileTable) {
        fileTable = _fileTable;
    }

    public void addStatement(IStatement statement) {
        exeStack.push(statement);
    }

    @Override
    public String toString() {
        return "ProgramState {" +
                "\n\t• exeStack: " + exeStack.toString() +
                "\n\t• symTable: " + symTable.toString() +
                "\n\t• out: " + out.toString() +
                "\n\t• fileTable: " + fileTable.toString() +
                //"\n\toriginalProgram: " + originalProgram.toString() +
                "\n}\n-----------------------------------------------\n";
    }
}
