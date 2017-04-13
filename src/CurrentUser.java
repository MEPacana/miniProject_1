/**
 * Created by Marie Curie on 10/04/2017.
 */
public class CurrentUser{
    public static String firstname;
    public static String lastname;
    public static String username;
    public static String password;
    public static String currentschool;
    public static int taskcount;


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
