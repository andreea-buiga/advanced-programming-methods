package Model.ProgramState;

import Model.Container.*;
import Model.Statement.IStatement;
import Model.Value.IValue;
import Exception.*;

import java.io.BufferedReader;
import java.io.IOException;

public class ProgramState {
    IDictionary<String, IValue> symTable;
    IList<IValue> out;
    IStack<IStatement> exeStack;
    IStatement originalProgram;
    IDictionary<String, BufferedReader> fileTable;
    IHeap<Integer, IValue> heap;
    private int id;
    private static int globalID = 1;

    public ProgramState(IStack<IStatement> stack, IHeap<Integer, IValue> _heap,
                        IDictionary<String, IValue> symT, IList<IValue> l, IStatement program,
                        IDictionary<String, BufferedReader> fileT) {
        exeStack = stack;
        heap = _heap;
        symTable = symT;
        out = l;
        /// originalProgram = program;
        this.fileTable = fileT;
        id = getGlobalID();
        this.exeStack.push(program);
    }

    public ProgramState(IStatement program) {
        exeStack = new MyStack<IStatement>();
        heap = new MyHeap<Integer, IValue>();
        symTable = new MyDictionary<String, IValue>();
        out = new MyList<IValue>();
        originalProgram = program;
        id = 1;
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

    public IHeap<Integer, IValue> getHeap() {
        return heap;
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

    public void setHeap(IHeap<Integer, IValue> _heap) {
        heap = _heap;
    }

    public  Integer getId() {
        return id;
    }

    public  void setId(Integer _id) {
        id = _id;
    }

    public synchronized int getGlobalID() {
        globalID *= 10;
        return globalID;
    }

    public void addStatement(IStatement statement) {
        exeStack.push(statement);
    }

    public boolean isNotCompleted() {
        return !exeStack.isEmpty();
    }

    public ProgramState oneStep() throws ContainerException, StatementException, IOException, ExpressionException {
        if(exeStack.isEmpty()) {
            throw new StatementException("prgstate stack is empty.\n");
        }
        IStatement crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }

    @Override
    public String toString() {
        return "Thread number id: " + id +
                "\n\t• exeStack: " + exeStack.toString() +
                "\n\t• heap: " + heap.toString() +
                "\n\t• symTable: " + symTable.toString() +
                "\n\t• out: " + out.toString() +
                "\n\t• fileTable: " + fileTable.toString() +
                //"\n\toriginalProgram: " + originalProgram.toString() +
                "\n}\n-----------------------------------------------\n";
    }
}
