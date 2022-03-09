package Model.Statement;

import Model.ADT.Heap;
import Model.ADT.IDict;
import Model.ADT.ISemaphore;
import Model.Expression.Exp;
import Model.PrgState;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Value.IValue;
import Model.Value.IntValue;
import javafx.util.Pair;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class ReleaseStmt implements IStmt {
    Exp var;
    private static ReentrantLock lock = new ReentrantLock();

    @Override
    public String toString() {
        return "release(" + var.toString() + ")";
    }

    public ReleaseStmt(Exp var) {
        this.var = var;
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        IType typeExp = var.typecheck(typeEnv);
        if(typeExp.equals(new IntType()))
            return typeEnv;
        else throw new Exception("Release typechecker: var not type int");
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        lock.lock();
        IDict<String, IValue> symTable = state.getSymTable();
        Heap<Integer, IValue> heap = (Heap<Integer, IValue>)state.getHeapTable();
        ISemaphore semaphoreTable = state.getSemaphoreTable();
        if (symTable.isDefined(var.toString())) {
            int foundIndex = ((IntValue) symTable.lookup(var.toString())).getValue();
            if (semaphoreTable.isDefined(foundIndex)){
                Pair<Integer, List<Integer>> semaphoreValue  = (Pair) semaphoreTable.getSemaphoreTable().get(foundIndex);
                List<Integer> threads = semaphoreValue.getValue();
                int nrMax = (int) semaphoreValue.getKey();
                if(threads.contains(state.getId())){
                    threads.remove(state.getId());
                }
                state.getSemaphoreTable().put(foundIndex, new Pair<>(nrMax, threads));
            }
            else
                throw new Exception("Release-index not in sem table");
        }
        else
            throw new Exception("Release - variable not defined");
        lock.unlock();
        return null;
    }
}
