package Model.Statement;

import Model.Container.IDictionary;
import Model.Expression.IExpression;
import Model.ProgramState.ProgramState;
import Exception.*;
import Model.Type.IType;
import Model.Type.StringType;
import Model.Value.IValue;
import Model.Value.StringValue;

import javax.swing.plaf.nimbus.State;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenRFile implements IStatement {
    private final IExpression exp;

    public OpenRFile(IExpression _exp) {
        exp = _exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ContainerException, ExpressionException, StatementException, FileNotFoundException {
        IValue evalVal;
        evalVal = this.exp.evaluate(state.getSymTable(), state.getHeap());
        if(evalVal.getType().equals(new StringType())) {
            StringValue stringEvalValue = (StringValue) evalVal;
            String expValue = stringEvalValue.getValue();
            if(!state.getFileTable().isDefined(expValue)) {
                BufferedReader fileDescriptor = new BufferedReader(new FileReader(expValue));
                state.getFileTable().update(expValue, fileDescriptor);
            }
            else {
                throw new StatementException("filename already exists!\n");
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
            throw new StatementException("Open Statement -  expression type is not a string.\n");
    }

    @Override
    public String toString() {
        return "openRFile(" + exp.toString() + ")";
    }
}
