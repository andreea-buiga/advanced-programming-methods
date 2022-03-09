package Repository;

import Model.ProgramState.ProgramState;
import Exception.ContainerException;
import Model.Statement.IStatement;

import java.io.IOException;

public interface IRepository {
    ProgramState getCurrentProgram() throws ContainerException;
    void logPrgStateExec()  throws ContainerException, IOException;
}
