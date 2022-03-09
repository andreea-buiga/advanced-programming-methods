package Model.Expression;

import Model.Container.IDictionary;
import Model.Container.IHeap;
import Model.Type.IntType;
import Model.Value.BoolValue;
import Model.Value.IValue;
import Exception.*;
import Model.Value.IntValue;

import java.util.Objects;

public class RelationalExpression implements IExpression {
    private final IExpression exp1, exp2;
    private final String op;

    public RelationalExpression(IExpression _exp1, IExpression _exp2, String _op) {
        exp1 = _exp1;
        exp2 = _exp2;
        op = _op;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> symTable, IHeap<Integer, IValue> heap) throws ContainerException, ExpressionException {
        IValue val1, val2;
        val1 = exp1.evaluate(symTable, heap);
        if(val1.getType().equals(new IntType())) {
            val2 = exp2.evaluate(symTable, heap);
            if(val2.getType().equals(new IntType())) {
                IntValue int_val1 = (IntValue) val1;
                IntValue int_val2 = (IntValue) val2;
                int n1, n2;
                n1 = int_val1.getValue();
                n2 = int_val2.getValue();
                if (Objects.equals(op, "<")) {
                    return new BoolValue(n1 < n2);
                }
                if(Objects.equals(op, "<=")) {
                    return new BoolValue(n1 <= n2);
                }
                if(Objects.equals(op, "==")) {
                    return new BoolValue(n1 == n2);
                }
                if(Objects.equals(op, "!=")) {
                    return new BoolValue(n1 != n2);
                }
                if(Objects.equals(op, ">")) {
                    return new BoolValue(n1 > n2);
                }
                if(Objects.equals(op, ">=")) {
                    return new BoolValue(n1 > n2);
                }
                else
                    throw new ExpressionException("invalid arithmetic operator.\n");
            }
            else {
                throw new ExpressionException("second operand is not an integer.\n");
            }
        }
        else {
            throw new ExpressionException("first operand is not an integer.\n");
        }
    }

    @Override
    public String toString() {
        return "(" + exp1.toString() + op + exp2.toString() + ")" ;
    }
}
