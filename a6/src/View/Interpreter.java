package View;

import Controller.Controller;
import Model.Container.MyDictionary;
import Model.Expression.*;
import Model.ProgramState.ProgramState;
import Model.Statement.*;
import Model.Type.*;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Repository.Repository;
import Exception.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Interpreter {
    public static void main(String[] args) throws StatementException, ContainerException, ExpressionException {
        IStatement ex1 = new CompoundStatement(
                new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(
                        new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))
                )
        );
        IStatement ex2 = new CompoundStatement(
                new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(
                        new VariableDeclarationStatement("b", new IntType()),
                        new CompoundStatement(
                                new AssignmentStatement("a", new ArithmeticExpression('+', new ValueExpression(new IntValue(2)), new ArithmeticExpression('*', new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                                new CompoundStatement(
                                    new AssignmentStatement("b", new ArithmeticExpression('+', new VariableExpression("a"), new ValueExpression(new IntValue(1)))),
                                    new PrintStatement(new VariableExpression("b"))
                                )
                        )
                )
        );
        IStatement ex3 = new CompoundStatement(
                new VariableDeclarationStatement("a", new BoolType()),
                new CompoundStatement(
                        new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(
                                new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(
                                        new IfStatement(new VariableExpression("a"), new AssignmentStatement("v", new ValueExpression(new IntValue(2))), new AssignmentStatement("v", new ValueExpression(new IntValue(3)))),
                                        new PrintStatement(new VariableExpression("v"))
                                )
                        )
                )
        );

        IStatement ex4 = new CompoundStatement(
                new VariableDeclarationStatement("varf", new StringType()),
                new CompoundStatement(
                        new AssignmentStatement("varf", new ValueExpression(new StringValue("test.in"))),
                        new CompoundStatement(
                                new OpenRFile(new VariableExpression("varf")),
                                new CompoundStatement(
                                        new VariableDeclarationStatement("varc", new IntType()),
                                        new CompoundStatement(
                                                new ReadFile(new VariableExpression("varf"),"varc"),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(
                                                                new ReadFile(new VariableExpression("varf"),"varc"),
                                                                new CompoundStatement(
                                                                        new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseRFile(new VariableExpression("varf"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        // Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
        IStatement ex5 = new CompoundStatement(
                new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(
                        new NewStatement("v",new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                                new CompoundStatement(
                                        new NewStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new PrintStatement(new VariableExpression("v")),
                                                new PrintStatement(new VariableExpression("a"))
                                        )
                                )
                        )
                )
        );

        // Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
        IStatement ex6 = new CompoundStatement(
                new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(
                        new NewStatement("v",new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                                new CompoundStatement(
                                        new NewStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new PrintStatement(new ReadHeap(new VariableExpression("v"))),
                                                new PrintStatement(new ArithmeticExpression('+' , new ReadHeap(new ReadHeap(new VariableExpression("a"))), new ValueExpression(new IntValue(5))))
                                        )
                                )
                        )
                )
        );

        // Ref int v;new(v,20);print(rH(v)); wH(v, 30);print(rH(v) + 5);
        IStatement ex7 = new CompoundStatement(
                new VariableDeclarationStatement("v",new ReferenceType(new IntType())),
                new CompoundStatement(
                        new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new PrintStatement(new ReadHeap(new VariableExpression("v"))),
                                new CompoundStatement(
                                    new WriteHeap("v", new ValueExpression(new IntValue(30))),
                                    new PrintStatement(new ArithmeticExpression('+', new ReadHeap(new VariableExpression("v")), new ValueExpression(new IntValue(5))))
                                )
                        )

                )
        );

        // Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
        IStatement ex8 = new CompoundStatement(
                new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(
                        new NewStatement("v",new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new  IntType()))),
                                new CompoundStatement(
                                        new NewStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new NewStatement("v", new ValueExpression(new IntValue(30))),
                                                new PrintStatement(new ReadHeap(new ReadHeap( new VariableExpression("a"))))
                                        )
                                )
                        )
                )
        );

        // int v; v=4; (while (v>0) print(v);v=v-1);print(v)
        IStatement ex9 = new CompoundStatement(
                new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(
                        new AssignmentStatement("v",new ValueExpression(new IntValue(4))),
                        new CompoundStatement(
                                new WhileStatement(
                                        new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(0)),">"),
                                        new CompoundStatement(
                                                new PrintStatement(new VariableExpression("v")),
                                                new AssignmentStatement("v", new ArithmeticExpression('-', new VariableExpression("v"), new ValueExpression(new IntValue(1))))
                                        )
                                ),
                                new PrintStatement(new VariableExpression("v"))
                        )
                )
        );

        IStatement forked = new CompoundStatement(
                new WriteHeap("a", new ValueExpression(new IntValue(30))),
                new CompoundStatement(
                        new AssignmentStatement("v",new ValueExpression(new IntValue(32))),
                        new CompoundStatement(
                                new PrintStatement(new VariableExpression("v")),
                                new PrintStatement(new ReadHeap(new VariableExpression("a"))))));

        IStatement ex10 = new CompoundStatement(
                new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(
                        new VariableDeclarationStatement("a",new ReferenceType(new IntType())),
                        new CompoundStatement(
                                new AssignmentStatement("v",new ValueExpression(new IntValue(10))),
                                new CompoundStatement(
                                        new NewStatement("a",new ValueExpression(new IntValue(22))),
                                        new CompoundStatement(
                                                new ForkStatement(forked),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(new ReadHeap(new VariableExpression("a"))))))
                        )
                )
        );

        List<ProgramState> prg1 = new ArrayList<>();
        try {
            ex1.typecheck(new MyDictionary<String, IType>());
        }
        catch (ContainerException | ExpressionException | StatementException e) {
            System.out.println(e.getMessage() + "for ex1.\n");
            System.exit(0);
        }
        prg1.add(new ProgramState(ex1));
        Repository repo1 = new Repository(prg1, "log1.txt");
        Controller ctrl1 = new Controller(repo1);

        List<ProgramState> prg2 = new ArrayList<>();
        try {
            ex2.typecheck(new MyDictionary<String, IType>());
        }
        catch (ContainerException | ExpressionException | StatementException e) {
            System.out.println(e.getMessage() + "for ex2.\n");
            System.exit(0);
        }
        prg2.add(new ProgramState(ex2));
        Repository repo2 = new Repository(prg2, "log2.txt");
        Controller ctrl2 = new Controller(repo2);

        List<ProgramState> prg3 = new ArrayList<>();
        try {
            ex3.typecheck(new MyDictionary<String, IType>());
        }
        catch (ContainerException | ExpressionException | StatementException e) {
            System.out.println(e.getMessage() + "for ex3.\n");
            System.exit(0);
        }
        prg3.add(new ProgramState(ex3));
        Repository repo3 = new Repository(prg3, "log3.txt");
        Controller ctrl3 = new Controller(repo3);

        List<ProgramState> prg4 = new ArrayList<>();
        try {
            ex4.typecheck(new MyDictionary<String, IType>());
        }
        catch (ContainerException | ExpressionException | StatementException e) {
            System.out.println(e.getMessage() + "for ex4.\n");
            System.exit(0);
        }
        prg4.add(new ProgramState(ex4));
        Repository repo4 = new Repository(prg4, "log4.txt");
        Controller ctrl4 = new Controller(repo4);

        List<ProgramState> prg5 = new ArrayList<>();
        try {
            ex5.typecheck(new MyDictionary<String, IType>());
        }
        catch (ContainerException | ExpressionException | StatementException e) {
            System.out.println(e.getMessage() + "for ex5.\n");
            System.exit(0);
        }
        prg5.add(new ProgramState(ex5));
        Repository repo5 = new Repository(prg5, "log5.txt");
        Controller ctrl5 = new Controller(repo5);

        List<ProgramState> prg6 = new ArrayList<>();
        try {
            ex6.typecheck(new MyDictionary<String, IType>());
        }
        catch (ContainerException | ExpressionException | StatementException e) {
            System.out.println(e.getMessage() + "for ex6.\n");
            System.exit(0);
        }
        prg6.add(new ProgramState(ex6));
        Repository repo6 = new Repository(prg6, "log6.txt");
        Controller ctrl6 = new Controller(repo6);

        List<ProgramState> prg7 = new ArrayList<>();
        try {
            ex7.typecheck(new MyDictionary<String, IType>());
        }
        catch (ContainerException | ExpressionException | StatementException e) {
            System.out.println(e.getMessage() + "for ex7.\n");
            System.exit(0);
        }
        prg7.add(new ProgramState(ex7));
        Repository repo7 = new Repository(prg7, "log7.txt");
        Controller ctrl7 = new Controller(repo7);

        List<ProgramState> prg8 = new ArrayList<>();
        try {
            ex8.typecheck(new MyDictionary<String, IType>());
        }
        catch (ContainerException | ExpressionException | StatementException e) {
            System.out.println(e.getMessage() + "for ex8.\n");
            System.exit(0);
        }
        prg8.add(new ProgramState(ex8));
        Repository repo8 = new Repository(prg8, "log8.txt");
        Controller ctrl8 = new Controller(repo8);

        List<ProgramState> prg9 = new ArrayList<>();
        try {
            ex9.typecheck(new MyDictionary<String, IType>());
        }
        catch (ContainerException | ExpressionException | StatementException e) {
            System.out.println(e.getMessage() + "for ex9.\n");
            System.exit(0);
        }
        prg9.add(new ProgramState(ex9));
        Repository repo9 = new Repository(prg9, "log9.txt");
        Controller ctrl9 = new Controller(repo9);

        List<ProgramState> prg10 = new ArrayList<>();
        try {
            ex10.typecheck(new MyDictionary<String, IType>());
        }
        catch (ContainerException | ExpressionException | StatementException e) {
            System.out.println(e.getMessage() + "for ex10.\n");
            System.exit(0);
        }
        prg10.add(new ProgramState(ex10));
        Repository repo10 = new Repository(prg10, "log10.txt");
        Controller ctrl10 = new Controller(repo10);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", ex1.toString(), ctrl1));
        menu.addCommand(new RunExample("2", ex2.toString(), ctrl2));
        menu.addCommand(new RunExample("3", ex3.toString(), ctrl3));
        menu.addCommand(new RunExample("4", ex4.toString(), ctrl4));
        menu.addCommand(new RunExample("5", ex5.toString(), ctrl5));
        menu.addCommand(new RunExample("6", ex6.toString(), ctrl6));
        menu.addCommand(new RunExample("7", ex7.toString(), ctrl7));
        menu.addCommand(new RunExample("8", ex8.toString(), ctrl8));
        menu.addCommand(new RunExample("9", ex9.toString(), ctrl9));
        menu.addCommand(new RunExample("10", ex10.toString(), ctrl10));
        menu.show();
    }
}