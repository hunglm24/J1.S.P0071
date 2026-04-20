import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TaskBusiness {

    private final ArrayList<Task> taskList;
    private int lastId = 0;

    public TaskBusiness() {
        taskList = new ArrayList<>();
    }

    // ===== Generate ID chuẩn =====
    public int getNextId() {
        return ++lastId;
    }

    // ===== Check overlap =====
    private boolean isDuplicate(Date date, String assignee, double from, double to) {
        for (Task t : taskList) {
            if (t.getDate().equals(date) &&
                t.getAssignee().equalsIgnoreCase(assignee)) {

                if (from < t.getPlanTo() && to > t.getPlanFrom()) {
                    return true;
                }
            }
        }
        return false;
    }

    // ===== Add Task =====
    public int addTask(String taskTypeID, String requirementName, String date,
                       String planFrom, String planTo,
                       String assignee, String reviewer) throws Exception {

        try {
            int typeID = Integer.parseInt(taskTypeID);
            double from = Double.parseDouble(planFrom);
            double to = Double.parseDouble(planTo);

            // Validate Task Type
            if (typeID < 1 || typeID > 4) {
                throw new Exception("Task Type must be 1-4");
            }

            // Validate Time
            if (from < 8 || to > 17.5) {
                throw new Exception("Working time must be between 8.0 and 17.5");
            }
            if (from >= to) {
                throw new Exception("From must be less than To");
            }

            // Validate .0 / .5
            if (!isValidTime(from) || !isValidTime(to)) {
                throw new Exception("Time must be x.0 or x.5");
            }
       
            // Validate Assignee
            if (assignee.trim().equalsIgnoreCase(reviewer.trim())) {
                throw new Exception("Assignee and reviewer must be different");
            }

            // Check overlap
            Date d = parseDate(date);
            if (isDuplicate(d, assignee, from, to)) {
                throw new Exception("Task overlap!");
            }

            // Create Task (FIX truyền Date)
            Task t = new Task(getNextId(), typeID, requirementName, date, from, to, assignee, reviewer);
            taskList.add(t);

            return t.getId();

        } catch (NumberFormatException e) {
            throw new Exception("Number format error: " + e.getMessage());
        }
    }

    // ===== Delete =====
    public void deleteTask(int id) throws Exception {
        if (taskList.isEmpty()) {
            throw new Exception("List is empty.");
        }

        boolean removed = taskList.removeIf(t -> t.getId() == id);

        if (!removed) {
            throw new Exception("Task ID not found.");
        }
    }

    // ===== Get Data =====
    public ArrayList<Task> getDataTasks() {
        return new ArrayList<>(taskList);
    }

    public boolean isEmpty() {
        return taskList.isEmpty();
    }

    // ===== Helper =====

    private boolean isValidTime(double time) {
        return (time * 10) % 5 == 0;
    }

    private Date parseDate(String dateStr) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        df.setLenient(false);
        return df.parse(dateStr);
    }

    private boolean isFutureDate(Date date) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date today = df.parse(df.format(new Date())); // bỏ giờ
        return date.after(today);
    }
}