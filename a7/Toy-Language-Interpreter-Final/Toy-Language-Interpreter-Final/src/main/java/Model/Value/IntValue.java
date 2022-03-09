package Model.Value;

import Model.Type.IType;
import Model.Type.IntType;

public class IntValue implements IValue{

    private final int value;

    public IntValue(){
        this.value = 0;
    }

    public IntValue(int i){
        this.value = i;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof IntValue) {
            IntValue o_value = (IntValue) o;
            return o_value.value == this.value;
        }
        return false;
    }

    @Override
    public String toString(){
        return Integer.toString(this.value);
    }

    @Override
    public IType getType() {
        return new IntType();
    }

    public int getValue(){
        return this.value;
    }

    @Override
    public IValue deepCopy() {
        return new IntValue(this.value);
    }
}
