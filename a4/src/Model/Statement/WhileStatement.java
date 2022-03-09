package Model.Statement;

import Model.Container.IDictionary;
import Model.Expression.IExpression;
import Model.ProgramState.ProgramState;
import Exception.*;
import Model.Type.BoolType;
import Model.Value.BoolValue;
import Model.Value.IValue;

import java.io.IOException;

public class WhileStatement implements IStatement {
    private IExpression exp;
    private IStatement statement;

    public WhileStatement(IExpression _exp, IStatement _statement) {
        exp = _exp;
        statement = _statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ContainerException, ExpressionException, StatementException, IOException {
        IDictionary<String, IValue> symTable = state.getSymTable();
        IValue evalVal = exp.evaluate(symTable, state.getHeap());
        if(evalVal.getType().equals(new BoolType())) {
            BoolValue boolEvalVal = (BoolValue) evalVal;
            if(boolEvalVal.getValue()) {  // boolEvalVal == true
                state.getExeStack().push(new WhileStatement(exp, statement));
                state.getExeStack().push(statement);
            }
        }
        else {
            throw new StatementException("condition expression is not a boolean.\n");
        }

        return state;
    }

    @Override
    public String toString() {
        return "(while(" + exp.toString() + ")" + statement.toString() + ")";
    }
}
