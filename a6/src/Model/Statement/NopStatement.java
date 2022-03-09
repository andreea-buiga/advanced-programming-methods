package Model.Statement;

import Model.Container.IDictionary;
import Model.ProgramState.ProgramState;
import Model.Type.IType;
import Exception.*;

public class NopStatement implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) {
        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws StatementException, ContainerException, ExpressionException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "";
    }
}
