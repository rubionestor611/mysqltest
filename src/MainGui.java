import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGui extends GUI{
    public MainGui(){
        this.frame = new JFrame(this.guiname);
        this.frame.setSize(this.WindowSize);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setupGUI2(this.frame);
        this.frame.setVisible(true);
    }
    protected void setupGUI(JFrame frame){
        frame.getContentPane().setBackground(Color.blue);
        JLabel label = createJLabel("Welcome to Nestor's Password Manager!",
                SwingConstants.CENTER, new Font("TimesRoman", Font.BOLD, 25), Color.WHITE,
                frame.getHeight() / 10, frame.getWidth());
        frame.add(BorderLayout.NORTH, label);
        String name = "ADD PASSWORD";
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SetGui setGUI = new SetGui(frame.getSize());
                setGUI.setVisible(true);
            }
        };

        Dimension dim = new Dimension(100,100);
        JButton addpassword = createJButton(name,listener,dim);
        frame.add(addpassword);
        name = "GET PASSWORD";
        listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GetGui getgui = new GetGui(frame.getSize());
            }
        };
        frame.add(createJLabel("", SwingConstants.CENTER, null,Color.BLACK,frame.getHeight() / 10, frame.getWidth()));
        JButton getpassword = createJButton(name, listener, dim);
        frame.add(getpassword);

        frame.setLayout(new GridLayout(5,7));
    }
    private void setupGUI2(JFrame frame){
        JPanel pane = new JPanel();
        pane.setBackground(Color.blue);
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        JLabel title = createJLabel("Welcome to Nestor's Password Manager!",SwingConstants.CENTER,
                new Font("Arial", Font.BOLD, 25), Color.WHITE,frame.getHeight() / 10, frame.getWidth());
        pane.add(title);
        
    }
}
