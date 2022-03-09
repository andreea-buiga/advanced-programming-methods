package Model.Statement;

import Model.ADT.IDict;
import Model.ADT.IStack;
import Model.PrgState;
import Model.Type.IType;

public class SleepStmt implements IStmt {
    private Integer value;

    public SleepStmt(Integer value){
        this.value = value;
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        return typeEnv;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        if (value != 0) {
            IStack<IStmt> executionStack = state.getStack();
            executionStack.push(new SleepStmt(value - 1));
            state.setExeStack(executionStack);
        }
        return null;
    }

    @Override
    public String toString(){
        return "sleep(" + value.toString() + ")";
    }
}
