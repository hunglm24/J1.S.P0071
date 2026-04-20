import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Task {
    private int id;
    private int taskTypeID;
    private String requirementName;
    private String date;
    private double planFrom;
    private double planTo;
    private String assignee;
    private String reviewer;

    public Task() {
    }

    public Task(int id, int taskTypeID, String requirementName, String date, 
                double planFrom, double planTo, String assignee, String reviewer) throws Exception {
        this.id = id;
        this.taskTypeID = taskTypeID;
        this.requirementName = requirementName;
        setDate(date); // Validate and set Date
        setPlanFrom(planFrom);
        setPlanTo(planTo); // Validate logical time
        this.assignee = assignee;
        this.reviewer = reviewer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id >= 1) {
            this.id = id;
        } else {
            throw new IllegalArgumentException("Age must be between 18 and 50.");
        }
    }

    public int getTaskTypeID() {
        return taskTypeID;
    }

    public void setTaskTypeID(int taskTypeID) {
        this.taskTypeID = taskTypeID;
    }

    public String getRequirementName() {
        return requirementName;
    }

    public void setRequirementName(String requirementName) {
        this.requirementName = requirementName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) throws ParseException {
        if (date == null || date.trim().isEmpty()) {
            throw new ParseException("Date cannot be null or empty", 0);
        }
        SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT);
        sdf.setLenient(false); // Bắt buộc ngày tồn tại thực tế
        try {
            // Parse để kiểm tra tính hợp lệ
            Date parsedDate = sdf.parse(date);  
            // Format lại để lưu chuỗi chuẩn (ví dụ nhập 1/1/2023 -> lưu 01/01/2023)
            this.date = sdf.format(parsedDate);         
        } catch (ParseException e) {
            // Ném lỗi cụ thể ra ngoài để Controller xử lý (yêu cầu nhập lại)
            throw new ParseException("Invalid date format. Please use "+Constant.DATE_FORMAT, 0);
        }
    }

    public double getPlanFrom() {
        return planFrom;
    }

    public void setPlanFrom(double planFrom) throws Exception {
        if (planFrom < 8.0 || planFrom >= 17.5) {
            throw new Exception("Plan From must be within 8.0 and 17.5");
        }
        this.planFrom = planFrom;
    }

    public double getPlanTo() {
        return planTo;
    }

    public void setPlanTo(double planTo) throws Exception {
        if (planTo <= planFrom || planTo > 17.5) {
            throw new Exception("Plan To must be greater than Plan From and <= 17.5");
        }
        this.planTo = planTo;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getTaskTypeName() {
        switch (taskTypeID) {
            case 1: return "Code";
            case 2: return "Test";
            case 3: return "Design";
            case 4: return "Review";
            default: return "Unknown";
        }
    }

    @Override
    public String toString() {
        double time = planTo - planFrom;
        return String.format("%-5d %-15s %-10s %-15s %-5.1f %-15s %-15s",
                id, requirementName, getTaskTypeName(), date, time, assignee, reviewer);
    }
}