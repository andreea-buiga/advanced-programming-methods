package Model.Type;

import Model.Value.IValue;

public interface IType {
    IValue defaultValue();
    IType deepCopy();
}
