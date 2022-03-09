package Model.Statement;

import Exceptions.AssignmentException;
import Exceptions.ExpressionException;
import Model.ADT.Heap;
import Model.ADT.IDict;
import Model.ADT.IStack;
import Model.Expression.Exp;
import Model.Expression.LogicExp;
import Model.Expression.RelationalExp;
import Model.Expression.VarExp;
import Model.PrgState;
import Model.Type.BoolType;
import Model.Type.IType;
import Model.Value.IValue;
import javafx.beans.binding.BooleanExpression;

public class ForStmt implements IStmt {
    String var;
    Exp init;
    Exp cond;
    Exp step;
    IStmt stmt;

    public ForStmt(String _var, Exp _init, Exp _cond, Exp _step, IStmt _stmt) {
        var = _var;
        init = _init;
        cond = _cond;
        step = _step;
        stmt = _stmt;
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        IType typeVar = typeEnv.lookup(var);
        IType typeInit = init.typecheck(typeEnv);
        IType typeCond = cond.typecheck(typeEnv);
        IType typeStep = step.typecheck(typeEnv);

        if (typeVar.equals(typeInit) && typeVar.equals(typeCond) && typeVar.equals(typeStep)){
            stmt.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new AssignmentException("Assignment: var and init have different types.");
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IStack<IStmt> exeStack = state.getStack();
        IStmt newStmt = new CompStmt(new AssignStmt(var, init), new WhileStmt(new RelationalExp(new VarExp("v"), cond, "<"), new CompStmt(stmt, new AssignStmt(var, step))));
        exeStack.push(newStmt);
        state.setExeStack(exeStack);

        return null;
    }
    @Override
    public String toString() {
        return "(for(" + var + " = " + init.toString() + "; " + var + " < " + cond.toString() + "; "
                + var + " = " + step.toString() + ") (" + stmt.toString() + "))";
    }
}
