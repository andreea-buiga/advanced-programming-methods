package Model.Statement;

import Exceptions.ExpressionException;
import Model.ADT.Heap;
import Model.ADT.IDict;
import Model.ADT.IStack;
import Model.Expression.Exp;
import Model.Expression.RelationalExp;
import Model.PrgState;
import Model.Type.IType;
import Model.Value.IValue;

public class SwitchStmt implements IStmt {
    private Exp exp,exp1,exp2;
    private IStmt statement1,statement2,statement3;

    public SwitchStmt(Exp exp, Exp exp1, Exp exp2, IStmt statement1, IStmt statement2, IStmt statement3) {
        this.exp = exp;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.statement1 = statement1;
        this.statement2 = statement2;
        this.statement3 = statement3;
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        IType typeExp = exp.typecheck(typeEnv);
        IType typeExp1 = exp1.typecheck(typeEnv);
        IType typeExp2 = exp2.typecheck(typeEnv);
        if (typeExp.equals(typeExp1) && typeExp.equals(typeExp2)) //check for same types
            return statement1.typecheck(statement2.typecheck(statement3.typecheck(typeEnv))); //typecheck the statements
        else
            throw new ExpressionException("Incompatible types");
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IStack<IStmt> executionStack = state.getStack();
        IDict<String, IValue> symbolTable = state.getSymTable();
        Heap<Integer, IValue> heapTable = (Heap<Integer, IValue>)state.getHeapTable();

        IValue _exp = exp.eval(symbolTable, heapTable);
        IValue _exp1 = exp1.eval(symbolTable, heapTable);
        IValue _exp2 = exp2.eval(symbolTable, heapTable);
        IStmt newStatement;
        if(_exp.equals(_exp1))
            newStatement = statement1;
        else
            if(_exp.equals(_exp2))
                newStatement = statement2;
            else
                newStatement = statement3;
        executionStack.push(newStatement);
        state.setExeStack(executionStack);
        return null;
    }

    @Override
    public String toString() {
        return "switch("+ exp.toString() + ")\n" +
                "(case "+ exp1.toString()  + " : " + statement1.toString() +
                ")\n(case " + exp2.toString() + " : " + statement2.toString() +
                ")\n(default " + statement3.toString() + ")";
    }
}
