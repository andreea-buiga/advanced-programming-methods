package Model.Expression;

import Model.Type.BoolType;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Value.BoolValue;
import Model.Value.IValue;
import Model.Value.IntValue;
import Exceptions.VariableTypeException;
import Model.ADT.Heap;
import Model.ADT.IDict;

public class RelationalExp extends Exp{
    private final String operator;
    private final Exp e1, e2;

    public RelationalExp(Exp _e1, Exp _e2, String _operator) {
        e1 = _e1;
        e2 = _e2;
        operator = _operator;
    }

    @Override
    public String toString() {
        return "(" + e1.toString() + " " + operator + " " + e2.toString() + ")";
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, Heap<Integer, IValue> heapTable) throws Exception {
        IValue v1 = e1.eval(symTable, heapTable);

        if (v1.getType().equals(new IntType())) {
            IValue v2 = e2.eval(symTable, heapTable);
            if (v2.getType().equals(new IntType())) {
                IntValue nr1 = (IntValue)v1;
                IntValue nr2 = (IntValue)v2;

                switch (operator) {
                    case ">":
                        return new BoolValue(nr1.getValue() > nr2.getValue());
                    case "<":
                        return new BoolValue(nr1.getValue() < nr2.getValue());
                    case "<=":
                        return new BoolValue(nr1.getValue() <= nr2.getValue());
                    case ">=":
                        return new BoolValue(nr1.getValue() >= nr2.getValue());
                    case "==":
                        return new BoolValue(nr1.getValue() == nr2.getValue());
                    case "!=" :
                        return new BoolValue(nr1.getValue() != nr2.getValue());

                }
            }
            else {
                throw new VariableTypeException("Operand 2 is not an integer.\n");
            }
        } else {
            throw new VariableTypeException("Operand 1 is not an integer.\n");
        }
        return new BoolValue();
    }

    @Override
    public IType typecheck(IDict<String, IType> typeEnv) throws Exception {
        IType typ1, typ2;

        typ1 = e1.typecheck(typeEnv);
        typ2 = e2.typecheck(typeEnv);

        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
                return new BoolType();
            } else throw new VariableTypeException("second operand is not an integer");
        } else throw new VariableTypeException("first operand is not an integer");
    }
}
