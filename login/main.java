package login;

import javax.swing.*;

/**
 * @file main.java
 * @author Gareth Andrews
 * @date 10 Dec 2016
 *
 * Initializes the program.
 */
public class main {
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable(){
            public void run() {
                new LoginGUI();
            }
        });
    }
}
