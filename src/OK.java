import javax.swing.*;

public class OK extends JFrame{
    private JPanel Fenetre;
    private JPanel CarreDeJeu;
    private JButton piocherButton;
    private JButton retorunerUneCarteButton;
    private JButton validerButton;
    private JSpinner spinner1;
    private JTree tree1;

    public OK(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(Fenetre);
        this.pack();

        }
        public static void main(String[] args){
        JFrame frame=new OK("KINGDOMINO");
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
