import javax.swing.*;

public class InterfaceGraphique extends JFrame {
    private JPanel VueGlobale;
    private JButton JOUERButton;
    private JButton PIOCHERButton;
    private JSpinner spinner1;

    public InterfaceGraphique(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(VueGlobale);
        this.pack();
        this.setSize(1280,720);
        this.setLocationRelativeTo(null);
    }
    public static void main(String[] args){
        JFrame frame = new InterfaceGraphique("KINGDOMINO");
        frame.setVisible(true);
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
