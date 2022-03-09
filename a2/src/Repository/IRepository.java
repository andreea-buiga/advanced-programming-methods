package Repository;

import Model.State.ProgramState;
import Exception.ContainerException;
import Model.Statement.IStatement;

public interface IRepository {
    ProgramState getCurrentProgram() throws ContainerException;
    void addProgramState(IStatement statement);
    int nrOfPrograms();
}
