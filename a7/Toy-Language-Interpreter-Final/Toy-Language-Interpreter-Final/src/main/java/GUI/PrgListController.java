package GUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import Model.ADT.*;
import Model.Expression.*;
import Model.Statement.*;
import Model.Type.*;
import Model.Value.IValue;
import Model.Value.IntValue;
import Model.PrgState;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;

import Controller.Controller;
import Repo.Repo;
import Repo.IRepo;
import javafx.util.Pair;

public class PrgListController implements Initializable
{
    @FXML
    ListView<Controller> myPrgList;
    @FXML
    Button runButton;

    public void setUp(ObservableList<Controller> myData) {
        // ex 1:  int v; v = 2; Print(v)
        /*IStmt ex1 = new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));

        // ex 2: a=2+3*5;b=a+1;Print(b)
        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()), new CompStmt(new VarDeclStmt("b",new IntType()),
                new CompStmt(new AssignStmt("a", new ArithExp('+',new ValueExp(new IntValue(2)),new ArithExp('*',
                        new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),  new CompStmt(
                        new AssignStmt("b",new ArithExp('+',new VarExp("a"), new ValueExp(new IntValue(1)))),
                        new PrintStmt(new VarExp("b"))))));

        // ex 3: bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()), new CompStmt(new VarDeclStmt("v",
                new IntType()),new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new IntValue(2))),
                        new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                        VarExp("v"))))));

        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompStmt(new OpenRFileStmt(new VarExp("varf")),
                                new CompStmt(new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
                                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                                        new CloseRFileStmt(new VarExp("varf"))))))))));

        IStmt ex5 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new VarExp("v")),
                                                new PrintStmt(new VarExp("a")))))));

        IStmt ex6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                                new PrintStmt(new ArithExp('+', new ValueExp(new IntValue(5)),
                                                        new ReadHeapExp(new ReadHeapExp(new VarExp("a"))))))))));

        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))), new CompStmt(
                                new WriteHeapStmt("v", new ValueExp(new IntValue(30))),
                                new PrintStmt(new ArithExp('+', new ValueExp(new IntValue(5)),
                                        new ReadHeapExp(new VarExp("v"))))))));

        IStmt ex8 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new NewStmt("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a")))))))));

        IStmt ex9 = new CompStmt(new VarDeclStmt("v",new IntType()),new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(4))),
                new CompStmt(new WhileStmt(new RelationalExp(new VarExp("v"),new ValueExp(new IntValue(0)),">"),
                        new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v",new ArithExp('-',
                                new VarExp("v"), new ValueExp(new IntValue(1)))))),
                        new PrintStmt(new VarExp("v")))));

        IStmt ex10 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt( new VarDeclStmt("a", new RefType(new IntType())),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))), new CompStmt(
                        new NewStmt("a", new ValueExp(new IntValue(22))), new CompStmt(
                        new ForkStmt(new CompStmt(new WriteHeapStmt("a", new ValueExp(new IntValue(30))),
                                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                        new CompStmt(new PrintStmt(new VarExp("v")),
                                                new PrintStmt(new ReadHeapExp(new VarExp("a"))))))),
                        new CompStmt(new PrintStmt(new VarExp("v")),
                                new PrintStmt(new ReadHeapExp(new VarExp("a")))))))));*/

        IStmt ex1 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new ForStmt("v", new ValueExp(new IntValue(0)), new ValueExp(new IntValue(3)),
                                        new ArithExp('+', new VarExp("v"), new ValueExp(new IntValue(1))),
                                        new ForkStmt(new PrintStmt(new VarExp("v")))),
                                        new PrintStmt(new ArithExp('*', new VarExp("v"), new ValueExp(new IntValue(10)))
                                )
                        )
                )
        );

        IStmt ex2 = new CompStmt(
                new VarDeclStmt("a", new IntType()),
                new CompStmt(
                        new VarDeclStmt("b", new IntType()),
                        new CompStmt(
                                new VarDeclStmt("c",new IntType()),
                                new CompStmt(
                                        new AssignStmt("a", new ValueExp(new IntValue(1))),
                                        new CompStmt(
                                                new AssignStmt("b",new ValueExp(new IntValue(2))),
                                                new CompStmt(
                                                        new AssignStmt("c", new ValueExp(new IntValue(5))),
                                                        new CompStmt(
                                                                new SwitchStmt(new ArithExp('*',new VarExp("a"), new ValueExp(new IntValue(10))),
                                                                        new ArithExp('*', new VarExp("b"), new VarExp("c")), new ValueExp(new IntValue(10)),
                                                                        new CompStmt(new PrintStmt(new VarExp("a")),new PrintStmt(new VarExp("b"))),
                                                                        new CompStmt(new PrintStmt(new ValueExp(new IntValue(100))), new PrintStmt(new ValueExp(new IntValue(200)))),
                                                                        new PrintStmt(new ValueExp(new IntValue(300)))),
                                                                new PrintStmt(new ValueExp(new IntValue(300))))))))));

        IStmt forked1 = new CompStmt(
                new AcquireStmt(new VarExp("cnt")),
                new CompStmt(
                        new WriteHeapStmt("v1", new ArithExp('*',new ReadHeapExp(new VarExp("v1")),new ValueExp(new IntValue(10)))),
                        new CompStmt(
                                new PrintStmt(new ReadHeapExp(new VarExp("v1"))),
                                new ReleaseStmt(new VarExp("cnt")))));

        IStmt forked2 = new CompStmt(
                new AcquireStmt(new VarExp("cnt")),
                new CompStmt(
                        new WriteHeapStmt("v1", new ArithExp('*',new ReadHeapExp(new VarExp("v1")),new ValueExp(new IntValue(10)))),
                        new CompStmt(
                                new WriteHeapStmt("v1", new ArithExp('*',new ReadHeapExp(new VarExp("v1")),new ValueExp(new IntValue(2)))),
                                new CompStmt(
                                        new PrintStmt(new ReadHeapExp(new VarExp("v1"))),
                                        new ReleaseStmt(new VarExp("cnt"))))));

        IStmt ex3 = new CompStmt(
                new VarDeclStmt("v1", new RefType(new IntType())),
                new CompStmt(
                        new VarDeclStmt("cnt", new IntType()),
                        new CompStmt(
                                new NewStmt("v1",new ValueExp(new IntValue(1))),
                                new CompStmt(
                                        new NewSemaphoreStmt(new VarExp("cnt"), new ReadHeapExp(new VarExp("v1"))),
                                        new CompStmt(
                                                new ForkStmt(forked1),
                                                new CompStmt(
                                                        new ForkStmt(forked2),
                                                        new CompStmt(
                                                                new AcquireStmt(new VarExp("cnt")),
                                                                new CompStmt(
                                                                        new PrintStmt(new ArithExp('-',new ReadHeapExp(new VarExp("v1")),new ValueExp(new IntValue(1)))),
                                                                        new ReleaseStmt(new VarExp("cnt"))))))))));

        IStmt ex4 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(10))),
                        new CompStmt(
                                new ForkStmt(
                                        new CompStmt(new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1)))),
                                                new CompStmt(new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1)))),
                                new PrintStmt(new VarExp("v"))))),
                        new CompStmt(
                                new SleepStmt(10),
                                new PrintStmt(new ArithExp('*', new VarExp("v"), new ValueExp(new IntValue(10))))
                        )
                        )
                )
        );

        /*IStmt forked3 = new CompStmt(
                new AcquireStmt(new VarExp("v")),
                new CompStmt(
                        new PrintStmt(new ValueExp(new IntValue(1))),
                        new ReleaseStmt(new VarExp("v"))));

        IStmt forked4 = new CompStmt(
                new AcquireStmt(new VarExp("v")),
                new CompStmt(
                        new PrintStmt(new ValueExp(new IntValue(2))),
                        new ReleaseStmt(new VarExp("v"))));

        IStmt forked5 = new CompStmt(
                new AcquireStmt(new VarExp("v")),
                new CompStmt(
                        new PrintStmt(new ValueExp(new IntValue(3))),
                        new ReleaseStmt(new VarExp("v"))));

        IStmt ex5 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new CompStmt(
                                new ForkStmt(forked3),
                                new CompStmt(
                                        new ForkStmt(forked4),
                                        new ForkStmt(forked5)))));*/

        // Typechecking
        //Vector<IStmt> check = new Vector<>(Arrays.asList(ex1, ex2, ex3, ex4, ex5, ex6, ex7, ex8, ex9, ex10, ex11));

        Vector<IStmt> check = new Vector<>(Arrays.asList(ex1, ex2, ex3, ex4));

        for (IStmt stmt : check) {
            try {
                IDict<String, IType> typeEnv = new Dict<>();
                stmt.typecheck(typeEnv);
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Toy Language - Current program finished");
                alert.setHeaderText(null);
                alert.setContentText("The types aren't valid!");
                Button confirm = (Button) alert.getDialogPane().lookupButton( ButtonType.OK );
                confirm.setDefaultButton(false);
                confirm.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
                alert.showAndWait();
                System.exit(1);
            }
        }

        for (int i = 0; i < 4; i++) {
            String filename = "log" + i + ".txt";
            IRepo repo = new Repo(filename);
            Controller controller = new Controller(repo);
            IStack<IStmt> exeStack = new MyStack<>();
            IDict<String, IValue> symTable = new Dict<>();
            IList<IValue> out = new MyList<>();
            IDict<String, BufferedReader> fileTable = new Dict<>();
            IDict<Integer, IValue> heap = new Heap<>();
            ISemaphore<Pair<Integer, List<Integer>>> semaphoreTable = new Semaphore();
            exeStack.push(check.get(i));
            PrgState myPrgState = new PrgState(exeStack, symTable, out, check.get(i), fileTable, heap, semaphoreTable);
            controller.addProgram(myPrgState);
            myData.add(controller);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Controller> myData = FXCollections.observableArrayList();

        this.setUp(myData);
        myPrgList.setItems(myData);

        myPrgList.getSelectionModel().selectFirst();
        runButton.setOnAction(new EventHandler<>() {
            @Override
            public void handle (ActionEvent e) {
                Stage programStage = new Stage();
                Parent programRoot;
                Callback<Class<?>, Object> controllerFactory = type -> {
                    if (type == PrgRunController.class)
                        return new PrgRunController(myPrgList.getSelectionModel().getSelectedItem());
                    
                    else {
                        try {
                            return type.getDeclaredConstructor().newInstance() ;
                        }

                        catch (Exception exc) {
                            System.err.println("Could not create controller for " + type.getName());
                            throw new RuntimeException(exc);
                        }
                    }
                };

                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProgramLayout.fxml"));
                    fxmlLoader.setControllerFactory(controllerFactory);
                    programRoot = fxmlLoader.load();
                    Scene programScene = new Scene(programRoot);
                    programStage.setTitle("Toy Language - Program running");
                    programStage.setScene(programScene);
                    programStage.show();
                }

                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
    }
}
