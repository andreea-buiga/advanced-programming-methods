package Model.Statement;

import Model.Container.IDictionary;
import Model.Expression.IExpression;
import Model.ProgramState.ProgramState;
import Exception.*;
import Model.Type.IType;
import Model.Type.StringType;
import Model.Value.IValue;
import Model.Value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFile implements IStatement {
    private final IExpression exp;

    public CloseRFile(IExpression _exp) {
        exp = _exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ContainerException, ExpressionException, StatementException, IOException {
        IValue evalVal = exp.evaluate(state.getSymTable(), state.getHeap());
        if(evalVal.getType().equals(new StringType())) {
            StringValue strEvalVal = (StringValue) evalVal;
            String expVal = strEvalVal.getValue();
            if(state.getFileTable().isDefined(expVal)) {
                BufferedReader fileDescriptor = state.getFileTable().lookup(expVal);
                fileDescriptor.close();
                state.getFileTable().remove(expVal);
            }
            else {
                throw new StatementException("filename doesn't exist.\n");
            }
        }
        else {
            throw new StatementException("expression doesn't evaluate to a string.\n");
        }

        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws StatementException, ContainerException, ExpressionException {
        IType ExpressionType = exp.typecheck(typeEnv);
        if (ExpressionType.equals(new StringType())) {
            return typeEnv;
        }
        else
            throw new StatementException("Close Statement -  expression type is not a string.\n");
    }

    @Override
    public String toString() {
        return "closeRFile(" + exp.toString() + ")";
    }
}
