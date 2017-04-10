import com.sun.org.apache.xpath.internal.SourceTree;
import jdk.nashorn.internal.ir.visitor.SimpleNodeVisitor;

import java.sql.*;
import java.io.File;
import java.util.Scanner;
import java.util.Vector;

public class TeazyDBMnpln {
    public static void main(String[] args) throws Exception {
        // register the driver 
        int choice;
        String sDriverName = "org.sqlite.JDBC";
        Class.forName(sDriverName);
        Scanner sc = new Scanner(System.in);
        // now we set up a set of fairly basic string variables to use in the body of the code proper
        String sTempDb = "TeazyDB.db";
        // which will produce a legitimate Url for SqlLite JDBC :
        // jdbc:sqlite:hello.db
        // create a database connection;
        databaseConnect(sTempDb);
        do {
            System.out.println("Please choose the following: \n1. Add Student\n2. Add Task" +
                    "\n3. Check Account \n4. Delete Student\n5. Delete Task\n6.Get Tasks\n7. Edit Name\n" +
                    "8.Edit Username\n9.Edit Password");
            choice = sc.nextInt();

            //Adding a student
            if (choice == 1) {
                System.out.println("UserID,Password,Fname,Lname,School");
                String tempUserID, tempPassword, tempFname, tempLname, tempSchool;
                System.out.println("You have chosen to populate a Student database");
                tempUserID = sc.next();
                tempPassword = sc.next();
                tempFname = sc.next();
                tempLname = sc.next();
                tempSchool = sc.next();
                addStudentDB(tempUserID, tempPassword, tempFname, tempLname, tempSchool);
            }
            //Adding a task
            else if (choice == 2) {
                System.out.println("UserID,TaskID,Desc,Header,Category,Deadline");
                String  tempUserID, tempTaskID, tempDescription, tempHeader, tempCategory, tempDeadline;
                tempUserID = sc.next();
                tempTaskID = sc.next();
                tempDescription = sc.next();
                tempHeader = sc.next();
                tempCategory = sc.next();
                tempDeadline = sc.next();
                addTaskDB(tempUserID,tempTaskID, tempDescription, tempHeader, tempCategory, tempDeadline);
            }
            //Check if account exists
            //returns boolean if it already exists
            else if (choice == 3) {
                String tempUserID, tempPassword;
                tempUserID = sc.next();
                tempPassword = sc.next();

                //check if they match an account
                if (usernamePasswordCheck(tempUserID, tempPassword)) {
                    System.out.println("Account Exists");
                } else {
                    System.out.println("Username or password is wrong");
                }
            } //Deleting a student
            //returns boolean if it is deleated
            else if (choice == 4) {
                String tempUserID;
                tempUserID = sc.next();
                if (!deleteStudent(tempUserID)) {
                    System.out.println("Student does not exist");
                } else {
                    System.out.println("Successfully deleated");
                }
            }
            //Deleting task
            //returns boolean if task is deleted
            else if(choice == 5){
                String tempTaskID,tempUserID;
                tempUserID = sc.next();
                tempTaskID = sc.next();
                if(!deleteTask(tempUserID, tempTaskID)){
                    System.out.println("Invalid Task");
                }else{
                    System.out.println("Task Deleted");
                }
            }
            else if(choice == 6){
                Vector<TaskClass> taskVector = new Vector<TaskClass>();
                String tempUserID;
                tempUserID = sc.next();
                //send UserID
                //must be checked first if UserID exists
                taskVector = getTasks(tempUserID);
                System.out.println("Tasks");
                for(int i = 0 ; i < taskVector.size() ; i++){
                    System.out.println("UserID: "+taskVector.get(i).getUserID()+
                    "\nTaskID: "+taskVector.get(i).getTaskID() +
                    "\nHeader: "+taskVector.get(i).getHeader() +
                    "\nDescription: "+taskVector.get(i).getDescription()+
                    "\nCategory: "+taskVector.get(i).getDescription() +
                    "\nDeadline: "+taskVector.get(i).getDeadline());
                }
            }else if(choice == 7){

            }
            else if(choice == 8){

            }else if(choice ==9){

            }
        } while (choice != 0);
        System.exit(0);
    }

