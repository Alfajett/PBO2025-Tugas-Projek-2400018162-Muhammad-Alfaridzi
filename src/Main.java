import javax.swing.*;
import game.*;
import gui.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            GameManager gameManager = new GameManager();

            MainMenuGUI mainMenu = new MainMenuGUI(gameManager);
            mainMenu.setVisible(true);

            // Welcome message di console
            System.out.println("=".repeat(60));
            System.out.println("CARD BATTLE GAME");
            System.out.println("Tugas Projek - Turn Base Card Game");
            System.out.println("=".repeat(60));
            System.out.println("\nGame started! Check the GUI window.");
            System.out.println("\n" + "=".repeat(60));
        });
    }
}