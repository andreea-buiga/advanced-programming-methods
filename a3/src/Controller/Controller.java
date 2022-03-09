package Controller;

import Model.Container.IStack;
import Model.ProgramState.ProgramState;
import Model.Statement.IStatement;
import Repository.IRepository;
import Exception.*;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Controller {
    IRepository repository;

    public Controller(IRepository _repository) {
        repository = _repository;
    }

    public ProgramState oneStep(ProgramState program) throws ContainerException, ExpressionException, StatementException, FileNotFoundException, IOException {
        IStack<IStatement> exeStack = program.getExeStack();
        if(exeStack.isEmpty())
            throw new ContainerException("program state stack is empty.\n");
        IStatement currentStatement = exeStack.pop();
        return currentStatement.execute(program);
    }

    public void allSteps() throws ContainerException, ExpressionException, StatementException, FileNotFoundException, IOException {
        ProgramState program = repository.getCurrentProgram();
        repository.logPrgStateExec();
        while(!program.getExeStack().isEmpty()) {
            oneStep(program);
            repository.logPrgStateExec();
        }
    }
}

