package Model.Expression;

import Model.Container.IDictionary;
import Model.Container.IHeap;
import Model.Container.MyDictionary;
import Model.Type.IType;
import Model.Value.IValue;
import Exception.*;

public class ValueExpression implements IExpression {
    IValue value;

    public ValueExpression(IValue _value) {
        value = _value;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> symTable, IHeap<Integer, IValue> heap) {
        return value;
    }

    @Override
    public IType typecheck(IDictionary<String, IType> typeEnv) throws ExpressionException{
        return value.getType();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
