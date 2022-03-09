package Model.Value;

import Model.Type.IType;
import Model.Type.BoolType;

public class BoolValue implements IValue {
    private final boolean value;

    public BoolValue(){
        this.value = false;
    }

    public BoolValue(boolean i){
        this.value = i;
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null || obj.getClass() != this.getClass())
            return false;
        BoolValue obj_value = (BoolValue) obj;
        return obj_value.value == this.value;
    }

    @Override
    public String toString(){
        return this.value ? "true" : "false";
    }

    @Override
    public IType getType() {
        return new BoolType();
    }

    public boolean getValue(){
        return this.value;
    }

    @Override
    public IValue deepCopy() {
        return new BoolValue(this.value);
    }
}
