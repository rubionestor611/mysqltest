import java.sql.Connection;
import java.sql.DriverManager;

public interface SQLConnection {
    default Connection getConnection(String port, String password){
        try{
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:" + port + "/passwords";
            String username = "root";
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
