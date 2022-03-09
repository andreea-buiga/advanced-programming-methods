package View;

import Controller.Controller;
import Exception.*;

import java.io.FileNotFoundException;
import java.io.IOException;

public class RunExample extends Command {
    private final Controller controller;
    public RunExample(String key, String desc,Controller controller){
        super(key, desc);
        this.controller = controller;
    }
    @Override
    public void execute() {
        try{
            controller.allSteps(); }
        catch (ContainerException | ExpressionException | StatementException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

