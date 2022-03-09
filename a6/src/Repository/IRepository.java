package Repository;

import Model.ProgramState.ProgramState;
import Exception.ContainerException;
import Model.Statement.IStatement;

import java.io.IOException;
import java.util.List;

public interface IRepository {
    List<ProgramState> getProgramStateList();
    void setProgramStateList(List<ProgramState> programStateList);
    void logPrgStateExec(ProgramState programState) throws ContainerException, IOException;
}
