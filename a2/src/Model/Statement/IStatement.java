package Model.Statement;

import Model.State.ProgramState;
import Exception.*;

public interface IStatement {
    ProgramState execute(ProgramState state) throws ContainerException, ExpressionException, StatementException;
}
