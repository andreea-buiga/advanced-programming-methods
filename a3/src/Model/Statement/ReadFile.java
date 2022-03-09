package Model.Statement;

import Model.Expression.IExpression;
import Model.ProgramState.ProgramState;
import Exception.*;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Value.IValue;
import Model.Value.IntValue;
import Model.Value.StringValue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ReadFile implements IStatement {
    private final IExpression exp;
    private final String varName;

    public ReadFile(IExpression _exp, String _varName) {
        exp = _exp;
        varName =_varName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ContainerException, ExpressionException, StatementException, IOException {
        if(state.getSymTable().isDefined(varName)) {
            if(state.getSymTable().lookup(varName).getType().equals(new IntType())) {
                IValue evalVal = this.exp.evaluate(state.getSymTable());
                if(evalVal.getType().equals(new StringType())) {
                    StringValue strEvalVal = (StringValue) evalVal;
                    String expVal = strEvalVal.getValue();
                    if(state.getFileTable().isDefined(expVal)) {
                        BufferedReader fileDescriptor = state.getFileTable().lookup(expVal);
                        String line = fileDescriptor.readLine();
                        IntValue readVal;
                        if(line == null) {
                            readVal = new IntValue(0);
                        }
                        else {
                            readVal = new IntValue(Integer.parseInt(line));
                        }
                        state.getSymTable().update(varName, readVal);
                    }
                    else {
                        throw new StatementException("no entry associated with the file table.\n");
                    }
                }
                else {
                    throw new StatementException("expression didn't evaluate to string.\n");
                }
            }
            else
                throw new StatementException("associated value type isn't int.\n");
        }
        else {
            throw new StatementException("variable name is not defined in the symbol table.\n");
        }

        return state;
    }

    @Override
    public String toString() {
        return "readFile(" + exp.toString() + ", " + varName + ")";
    }
}
