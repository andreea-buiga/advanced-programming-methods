package Model.Type;

import Model.Value.IValue;
import Model.Value.IntValue;

public class IntType implements IType{
    @Override
    public IValue defaultValue() {
        return new IntValue(0);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof IntType;
    }

    @Override
    public IType deepCopy() {
        return new IntType();
    }

    @Override
    public String toString(){
        return "int";
    }
}
