import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

public class GetGui extends GUI implements SQLConnection{
    public GetGui(Dimension frameSize, String port, String password){
        this.frame = new JFrame();
        this.frame.setSize(frameSize);
        this.frame.setBackground(Color.PINK);
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setupGUI(frame, port, password);
        frame.setVisible(true);
    }

    protected void setupGUI(JFrame frame, String port, String sqlpassword) {
        frame.setTitle("Password Manager");
        frame.getContentPane().setBackground(Color.BLACK);
        JLabel title = createJLabel("Get Password Form", SwingConstants.CENTER,
                new Font("TimesRoman", Font.BOLD, 25),
                Color.WHITE, frame.getHeight() / 10, frame.getWidth());
        frame.add(BorderLayout.NORTH, title);

        JPanel pane = new JPanel();
        pane.setLayout(new GridBagLayout());
        pane.setBackground(Color.PINK);
        GridBagConstraints constraints = new GridBagConstraints();

        JPanel fieldpane = new JPanel();
        fieldpane.setBackground(new Color(3, 252, 215));
        fieldpane.setLayout(new GridBagLayout());
        GridBagConstraints fieldpane_constraints = new GridBagConstraints();

        JLabel site = new JLabel("Site Name:");
        fieldpane_constraints.gridx = 0;
        fieldpane_constraints.gridy = 0;
        fieldpane_constraints.ipadx = 10;
        fieldpane.add(site,fieldpane_constraints);

        JTextField sitefield = new JTextField(25);
        fieldpane_constraints.gridx = 1;
        fieldpane_constraints.gridy = 0;
        fieldpane_constraints.ipadx = 10;
        fieldpane.add(sitefield,fieldpane_constraints);

        JLabel username = new JLabel("Username for Site:");
        fieldpane_constraints.gridx = 0;
        fieldpane_constraints.gridy = 1;
        fieldpane_constraints.ipadx = 10;
        fieldpane.add(username, fieldpane_constraints);

        JTextField userfield = new JTextField(25);
        fieldpane_constraints.gridx = 1;
        fieldpane_constraints.gridy = 1;
        fieldpane_constraints.ipadx = 10;
        fieldpane.add(userfield,fieldpane_constraints);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.ipady = 20;
        pane.add(fieldpane, constraints);

        JTextField passwordfield = new JTextField(50);
        passwordfield.setFont(new Font("TimesRoman", Font.PLAIN, 15));
        passwordfield.setHorizontalAlignment(JTextField.CENTER);
        passwordfield.setEditable(false);
        passwordfield.setBackground(new Color(3, 252, 215));
        constraints.gridx = 0;
        constraints.gridy = 2;
        pane.add(passwordfield, constraints);

        JPanel buttonpane = new JPanel();
        buttonpane.setLayout(new GridBagLayout());
        buttonpane.setBackground(new Color(3, 252, 215));
        buttonpane.setSize(frame.getWidth(), buttonpane.getHeight());
        GridBagConstraints buttonpane_constraints = new GridBagConstraints();

        JButton search = new JButton("Get Password");
        buttonpane_constraints.gridx = 0;
        buttonpane_constraints.gridy = 0;
        buttonpane_constraints.ipady = 5;
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = getPassword(sitefield.getText(), userfield.getText(), port, sqlpassword);
                passwordfield.setText("");
                passwordfield.setText(password);
            }
        });
        buttonpane.add(search, buttonpane_constraints);

        JButton copy = new JButton("Copy to Clipboard");
        copy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                copytoClipboard(passwordfield.getText());
            }
        });
        buttonpane_constraints.gridx = 2;
        buttonpane_constraints.gridy = 0;
        buttonpane_constraints.ipady = 5;
        buttonpane.add(copy,buttonpane_constraints);

        JButton clear = new JButton("Clear");
        buttonpane_constraints.gridx = 1;
        buttonpane_constraints.gridy = 1;
        buttonpane_constraints.ipady = 5;
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearallText(sitefield, userfield, passwordfield);
            }
        });
        buttonpane.add(clear, buttonpane_constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        pane.add(buttonpane, constraints);

        frame.add(pane);

    }

    private void copytoClipboard(String copy) {
        StringSelection stringSelection = new StringSelection(copy);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    private void clearallText(JTextField site, JTextField user, JTextField password) {
        site.setText("");
        user.setText("");
        password.setText("");
    }

    private String getPassword(String site, String user, String port, String password){
        Connection con = getConnection(port, password);
        String query = buildQuery(site, user);
        if(query != null){
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
        return "";
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

    @Override
    protected void setupGUI(JFrame frame) {
        return;
    }
}
