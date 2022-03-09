package Model.Type;

import Model.Value.IValue;
import Model.Value.BoolValue;

public class BoolType implements IType {
    @Override
    public IValue defaultValue() {
        return new BoolValue(false);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || obj.getClass() != this.getClass())
            return false;
        return true;
    }

    @Override
    public String toString(){
        return "bool";
    }

    @Override
    public IType deepCopy() {
        return new BoolType();
    }
}
