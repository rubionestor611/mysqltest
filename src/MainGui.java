import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGui extends GUI{
    public MainGui(){
        this.frame = new JFrame(this.guiname);
        this.frame.setSize(this.WindowSize);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setupGUI(this.frame);
        this.frame.setVisible(true);
    }
    protected void setupGUI(JFrame frame){
        frame.getContentPane().setBackground(Color.blue);
        JLabel label = new JLabel("Welcome to Nestor's Password Manager!", SwingConstants.CENTER);
        label.setSize(frame.getWidth(), frame.getHeight() / 10);
        label.setFont(new Font("TimesRoman", Font.BOLD, 25));
        frame.add(BorderLayout.NORTH, label);
        String name = "ADD PASSWORD";
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                SetGui setGUI = new SetGui();
                frame.setVisible(true);
            }
        };
        Dimension dim = new Dimension(100,100);
        JButton addpassword = createJButton(name,listener,dim);
        frame.add(addpassword);
        name = "Get Password";
        listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                GetGui getgui = new GetGui();
                frame.setVisible(true);
            }
        };
        JButton getpassword = createJButton(name, listener, dim);
        frame.add(getpassword);
        frame.setLayout(new GridLayout(5,7));
    }

    private JButton createJButton(String name, ActionListener listener, Dimension dim){
        JButton button = new JButton(name);
        button.setSize(dim);
        button.addActionListener(listener);
        return button;
    }
}
