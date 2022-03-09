package Model.Expression;

import Model.Container.IDictionary;
import Model.Container.IHeap;
import Model.Type.IntType;
import Model.Value.IValue;
import Model.Value.IntValue;
import Exception.*;

public class ArithmeticExpression implements IExpression {
    IExpression exp1, exp2;
    int op;

    public ArithmeticExpression(Character _op, IExpression _exp1, IExpression _exp2) {
        if(_op.equals('+')) {
            op = 1;
        }
        else
            if(_op.equals('-')) {
                op = 2;
            }
            else
                if(_op.equals('*')) {
                    op = 3;
                }
                else
                    if(_op.equals('/')) {
                        op = 4;
                    }

        exp1 = _exp1;
        exp2 = _exp2;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> symTable, IHeap<Integer, IValue> heap) throws ContainerException, ExpressionException {
        IValue val1, val2;
        val1 = exp1.evaluate(symTable, heap);
        if (val1.getType().equals(new IntType())) {
            val2 = exp2.evaluate(symTable, heap);
            if (val2.getType().equals(new IntType())) {
                IntValue int_val1 = (IntValue) val1;
                IntValue int_val2 = (IntValue) val2;
                int n1, n2;
                n1 = int_val1.getValue();
                n2 = int_val2.getValue();
                if (op == 1) {
                    return new IntValue(n1 + n2);
                }
                if(op == 2) {
                    return new IntValue(n1 - n2);
                }
                if(op == 3) {
                    return new IntValue(n1 * n2);
                }
                if(op == 4)
                    if(n2 == 0) {
                        throw new ExpressionException("division by zero.\n");
                    }
                    else {
                        return new IntValue(n1 / n2);
                    }
                else
                    throw new ExpressionException("invalid arithmetic operator.\n");
            }
            else
                throw new ExpressionException("second operand is not an integer.\n");
        }
        else
            throw new ExpressionException("first operand is not an integer.\n");
    }

    @Override
    public String toString() {
        String _op = "";
        if(op == 1)
            _op = " + ";
        if(op == 2)
            _op = " - ";
        if(op == 3)
            _op = " * ";
        if(op == 4)
            _op = " / ";
        return exp1.toString() + _op + exp2.toString();
    }
}
