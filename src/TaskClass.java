/**
 * Created by User on 4/4/2017.
 */
public class TaskClass {
    private String taskID, userID, description, category,deadline;
    int[] deadlineInt = new int[3];

    public TaskClass (){
        userID = CurrentUser.getUsername();
        taskID = TeazyDBMnpln.generateTaskID(userID);
        description = "<C'est un task default.>";
        category = "<C'est une categori default>";
        deadline = "<Vous pouvez le faire un jour>";
    }
    public boolean isDone(){
        return true;
    }

    public TaskClass (String desc) {
        this();
        userID = CurrentUser.getUsername();
        taskID = TeazyDBMnpln.generateTaskID(userID);
        this.description = desc;
    }

    public TaskClass (String desc, String deadline) {
        this();
        userID = CurrentUser.getUsername();
        taskID = TeazyDBMnpln.generateTaskID(userID);
        this.description = desc;
        this.deadline = deadline;

        String temp[];
        temp = deadline.split("/");

        for(int i = 0; i < 3; i++) {
            deadlineInt[i] = Integer.parseInt(temp[i]);
        }
    }

    public TaskClass(String description, String category,String deadline) {
        this.userID = CurrentUser.getUsername();
        this.taskID = TeazyDBMnpln.generateTaskID(userID);
        this.description = description;
        this.category = category;
        this.deadline = deadline;
        String temp[];
        temp = deadline.split("/");

        for(int i = 0; i < 3; i++) {
            deadlineInt[i] = Integer.parseInt(temp[i]);
        }
    }

    public TaskClass(String userID, String taskID, String description, String category,String deadline) {
        this.userID = userID;
        this.taskID = taskID;
        this.description = description;
        this.category = category;
        this.deadline = deadline;
        String temp[];
        temp = deadline.split("/");

        for(int i = 0; i < 3; i++) {
            deadlineInt[i] = Integer.parseInt(temp[i]);
        }
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

    public String getCategory() {
        return category;
    }
}
