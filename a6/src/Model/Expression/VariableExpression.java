package Model.Expression;

import Model.Container.IDictionary;
import Model.Container.IHeap;
import Model.Type.IType;
import Model.Value.IValue;
import Exception.*;

public class VariableExpression implements IExpression {
    String id;

    public VariableExpression(String _id) {
        id = _id;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> symTable, IHeap<Integer, IValue> heap) throws ContainerException {
        return symTable.lookup(id);
    }

    @Override
    public IType typecheck(IDictionary<String, IType> typeEnv) throws ExpressionException, ContainerException {
        return typeEnv.lookup(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
