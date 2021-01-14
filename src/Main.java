import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        LoginGui login = new LoginGui();
    }
}
    /*Connection c = getConnection();
    SelectAllFromTable(c);
    private static void SelectAllFromTable(Connection con) {
        String command = getSQLCommand();
        ArrayList<Employee> emplist = new ArrayList<>();
        try{
            Statement statement = con.createStatement();
            while(!command.equals("DONE")){
                ResultSet resultSet = statement.executeQuery(command);

                while(resultSet.next()){
                    int id = resultSet.getInt("emp_id");
                    String first = resultSet.getString("first_name");
                    String last = resultSet.getString("last_name");
                    Date birth = resultSet.getDate("birth_day");
                    String sex = resultSet.getString("sex");
                    int salary = resultSet.getInt("salary");
                    int supervisor = resultSet.getInt("super_id");
                    int branch = resultSet.getInt("branch_id");
                    emplist.add(new Employee(id,first,last,birth,sex,salary,supervisor,branch));
                }
                for(Employee e : emplist){
                    System.out.println(e);
                }
                emplist.clear();
                command = getSQLCommand();
            }
            statement.close();

        }catch(Exception e){
            System.out.println(e);
        }
    }

    private static String getSQLCommand() {
        System.out.print("SQL Command: ");
        String ret = scan.nextLine();
        scan.reset();
        return ret;
    }

    public static Connection getConnection(){
        try{
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3305/giraffe";
            String username = "root";
            String password = "Golazohiguain9";
            Class.forName(driver);

            Connection con = DriverManager.getConnection(url,username,password);
            System.out.println("Connected");
            return con;
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }
}
/*
import java.sql.Connection;

public class Main {
    public static void main(String[] args){

    }
    private static Connection establishConnection(){
        try{
            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3305/testdb"
        }catch(Exception e){
            System.out.println(e);
        }
    }
}

 */
