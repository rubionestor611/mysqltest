import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SetGui extends JFrame{
    private JLabel title;
    private JLabel site;
    private JTextField sitefield;
    private JLabel username;
    private JTextField usernamefield;
    private JLabel password;
    private JTextField passwordfield;
    private JButton submit;
    private JButton clear;
    private Container c;
    private JTextArea results;

    public SetGui(Dimension size){
        setTitle("Password Manager");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(size);

        c = this.getContentPane();
        c.setLayout(null);

        title = new JLabel("Add Password to MYSQL Database");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setSize(700,30);
        title.setLocation((this.getWidth() / 2 ) - (title.getWidth() / 2), this.getHeight() / 10);
        c.add(title);

        site = new JLabel("Site:");
        site.setFont(new Font("Arial", Font.PLAIN, 20));
        site.setSize(100,20);
        site.setLocation(title.getX(),this.getHeight() / 5);
        c.add(site);

        sitefield = new JTextField();
        sitefield.setFont(new Font("Arial", Font.PLAIN, 15));
        sitefield.setSize(190,20);
        sitefield.setLocation(site.getX() + 150, site.getY());
        c.add(sitefield);

        username = new JLabel("Username:");
        username.setFont(new Font("Arial", Font.PLAIN, 20));
        username.setSize(100,20);
        username.setLocation(site.getX(), site.getY() + 60);
        c.add(username);

        usernamefield = new JTextField();
        usernamefield.setFont(new Font("Arial", Font.PLAIN, 15));
        usernamefield.setSize(190, 20);
        usernamefield.setLocation(username.getX() + 150, username.getY());
        c.add(usernamefield);

        password = new JLabel("Password:");
        password.setFont(new Font("Arial", Font.PLAIN, 20));
        password.setSize(100,20);
        password.setLocation(username.getX(), username.getY() + 60);
        c.add(password);

        passwordfield = new JTextField();
        passwordfield.setFont(new Font("Arial", Font.PLAIN, 15));
        passwordfield.setSize(190, 20);
        passwordfield.setLocation(password.getX() + 150, password.getY());
        c.add(passwordfield);

        results = new JTextArea();
        results.setFont(new Font("Arial", Font.PLAIN, 15));
        results.setSize(200,400);
        results.setLocation((int) Math.round(this.getWidth() * 0.7), site.getY());
        results.setEditable(false);
        results.setLineWrap(true);
        c.add(results);

        submit = new JButton("Submit");
        submit.setFont(new Font("Arial", Font.PLAIN, 15));
        submit.setSize(100,20);
        submit.setLocation(password.getX(), password.getY() + 60);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                results.setText(trytoSubmit(sitefield.getText(), usernamefield.getText(), passwordfield.getText()));
            }
        });
        c.add(submit);

        clear = new JButton("Clear");
        clear.setFont(new Font("Arial", Font.PLAIN, 15));
        clear.setSize(100,20);
        clear.setLocation(passwordfield.getX(), submit.getY());
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearAllText(usernamefield, sitefield, passwordfield, results);
            }
        });
        c.add(clear);

    }

    private String trytoSubmit(String site, String user, String pass) {
        String ret = "";
        try{
            Connection con = getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM password WHERE site_name = '" + site + "' AND username = '" + user + "';");
            rs.
            if(!rs.next()){
                String query = "INSERT INTO password VALUES (?,?,?)";
                PreparedStatement preparedstmt = con.prepareStatement(query);

                preparedstmt.setString(1,site);
                preparedstmt.setString(2,user);
                preparedstmt.setString(3,pass);

                preparedstmt.execute();
            }else{
                ret = "A password for " + user + " on " + site + " already exists";
            }
            ret = "Password Added!\n";
            ret += "-site: " + site + "\n";
            ret += "-username: " + user + "\n";
            ret += "-password: " + pass + "\n";
            rs.close();
            st.close();
            con.close();
        }catch(Exception e){
            e.printStackTrace();
            ret = "Error adding " + pass + " as " + user + "'s password on " + site + ".";
        }
        return ret;
    }

    private void clearAllText(JTextField user, JTextField site, JTextField pass, JTextArea res) {
        user.setText("");
        site.setText("");
        pass.setText("");
        res.setText("");
    }
    private static Connection getConnection(){
        try{
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3305/passwords";
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
