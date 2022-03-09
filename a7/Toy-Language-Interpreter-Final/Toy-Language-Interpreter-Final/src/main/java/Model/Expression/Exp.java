package Model.Expression;
import Model.Type.IType;
import Model.Value.IValue;
import Model.ADT.Heap;
import Model.ADT.IDict;

public abstract class Exp {

    public abstract IValue eval(IDict<String, IValue> symTable, Heap<Integer, IValue> heapTable) throws Exception;
    public abstract IType typecheck(IDict<String, IType> typeEnv) throws Exception;
    public abstract String toString();
}
