package Model.Statement;

import Model.Container.IDictionary;
import Model.Expression.IExpression;
import Model.ProgramState.ProgramState;
import Exception.*;
import Model.Type.IType;
import Model.Type.ReferenceType;
import Model.Value.IValue;
import Model.Value.ReferenceValue;

import java.io.IOException;

public class WriteHeap implements IStatement {
    private String varName;
    private IExpression exp;

    public WriteHeap(String _varName, IExpression _exp) {
        varName = _varName;
        exp = _exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ContainerException, ExpressionException, StatementException, IOException {
        if (state.getSymTable().isDefined(varName)){
            IValue val = state.getSymTable().lookup(varName);
            if (val instanceof ReferenceValue){
                int address = ((ReferenceValue)val).getAddress();
                if (state.getHeap().get(address) != null) {
                    IValue evaluationValue = exp.evaluate(state.getSymTable(), state.getHeap());
                    if (evaluationValue.getType().equals(((ReferenceValue) val).getLocationType())) {
                        state.getHeap().put(address, evaluationValue);
                    }
                    else {
                        throw new StatementException("incompatible types.\n");
                    }
                }
                else {
                    throw new StatementException("address is not a key in the heap.\n");
                }
            }
            else {
                throw new StatementException("value not of reference type.\n");
            }
        }
        else {
            throw new StatementException("variable not defined.\n");
        }

        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws StatementException, ContainerException, ExpressionException {
        IType typeVar = typeEnv.lookup(varName);
        IType typeExp = exp.typecheck(typeEnv);
        if(typeVar.equals(new ReferenceType(typeExp))) {
            return typeEnv;
        }
        else
            throw new StatementException("Heap write statement - different types");
    }

    @Override
    public String toString() {
        return "wH(" + varName + ", " + exp.toString() + ")";
    }
}
