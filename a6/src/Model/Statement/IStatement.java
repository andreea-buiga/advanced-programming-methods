package Model.Statement;

import Model.Container.IDictionary;
import Model.ProgramState.ProgramState;
import Exception.*;
import Model.Type.IType;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IStatement {
    ProgramState execute(ProgramState state) throws ContainerException, ExpressionException, StatementException, IOException;
    IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws StatementException, ContainerException, ExpressionException;
}
