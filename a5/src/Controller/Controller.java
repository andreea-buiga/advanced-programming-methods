package Controller;

import Model.ProgramState.ProgramState;
import Model.Value.IValue;
import Model.Value.ReferenceValue;
import Repository.IRepository;
import Exception.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    IRepository repository;
    private ExecutorService executor;

    public Controller(IRepository _repository) {
        repository = _repository;
    }

    /*public ProgramState oneStep(ProgramState program) throws ContainerException, ExpressionException, StatementException, FileNotFoundException, IOException {
        IStack<IStatement> exeStack = program.getExeStack();
        if(exeStack.isEmpty()) {
            throw new ContainerException("program state stack is empty.\n");
        }
        IStatement currentStatement = exeStack.pop();
        return currentStatement.execute(program);
    }*/

    private void oneStepForAllProgram(List<ProgramState> prgList) throws InterruptedException {
        prgList.forEach(prg -> {
            try {
                repository.logPrgStateExec(prg);
            } catch (IOException | ContainerException e) {
                e.printStackTrace();
            }
        });

        List<Callable<ProgramState>> callList = prgList.stream()
                .filter(p->!p.getExeStack().isEmpty())
                .map((ProgramState p) ->
                        (Callable<ProgramState>)(()->{
                            try {
                                return p.oneStep();
                            }
                            catch (StatementException e){
                                System.out.println(e.getMessage());
                                return null;
                            }
                        })
                )
                .collect(Collectors.toList());

        List<ProgramState> newProgramList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        System.out.println("end of program.\n");
                    }
                    return null;
                })
                .filter(p -> p!=null)
                .collect(Collectors.toList());

        prgList.addAll(newProgramList);
        prgList.forEach(prg -> {
            try {
                repository.logPrgStateExec(prg);
            } catch (ContainerException | IOException e) {
                System.out.println(e.getMessage());
            }
        });

        repository.setProgramStateList(prgList);
    }

    public void allSteps() throws ContainerException, ExpressionException, StatementException, IOException, InterruptedException {
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> prgList = removeCompletedProgram(repository.getProgramStateList());
        while(prgList.size() > 0) {
            callGarbageCollector(prgList);
            oneStepForAllProgram(prgList);
            prgList = removeCompletedProgram(repository.getProgramStateList());
        }

        executor.shutdownNow();
        repository.setProgramStateList(prgList);
    }

    public void callGarbageCollector(List<ProgramState> programStates){
        programStates.forEach(
                p-> {p.getHeap().setContent(garbageCollector(getUsedAddresses(p.getSymTable().getContent().values(),
                        p.getHeap().getContent().values()), p.getHeap().getContent()));}
        );
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

    private List<ProgramState> removeCompletedProgram(List<ProgramState> inProgramList) {
        return inProgramList.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }
}

