package View;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private Map<String, Command> commands;
    public TextMenu(){ commands = new HashMap<>(); }
    public void addCommand(Command c){ commands.put(c.getKey(),c);}
    private void printMenu(){
        for(Command com : commands.values()){
            String line=String.format("|%3s | %2s", com.getKey(), com.getDescription());
            System.out.println("-----------------------------------------------------------------------------");
            System.out.println(line);
        }
        System.out.println("-----------------------------------------------------------------------------\n");
    }
    public void show() {
        Scanner scanner = new Scanner(System.in);
        while(true){
            printMenu();
            System.out.printf("> input the option: ");
            String key = scanner.nextLine();
            Command com = commands.get(key);
            if (com == null) {
                System.out.println("invalid option. please choose an option from 0 to 4.\n");
                continue;
            }
            com.execute();
        }
    }
}
