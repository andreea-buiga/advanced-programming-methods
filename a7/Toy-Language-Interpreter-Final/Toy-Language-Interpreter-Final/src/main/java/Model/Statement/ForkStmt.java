package Model.Statement;

import Model.Type.IType;
import Model.PrgState;
import Model.ADT.IDict;
import Model.ADT.IStack;
import Model.ADT.MyStack;

public class ForkStmt implements IStmt{
    private final IStmt stmt;

    public ForkStmt(IStmt _stmt) {
        stmt = _stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {

        IStack<IStmt> newStack = new MyStack<>();
        newStack.push(stmt);

        return new PrgState(
                newStack, state.getSymTable().deepCopy(), state.getOutput(),
                stmt, state.getFileTable(), state.getHeapTable(), state.getSemaphoreTable());
    }

    @Override
    public String toString() {
        return "fork(" + stmt.toString() + ")";
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        stmt.typecheck(typeEnv.deepCopy());
        return typeEnv;
    }
}
