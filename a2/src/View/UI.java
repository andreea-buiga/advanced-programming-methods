package View;

import Controller.Controller;
import Exception.*;
import Model.Expression.ArithmeticExpression;
import Model.Expression.ValueExpression;
import Model.Expression.VariableExpression;
import Model.State.ProgramState;
import Model.Statement.*;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Repository.Repository;

import java.util.Scanner;

public class UI {
    private Controller controller;

    public UI(Controller _controller) {
        controller = _controller;
    }

    private static void print_menu() {
        System.out.println("" +
                "\n\t--------------------------------------------------------------------------\n" +
                "\t| 1 | int v; v = 2; Print(v)                                             |\n" +
                "\t--------------------------------------------------------------------------\n" +
                "\t| 2 | a=2+3*5;b=a+1;Print(b)                                             |\n" +
                "\t--------------------------------------------------------------------------\n" +
                "\t| 3 | bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)            |\n" +
                "\t--------------------------------------------------------------------------\n" +
                "\t| 0 | exit the program.                                                  |\n" +
                "\t--------------------------------------------------------------------------\n");
    }

    private void program1() throws ContainerException, ExpressionException, StatementException {
        /// int v; v = 2; Print(v)
        IStatement ex1 = new CompoundStatement(new VariableDeclarationStatement("v",new IntType()),
                         new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(2))),
                         new PrintStatement(new VariableExpression("v"))));
        Repository r1 = new Repository();
        controller = new Controller(r1);
        controller.addProgram(ex1);
        controller.allSteps();
    }

    private void program2() throws ContainerException, ExpressionException, StatementException {
        /// a=2+3*5;b=a+1;Print(b)
        IStatement ex2 = new CompoundStatement( new VariableDeclarationStatement("a",new IntType()),
                         new CompoundStatement(new VariableDeclarationStatement("b",new IntType()),
                         new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression('+', new ValueExpression(new IntValue(2)), new ArithmeticExpression('*',
                         new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),  new CompoundStatement(
                         new AssignmentStatement("b",new ArithmeticExpression('+',new VariableExpression("a"), new ValueExpression(new IntValue(1)))),
                         new PrintStatement(new VariableExpression("b"))))));
        /*
        controller.addStatementToCurrentProgram(ex2);
        controller.allSteps();
        */
        Repository r2 = new Repository();
        controller = new Controller(r2);
        controller.addProgram(ex2);
        controller.allSteps();
    }

    private void program3() throws ContainerException, ExpressionException, StatementException {
        /// bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        IStatement ex3 = new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()),
                         new CompoundStatement(new VariableDeclarationStatement("v",
                         new IntType()),new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                         new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignmentStatement("v",new ValueExpression(new IntValue(2))),
                         new AssignmentStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new VariableExpression("v"))))));
        /*
        controller.addStatementToCurrentProgram(ex3);
        controller.allSteps();
        */
        Repository r3 = new Repository();
        controller = new Controller(r3);
        controller.addProgram(ex3);
        controller.allSteps();
    }

    public void run() {
        boolean isDone = false;
        print_menu();
        while (!isDone) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("> your option: ");
            int command = scanner.nextInt();
            try {
                switch (command) {
                    case 0:
                        System.out.println("bye bye!\n");
                        isDone = true;
                        break;
                    case 1:
                        System.out.println("\t> program 1\n");
                        program1();
                        break;
                    case 2:
                        System.out.println("\t> program 2\n");
                        program2();
                        break;
                    case 3:
                        System.out.println("\t> program 3\n");
                        program3();
                        break;
                }
            } catch (ContainerException | ExpressionException | StatementException EX) {
                System.out.println(EX.getMessage());
            }
        }
    }
}
