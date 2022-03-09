package Model.Statement;

import Model.Type.IType;
import Model.PrgState;
import Model.ADT.IDict;

public interface IStmt {
    String toString();
    IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception;
    PrgState execute(PrgState state) throws Exception;
}
