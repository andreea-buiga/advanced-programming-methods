package Repository;

import Model.State.ProgramState;
import Model.Container.MyList;
import Exception.ContainerException;
import Model.Statement.IStatement;

public class Repository implements IRepository {
    MyList<ProgramState> ps_list;

    public Repository() {
        ps_list = new MyList<ProgramState>();
    }

    @Override
    public ProgramState getCurrentProgram() throws ContainerException {
        return ps_list.get(0);
    }

    @Override
    public void addProgramState(IStatement statement) {
        ps_list.add(new ProgramState(statement));
    }

    @Override
    public int nrOfPrograms() {
        return ps_list.size();
    }
}
