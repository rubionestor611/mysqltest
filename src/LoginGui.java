import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class LoginGui extends JFrame implements SQLConnection{
    private JLabel password;
    private JTextField passwordfield;
    private JLabel port;
    private JTextField portfield;
    private Container c;
    private JButton login;
    private JButton clear;
    private JButton cancel;
    private Font font;

    public LoginGui(){
        font = new Font("Arial", Font.BOLD, 17);
        c = this.getContentPane();
        c.setLayout(null);
        c.setBackground(new Color(50,70,110));

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(340,200);
        this.setResizable(false);
        this.setTitle("Login");

        setupPort();

        setupPassword();

        setupButtons();

        this.setVisible(true);
    }

    private void setupButtons() {
        this.login = new JButton("Login");
        this.login.setBackground(this.port.getBackground());
        this.login.setSize(97, 40);
        this.login.setFont(this.font);
        this.login.setLocation(this.port.getX() + 2, this.password.getY() + this.password.getHeight() + 5);
        this.login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(hasConnection(portfield.getText(), passwordfield.getText())){
                    MainGui main = new MainGui(portfield.getText(), passwordfield.getText());
                    dispose();
                }
            }
        });
        c.add(this.login);

        this.clear = new JButton("Clear");
        this.clear.setBackground(this.login.getBackground());
        this.clear.setSize(this.login.getSize());
        this.clear.setLocation(this.login.getX() + this.login.getWidth() + 2, this.login.getY());
        this.clear.setFont(font);
        this.clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearAllText();
            }
        });
        c.add(this.clear);

        this.cancel = new JButton("Cancel");
        this.cancel.setBackground(this.clear.getBackground());
        this.cancel.setSize(this.clear.getSize());
        this.cancel.setLocation(this.clear.getX() + this.clear.getWidth() + 2, this.clear.getY());
        this.cancel.setFont(font);
        this.cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        c.add(this.cancel);
    }

    private void clearAllText() {
        this.portfield.setText("");
        this.passwordfield.setText("");
    }

    private void setupPort(){
        this.port = new JLabel("Port:");
        this.port.setBackground(Color.GRAY);
        this.port.setOpaque(true);
        this.port.setFont(font);
        this.port.setSize(100,50);
        this.port.setBorder(new EtchedBorder());
        port.setLocation(10,5);
        c.add(this.port);

        this.portfield = new JTextField(15);
        portfield.setMinimumSize(new Dimension(200,50));
        portfield.setSize(portfield.getMinimumSize());
        portfield.setBorder(new EtchedBorder());
        portfield.setFont(font);
        portfield.setLocation(port.getX() + port.getWidth() + 2, this.port.getY());
        c.add(portfield);
    }
    private void setupPassword(){
        this.password = new JLabel("Password:");
        this.password.setSize(this.port.getSize());
        this.password.setOpaque(true);
        this.password.setBorder(this.port.getBorder());
        this.password.setBackground(this.port.getBackground());
        this.password.setFont(font);
        password.setLocation(this.port.getX(),this.port.getY() + this.port.getHeight() + 1);
        c.add(this.password);

        this.passwordfield = new JTextField(15);
        this.passwordfield.setSize(portfield.getSize());
        this.passwordfield.setFont(font);
        this.passwordfield.setBorder(portfield.getBorder());
        passwordfield.setLocation(password.getX() + password.getWidth() + 2, this.password.getY());
        c.add(passwordfield);
    }
    private boolean hasConnection(String port, String password){
        try{
            Connection conn = getConnection(port,password);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Invalid login credentials for local MySQL server.",
            "Login Fail", JOptionPane.OK_OPTION);
        }
        return false;
    }
}
