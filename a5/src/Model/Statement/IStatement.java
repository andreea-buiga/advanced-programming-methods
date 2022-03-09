package Model.Statement;

import Model.ProgramState.ProgramState;
import Exception.*;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IStatement {
    ProgramState execute(ProgramState state) throws ContainerException, ExpressionException, StatementException, IOException;
}
