package Model.Type;

import Model.Value.IValue;
import Model.Value.ReferenceValue;

public class ReferenceType implements IType {
    IType inner;

    public ReferenceType(IType _inner) {
        inner = _inner;
    }

    public IType getInner() {
        return inner;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ReferenceType)
            return this.inner.equals(((ReferenceType) obj).getInner());
        else
            return false;
    }

    @Override
    public IValue defaultValue() {
        return new ReferenceValue(0, inner);
    }

    @Override
    public IType deepCopy() {
        return new ReferenceType(inner.deepCopy());
    }

    @Override
    public String toString() {
        return "Ref " + inner.toString();
    }
}
