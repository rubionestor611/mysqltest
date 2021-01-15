import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Random;

public class RandomPasswordGenPane extends JPanel {
    private JLabel label;
    private JCheckBox symbolsbox;
    private JLabel symbols;
    private JCheckBox numsbox;
    private JLabel nums;
    private JTextField lengthfield;
    private JLabel length;
    private JButton Generate;
    private GridBagConstraints constraints;
    private char[] symbolsSet= {'!','@','#','$','%','&','?','/','*','-','+'};
    private char[] numbersSet = {'0','1','2','3','4','5','6','7','8','9'};
    private char[] lettersSet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
                                 'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    private Random rand = new Random();
    private Container optionscontainer;
    private GridBagConstraints optionsconstraints;
    private boolean withNumbers = false;
    private boolean withSymbols = false;
    
    public RandomPasswordGenPane(int width, int height){
        this.setLayout(new GridBagLayout());
        constraints = new GridBagConstraints();
        this.setBackground(Color.ORANGE);
        this.setSize(width, height);

        setupOptionsContainer();//remove options container if idea fails

        setupLabel();

        setupSymbol();

        setupNums();

        setupLength();

        constraints.gridx = 0;
        constraints.gridy = 1;
        this.add(optionscontainer, constraints);

        setupGenerate();


    }

    private void setupOptionsContainer() {
        optionscontainer = new Container();
        optionscontainer.setLayout(new GridBagLayout());
        optionsconstraints = new GridBagConstraints();
    }

    private void setupLabel() {
        this.label = new JLabel("Optional Password Generator");
        label.setForeground(Color.BLACK);
        this.label.setFont(new Font("Arial", Font.BOLD, 20));

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.ipady = 50;
        this.add(label, constraints);
    }

    private void setupGenerate() {
        this.Generate = new JButton("Generate");
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.ipady = 20;
        this.add(Generate, constraints);
    }

    private void setupSymbol() {
        this.symbols = new JLabel("Symbols(!@#$%&?/*-+): ", SwingConstants.LEFT);
        this.optionsconstraints.gridx = 0;
        this.optionsconstraints.gridy = 0;
        this.optionscontainer.add(this.symbols, this.optionsconstraints);

        this.symbolsbox = new JCheckBox();
        this.optionsconstraints.gridx = 1;
        this.optionsconstraints.gridy = 0;
        this.symbolsbox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                withSymbols = !withSymbols;
            }
        });
        this.optionscontainer.add(this.symbolsbox, this.optionsconstraints);

    }
    private void setupNums(){
        this.nums = new JLabel("Numbers(0 - 9):               ");
        this.nums.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        this.nums.setSize(this.symbols.getSize());
        this.optionsconstraints.gridx = 0;
        this.optionsconstraints.gridy = 1;
        this.optionscontainer.add(this.nums, this.optionsconstraints);

        this.numsbox = new JCheckBox();
        this.optionsconstraints.gridx = 1;
        this.optionsconstraints.gridy = 1;
        this.numsbox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                withNumbers = !withNumbers;
            }
        });
        this.optionscontainer.add(this.numsbox, this.optionsconstraints);
    }
    private void setupLength(){
        this.length = new JLabel("Length:                            ");
        this.length.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        this.length.setSize(this.symbols.getSize());
        this.length.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.optionsconstraints.gridx = 0;
        this.optionsconstraints.gridy = 2;
        this.optionscontainer.add(this.length, this.optionsconstraints);

        this.lengthfield = new JTextField(3);
        this.optionsconstraints.gridx = 1;
        this.optionsconstraints.gridy = 2;
        this.optionscontainer.add(this.lengthfield, this.optionsconstraints);

    }
    public JButton getGenerate(){
        return this.Generate;
    }
    public String generatePassword(){
        try{
            Integer.parseInt(lengthfield.getText());
            return generatePassword2(Integer.parseInt(this.lengthfield.getText()),withSymbols, withNumbers);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Please input a valid integer for the length of the generated password.",
                    "Error", JOptionPane.WARNING_MESSAGE);
        }
        return null;
    }
    private String generatePassword2(int length, boolean symbols, boolean numbers){
        if(symbols && numbers){
            return generateFullyRandomPassword(length);
        }else if(numbers){
            return generateRandomPasswordNumandChar(length);
        }else if(symbols){
            return generateRandomPasswordSymbolsandChar(length);
        }else{
            return generateRandomPasswordCharOnly(length);
        }
    }

    public String generateRandomPasswordCharOnly(int length) {
        int random1;
        String res = "";
        for(int i = 0; i < length; i++){
            random1 = rand.nextInt(this.lettersSet.length);
            res += this.lettersSet[random1];
        }
        return res;
    }

    public String generateRandomPasswordNumandChar(int length) {
        int rand1;
        int rand2;
        String ret = "";
        for(int i = 0; i < length; i++){
            rand1 = rand.nextInt();
            if(rand1 % 2 == 0){//letters
                rand2= rand.nextInt(this.lettersSet.length);
                ret += lettersSet[rand2];
            }else{//num
                rand2= rand.nextInt(this.numbersSet.length);
                ret += numbersSet[rand2];
            }
        }
        return ret;
    }

    public String generateFullyRandomPassword(int length) {
        int rand1;
        int rand2;
        String ret = "";
        for(int i = 0; i < length; i++){
            rand1 = rand.nextInt(3);
            if(rand1 == 0){//letters
                rand2 = rand.nextInt(this.lettersSet.length);
                ret += lettersSet[rand2];
            }else if (rand1 == 1){//num
                rand2= rand.nextInt(this.numbersSet.length);
                ret += numbersSet[rand2];
            }else{
                rand2= rand.nextInt(this.symbolsSet.length);
                ret += symbolsSet[rand2];
            }
        }
        return ret;
    }
    public String generateRandomPasswordSymbolsandChar(int length){
        int rand1;
        int rand2;
        String ret = "";
        for(int i = 0; i < length; i++){
            rand1 = rand.nextInt();
            if(rand1 % 2 == 0){//letters
                rand2= rand.nextInt(this.lettersSet.length);
                ret += lettersSet[rand2];
            }else{//num
                rand2= rand.nextInt(this.symbolsSet.length);
                ret += symbolsSet[rand2];
            }
        }
        return ret;
    }
}
