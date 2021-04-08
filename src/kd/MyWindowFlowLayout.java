package kd;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyWindowFlowLayout extends JFrame implements ActionListener {
    public MyWindowFlowLayout(){
        super("KINGDOMINO");
        this.setDefaultCloseOperation((JFrame.DISPOSE_ON_CLOSE));
        this.setSize(1280,720);
        this.setLocationRelativeTo(null);

        JPanel contentPane=(JPanel) this.getContentPane();
        contentPane.setLayout(new FlowLayout());

        JButton BoutonPiocher= new JButton("PIOCHER");
        BoutonPiocher.addActionListener(this);
        contentPane.add(BoutonPiocher);

    }
    public static void main(String[]args) throws Exception {
        UIManager.setLookAndFeel(new NimbusLookAndFeel());

        MyWindowFlowLayout mywindow= new MyWindowFlowLayout();
        mywindow.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (((JButton)event.getSource()).getText().equals("Salut")){
            System.out.println("Cliqué");
        }
        else{
            System.out.println("Autre bouton");
        }
    }
}
