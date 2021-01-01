import javax.swing.*;
import java.awt.*;
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

        constraints.gridx = 0;
        constraints.gridy = 0;
        this.add(label, constraints);
    }

    private void setupGenerate() {
        this.Generate = new JButton("Generate");
        constraints.gridx = 0;
        constraints.gridy = 4;
        this.add(Generate, constraints);
    }

    private void setupSymbol() {
        Container c = new Container();
        c.setLayout(new GridBagLayout());
        GridBagConstraints localConstraints = new GridBagConstraints();

        this.symbols = new JLabel("Symbols(!@#$%&?/*-+): ");
        localConstraints.gridx = 0;
        localConstraints.gridy = 0;
        c.add(symbols, localConstraints);

        this.symbolsbox = new JCheckBox();
        localConstraints.gridx = 1;
        localConstraints.gridy = 0;
        c.add(symbolsbox, localConstraints);

        constraints.gridy = 1;
        constraints.gridx = 0;
        this.add(c,constraints);

    }
    private void setupNums(){
        Container c = new Container();
        c.setLayout(new GridBagLayout());
        GridBagConstraints localConstraints = new GridBagConstraints();

        this.nums = new JLabel("Numbers(0 - 9):               ");
        localConstraints.gridx = 0;
        localConstraints.gridy = 0;
        c.add(nums, localConstraints);

        this.numsbox = new JCheckBox();
        localConstraints.gridy = 0;
        localConstraints.gridx = 1;
        c.add(numsbox, localConstraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        this.add(c,constraints);
    }
    private void setupLength(){
        Container c = new Container();
        c.setLayout(new GridBagLayout());
        GridBagConstraints localConstraints = new GridBagConstraints();

        this.length = new JLabel("Length:                            ");
        localConstraints.gridx = 0;
        localConstraints.gridy = 0;
        c.add(length, localConstraints);

        this.lengthfield = new JTextField(1);
        localConstraints.gridy = 0;
        localConstraints.gridx = 1;
        c.add(lengthfield, localConstraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        this.add(c,constraints);
    }
    public JButton getGenerate(){
        return this.Generate;
    }
    public String generatePassword(){
        return generatePassword2(Integer.parseInt(this.lengthfield.getText()),symbolsbox.isSelected(), numsbox.isSelected());
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
                ret += lettersSet[rand2];
            }
        }
        return ret;
    }

    public String generateFullyRandomPassword(int length) {
        return null;
    }
    public String generateRandomPasswordSymbolsandChar(int length){
        return null;
    }
}
