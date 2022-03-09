package Model.Statement;

import Model.Expression.IExpression;
import Model.ProgramState.ProgramState;
import Model.Type.BoolType;
import Model.Type.IType;
import Model.Value.BoolValue;
import Model.Value.IValue;
import Model.Container.*;
import Exception.*;

public class IfStatement implements IStatement {
    IStatement thenStatement, elseStatement;
    IExpression exp;

    public IfStatement(IExpression _exp, IStatement _thenS, IStatement _elseS) {
        exp = _exp;
        thenStatement = _thenS;
        elseStatement = _elseS;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ContainerException, ExpressionException, StatementException {
        IStack<IStatement> exeStack = state.getExeStack();
        IDictionary<String, IValue> symTable = state.getSymTable();
        if(!exp.evaluate(symTable, state.getHeap()).getType().equals(new BoolType()))
            throw new StatementException("conditional expression is not a boolean.\n");
        IValue result = exp.evaluate(symTable, state.getHeap());
        if(((BoolValue) result).getValue()) {
            exeStack.push(thenStatement);
        }
        else {
            exeStack.push(elseStatement);
        }
        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws StatementException, ContainerException, ExpressionException {
        IType typeExp = exp.typecheck(typeEnv);
        if (typeExp.equals(new BoolType())){
            thenStatement.typecheck(typeEnv);
            elseStatement.typecheck(typeEnv);
            return typeEnv.cloneDict();
        }
        else
            throw new StatementException("The condition of IF has not the type bool.\n");
    }

    @Override
    public String toString() {
        return "if(" + exp.toString() + ") then (" + thenStatement.toString() + ") else(" + elseStatement.toString() + "))";
    }
}
