package Model.Statement;


import Exceptions.DeclaredVariableException;
import Model.Type.IType;
import Model.Value.IValue;
import Model.PrgState;
import Model.ADT.IDict;

public class VarDeclStmt implements IStmt{
    String name;
    IType type;

    public VarDeclStmt(String name, IType type){
        this.name = name;
        this.type = type;
    }


    @Override
    public PrgState execute(PrgState state) throws Exception {
        IDict<String, IValue> symTbl = state.getSymTable();
        if (symTbl.isDefined(name)) {
            throw new DeclaredVariableException("Variable already declared.");
        }

        symTbl.add(name, type.defaultValue());

        return null;
    }

    @Override
    public String toString() {
        return "(" + name + " = " + type + ")";
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        typeEnv.add(name, type);
        return typeEnv;
    }
}
