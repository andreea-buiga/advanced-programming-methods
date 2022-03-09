package Model.Type;

import Model.Value.IValue;
import Model.Value.IntValue;
import Model.Value.StringValue;

public class StringType implements IType {
    @Override
    public StringValue defaultValue() {
        return new StringValue("");
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || obj.getClass() != this.getClass())
            return false;
        return true;
    }

    @Override
    public IType deepCopy() {
        return new StringType();
    }

    @Override
    public String toString() {
        return "string";
    }
}
