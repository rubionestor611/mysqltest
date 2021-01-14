import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class RemoveGUI extends JFrame implements SQLConnection{
    private Container c;
    private JLabel title;
    private JTextPane warning;
    private JTextField sitefield;
    private JTextField usernamefield;
    private JTextField passwordfield;
    private JButton remove;
    private JButton clear;
    private JButton cancel;

    public RemoveGUI(Dimension size, String port, String password){
        this.setSize(size);

        c = this.getContentPane();
        c.setLayout(null);
        c.setBackground(SystemColor.YELLOW);

        title = new JLabel("Remove Password from Local MySQL Database");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setSize(700,30);
        title.setForeground(Color.BLACK);
        title.setLocation((this.getWidth() / 2 ) - (title.getWidth() / 2), this.getHeight() / 10);
        c.add(title);

        warning = new JTextPane();
        warning.setFont(new Font("Arial", Font.BOLD, 15));
        warning.setSize(700,50);
        warning.setBackground(Color.BLACK);
        StyledDocument doc = warning.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        warning.setText("Warning: This will be a temporary removal once completed.\n Be sure you are done tracking at this point before continuing.");
        warning.setEditable(false);
        warning.setLocation(title.getX(), title.getY() + title.getHeight() );
        warning.setForeground(Color.RED);
        c.add(warning);

        setupSitefield();

        setupUsernameField();

        setupPasswordField();

        setupButtons(port, password);

        this.setVisible(true);
    }

    private void setupButtons(String port, String sqlpass) {
        remove = new JButton("Remove");
        remove.setLocation(sitefield.getX() + sitefield.getWidth() + 30, sitefield.getY());
        remove.setSize(sitefield.getWidth() / 4, sitefield.getHeight());
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                trytoRemovePasswordfromMySQL(port,sqlpass);
            }
        });
        c.add(remove);

        clear = new JButton("Clear");
        clear.setLocation(remove.getX(), usernamefield.getY());
        clear.setSize(remove.getSize());
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.remove(usernamefield);
                c.remove(sitefield);
                c.remove(passwordfield);
                setupSitefield();
                setupUsernameField();
                setupPasswordField();
            }
        });
        c.add(clear);

        cancel = new JButton("Cancel");
        cancel.setLocation(clear.getX(), passwordfield.getY());
        cancel.setSize(clear.getSize());
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        c.add(cancel);
    }

    private void trytoRemovePasswordfromMySQL(String port, String pass) {
        try{
            if(!ContainsPassword(passwordfield.getText(), sitefield.getText(), usernamefield.getText(), port, pass)){
                confirmRemoval(port,pass);
            }else{
                JOptionPane.showMessageDialog(null,"No password for " + usernamefield.getText() +
                        " on " + sitefield.getText() + " with password specified to remove", "No Password Found", JOptionPane.INFORMATION_MESSAGE);
            }
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error removing password from MySQL", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void confirmRemoval(String port, String sqlpass) {
        int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this password from the database FOREVER?", "Confirm Choice",
                JOptionPane.YES_NO_OPTION);
        if(choice == JOptionPane.YES_OPTION){
            removePassword(sitefield.getText(), usernamefield.getText(), passwordfield.getText(), port, sqlpass);
        }else{
            JOptionPane.showMessageDialog(null, "Data deletion cancelled", "Process Cancelled", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void removePassword(String site, String user, String password, String port, String sqlpass) {
        try{
            Connection c = getConnection(port,sqlpass);
            Statement st = c.createStatement();
            st.executeUpdate("DELETE FROM password WHERE username = '" + user + "' AND site_name = '" + site + "' AND password = '" + password + "';");
            if(ContainsPassword(password, site, user, port, sqlpass)){
                JOptionPane.showMessageDialog(null, "Yay, dead weight removed from database!", "Password Removed", JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null, "Error removing password, it can still be found in the database...", "Error", JOptionPane.ERROR_MESSAGE);
            }

        }catch(Exception e){
            e.printStackTrace();

        }

    }

    private void setupPasswordField() {
        passwordfield = new JTextField();
        passwordfield.setText("Enter Password...");
        passwordfield.setFont(new Font("Arial", Font.PLAIN, 15));
        passwordfield.setLocation(usernamefield.getX(), usernamefield.getY() + 40);
        passwordfield.setSize(usernamefield.getSize());
        passwordfield.setForeground(Color.GRAY);
        passwordfield.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(passwordfield.getText().equals("Enter Password...")){
                    passwordfield.setText("");
                    passwordfield.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(passwordfield.getText().equals("")){
                    passwordfield.setText("Enter Password...");
                    passwordfield.setForeground(Color.GRAY);
                }
            }
        });
        c.add(passwordfield);
    }

    private void setupUsernameField() {
        usernamefield = new JTextField();
        usernamefield.setText("Enter Username...");
        usernamefield.setFont(new Font("Arial", Font.PLAIN, 15));
        usernamefield.setLocation(sitefield.getX(), sitefield.getY() + 40);
        usernamefield.setSize(sitefield.getSize());
        usernamefield.setForeground(Color.GRAY);
        usernamefield.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(usernamefield.getText().equals("Enter Username...")){
                    usernamefield.setText("");
                    usernamefield.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(usernamefield.getText().equals("")){
                    usernamefield.setText("Enter Username...");
                    usernamefield.setForeground(Color.GRAY);
                }
            }
        });
        c.add(usernamefield);
    }

    private void setupSitefield() {
        sitefield = new JTextField();
        sitefield.setText("Enter Site Name...");
        sitefield.setFont(new Font("Arial", Font.PLAIN, 15));
        sitefield.setLocation(title.getX() - 15,warning.getY() + 120);
        sitefield.setSize(this.getWidth() / 3, 30);
        sitefield.setForeground(Color.GRAY);
        sitefield.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(sitefield.getText().equals("Enter Site Name...")){
                    sitefield.setText("");
                    sitefield.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(sitefield.getText().equals("")){
                    sitefield.setText("Enter Site Name...");
                    sitefield.setForeground(Color.GRAY);
                }
            }
        });
        c.add(sitefield);
    }
    private boolean ContainsPassword(String password, String site, String user, String port, String sqlpass){
        boolean ret = true;
        try{
            Connection con = getConnection(port, sqlpass);
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
