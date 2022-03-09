package Model.Expression;

import Model.Container.IDictionary;
import Model.Container.IHeap;
import Model.Type.BoolType;
import Model.Type.IType;
import Model.Value.BoolValue;
import Model.Value.IValue;
import Exception.*;

public class LogicExpression implements IExpression {
    IExpression exp1, exp2;
    int op;

    public LogicExpression(IExpression _exp1, IExpression _exp2, int _op) {
        exp1 = _exp1;
        exp2 = _exp2;
        op = _op;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> symTable, IHeap<Integer, IValue> heap) throws ContainerException, ExpressionException {
        IValue val1, val2;
        val1 = exp1.evaluate(symTable, heap);
        if(val1.getType().equals(new BoolType())) {
            val2 = exp2.evaluate(symTable, heap);
            if(val2.getType().equals(new BoolType())) {
                BoolValue bool_val1 = (BoolValue) val1;
                BoolValue bool_val2 = (BoolValue) val2;
                boolean n1, n2;
                n1 = bool_val1.getValue();
                n2 = bool_val2.getValue();
                if(op == 1) {
                    return new BoolValue(n1 && n2);
                }
                if(op == 2) {
                    return new BoolValue(n1 || n2);
                }
                else
                    throw new ExpressionException("invalid logic operator.\n");
            }
            else
                throw new ExpressionException("second operand is not a bool.\n");
        }
        else
            throw new ExpressionException("first operand is not a bool.\n");
    }

    @Override
    public IType typecheck(IDictionary<String, IType> typeEnv) throws ExpressionException, ContainerException {
        IType typ1 = exp1.typecheck(typeEnv), typ2 = exp2.typecheck(typeEnv);
        if (exp1.equals(new BoolType()))
            if (exp2.equals(new BoolType()))
                return new BoolType();
            else
                throw new ExpressionException("Second operand is not an integer");
        else
            throw new ExpressionException("Second operand is not an integer");
    }

    @Override
    public String toString() {
        String _op = "";
        if(op == 1)
            _op = " and ";
        if(op == 2)
            _op = " or ";
        return exp1.toString() + _op + exp2.toString();
    }
}
