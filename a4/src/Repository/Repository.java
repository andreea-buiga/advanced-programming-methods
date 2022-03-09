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

public class Repository implements IRepository {
    ///private final MyList<ProgramState> ps_list;
    private final ProgramState programState;
    private final Path logFilePath;
    private PrintWriter logFile;

    public Repository(ProgramState _programState, String fileName) {
        programState = _programState;
        logFilePath = Paths.get(fileName).toAbsolutePath();
    }

    @Override
    public ProgramState getCurrentProgram() throws ContainerException {
        return programState;
    }

    @Override
    public void logPrgStateExec() throws ContainerException, IOException {
        logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath.toString(), true)));
        logFile.println(getCurrentProgram().toString());
        logFile.close();
    }
}
