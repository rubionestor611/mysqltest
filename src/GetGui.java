import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class GetGui extends GUI{
    public GetGui(Dimension frameSize){
        this.frame = new JFrame();
        this.frame.setSize(frameSize);
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setupGUI(frame);
        frame.setVisible(true);
    }

    @Override
    protected void setupGUI(JFrame frame) {
        frame.setTitle("Password Manager");
        frame.getContentPane().setBackground(Color.BLACK);
        JLabel title = createJLabel("Get Password Form", SwingConstants.CENTER,
                new Font("TimesRoman", Font.BOLD, 25),
                Color.WHITE, frame.getHeight() / 10, frame.getWidth());
        frame.add(BorderLayout.NORTH, title);
        JPanel pane = new JPanel();
        pane.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        JLabel site = new JLabel("Site Name:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        pane.add(site,constraints);

        JTextField sitefield = new JTextField(25);
        constraints.gridx = 2;
        constraints.gridy = 0;
        pane.add(sitefield,constraints);

        JLabel username = new JLabel("Username for Site:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        pane.add(username, constraints);

        JTextField userfield = new JTextField(25);
        constraints.gridx = 2;
        constraints.gridy = 2;
        pane.add(userfield,constraints);

        JTextField passwordfield = new JTextField(50);
        passwordfield.setFont(new Font("TimesRoman", Font.PLAIN, 15));
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 4;
        pane.add(passwordfield, constraints);

        JButton search = new JButton("Get Password");
        constraints.gridx = 3;
        constraints.gridy = 1;
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = getPassword(sitefield.getText(), userfield.getText());
                passwordfield.setText("");
                passwordfield.setText(password);
            }
        });
        pane.add(search, constraints);

        JLabel filler = new JLabel("   ");
        filler.setSize(10,10);
        constraints.gridx = 1;
        constraints.gridy = 0;
        pane.add(filler, constraints);

        JLabel filler2 = new JLabel("   ");
        constraints.gridy = 2;
        pane.add(filler2, constraints);

        JLabel filler3 = new JLabel("   ");
        constraints.gridx = 3;
        constraints.gridy = 1;
        pane.add(filler3, constraints);

        JLabel filler4 = new JLabel("   ");
        constraints.gridx = 0;
        constraints.gridy = 3;
        pane.add(filler4,constraints);

        frame.add(pane);

    }
    private String getPassword(String site, String user){
        Connection con = getConnection();
        String query = buildQuery(site, user);
        try {
            Statement st = con.createStatement();
            ResultSet resultSet = st.executeQuery(query);
            String result = "";
            if(resultSet.next()){
                System.out.println("results found");
                result = resultSet.getString("password");
            }else{
                result = "none found";
            }
            st.close();
            return result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return "Error searching for password";
        }
    }

    private String buildQuery(String site, String user) {
        if(site.isBlank() && user.isBlank()){
            JOptionPane.showMessageDialog(null, "Error retrieving info with two blank values");
            return null;
        }
        String query = "SELECT PASSWORD FROM password WHERE ";
        if(!site.isBlank() && !user.isBlank()){
            query += "site_name = '" + site + "' AND username = '" + user + "';";
        }else if(site.isBlank()){
            query += "name = '" + user + "';";
        }else{//name is blank
            query += "site_name = '" + site + "';";
        }
        System.out.println(query);
        return query;
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
