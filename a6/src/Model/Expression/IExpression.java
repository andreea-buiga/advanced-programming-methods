package Model.Expression;

import Model.Container.IDictionary;
import Model.Container.IHeap;
import Model.Type.IType;
import Model.Value.IValue;
import Exception.*;

public interface IExpression {
    IValue evaluate(IDictionary<String, IValue> symTable, IHeap<Integer, IValue> heap) throws ContainerException, ExpressionException;
    IType typecheck(IDictionary<String, IType> typeEnv) throws ExpressionException, ContainerException;
}
