import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class UpdateGui extends JFrame{
    private JLabel title;
    private JLabel site;
    private JTextField sitefield;
    private JLabel username;
    private JTextField usernamefield;
    private JLabel password;
    private JTextField passwordfield;
    private JButton update;
    private JButton clear;
    private JButton cancel;
    private int confirmchange;
    private GridBagConstraints constraints;

    public UpdateGui(Dimension size){
        this.setSize(size);
        this.getContentPane().setLayout(new GridBagLayout());
        this.getContentPane().setSize((int)Math.round(this.getWidth() * 0.9), (int) Math.round(this.getHeight() * 0.9));
        this.constraints = new GridBagConstraints();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("Password Manager");
        Color theme = new Color(102,255,102);
        this.getContentPane().setBackground(theme);
        this.formatGui(theme);
        this.setVisible(true);
    }

    private void formatGui(Color theme) {
        JPanel pane = new JPanel();
        pane.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        title = new JLabel("Update MySQL Password");
        title.setFont(new Font("TimesRoman", Font.BOLD, 25));
        constraints.gridx = 0;
        constraints.gridy = 0;
        pane.add(title,constraints);

        JLabel filler = new JLabel(" ");
        filler.setSize(1,100);
        constraints.gridx = 0;
        constraints.gridy = 2;
        pane.add(filler, constraints);

        site = new JLabel("Site");
        constraints.gridx = 0;
        constraints.gridy = 2;
        pane.add(site,constraints);

        sitefield = new JTextField(25);
        constraints.gridx = 0;
        constraints.gridy = 3;
        pane.add(sitefield,constraints);

        username = new JLabel("Username:");
        constraints.gridy = 4;
        pane.add(username, constraints);

        usernamefield = new JTextField(25);
        constraints.gridx = 0;
        constraints.gridy = 5;
        pane.add(usernamefield, constraints);

        password = new JLabel("New Password:");
        constraints.gridy = 6;
        pane.add(password, constraints);

        passwordfield = new JTextField(25);
        constraints.gridy = 7;
        pane.add(passwordfield, constraints);

        this.constraints.gridx = 0;
        this.constraints.gridy = 0;
        pane.setBackground(theme);
        this.add(pane, this.constraints);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new GridBagLayout());

        update = new JButton("Update");
        constraints.gridx = 0;
        constraints.gridy = 0;
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!sitefield.getText().strip().equals("") && !usernamefield.getText().strip().equals("") && !passwordfield.getText().strip().equals("")){
                    tryUpdate(sitefield.getText(), usernamefield.getText(), passwordfield.getText());
                }else{
                    JOptionPane.showMessageDialog(null, "Empty values cannot be used in update", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPane.add(update, constraints);

        clear = new JButton("Clear");
        constraints.gridx = 2;
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearAllText();
            }
        });
        buttonPane.add(clear, constraints);

        cancel = new JButton("Cancel");
        constraints.gridx = 4;
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });
        buttonPane.add(cancel, constraints);
        buttonPane.setBackground(theme);

        this.constraints.gridy = 1;
        this.constraints.ipady = 20;
        this.add(buttonPane, this.constraints);

    }

    private void clearAllText() {
        this.sitefield.setText("");
        this.usernamefield.setText("");
        this.passwordfield.setText("");
    }

    private void tryUpdate(String site, String user, String password) {
        Connection con = getConnection();
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(*) AS count FROM password WHERE site_name = '" + site + "' AND username = '" + user + "';");
            rs.next();
            if(rs.getInt("count") == 1){
                confirmchange = JOptionPane.showConfirmDialog(null, "Are you sure you want to update the " +
                        "password on " + site + " for " + user + "?", "Confirm Change", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(confirmchange == 0 && ContainsPassword(password, site, user)){//if yes and no matches
                    String query = "UPDATE password SET password = ? WHERE site_name = ? AND username = ?;";
                    PreparedStatement preparedstmt = con.prepareStatement(query);

                    preparedstmt.setString(1,password);
                    preparedstmt.setString(2,site);
                    preparedstmt.setString(3,user);

                    preparedstmt.execute();
                    JOptionPane.showMessageDialog(null, "Password Updated", "Password Updated", JOptionPane.INFORMATION_MESSAGE);
                }else if(confirmchange == 0 && !ContainsPassword(password, site, user)){
                    JOptionPane.showMessageDialog(null, "New password can't match current one", "No Update to Passwords", JOptionPane.WARNING_MESSAGE);
                }else{//its no
                    JOptionPane.showMessageDialog(null, "Update of password cancelled", "No Update to Passwords", JOptionPane.INFORMATION_MESSAGE);
                }
                rs.close();
                st.close();
                con.close();
            }else{
                JOptionPane.showMessageDialog(null, "No Password for " + user + " to update on " + site, "Error Updating", JOptionPane.OK_OPTION);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error attempting update on MySQL Server", "Error", JOptionPane.OK_OPTION);
        }
    }

    private Connection getConnection(){
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
    private boolean ContainsPassword(String password, String site, String user){
        boolean ret = true;
        try{
            Connection con = getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT password FROM password WHERE site_name = '" + site + "' AND username = '" + user + "';");
            rs.next();
            if(password.equals(rs.getString("password"))){
                ret = false;
            }
            rs.close();
            st.close();
            con.close();
        }catch(Exception e){
            e.printStackTrace();
            ret = true;
        }
        return ret;

    }

}
