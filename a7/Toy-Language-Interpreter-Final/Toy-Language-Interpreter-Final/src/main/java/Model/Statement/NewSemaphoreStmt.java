package Model.Statement;

import Exceptions.ExpressionException;
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class NewSemaphoreStmt implements IStmt {
    Exp var;
    Exp exp1;
    private static ReentrantLock lock = new ReentrantLock();

    @Override
    public String toString() {
        return "newSemaphore(" + var.toString() + ", " + exp1.toString() + ")";
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        IType typeVar = var.typecheck(typeEnv);
        if(typeVar.equals(new IntType())) {
            IType typeExp = exp1.typecheck(typeEnv);
            if (typeExp.equals(new IntType())){
                return typeEnv;
            }
            else
                throw new Exception("Create sem typechecker: exp not type int");
        }
        else
            throw new Exception("Create sem typechecker: var not type int");
    }

    public NewSemaphoreStmt(Exp var, Exp exp1) {
        this.var = var;
        this.exp1 = exp1;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        lock.lock();
        IDict<String, IValue> symTable = state.getSymTable();
        Heap<Integer, IValue> heap = (Heap<Integer, IValue>)state.getHeapTable();
        ISemaphore<Pair<Integer, List<Integer>>> semaphoreTable = state.getSemaphoreTable();
        IValue expValue = exp1.eval(symTable, heap);
        if (expValue.getType().equals(new IntType())){
            int actualValue = ((IntValue)expValue).getValue();
            int location = semaphoreTable.getLocation();
            semaphoreTable.put(location,new Pair<>(actualValue,new ArrayList<>()));
            String varValue = var.toString();
            state.setSemaphoreTable(semaphoreTable);
            state.setSymTable(symTable);

            if (symTable.isDefined(varValue) && var.eval(symTable,heap).getType().equals(new IntType())){
                symTable.update(varValue,new IntValue(location));
            }
            else
                throw new Exception("Create semaphore: variable not defined/ not of type int");
        }
        else
            throw new Exception("Create semaphore: wrong type of expression value");
        lock.unlock();
        return null;
    }
}
