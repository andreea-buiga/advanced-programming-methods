package Model.Statement;

import Model.Expression.IExpression;
import Model.ProgramState.ProgramState;
import Exception.*;
import Model.Value.IValue;
import Model.Value.ReferenceValue;

import java.io.IOException;

public class NewStatement implements IStatement {
    private String varName;
    private IExpression exp;

    public NewStatement(String _varName, IExpression _exp) {
        varName = _varName;
        exp = _exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ContainerException, ExpressionException, StatementException, IOException {
        if(state.getSymTable().isDefined(varName)) {
            IValue value = state.getSymTable().lookup(varName);
            if(value instanceof ReferenceValue) {
                IValue expressionVal = exp.evaluate(state.getSymTable(), state.getHeap());
                if(expressionVal.getType().equals(((ReferenceValue) value).getLocationType())) {
                    int location = state.getHeap().allocate(state.getHeap().getFreeAddress(), expressionVal);
                    state.getSymTable().update(varName, new ReferenceValue(location, expressionVal.getType()));
                }
                else {
                    throw new StatementException("types are not equal.\n");
                }
            }
            else {
                throw new StatementException("value is not of reference type.\n");
            }
        }
        else {
            throw new StatementException("variable is not defined.\n");
        }

        return null;
    }

    @Override
    public String toString() {
        return "new(" + varName + ", " + exp.toString() + ")";
    }
}
