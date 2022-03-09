import Repository.Repository;
import Controller.Controller;
import View.UI;

public class Main {
    public static void main(String[] args) {
        Repository repository = new Repository();
        Controller controller = new Controller(repository);
        UI ui = new UI(controller);
        ui.run();
    }
}
