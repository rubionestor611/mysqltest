import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public abstract class GUI {
    protected JFrame frame = new JFrame();
    protected Dimension WindowSize  = new Dimension(1000, 1000);
    protected String guiname = "Password Manager";
    protected abstract void setupGUI(JFrame frame);
}
/*public static void main(String[] args){
        JFrame frame = createFrame();
        JMenuBar menubar = createMenuBar();
        frame.getContentPane().add(BorderLayout.NORTH, menubar);
        JLabel label1 = new JLabel("Enter SQL Command");
        JTextField textfield1 = new JTextField(100);
        textfield1.setSize(300,100);

        JButton submit = new JButton("Submit Command");
        JLabel label2 = new JLabel("Results");
        JTextArea txtarea = new JTextArea();

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection c = Main.getConnection();
                try {
                    Statement s = c.createStatement();
                    String command = textfield1.getText();
                    ResultSet rs = s.executeQuery(command);
                    txtarea.replaceRange("", 0, txtarea.getText().length());
                    appendResultsettoTxtArea(rs, txtarea, command);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
        frame.getContentPane().add(BorderLayout.PAGE_START, label1);
        frame.getContentPane().add(BorderLayout.WEST, textfield1);
        frame.getContentPane().add(BorderLayout.SOUTH, submit);
        frame.getContentPane().add(BorderLayout.CENTER, label2);
        frame.getContentPane().add(BorderLayout.EAST, txtarea);
        frame.setVisible(true);
    }

    private static void appendResultsettoTxtArea(ResultSet resultSet, JTextArea ta,String command) {
        try{
            ArrayList<Employee> emplist = new ArrayList<>();
            int id,salary,supervisor, branch;
            id = salary = supervisor = branch = 0;
            String first,last,sex;
            first = last = sex = "";
            Date birth;
            birth = new Date(2020);
            while(resultSet.next()){
                if(command.matches("^(.+)emp_id(.+)FROM(.+)$")){
                    id = resultSet.getInt("emp_id");
                }
                if(command.matches("^(.+)first_name(.+)FROM(.+)$")){
                    first = resultSet.getString("first_name");
                }
                if(command.matches("^(.+)last_name(.+)FROM(.+)$")){
                    last = resultSet.getString("last_name");
                }
                if(command.matches("^(.+)birth(.+)FROM(.+)$")){
                    birth = resultSet.getDate("birth_day");
                }
                if(command.matches("^(.+)sex(.+)FROM(.+)$")){
                    sex = resultSet.getString("sex");
                }
                if(command.matches("^(.+)salary(.+)FROM(.+)$")){
                    salary = resultSet.getInt("salary");
                }
                if(command.matches("^(.+)super_id(.+)FROM(.+)$")){
                    supervisor = resultSet.getInt("super_id");
                }
                if(command.matches("^(.+)emp_id(.+)FROM(.+)$")){
                    branch = resultSet.getInt("branch_id");
                }
                emplist.add(new Employee(id,first,last,birth,sex,salary,supervisor,branch));
            }
            for(Employee e : emplist){
                ta.append(String.valueOf(e));
                ta.append("\n");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error retrieving info from data set");
        }

    }

    private static JMenuBar createMenuBar() {
        JMenuBar ret = new JMenuBar();
        JMenu m1 = new JMenu("FILE");
        JMenu m2 = new JMenu("CLEAR");
        ret.add(m1);
        ret.add(m2);
        JMenuItem m11 = new JMenuItem("Close");
        m11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        m1.add(m11);
        return ret;
    }

    public static JFrame createFrame(){
        JFrame ret = new JFrame("Nestor's GUI");
        ret.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ret.setSize(500,500);
        return ret;
    }*/