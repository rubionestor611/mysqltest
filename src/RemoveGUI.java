import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class RemoveGUI extends JFrame {
    private Container c;
    private JLabel title;
    private JTextPane warning;
    private JTextField sitefield;
    private JTextField usernamefield;
    private JTextField passwordfield;
    private JButton remove;
    private JButton clear;
    private JButton cancel;

    public RemoveGUI(Dimension size){
        this.setSize(size);

        c = this.getContentPane();
        c.setLayout(null);

        title = new JLabel("Remove Password from Local MySQL Databse");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setSize(700,30);
        title.setLocation((this.getWidth() / 2 ) - (title.getWidth() / 2), this.getHeight() / 10);
        c.add(title);

        warning = new JTextPane();
        warning.setFont(new Font("Arial", Font.BOLD, 15));
        warning.setSize(700,50);
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

        this.setVisible(true);
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
}
