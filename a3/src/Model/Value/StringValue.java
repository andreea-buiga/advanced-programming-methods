package Model.Value;

import Model.Type.IType;
import Model.Type.StringType;

public class StringValue implements IValue {
    String value;

    public StringValue() {
        value = "";
    }

    public StringValue(String _value) {
        value = _value;
    }

    @Override
    public IType getType() {
        return new StringType();
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public IValue deepCopy() {
        return new StringValue();
    }

    @Override
    public String toString(){
        return "\"" + value + "\"";
    }
}
