package Model.Statement;

import Exceptions.ExpressionException;
import Model.ADT.IDict;
import Model.ADT.ISemaphore;
import Model.ADT.IStack;
import Model.Expression.Exp;
import Model.PrgState;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Value.IValue;
import Model.Value.IntValue;
import javafx.util.Pair;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class AcquireStmt implements IStmt {
    Exp var;
    private static ReentrantLock lock = new ReentrantLock();

    @Override
    public String toString() {
        return "acquire(" + var.toString() + ")";
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        /*IType typeExp = var.typecheck(typeEnv);
        if(typeExp.equals(new IntType()))
            return typeEnv;
        else throw new ExpressionException("Acquire typechecker: var not type int");*/
        return typeEnv;
    }

    public AcquireStmt(Exp var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        lock.lock();
        IStack<IStmt> exeStack = state.getStack();
        IDict<String, IValue> symTable = state.getSymTable();
        ISemaphore<Pair<Integer, List<Integer>>> semaphoreTable = state.getSemaphoreTable();
        if (symTable.isDefined(var.toString())) {
            int foundIndex = ((IntValue) symTable.lookup(var.toString())).getValue();
            if (semaphoreTable.isDefined(foundIndex)){
                Pair<Integer, List<Integer>> semaphoreValue = (Pair) semaphoreTable.getSemaphoreTable().get(foundIndex);
                List<Integer> threads = semaphoreValue.getValue();
                Integer nr_max = semaphoreValue.getKey();
                int threads_len = threads.size();
                if(nr_max != threads_len){
                    if(threads.contains(state.getId())) {
                        throw new Exception("already in process.\n");
                    }
                    threads.add(state.getId());
                    state.getSemaphoreTable().put(foundIndex, new Pair<>(nr_max, threads));
                }
                else {
                    exeStack.push(this);
                    state.setExeStack(exeStack);
                }
            }
            else
                throw new ExpressionException("Acquire-index not in sem table");
        }
        else
            throw new ExpressionException("Acquire - variable not defined");
        lock.unlock();
        return null;
    }
}
