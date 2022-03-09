package Model.Expression;
import Model.Type.IType;
import Model.Value.IValue;
import Model.ADT.Heap;
import Model.ADT.IDict;

public class VarExp extends Exp{
    String id;

    public VarExp(String id){
        this.id = id;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, Heap<Integer, IValue> heapTable) throws Exception {
        return symTable.lookup(id);
    }

    @Override
    public IType typecheck(IDict<String, IType> typeEnv) throws Exception {
        return typeEnv.lookup(id);
    }

    public String toString() {
        return id;
    }

}
