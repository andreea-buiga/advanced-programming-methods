package Model.Expression;

import Model.Container.IDictionary;
import Model.Container.IHeap;
import Model.Value.IValue;
import Exception.*;

public interface IExpression {
    IValue evaluate(IDictionary<String, IValue> symTable, IHeap<Integer, IValue> heap) throws ContainerException, ExpressionException;
}
