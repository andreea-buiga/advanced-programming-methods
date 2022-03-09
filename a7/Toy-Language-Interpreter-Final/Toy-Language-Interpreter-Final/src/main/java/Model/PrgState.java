package Model;
import Model.ADT.IDict;
import Model.ADT.IList;
import Model.ADT.ISemaphore;
import Model.ADT.IStack;
import Model.Statement.IStmt;
import Model.Value.IValue;
import Exceptions.StackEmptyException;
import Model.Value.IntValue;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.util.List;

public class PrgState {

    private static int threadCount = 0;
    private final int threadId;
    private IStack<IStmt> exeStack;
    private IDict<String, IValue> symTable;
    private IDict<String, BufferedReader> fileTable;
    private IList<IValue> out;
    private IDict<Integer, IValue> heapTable;
    private IStmt originalProgram; //optional field, but good to have

    private ISemaphore<Pair<Integer, List<Integer>>> semaphoreTable;

    private synchronized static int getThreadId() {
        threadCount ++;
        return threadCount - 1;
    }

    public PrgState(IStack<IStmt> _es, IDict<String, IValue> _symtbl, IList<IValue> _out, IStmt _og,
                    IDict<String, BufferedReader> _fileTable, IDict<Integer, IValue> _heap, ISemaphore<Pair<Integer,List<Integer>>> _semaphoreTable) {
        threadId = getThreadId();
        exeStack = _es;
        symTable = _symtbl;
        out = _out;
        originalProgram = _og;
        fileTable = _fileTable;
        heapTable = _heap;
        semaphoreTable = _semaphoreTable;
    }

    public int getId() { return this.threadId; }

    public IList<IValue> getOutput() {
        return out;
    }

    public IDict<String, IValue> getSymTable() {
        return this.symTable;
    }

    public IDict<Integer, IValue> getHeapTable() { return this.heapTable; }

    public IStack<IStmt> getStack() { return this.exeStack; }

    public IDict<String, BufferedReader> getFileTable() { return this.fileTable; }

    public ISemaphore<Pair<Integer, List<Integer>>> getSemaphoreTable() { return this.semaphoreTable; }

    public void setExeStack(IStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }

    public void setOutput(IList<IValue> output) {
        this.out = output;
    }

    public void setSymTable(IDict<String, IValue> symTable) {
        this.symTable = symTable;
    }

    public void setSemaphoreTable(ISemaphore<Pair<Integer, List<Integer>>> semaphoreTable) {
        this.semaphoreTable = semaphoreTable;
    }

    public PrgState oneStep() throws Exception{
        if (exeStack.isEmpty()) throw new StackEmptyException("PrgState stack is empty.");

        IStmt crtStmt = exeStack.pop();

        return crtStmt.execute(this);
    }

    public Boolean isNotCompleted() {
        return !exeStack.isEmpty();
    }


    public String toString() {
        return "Thread ID:" + threadId + "\n" + "Stack: " + exeStack.toString() +
                "\n" + "SymTable: " + symTable.toString() + "\n" +
                "Out: " + out.toString() + "\n" + "Heap: " +
                heapTable.toString() + "\n" + "Semaphore table\n" + semaphoreTable.toString() + '\n';
    }

    public String toFile() {
        return "----------------------------------------------------------------------------\n" +
                "Thread ID:" + threadId + "\n" +
                "ExeStack:" + exeStack.toFile() + "\n" +
                "\nSymTable: \n" + symTable.toFile() + "\n" +
                "Out: \n" + out.toFile() + "\n" +
                "FileTable: \n" + fileTable.toFile() + "\n" +
                "Heap: \n" + heapTable.toFile() +
                "Semaphore table\n" + semaphoreTable.toString() +"\n";
    }
}