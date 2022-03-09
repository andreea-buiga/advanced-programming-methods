package Model.Statement;


import Model.Type.IType;
import Model.Value.IValue;
import Model.PrgState;
import Model.ADT.Heap;
import Model.ADT.IDict;
import Model.ADT.IList;
import Model.Expression.Exp;

public class PrintStmt implements IStmt{

    private final Exp expression;

    public PrintStmt(Exp exp){
        this.expression = exp;
    }

    @Override
    public String toString() {
        return "print(" + this.expression.toString() + ")";
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        expression.typecheck(typeEnv);

        return typeEnv;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception{
        IList<IValue> output = state.getOutput();
        Heap<Integer, IValue> heapTbl = (Heap<Integer, IValue>) state.getHeapTable();

        output.add(expression.eval(state.getSymTable(), heapTbl));
        state.setOutput(output);
        return null;
    }


}
