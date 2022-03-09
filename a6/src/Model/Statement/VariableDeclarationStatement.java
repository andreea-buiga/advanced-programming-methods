package Model.Statement;

import Model.Container.IDictionary;
import Model.ProgramState.ProgramState;
import Exception.*;
import Model.Type.BoolType;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Value.BoolValue;
import Model.Value.IValue;
import Model.Value.IntValue;
import Model.Value.StringValue;

public class VariableDeclarationStatement implements IStatement {
    String name;
    IType type;

    public VariableDeclarationStatement(String _name, IType _type) {
        name = _name;
        type = _type;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        /*IDictionary<String, IValue> symTable = state.getSymTable();
        if(symTable.isDefined(name))
            throw new StatementException("symbol " + name + " is already defined.\n");
        else {
               IValue val;
               if(type.equals(new BoolType()))
                   val = new BoolValue();
               else if(type.equals(new IntType()))
                       val = new IntValue();
               else if(type.equals(new StringType()))
                   val = new StringValue();
               else if (type.eq)
                   else
                   throw new StatementException("invalid type of value.\n");
               symTable.update(name, val);
        }*/
        IDictionary<String, IValue> table = state.getSymTable();
        if (table.isDefined(this.name))
            throw new StatementException("variable already declared.\n");
        else
            table.update(this.name, this.type.defaultValue());
        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws StatementException, ContainerException, ExpressionException {
        typeEnv.update(name,type);
        return typeEnv;
    }

    @Override
    public String toString() {
        return type.toString() + " " + this.name;
    }
}
