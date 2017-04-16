import com.sun.org.apache.xpath.internal.SourceTree;
import jdk.nashorn.internal.ir.visitor.SimpleNodeVisitor;

import java.sql.*;
import java.io.File;
import java.util.ArrayList;
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
                    "\n3. Check Account \n4. Delete Student\n5. Delete Task\n6. Get Tasks\n7. Edit Name\n" +
                    "8. Edit Username\n9. Edit Password\n10. Get First Name\n11. Get Last Name\n12. Get School" +
                    "\n13. Get TaskCount\n14. Create Category\n15. Check Category\n16. Get Category");
            choice = sc.nextInt();

            //Adding a student
            if (choice == 1) {
                System.out.println("UserID,Password,fName,lName,School");
                String tempUserID, tempPassword, tempFName, tempLName, tempSchool;
                System.out.println("You have chosen to populate a Student database");
                tempUserID = sc.next();
                tempPassword = sc.next();
                tempFName = sc.next();
                tempLName = sc.next();
                tempSchool = sc.next();
                addStudentDB(tempUserID, tempPassword, tempFName, tempLName, tempSchool);
            }
            //Adding a task
            else if (choice == 2) {
                System.out.println("UserID,Desc,Category,Deadline");
                String  tempUserID, tempDescription,tempCategory, tempDeadline;
                tempUserID = sc.next();
                tempDescription = sc.next();
                tempCategory = sc.next();
                tempDeadline = sc.next();
                addTaskDB(tempUserID, tempDescription, tempCategory, tempDeadline);
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
                    "\nDescription: "+taskVector.get(i).getDescription()+
                    "\nCategory: "+taskVector.get(i).getDescription() +
                    "\nDeadline: "+taskVector.get(i).getDeadline());
                }
            }else if(choice == 7){
                String tempUserID, tempfName, templName;
                tempUserID =  sc.next();
                tempfName = sc.next();
                templName = sc.next();

                updateName(tempUserID,tempfName,templName);
            }
            else if(choice == 8){
                String tempUserID, tempUsername;
                tempUserID =  sc.next();
                tempUsername = sc.next();

                updateUsername(tempUserID,tempUsername);
            }else if(choice ==9){
                String tempUserID, tempPassword;
                tempUserID =  sc.next();
                tempPassword = sc.next();

                updatePassword(tempUserID,tempPassword);
            }
            else if(choice ==10){
                String tempUserID,tempFName;
                System.out.println("UserID");
                tempUserID =  sc.next();

                tempFName = getFName(tempUserID);
                System.out.println("The first name is "+tempFName);
            }
            else if(choice == 11){
                String tempUserID,tempLName;
                System.out.println("UserID");
                tempUserID =  sc.next();

                tempLName = getLName(tempUserID);
                System.out.println("The last name is "+tempLName);
            }else if(choice == 12){
                String tempUserID,school;
                System.out.println("UserID");
                tempUserID =  sc.next();

                school = getSchool(tempUserID);
                System.out.println("The school is "+school);
            }
            else if(choice == 13){
                String tempUserID;
                int taskNum;
                System.out.println("UserID");
                tempUserID =  sc.next();

                taskNum = getTaskCount(tempUserID);
                System.out.println("The count is "+taskNum);
            }
            else if(choice == 14){
                String tempCategory,tempUserID;
                System.out.println("UserID, Category");
                tempUserID =  sc.next();
                tempCategory = sc.next();
                addCategoryDB(tempUserID,tempCategory);
            }
            else if(choice == 15){
                String tempCategory;
                System.out.println("Category");
                tempCategory = sc.next();
                if(categoryExists(tempCategory)){
                    System.out.println("Category Exists");
                }else{
                    System.out.println("Category Doesn't Exist");
                }
            }
            else if(choice == 16){
                String tempUserID;
                System.out.println("UserID");
                tempUserID = sc.next();
                if(usernameExists(tempUserID)){
                    Vector<String> categories;
                    categories = getCategories(tempUserID);
                    for(int i = 0 ;i < categories.size(); i++){
                        System.out.println(categories.get(i));
                    }

                 }else{
                    System.out.println("Category Doesn't Exist");
                }
            }
        } while (choice != 0);
        System.exit(0);
    }
    ////////////////////////////////////////// A D D T O D A T A B A S E ///////////////////////////////////////////////////////////////////
    public static void addStudentDB(String userID, String password, String fname, String lname, String school){
        String sMakeInsert = "INSERT INTO STUDENT VALUES('"+userID+"','"+password+"','"+ fname+"','"+lname+"','"+school+"')";
         // general insert
        if(usernameExists(userID)){
            System.out.println("Username: "+userID+" already exists");
        }else{
            insertToDB(sMakeInsert);
            addCategoryDB(userID,"General");
        }
    }

    public static void addTaskDB( String userID, String description, String category,String deadline){
        String taskID;
        taskID = generateTaskID(userID);
        String sMakeInsert = "INSERT INTO TASK VALUES('" + taskID + "','"+userID+"','" + description +
                "','" + category + "','" + deadline +"')";
        if(!usernameExists(userID)){
            System.out.println("Username: "+userID+" does not exist");
        }else if(!categoryExists(category)){
            System.out.println("Category: "+category+" does not exist");
        }else{
            System.out.println("Nisud diri");
            System.out.println("UserID: "+userID+"\ntaskID"+taskID+"\ndescription: "+description+
                    "\ncategory: "+category+"\ndeadline"+deadline);
            insertToDB(sMakeInsert); // general insert
        }
    }

    public static void addCategoryDB( String userID, String category){
        String sMakeInsert = "INSERT INTO CATEGORYLIST VALUES('" + userID +"','"+ category + "')";
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
                databaseConnect("TeazyDB.db");
                System.out.println("That Database does not yet exist");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    ////////////////////////////////////////// C H E C K S///////////////////////////////////////////////////////////////////

    public static boolean usernamePasswordCheck(String username, String password) {
        String sMakeSelect = new String("SELECT userID FROM STUDENT WHERE userID = ?" +
                " AND password = ?");
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
                }else{
                    databaseConnect("TeazyDB.db");
                    System.out.println("That Database does not yet exist");
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
                            //System.out.println("Username: " + rs.getString("userID") + " exists");
                        }
                        return true;
                    }
                }
            }else{
                databaseConnect("TeazyDB.db");
                System.out.println("That Database does not yet exist");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("This shouldn't happen");
        return false;
    }

    public static boolean taskIDExists(String taskID) {
        String sMakeSelect = new String("SELECT taskID FROM TASK WHERE taskID = ?");
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
            }else{
                //Creating database
                System.out.println("Database doesn't exist yet. Making database now.");
                databaseConnect("TeazyDB.db");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("This shouldn't happen in task");
        return false;
    }

    public static boolean categoryExists(String category) {
        String sMakeSelect = new String("SELECT category FROM CATEGORYLIST WHERE category = ?");
       try {
            if (dbExists("TeazyDB.db")) {
                try (Connection conn = DriverManager.getConnection("jdbc:sqlite:TeazyDB.db");
                     PreparedStatement pstmt = conn.prepareStatement(sMakeSelect)) {
                    pstmt.setString(1, category);
                    ResultSet rs = pstmt.executeQuery();

                    if (!rs.isBeforeFirst()) {
                        System.out.println("Category does not exist");
                        return false;
                    } else {
                        while (rs.next()) {
                            System.out.println("category with taskheader: " + rs.getString("category") + "exists");
                        }
                        return true;
                    }
                }
            }else{
                //Creating database
                System.out.println("Database doesn't exist yet. Making database now.");
                databaseConnect("TeazyDB.db");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("This shouldn't happen in task");
        return false;
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
            }else{
                //Creating database
                System.out.println("Database doesn't exist yet. Making database now.");
                databaseConnect("TeazyDB.db");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        System.out.println("This shouldn't happen");
        return false;
    }

    ///////////////////////////////////////////////// D E L E T E ///////////////////////////////////////////////////////////////////////
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
                }else{
                    //Creating database
                    System.out.println("Database doesn't exist yet. Making database now.");
                    databaseConnect("TeazyDB.db");
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
                }else{
                    //Creating database
                    System.out.println("Database doesn't exist yet. Making database now.");
                    databaseConnect("TeazyDB.db");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
    ///////////////////////////////////////////////// U P D A T E  ///////////////////////////////////////////////////////////////////////

    public static void updateName(String userID, String fName, String lName) {
        String sUpdateName = "UPDATE STUDENT SET fname = ? , "
                + "lname = ? "
                + "WHERE userID = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:TeazyDB.db");
             PreparedStatement pstmt = conn.prepareStatement(sUpdateName)) {
            // set the corresponding param
            pstmt.setString(1, fName);
            pstmt.setString(2, lName);
            pstmt.setString(3, userID);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updatePassword(String userID, String password) {
        String sUpdateName = "UPDATE STUDENT SET password = ?"
                + "WHERE userID = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:TeazyDB.db");
             PreparedStatement pstmt = conn.prepareStatement(sUpdateName)) {

            // set the corresponding param
            pstmt.setString(1, password);
            pstmt.setString(2, userID);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateUsername(String userID, String username) {
        String sUpdateName = "UPDATE STUDENT SET userID = ?"
                + "WHERE userID = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:TeazyDB.db");
             PreparedStatement pstmt = conn.prepareStatement(sUpdateName)) {

            // set the corresponding param
            pstmt.setString(1, username);
            pstmt.setString(2, userID);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
/////////////////////////////////////////////// G E T T E R S//////////////////////////////////////////////////////////////////////////////////

    public static Vector<String> getCategories(String userID){
        Vector<String> categories = new Vector<String>();
        if (!usernameExists(userID)) {
            System.out.println("Invalid username" + userID);
        }else{
            String sSelectCategories = new String("SELECT CATEGORY FROM CATEGORYLIST WHERE userID = ?");
            try(Connection conn = DriverManager.getConnection("jdbc:sqlite:TeazyDB.db");
                PreparedStatement pstmt = conn.prepareStatement(sSelectCategories)){

                pstmt.setString(1,userID);
                ResultSet rs = pstmt.executeQuery();

                while(rs.next()){
                    categories.add(rs.getString("CATEGORY"));
                }
                return categories;
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        System.out.println("this should not go here");
        return categories;
    }

    public static Vector<TaskClass> sortTaskVector(Vector<TaskClass> taskVector){
        for(int i= 0;i< taskVector.size();i++){
            System.out.println(taskVector.get(i).getDeadline());
        }
        if(taskVector.size() == 0 || taskVector.size() == 1 ){
            return taskVector;
        }else {
            ArrayList<String> dateString = new ArrayList<String>();
            Vector<TaskClass> sortedTaskVector = new Vector<TaskClass>();
            for(int i = 0; i <taskVector.size() ; i++){
                dateString.add(taskVector.get(i).getDeadline());
            }
            dateString = CalendarDemo.dateSorter(dateString);
            int j = 0, i;
            while(taskVector.size() > 0){
                for(i = 0 ; i < taskVector.size(); i++){
                    if(dateString.get(j).compareTo(taskVector.get(i).getDeadline()) == 0){
                        break;
                    }
                }
                sortedTaskVector.addElement(taskVector.get(i));
                taskVector.remove(i);
                j++;
            }
            for(i = 0;i< taskVector.size();i++){
                System.out.println(taskVector.get(i).getDeadline());
            }
            return sortedTaskVector;
        }
    }

    public static String getFName(String userID){
        String fName = "";
        if (!usernameExists(userID)) {
            System.out.println("Invalid username" + userID);
        }else{
            String sSelectFName = new String("SELECT fname FROM STUDENT WHERE userID = ?");
            try(Connection conn = DriverManager.getConnection("jdbc:sqlite:TeazyDB.db");
                PreparedStatement pstmt = conn.prepareStatement(sSelectFName)){

                pstmt.setString(1,userID);
                ResultSet rs = pstmt.executeQuery();
                while(rs.next()) {
                    System.out.println("Student has the first name: "+rs.getString("fname"));
                        fName = rs.getString("fname");
                    }
                    return fName;
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        System.out.println("this should not go here");
        return fName;
    }
    public static String getLName(String userID){
        String lName = "";
        if (!usernameExists(userID)) {
            System.out.println("Invalid username" + userID);
        }else{
            String sSelectFName = new String("SELECT lname FROM STUDENT WHERE userID = ?");
            try(Connection conn = DriverManager.getConnection("jdbc:sqlite:TeazyDB.db");
                PreparedStatement pstmt = conn.prepareStatement(sSelectFName)){

                pstmt.setString(1,userID);
                ResultSet rs = pstmt.executeQuery();
                while(rs.next()) {
                    System.out.println("Student has the first name: "+rs.getString("lname"));
                    lName = rs.getString("lname");
                }
                return lName;
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        System.out.println("this should not go here");
        return lName;
    }

    public static int getTaskCount(String userID){
        int tasknum = 0;
        if (!usernameExists(userID)) {
            System.out.println("Invalid username" + userID);
        }else{
            String sGetCount = new String("SELECT COUNT(*) AS rowcount FROM TASK WHERE userID = ?");
            try(Connection conn = DriverManager.getConnection("jdbc:sqlite:TeazyDB.db");
                PreparedStatement pstmt = conn.prepareStatement(sGetCount)){

                pstmt.setString(1,userID);
                ResultSet rs = pstmt.executeQuery();
                tasknum = rs.getInt("rowcount");
                return tasknum;
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        System.out.println("this should not go here");
        return tasknum;
    }

    public static String generateTaskID(String userID){
        int taskID = getTaskCount(userID) +1;
        String tempTaskID="error";
        if (!usernameExists(userID)) {
            System.out.println("Invalid username" + userID);
        }else{
            while(taskIDExists(tempTaskID = userID+taskID)){
                System.out.println("tried: "+tempTaskID);
                taskID++;
            }
            return tempTaskID;
        }
        System.out.println("Username does not exist");
        return tempTaskID;
    }

    public static String getSchool(String userID){
        String school = "";
        if (!usernameExists(userID)) {
            System.out.println("Invalid username" + userID);
        }else{
            String sSelectFName = new String("SELECT school FROM STUDENT WHERE userID = ?");
            try(Connection conn = DriverManager.getConnection("jdbc:sqlite:TeazyDB.db");
                PreparedStatement pstmt = conn.prepareStatement(sSelectFName)){

                pstmt.setString(1,userID);
                ResultSet rs = pstmt.executeQuery();
                while(rs.next()) {
                    System.out.println("Student has the first name: "+rs.getString("school"));
                    school = rs.getString("school");
                }
                return school;
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        System.out.println("Username does not exist");
        return school;
    }

    public static void databaseConnect(String sTempDb){
        int iTimeout = 30;
        String sMakeTable = "CREATE TABLE STUDENT (userID TEXT, password TEXT, fname TEXT, lname TEXT, school TEXT)";
        String sMakeTable2 = "CREATE TABLE TASK (taskID TEXT, userID TEXT, description TEXT, category TEXT,deadline TEXT)";
        String sMakeTable3 = "CREATE TABLE CATEGORYLIST (userID TEXT, category TEXT)";
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
                        stmt.executeUpdate(sMakeTable3);
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