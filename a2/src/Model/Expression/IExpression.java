package Model.Expression;

import Model.Container.IDictionary;
import Model.Value.IValue;
import Exception.*;

public interface IExpression {
    IValue evaluate(IDictionary<String, IValue> symTable) throws ContainerException, ExpressionException;
}
