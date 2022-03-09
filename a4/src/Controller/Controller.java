package Controller;

import Model.Container.IStack;
import Model.ProgramState.ProgramState;
import Model.Statement.IStatement;
import Model.Value.IValue;
import Model.Value.ReferenceValue;
import Repository.IRepository;
import Exception.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Controller {
    IRepository repository;

    public Controller(IRepository _repository) {
        repository = _repository;
    }

    public ProgramState oneStep(ProgramState program) throws ContainerException, ExpressionException, StatementException, FileNotFoundException, IOException {
        IStack<IStatement> exeStack = program.getExeStack();
        if(exeStack.isEmpty()) {
            throw new ContainerException("program state stack is empty.\n");
        }
        IStatement currentStatement = exeStack.pop();
        return currentStatement.execute(program);
    }

    public void allSteps() throws ContainerException, ExpressionException, StatementException, FileNotFoundException, IOException {
        ProgramState program = repository.getCurrentProgram();
        repository.logPrgStateExec();
        while(!program.getExeStack().isEmpty()) {
            oneStep(program);
            program.getHeap().setContent(garbageCollector(getUsedAddresses(program.getSymTable().getContent().values(),
                    program.getHeap().getContent().values()), program.getHeap().getContent()));
            repository.logPrgStateExec();
        }
        System.out.println(program);
    }

    private List<Integer> getUsedAddresses(Collection<IValue> symTableValues, Collection<IValue> heapValues) {
        List<Integer> symTableAddresses = symTableValues.stream()
                .filter(v -> v instanceof ReferenceValue)
                .map(value-> {
                    ReferenceValue value2 = (ReferenceValue) value;
                    return value2.getAddress();
                })
                .collect(Collectors.toList());

        List<Integer> heapAddresses = heapValues.stream()
                .filter(v -> v instanceof ReferenceValue)
                .map(value-> {
                    ReferenceValue value2 = (ReferenceValue) value;
                    return value2.getAddress();
                })
                .collect(Collectors.toList());

        symTableAddresses.addAll(heapAddresses);
        return symTableAddresses;
    }

    private Map<Integer, IValue> garbageCollector(List<Integer> usedAddresses, Map<Integer, IValue> heap) {
        return heap.entrySet().stream()
                .filter(e -> usedAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}

