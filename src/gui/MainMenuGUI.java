package gui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import game.*;
import enemies.*;

public class MainMenuGUI extends JFrame {
    private GameManager gameManager;
    private JLabel coinsLabel;

    private static final Color BG = new Color(32, 34, 37);
    private static final Color SURFACE = new Color(45, 48, 54);
    private static final Color TEXT = new Color(240, 240, 240);
    private static final Color MUTED = new Color(190, 190, 190);

    public MainMenuGUI(GameManager gameManager) {
        this.gameManager = gameManager;

        setTitle("Card Battle Game");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(BG);

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(BG);
        JLabel titleLabel = new JLabel("CARD BATTLE GAME");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        titleLabel.setForeground(TEXT);
        titlePanel.add(titleLabel);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setBackground(BG);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        // Play button
        JButton playButton = createStyledButton("PLAY", new Color(100, 200, 100));
        playButton.addActionListener(e -> startBattle());

        // Shop button
        JButton shopButton = createStyledButton("SHOP", new Color(200, 150, 100));
        shopButton.addActionListener(e -> openShop());

        // Deck info button
        JButton deckButton = createStyledButton("VIEW DECK", new Color(100, 150, 200));
        deckButton.addActionListener(e -> showDeckInfo());

        // Exit button
        JButton exitButton = createStyledButton("EXIT", new Color(200, 100, 100));
        exitButton.addActionListener(e -> System.exit(0));

        buttonsPanel.add(playButton);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        buttonsPanel.add(shopButton);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        buttonsPanel.add(deckButton);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        buttonsPanel.add(exitButton);

        JPanel coinsPanel = new JPanel();
        coinsPanel.setBackground(BG);
        coinsLabel = new JLabel("Coins: " + gameManager.getCoins());
        coinsLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        coinsLabel.setForeground(MUTED);
        coinsPanel.add(coinsLabel);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(buttonsPanel, BorderLayout.CENTER);
        mainPanel.add(coinsPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setUI(new BasicButtonUI());
        button.setFont(new Font("SansSerif", Font.BOLD, 17));
        button.setPreferredSize(new Dimension(200, 50));
        button.setMaximumSize(new Dimension(200, 50));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);
        button.setBorderPainted(true);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 0, 0, 60), 1),
                BorderFactory.createEmptyBorder(10, 16, 10, 16)
        ));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });

        return button;
    }

    private void startBattle() {
        Enemy enemy = gameManager.createEnemy();

        BattleSystem battleSystem = new BattleSystem(gameManager.getPlayer(), enemy);

        BattleGUI battleGUI = new BattleGUI(battleSystem, gameManager, this);
        battleGUI.setVisible(true);
        this.setVisible(false);
    }

    private void openShop() {
        ShopGUI shopGUI = new ShopGUI(gameManager, this);
        shopGUI.setVisible(true);
        this.setVisible(false);
    }

    private void showDeckInfo() {
        String deckInfo = gameManager.getDeckInfo();
        JTextArea textArea = new JTextArea(deckInfo);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JOptionPane.showMessageDialog(this,
                new JScrollPane(textArea),
                "Your Deck",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void updateCoinsDisplay() {
        coinsLabel.setText("Coins: " + gameManager.getCoins());
    }
}