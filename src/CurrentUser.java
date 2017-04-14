/**
 * Created by Marie Curie on 10/04/2017.
 */
public class CurrentUser{
    private static String firstname;
    private static String lastname;
    private static String username;
    private static String password;
    private static String currentschool;
    private static int taskcount;

    public static int getTaskcount() {
        return taskcount;
    }

    public static void setTaskcount(int taskcount) {
        CurrentUser.taskcount = taskcount;
    }
    public static String getFirstname() {
        return firstname;
    }

    public static void setFirstname(String firstname) {
        CurrentUser.firstname = firstname;
    }

    public static String getLastname() {
        return lastname;
    }

    public static void setLastname(String lastname) {
        CurrentUser.lastname = lastname;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        CurrentUser.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        CurrentUser.password = password;
    }

    public static String getCurrentschool() {
        return currentschool;
    }

    public static void setCurrentschool(String currentschool) {
        CurrentUser.currentschool = currentschool;
    }
}
