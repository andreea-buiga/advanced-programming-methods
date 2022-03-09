package Model.Expression;

import Model.Container.IDictionary;
import Model.Container.IHeap;
import Model.Value.IValue;
import Exception.*;
import Model.Value.ReferenceValue;

public class ReadHeap implements IExpression {
    private IExpression exp;

    public ReadHeap(IExpression _exp) {
        exp = _exp;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> symTable, IHeap<Integer, IValue> heap) throws ContainerException, ExpressionException {
        IValue evalVal = exp.evaluate(symTable, heap);
        if(evalVal instanceof ReferenceValue) {
            int address = ((ReferenceValue) evalVal).getAddress();
            IValue valueFromHeap = heap.get(address);
            if(valueFromHeap != null) {
                return valueFromHeap;
            }
            else {
                throw new ExpressionException("address doesn't have a value.\n");
            }
        }
        else {
            throw new ExpressionException("value is not of type reference value.\n");
        }
    }

    @Override
    public String toString() {
        return "rH(" + exp.toString() + ")";
    }
}
