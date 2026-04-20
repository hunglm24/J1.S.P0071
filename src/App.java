public class App {
    public static void main(String[] args) {
        ViewTask view = new ViewTask();
        Controller controller = new Controller();
        InputData inp = new InputData();
        
        int choice;
        boolean loop = true;
        
        while (loop) {
            view.printMenu();
            choice = inp.inputInteger("Your choice: ", 1, 4);
            switch (choice) {
                case 1:
                    controller.addTask();
                    break;
                case 2:
                    controller.deleteTask();
                    break;
                case 3:
                    controller.displayTasks();
                    break;
                case 4:
                    loop = false;
                    break;
            }
        }
    }
}