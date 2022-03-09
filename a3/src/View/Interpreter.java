package View;

import Controller.Controller;
import Model.Expression.ArithmeticExpression;
import Model.Expression.ValueExpression;
import Model.Expression.VariableExpression;
import Model.ProgramState.ProgramState;
import Model.Statement.*;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Repository.Repository;

class Interpreter {
    public static void main(String[] args) {
        IStatement ex1 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))));
        IStatement ex2 = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression('+', new ValueExpression(new IntValue(2)), new ArithmeticExpression('*',
                                new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))), new CompoundStatement(
                                new AssignmentStatement("b", new ArithmeticExpression('+', new VariableExpression("a"), new ValueExpression(new IntValue(1)))),
                                new PrintStatement(new VariableExpression("b"))))));
        IStatement ex3 = new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()),
                        new CompoundStatement(new VariableDeclarationStatement("v",
                        new IntType()),new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                        new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignmentStatement("v",new ValueExpression(new IntValue(2))),
                                new AssignmentStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new VariableExpression("v"))))));
        IStatement ex4 = new CompoundStatement(
                new VariableDeclarationStatement("varf", new StringType()), new CompoundStatement(
                new AssignmentStatement("varf", new ValueExpression(new StringValue("test.in"))), new CompoundStatement(
                new OpenRFile(new VariableExpression("varf")), new CompoundStatement(
                new VariableDeclarationStatement("varc", new IntType()), new CompoundStatement(
                new ReadFile(new VariableExpression("varf"),"varc"), new CompoundStatement(
                new PrintStatement(new VariableExpression("varc")),new CompoundStatement(
                new ReadFile(new VariableExpression("varf"),"varc"), new CompoundStatement(
                        new PrintStatement(new VariableExpression("varc")), new CloseRFile(new VariableExpression("varf"))))))))));

        ProgramState prg1 = new ProgramState(ex1);
        Repository repo1 = new Repository(prg1, "log1.txt");
        Controller ctrl1 = new Controller(repo1);
        ProgramState prg2 = new ProgramState(ex2);
        Repository repo2 = new Repository(prg2, "log2.txt");
        Controller ctrl2 = new Controller(repo2);
        ProgramState prg3 = new ProgramState(ex3);
        Repository repo3 = new Repository(prg3, "log3.txt");
        Controller ctrl3 = new Controller(repo3);
        ProgramState prg4 = new ProgramState(ex4);
        Repository repo4 = new Repository(prg4, "log4.txt");
        Controller ctrl4 = new Controller(repo4);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", ex1.toString(), ctrl1));
        menu.addCommand(new RunExample("2", ex2.toString(), ctrl2));
        menu.addCommand(new RunExample("3", ex3.toString(), ctrl3));
        menu.addCommand(new RunExample("4", ex4.toString(), ctrl4));
        menu.show();
    }
}