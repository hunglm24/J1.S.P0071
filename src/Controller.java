import java.util.ArrayList;

public class Controller {

    private final TaskBusiness taskBusiness = new TaskBusiness();
    private final ViewTask view = new ViewTask();
    private final InputData inp = new InputData();

    // ===== Option 1: Add Task =====
    public void addTask() {
        System.out.println("------------ Add Task ---------------");

        try {
            String reqName = inp.inputString("Requirement Name: ", Constant.REGEX_TEXT);

            String taskTypeID = inp.inputString(
                    "Task Type (1-Code, 2-Test, 3-Design, 4-Review): ",
                    "[1-4]" // FIX regex
            );

            String date = inp.inputDate("Date (dd-MM-yyyy): ");

            double from = inp.inputDouble("From (8.0 - 17.5): ", 8.0, 17.5);
            double to = inp.inputDouble("To (" + (from + 0.5) + " - 17.5): ", from + 0.5, 17.5);

            String assignee = inp.inputString("Assignee: ", Constant.REGEX_NAME);
            String reviewer = inp.inputString("Reviewer: ", Constant.REGEX_NAME);

            // ✅ ĐÚNG THỨ TỰ theo TaskBusiness của bạn
            int id = taskBusiness.addTask(
                    taskTypeID,
                    reqName,
                    date,
                    String.valueOf(from),
                    String.valueOf(to),
                    assignee,
                    reviewer
            );

            view.displayMess("Task added successfully. ID = " + id);

        } catch (Exception ex) {
            view.displayMess("Error adding task: " + ex.getMessage());
        }
    }

    // ===== Option 2: Delete Task =====
    public void deleteTask() {
        System.out.println("--------- Delete Task --------");

        if (taskBusiness.isEmpty()) {
            view.displayMess("List is empty, nothing to delete.");
            return;
        }

        try {
            int id = inp.inputInteger("Enter ID: ", 1, Integer.MAX_VALUE);

            taskBusiness.deleteTask(id);

            view.displayMess("Task deleted successfully.");

        } catch (Exception ex) {
            view.displayMess("Error deleting task: " + ex.getMessage());
        }
    }

    // ===== Option 3: Display Tasks =====
    public void displayTasks() {
        System.out.println("----------- Task List -----------");

        ArrayList<Task> list = taskBusiness.getDataTasks();

        if (list.isEmpty()) {
            view.displayMess("No tasks found.");
            return;
        }

        System.out.format("%-5s %-15s %-10s %-15s %-10s %-15s %-15s\n",
                "ID", "Name", "Type", "Date", "Time", "Assignee", "Reviewer");

        for (Task t : list) {
            System.out.println(t); // gọi toString()
        }
    }
}