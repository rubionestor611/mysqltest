import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public abstract class GUI{
    protected JFrame frame = new JFrame();
    protected Dimension WindowSize  = Toolkit.getDefaultToolkit().getScreenSize();
    protected String guiname = "Password Manager";
    protected abstract void setupGUI(JFrame frame);
    protected JLabel createJLabel(String txt,int placement, Font font, Color color, int height, int width){
        JLabel label = new JLabel(txt,placement);
        label.setSize(height, width);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }
    protected JButton createJButton(String name, ActionListener listener, Dimension dim){
        JButton button = new JButton(name);
        button.setSize(dim);
        button.addActionListener(listener);
        return button;
    }
}