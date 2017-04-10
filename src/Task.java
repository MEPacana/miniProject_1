public class Task {
    String description, category, deadline;
    int[] deadlineInt = new int[3];
    public Task (){
        description = "<C'est un task default.>";
        category = "<C'est une categori default>";
        deadline = "<Vous pouvez le faire un jour>";
    }

    public Task (String desc) {
        this();
        this.description = desc;
    }

    public Task (String desc, String deadline) {
        this();
        this.description = desc;
        this.deadline = deadline;

        String temp[];
        temp = deadline.split("/");

        for(int i = 0; i < 3; i++) {
            deadlineInt[i] = Integer.parseInt(temp[i]);
        }
    }

    public Task (String desc, String deadline, String category) {
        this.description = desc;
        this.deadline = deadline;
        this.category = category;

        String temp[];
        temp = deadline.split("/");

        for(int i = 0; i < 3; i++) {
            deadlineInt[i] = Integer.parseInt(temp[i]);
        }
    }

    public String getDescription () {
        return description;
    }
}