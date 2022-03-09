package Model.Type;

import Model.Value.BoolValue;
import Model.Value.IValue;

public class BoolType implements IType{
    @Override
    public IValue defaultValue() {
        return new BoolValue(false);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof BoolType;
    }

    @Override
    public IType deepCopy() {
        return new BoolType();
    }

    @Override
    public String toString(){
        return "bool";
    }
}
