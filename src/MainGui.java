import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGui extends GUI{
    public MainGui(String port, String password){
        this.frame = new JFrame(this.guiname);
        frame.setTitle("Password Manager");
        this.frame.setSize(1000, this.WindowSize.height);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setupGUI2(this.frame, port, password);
        this.frame.setResizable(false);
        this.frame.setVisible(true);
    }
    private void setupGUI2(JFrame frame, String port, String password){
        JButton get_password = new JButton("Get Password");
        get_password.setSize((int)Math.round(frame.getWidth() * 0.5),50);
        get_password.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GetGui getGui = new GetGui(frame.getSize(), port, password);
            }
        });

        JButton add_password = new JButton("Add Password");
        add_password.setSize(get_password.getWidth(),50);
        add_password.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SetGui setGui = new SetGui(frame.getSize(), port, password);
                setGui.setVisible(true);
            }
        });
        JButton update_password = new JButton("Update Password");
        update_password.setSize(add_password.getWidth(),50);
        update_password.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateGui updateGui = new UpdateGui(frame.getSize(), port, password);
            }
        });

        JButton remove_password = new JButton("Remove Password");
        remove_password.setSize(update_password.getWidth(),50);
        remove_password.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RemoveGUI removeGUI = new RemoveGUI(frame.getSize(), port, password);
            }
        });

        Container c = frame.getContentPane();
        c.setLayout(null);
        c.setBackground(Color.cyan);

        JLabel title = new JLabel("Welcome to Nestor's Password Manager!");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setSize(700,40);
        title.setLocation((frame.getWidth() / 5 ) , frame.getHeight() / 10);
        c.add(title);

        get_password.setLocation(frame.getWidth() / 4, title.getY() + 70);
        c.add(get_password);

        add_password.setLocation(get_password.getX(), get_password.getY() + 70);
        c.add(add_password);

        update_password.setLocation(add_password.getX(), add_password.getY() + 70);
        c.add(update_password);

        remove_password.setLocation(update_password.getX(), update_password.getY() + 70);
        c.add(remove_password);

    }

    @Override
    protected void setupGUI(JFrame frame) {
        return;
    }
}
