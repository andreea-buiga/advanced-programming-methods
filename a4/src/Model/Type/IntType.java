package Model.Type;

import Model.Value.IValue;
import Model.Value.IntValue;

public class IntType implements IType {
    @Override
    public IntValue defaultValue() {
        return new IntValue(0);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || obj.getClass() != this.getClass())
            return false;
        return true;
    }

    @Override
    public String toString(){
        return "int";
    }

    @Override
    public IType deepCopy() {
        return new IntType();
    }
}
