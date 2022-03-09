package Model.Statement;

import Model.Container.IDictionary;
import Model.Expression.IExpression;
import Model.ProgramState.ProgramState;
import Model.Type.IType;
import Model.Value.IValue;
import Exception.*;

public class AssignmentStatement implements IStatement {
    String id;
    IExpression exp;

    public AssignmentStatement(String _id, IExpression _exp) {
        id = _id;
        exp = _exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ContainerException, ExpressionException, StatementException {
        IDictionary<String, IValue> symTable = state.getSymTable();
        //IStack<IStatement> exeStack = state.getExeStack();
        if(symTable.isDefined(id)) {
            IValue val = exp.evaluate(symTable);
            IType typeId = (symTable.lookup(id)).getType();
            if(val.getType().equals(typeId)) {
                symTable.update(id, val);
            }
            else
                throw new StatementException("type of expression and type of variable do not match.\n");
        }
        else
            throw new StatementException("variable id is not declared.\n");
        return state;
    }

    @Override
    public String toString() {
        return id + " = " + exp.toString();
    }
}
