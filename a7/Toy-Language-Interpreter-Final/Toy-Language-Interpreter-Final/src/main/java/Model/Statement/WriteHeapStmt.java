package Model.Statement;

import Model.Type.RefType;
import Model.Value.IValue;
import Model.Value.RefValue;
import Exceptions.HeapException;
import Exceptions.UndefinedException;
import Exceptions.VariableTypeException;
import Model.PrgState;
import Model.ADT.Heap;
import Model.ADT.IDict;
import Model.Expression.Exp;
import Model.Type.IType;

public class WriteHeapStmt implements IStmt{
    private final String variableName;
    private final Exp exp;

    public WriteHeapStmt(String _variableName, Exp _exp) {
        this.variableName = _variableName;
        this.exp = _exp;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IDict<String, IValue> symTbl = state.getSymTable();
        Heap<Integer, IValue> heapTbl = (Heap<Integer, IValue>) state.getHeapTable();

        if (symTbl.isDefined(variableName)) {
            IValue val = symTbl.lookup(variableName);
            if (val.getType() instanceof RefType) {
                int address = ((RefValue)val).getAddr();
                if (heapTbl.isDefined(address)) {
                    IValue evalExp = exp.eval(symTbl, heapTbl);
                    IType locationType = ((RefValue) val).getLocType();
                    if (evalExp.getType().equals(locationType)) {
                        heapTbl.update(address, evalExp);
                    } else throw new HeapException("Invalid type for the variable name and the heap");
                } else throw new HeapException("Uninitialized address memory");
            }else throw new VariableTypeException("The variable type must be RefType.");
        } else throw new UndefinedException("Variable undefined");

        return null;
    }

    @Override
    public String toString() {
        return "wH(" + variableName + " = " + exp.toString() + ")";
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        IType typeVar = typeEnv.lookup(variableName);
        IType typeExp = exp.typecheck(typeEnv);
        if (typeVar.equals(new RefType(typeExp))) {
            return typeEnv;
        } else throw new VariableTypeException("WriteHeap stmt: right hand side and left hand side have different types.");
    }
}
