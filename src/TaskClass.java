/**
 * Created by User on 4/4/2017.
 */
public class TaskClass {
    private String taskID, userID, description, header, category,deadline;

    public TaskClass(String userID, String taskID, String description, String header, String category,String deadline) {
        this.taskID = taskID;
        this.userID = userID;
        this.description = description;
        this.header = header;
        this.category = category;
        this.deadline = deadline;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTaskID() {
        return taskID;
    }

    public String getUserID() {
        return userID;
    }

    public String getDescription() {
        return description;
    }

    public String getHeader() {
        return header;
    }

    public String getCategory() {
        return category;
    }
}
