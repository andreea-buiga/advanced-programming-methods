package Repository;

import Model.ProgramState.ProgramState;
import Model.Container.MyList;
import Exception.ContainerException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private List<ProgramState> programStateList;
    private final Path logFilePath;
    private PrintWriter logFile;

    /*public Repository() {
        programStateList = new ArrayList<>();
    }
    */

    public Repository(List<ProgramState> _programStateList, String fileName) {
        programStateList = _programStateList;
        logFilePath = Paths.get(fileName).toAbsolutePath();
    }

    @Override
    public List<ProgramState> getProgramStateList() {
        return programStateList;
    }

    @Override
    public void setProgramStateList(List<ProgramState> programStateList) {
        this.programStateList = programStateList;
    }

    @Override
    public void logPrgStateExec(ProgramState programState) throws ContainerException, IOException {
        logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath.toString(), true)));
        logFile.println(programState.toString());
        logFile.close();
    }
}