    public static Vector<TaskClass> getTasks(String userID){
        Vector<TaskClass> taskVector = new Vector<TaskClass>();
        if (!usernameExists(userID)) {
            System.out.println("Invalid username" + userID);
        }else{
            String sSelectTaskID = new String("SELECT taskID FROM TASK WHERE userID = ?");
            String sSelectUserID = new String("SELECT userID FROM TASK WHERE userID = ?");
            String sSelectdescription = new String("SELECT description FROM TASK WHERE userID = ?");
            String sSelectheader = new String("SELECT header FROM TASK WHERE userID = ?");
            String sSelectcategory = new String("SELECT category FROM TASK WHERE userID = ?");
            String sSelectdeadline = new String("SELECT deadline FROM TASK WHERE userID = ?");
            try(Connection conn = DriverManager.getConnection("jdbc:sqlite:TeazyDB.db");
                PreparedStatement pstmt = conn.prepareStatement(sSelectUserID);
                PreparedStatement pstmt1 = conn.prepareStatement(sSelectTaskID);
                PreparedStatement pstmt2 = conn.prepareStatement(sSelectdescription);
                PreparedStatement pstmt3 = conn.prepareStatement(sSelectheader);
                PreparedStatement pstmt4 = conn.prepareStatement(sSelectcategory);
                PreparedStatement pstmt5 = conn.prepareStatement(sSelectdeadline)){

                pstmt.setString(1,userID);
                pstmt1.setString(1,userID);
                pstmt2.setString(1,userID);
                pstmt3.setString(1,userID);
                pstmt4.setString(1,userID);
                pstmt5.setString(1,userID);
                ResultSet rs = pstmt.executeQuery();
                ResultSet rs1 = pstmt1.executeQuery();
                ResultSet rs2 = pstmt2.executeQuery();
                ResultSet rs3 = pstmt3.executeQuery();
                ResultSet rs4 = pstmt4.executeQuery();
                ResultSet rs5 = pstmt5.executeQuery();

                if(rs.getFetchSize() == rs1.getFetchSize() && rs1.getFetchSize() == rs2.getFetchSize() &&
                        rs2.getFetchSize() == rs3.getFetchSize() && rs3.getFetchSize() == rs4.getFetchSize() &&
                        rs4.getFetchSize() == rs5.getFetchSize()) {
                    for(int i = 0; rs.next() && rs1.next() && rs2.next() && rs3.next() && rs4.next() && rs5.next()  ; i++) {
                        System.out.println("Task with taskheader: " + rs1.getString("taskID") + "exists");

                        taskVector.addElement(new TaskClass(rs.getString("userID"),rs1.getString("taskID"),rs2.getString("description"),
                                rs3.getString("header"),rs4.getString("category"),rs5.getString("deadline")));
                    }
                    return taskVector;
                }else{
                    System.out.println("Something is wrong");
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        System.out.println("this should not go here");
        return taskVector;
    }

    public static boolean usernamePasswordCheck(String username, String password) {
        String sMakeSelect = new String("SELECT userID FROM STUDENT WHERE userID = ?" +
                " AND password = ?");
        System.out.println(sMakeSelect);

        //check if password exists
        if (!usernameExists(username)) {
            System.out.println("Invalid username" + username);
            return false;
        } else if (!passwordExists(password)) {
            System.out.println("Invalid password" + password);
            return false;
        } else {
            //check if username and password match
            try {
                if (dbExists("TeazyDB.db")) {
                    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:TeazyDB.db");
                    PreparedStatement pstmt = conn.prepareStatement(sMakeSelect)) {

                        pstmt.setString(1, username);
                        pstmt.setString(2, password);
                        ResultSet rs = pstmt.executeQuery();

                        if (!rs.isBeforeFirst()) {
                            return false;
                        } else {
                            while (rs.next()) {
                            }
                            return true;
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("This shouldn't happen");
        return false;
    }

    public static boolean usernameExists(String username) {
        String sMakeSelect = new String("SELECT userID FROM STUDENT WHERE userID = ?");
        try {
            if (dbExists("TeazyDB.db")) {
                try (Connection conn = DriverManager.getConnection("jdbc:sqlite:TeazyDB.db");
                     PreparedStatement pstmt = conn.prepareStatement(sMakeSelect)) {
                    pstmt.setString(1, username);
                    ResultSet rs = pstmt.executeQuery();
                    if (!rs.isBeforeFirst()) {
                        System.out.println("Username does not exist");
                        return false;
                    } else {
                        while (rs.next()) {
                            System.out.println("Username: " + rs.getString("userID") + "exists");
                        }
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("This shouldn't happen");
        return false;
    }

    public static boolean taskIDExists(String taskID) {
        String sMakeSelect = new String("SELECT taskID FROM TASK WHERE taskID = ?");
        System.out.println(sMakeSelect);
        try {
            if (dbExists("TeazyDB.db")) {
                try (Connection conn = DriverManager.getConnection("jdbc:sqlite:TeazyDB.db");
                     PreparedStatement pstmt = conn.prepareStatement(sMakeSelect)) {
                    pstmt.setString(1, taskID);
                    ResultSet rs = pstmt.executeQuery();

                    if (!rs.isBeforeFirst()) {
                        System.out.println("Task does not exist");
                        return false;
                    } else {
                        while (rs.next()) {
                            System.out.println("Task with taskheader: " + rs.getString("taskID") + "exists");
                        }
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("This shouldn't happen in task");
        return false;
    }

    public static boolean deleteTask(String userID, String taskID) {
        String sDeleteStud = new String("DELETE FROM TASK WHERE taskID = ? AND " +
                " userID = ? ");
        if (!usernameExists(userID)) {
            System.out.println("Invalid username: "+ userID);
            return false;
        }if(!taskIDExists(taskID)){
            System.out.println("Invalid TaskID: "+taskID);
            return false;
        }
        else {
            try {
                if (dbExists("TeazyDB.db")) {
                    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:TeazyDB.db");
                         PreparedStatement pstmt = conn.prepareStatement(sDeleteStud)) {

                        pstmt.setString(1, taskID);
                        pstmt.setString(2, userID);
                        pstmt.executeUpdate();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static boolean deleteStudent(String userID) {
        String sDeleteStud = new String("DELETE FROM STUDENT WHERE userID = ?");
        String sDeleteTasks = new String("DELETE FROM TASK WHERE userID = ?");
        if (!usernameExists(userID)) {
            return false;
        }
        else {
            try {
                if (dbExists("TeazyDB.db")) {
                    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:TeazyDB.db");
                         PreparedStatement pstmt = conn.prepareStatement(sDeleteStud);
                         PreparedStatement pstmt2 = conn.prepareStatement(sDeleteTasks);) {

                        pstmt.setString(1, userID);
                        pstmt.setString(1, userID);
                        pstmt.executeUpdate();
                        pstmt2.executeUpdate();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }


    public static boolean passwordExists(String password){
        String sMakeSelect = new String("SELECT password FROM STUDENT WHERE password = ?");
       try{
            if(dbExists("TeazyDB.db")) {
                try(Connection conn = DriverManager.getConnection("jdbc:sqlite:TeazyDB.db");
                    PreparedStatement pstmt  = conn.prepareStatement(sMakeSelect)) {
                    pstmt.setString(1, password);
                    ResultSet rs = pstmt.executeQuery();
                    if (!rs.isBeforeFirst()) {
                        System.out.println("Password does not exist");
                        return false;
                    } else {
                        while (rs.next()) {
                            System.out.println("Password: "+rs.getString("password")+" exists");

                        }
                        return true;
                    }
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        System.out.println("This shouldn't happen");
        return false;
    }

    public static void addStudentDB(String userID, String password, String fname, String lname, String school){
        String sMakeInsert = "INSERT INTO STUDENT VALUES('"+userID+"','"+password+"','"+ fname+"','"+lname+"','"+school+"')";
        insertToDB(sMakeInsert); // general insert
    }

    public static void addTaskDB( String userID, String taskID, String description, String header, String category,String deadline){
        String sMakeInsert = "INSERT INTO TASK VALUES('" + taskID + "','"+userID+"','" + description +
                                                        "','" + header + "','" + category + "','" + deadline +"')";
        if(!usernameExists(userID)){
            System.out.println("Username: "+userID+" does not exist");
        }else {
            insertToDB(sMakeInsert); // general insert
        }
    }

    public static void insertToDB(String sMakeInsert){
        try {
            if (dbExists("TeazyDB.db")) {
                System.out.println("Adding Database");
                //try {
                Connection conn = DriverManager.getConnection("jdbc:sqlite:TeazyDB.db");
                try {
                    Statement stmt = conn.createStatement();
                    try {
                        stmt.executeUpdate(sMakeInsert);
                    } finally {
                        try {
                            stmt.close();
                        } catch (Exception ignore) {
                        }
                    }
                }finally {
                    try {
                        conn.close();
                    } catch (Exception ignore) {
                    }
                }
            } else {
                System.out.println("That Database does not yet exist");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        System.out.println("Nakagawas");
    }

    public static void databaseConnect(String sTempDb){
        int iTimeout = 30;
        String sMakeTable = "CREATE TABLE STUDENT (userID TEXT, password TEXT, fname TEXT, lname TEXT, school TEXT)";
        String sMakeTable2 = "CREATE TABLE TASK (taskID TEXT, userID TEXT, description TEXT, header TEXT, category TEXT,deadline TEXT)";
        String sMakeInsert = "INSERT INTO dummy VALUES(1,'Hello from the database')";
        String sMakeSelect = "SELECT response from dummy";
        String sJdbc = "jdbc:sqlite";
        String sDbUrl = sJdbc + ":" + sTempDb;
        try {
            if (dbExists(sTempDb)) //here's how to check
            {
                System.out.print("This database name already exists");
            } else {

                Connection conn = DriverManager.getConnection(sDbUrl);
                System.out.println("Wa pa na initialize");

                try {
                    Statement stmt = conn.createStatement();
                    try {
                        stmt.setQueryTimeout(iTimeout);
                        stmt.executeUpdate(sMakeTable);
                        stmt.executeUpdate(sMakeTable2);
                    } finally {
                        try {
                            stmt.close();
                        } catch (Exception ignore) {
                        }
                    }
                } finally {
                    try {
                        conn.close();
                    } catch (Exception ignore) {
                    }
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static boolean dbExists(String sTempDb) throws SQLException {
        File file = new File (sTempDb);
        if(file.exists()) //here's how to check
        {
            return true;
        }
        return false;
    }
}