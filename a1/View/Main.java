package View;

import java.util.Scanner;
import Model.*;
import Repository.*;
import Controller.*;

/*
4. La o ferma se cresc pasari, vaci si porci.
Sa se afiseze toate animalele care au greutatea mai mare decat 3kg.
 */

public class Main {
    public static void main(String[] args) {
        Repository repository = new Repository(10);
        Controller ctrl = new Controller(repository);
        menu(ctrl);
    }

    public static void populate(Controller controller) throws Exception {
        controller.addAnimal(new Bird(2));
        controller.addAnimal(new Bird(5));
        controller.addAnimal(new Pig(1));
        controller.addAnimal(new Cow(10));
    }

    public static void printMenu() {
        System.out.println("\n\t-------------------------------------------\n" +
                "\t| 1 | add an animal.                      |\n" +
                "\t-------------------------------------------\n" +
                "\t| 2 | remove an animal by index.          |\n" +
                "\t-------------------------------------------\n" +
                "\t| 3 | display all the animals.            |\n" +
                "\t-------------------------------------------\n" +
                "\t| 4 | filter animals with weight > 3 kg.  |\n" +
                "\t-------------------------------------------\n" +
                "\t| 0 | exit the program.                   |\n" +
                "\t-------------------------------------------\n");
    }

    public static void printAddMenu() {
        System.out.println("\n\t-------------------------------------------\n" +
                "\t| 1 | add a bird.                         |\n" +
                "\t-------------------------------------------\n" +
                "\t| 2 | add a cow.                          |\n" +
                "\t-------------------------------------------\n" +
                "\t| 3 | add a pig.                          |\n" +
                "\t-------------------------------------------\n");
    }

    public static void addAnimal(Controller controller) throws Exception {
        printAddMenu();
        Scanner scanner = new Scanner(System.in);
        System.out.println("> your want to add: ");
        int command = scanner.nextInt();
        if(command == 1) {
            System.out.println("> the weight of the bird: ");
            int weight = scanner.nextInt();
            controller.addAnimal(new Bird(weight));
            System.out.println("> the bird was added successfully!");
        }
        else
            if(command == 2) {
                System.out.println("> the weight of the cow: ");
                int weight = scanner.nextInt();
                controller.addAnimal(new Cow(weight));
                System.out.println("> the cow was added successfully!");
            }
            else
                if(command == 3) {
                    System.out.println("> the weight of the pig: ");
                    int weight = scanner.nextInt();
                    controller.addAnimal(new Pig(weight));
                    System.out.println("> the pig was added successfully!");
                }
                else {
                    System.out.println("invalid command!");
                }
    }

    public static void removeAnimal(Controller controller) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("> the index from where to remove the animal: ");
        int index_to_delete = scanner.nextInt();
        controller.removeAnimal(index_to_delete);
        System.out.println("> the animal was removed successfully!");
    }

    public static void displayAllAnimals(Controller controller) {
        IAnimal[] allAnimals = controller.getAllAnimals();
        if(controller.length() == 0) {
            System.out.println("the list is empty!\n");
        }
        else {
            System.out.println("\n");
            int index = 0;
            for(IAnimal animal : allAnimals) {
                if(animal != null)
                    System.out.println("\t" + index + " | " + animal);
                index++;
            }
        }
    }

    public static void filterAnimals(Controller controller) {
        IAnimal[] filteredAnimals = controller.filterAnimals(3);
        if(filteredAnimals == null) {
            System.out.println("no animals over 3 kg!\n");
        }
        else {
            System.out.println("\n");
            int index = 0;
            for(IAnimal animal : filteredAnimals) {
                if(animal != null)
                    System.out.println("\t" + index + " | " + animal);
                index++;
            }
        }
    }

    public static void menu(Controller controller) {
        boolean ok = true;
        try {
            populate(controller);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        while(ok) {
            printMenu();
            Scanner scanner = new Scanner(System.in);
            try {
                System.out.println("> your option: ");
                int command = scanner.nextInt();
                switch (command) {
                    case 0:
                        break;
                    case 1:
                        addAnimal(controller);
                        break;
                    case 2:
                        removeAnimal(controller);
                        break;
                    case 3:
                        displayAllAnimals(controller);
                        break;
                    case 4:
                        filterAnimals(controller);
                        break;
                }
                if (command == 0) {
                    break;
                }
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

