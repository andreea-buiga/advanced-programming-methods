package Controller;

import Model.Container.IStack;
import Model.State.ProgramState;
import Model.Statement.IStatement;
import Repository.IRepository;
import Repository.Repository;
import Exception.*;

public class Controller {
    IRepository repository;

    public Controller(IRepository _repository) {
        repository = _repository;
    }

    public ProgramState oneStep(ProgramState program) throws ContainerException, ExpressionException, StatementException {
        IStack<IStatement> exeStack = program.getExeStack();
        if(exeStack.isEmpty())
            throw new ContainerException("program state stack is empty.\n");
        IStatement currentStatement = exeStack.pop();
        return currentStatement.execute(program);
    }

    public void allSteps() throws ContainerException, ExpressionException, StatementException {
        ProgramState program = repository.getCurrentProgram();
        System.out.println(program);
        while(!program.getExeStack().isEmpty()) {
            oneStep(program);
            System.out.println(program);
        }
    }

    public void addProgram(IStatement statement) {
        repository.addProgramState(statement);
    }

    public String displayCurrentState() throws ContainerException {
        return repository.getCurrentProgram().toString();
    }

    public void addStatementToCurrentProgram(IStatement statement) throws ContainerException {
        if(repository.nrOfPrograms() != 0) {
            repository.getCurrentProgram().addStatement(statement);
        }
        else {
            repository.addProgramState(statement);
        }
    }
}

