import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SetGui extends JFrame implements SQLConnection{
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
    private RandomPasswordGenPane generator;

    public SetGui(Dimension size, String port, String sqlpassword){
        setTitle("Password Manager");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(size);

        c = this.getContentPane();
        c.setLayout(null);
        c.setBackground(Color.BLACK);

        title = new JLabel("Add Password to MYSQL Database");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(Color.ORANGE);
        title.setSize(700,30);
        title.setLocation((this.getWidth() / 2 ) - (title.getWidth() / 2), this.getHeight() / 10);
        c.add(title);

        site = new JLabel("Site:");
        site.setFont(new Font("Arial", Font.PLAIN, 20));
        site.setSize(100,20);
        site.setForeground(Color.ORANGE);
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
        username.setForeground(Color.ORANGE);
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
        password.setForeground(Color.ORANGE);
        password.setLocation(username.getX(), username.getY() + 60);
        c.add(password);

        passwordfield = new JTextField();
        passwordfield.setFont(new Font("Arial", Font.PLAIN, 15));
        passwordfield.setSize(190, 20);
        passwordfield.setLocation(password.getX() + 150, password.getY());
        c.add(passwordfield);

        results = new JTextArea();
        results.setFont(new Font("Arial", Font.PLAIN, 15));
        results.setSize(350,400);
        results.setLocation((int) Math.round(this.getWidth() * 0.6), site.getY());
        results.setEditable(false);
        results.setLineWrap(true);
        results.setWrapStyleWord(true);
        c.add(results);

        submit = new JButton("Submit");
        submit.setFont(new Font("Arial", Font.PLAIN, 15));
        submit.setSize(100,20);
        submit.setLocation(password.getX(), password.getY() + 60);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                results.setText(trytoSubmit(sitefield.getText(), usernamefield.getText(), passwordfield.getText(), port, sqlpassword));
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

        generator = new RandomPasswordGenPane((passwordfield.getX() + passwordfield.getWidth()) - (this.submit.getX()), this.getWidth() / 4);
        generator.setLocation(this.submit.getX(), this.submit.getY() + this.submit.getHeight() + 5);
        generator.getGenerate().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                passwordfield.setText(generator.generatePassword());
            }
        });
        c.add(generator);
    }

    private String trytoSubmit(String site, String user, String pass, String port, String sqlpass) {
        String ret = "";
        try{
            Connection con = getConnection(port, sqlpass);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(*) AS count FROM password WHERE site_name = '" + site + "' AND username = '" + user + "';");
            rs.next();
            if(rs.getInt("count") == 0){
                String query = "INSERT INTO password VALUES (?,?,?)";
                PreparedStatement preparedstmt = con.prepareStatement(query);

                preparedstmt.setString(1,site);
                preparedstmt.setString(2,user);
                preparedstmt.setString(3,pass);

                preparedstmt.execute();
                ret = "Password Added!\n";
                ret += "-site: " + site + "\n";
                ret += "-username: " + user + "\n";
                ret += "-password: " + pass + "\n";
                rs.close();
                st.close();
                con.close();
            }else{
                ret = "A password for " + user + " on " + site + " already exists";
            }

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
}
