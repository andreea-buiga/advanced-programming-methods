package Model.Statement;

import Model.Expression.IExpression;
import Model.State.ProgramState;
import Model.Type.BoolType;
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
        if(!exp.evaluate(symTable).getType().equals(new BoolType()))
            throw new StatementException("conditional expression is not a boolean");
        IValue result = exp.evaluate(symTable);
        if(((BoolValue) result).getValue()) {
            exeStack.push(thenStatement);
        }
        else {
            exeStack.push(elseStatement);
        }
        return state;
    }

    @Override
    public String toString() {
        return "if(" + exp.toString() + ") then (" + thenStatement.toString() + ") else(" + elseStatement.toString() + "))";
    }
}
