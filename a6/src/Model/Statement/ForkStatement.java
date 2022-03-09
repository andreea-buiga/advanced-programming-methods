package Model.Statement;

import Model.Container.IDictionary;
import Model.Container.MyStack;
import Model.ProgramState.ProgramState;
import Exception.*;
import Model.Type.IType;

import java.io.IOException;

public class ForkStatement implements IStatement {
    private IStatement statement;

    public ForkStatement(IStatement _statement) {
        statement = _statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ContainerException, ExpressionException, StatementException, IOException {
        return new ProgramState(new MyStack<>(), state.getHeap(), state.getSymTable(),
                state.getOut(), this.statement, state.getFileTable());
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws StatementException, ContainerException, ExpressionException {
        statement.typecheck(typeEnv.cloneDict());
        return typeEnv;
    }

    @Override
    public String toString() {
        return "fork(" + this.statement.toString() + ")";
    }
}
