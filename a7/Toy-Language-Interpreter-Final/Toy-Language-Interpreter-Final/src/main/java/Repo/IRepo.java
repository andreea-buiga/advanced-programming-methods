package Repo;
import Model.PrgState;
import Model.ADT.IList;


public interface IRepo {
    void addPrg(PrgState newPrg);
    IList<PrgState> getPrgList();
    void setPrgList(IList<PrgState> newPrgList);
    void logPrgStateExec(PrgState state) throws Exception;
    }
