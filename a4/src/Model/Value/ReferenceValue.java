package Model.Value;

import Model.Type.IType;
import Model.Type.ReferenceType;

public class ReferenceValue implements IValue {
    int address;
    IType locationType;

    public ReferenceValue(int _address, IType _locationType) {
        address = _address;
        locationType = _locationType;
    }

    public int getAddress() {
        return address;
    }

    public IType getLocationType() {
        return locationType;
    }

    @Override
    public IType getType() {
        return new ReferenceType(locationType);
    }

    @Override
    public IValue deepCopy() {
        return new ReferenceValue(address, locationType);
    }

    @Override
    public String toString() {
        return "(" + address + ", " + locationType.toString() + ")" ;
    }
}
