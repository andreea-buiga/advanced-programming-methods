package Model.Statement;

import Model.Expression.IExpression;
import Model.Value.*;
import Model.ProgramState.ProgramState;
import Exception.*;
import Model.Container.*;

public class PrintStatement implements IStatement {
    IExpression exp;

    public PrintStatement(IExpression _exp) {
        exp = _exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ContainerException, ExpressionException {
        IList<IValue> out = state.getOut();
        IDictionary<String, IValue> symTable = state.getSymTable();
        out.add(exp.evaluate(symTable, state.getHeap()));
        return null;
    }

    @Override
    public String toString() {
        return "print(" + exp.toString() + ")";
    }
}
