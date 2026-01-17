package gui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.util.ArrayList;

import game.*;
import characters.*;
import enemies.*;
import cards.*;

public class BattleGUI extends JFrame {
    private final BattleSystem battleSystem;
    private final GameManager gameManager;
    private final MainMenuGUI mainMenu;

    // UI Components
    private JLabel playerHPLabel;
    private JLabel playerShieldLabel;
    private JLabel enemyHPLabel;
    private JLabel enemyShieldLabel;
    private JLabel nextMoveLabel;
    private JLabel deckCountLabel;
    private JLabel discardCountLabel;
    private JLabel playsRemainingLabel;
    private JPanel handPanel;
    private JButton endTurnButton;
    private JLabel handLabel;

    private static final Color BG = new Color(32, 34, 37);
    private static final Color SURFACE = new Color(45, 48, 54);
    private static final Color SURFACE_2 = new Color(55, 58, 64);
    private static final Color TEXT = new Color(240, 240, 240);
    private static final Color MUTED = new Color(190, 190, 190);
    private static final Color GOOD = new Color(98, 190, 120);
    private static final Color WARNING = new Color(230, 200, 120);

    private final ArrayList<Integer> selectedCardIndices = new ArrayList<>();

    public BattleGUI(BattleSystem battleSystem, GameManager gameManager, MainMenuGUI mainMenu) {
        this.battleSystem = battleSystem;
        this.gameManager = gameManager;
        this.mainMenu = mainMenu;

        setTitle("Battle - " + battleSystem.getEnemy().getName());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();

        // Start battle
        battleSystem.startBattle();
        refreshAll();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(BG);

        JPanel statusPanel = createStatusPanel();

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(BG);

        handLabel = new JLabel("", SwingConstants.CENTER);
        handLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        handLabel.setForeground(TEXT);

        handPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        handPanel.setBackground(SURFACE);
        handPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        centerPanel.add(handLabel, BorderLayout.NORTH);
        centerPanel.add(handPanel, BorderLayout.CENTER);

        JPanel controlPanel = createControlPanel();

        mainPanel.add(statusPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createStatusPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 5, 5));
        panel.setBackground(BG);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 60), 1),
                "Battle Status",
                0,
                0,
                new Font("SansSerif", Font.BOLD, 14),
                TEXT));

        // Player status
        JPanel playerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        playerPanel.setBackground(BG);
        playerHPLabel = new JLabel();
        playerHPLabel.setForeground(TEXT);
        playerHPLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        playerShieldLabel = new JLabel();
        playerShieldLabel.setForeground(MUTED);
        playerShieldLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));

        playerPanel.add(playerHPLabel);
        playerPanel.add(Box.createHorizontalStrut(15));
        playerPanel.add(playerShieldLabel);

        // Enemy status
        JPanel enemyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        enemyPanel.setBackground(BG);
        enemyHPLabel = new JLabel();
        enemyHPLabel.setForeground(TEXT);
        enemyHPLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        enemyShieldLabel = new JLabel();
        enemyShieldLabel.setForeground(MUTED);
        enemyShieldLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));

        nextMoveLabel = new JLabel();
        nextMoveLabel.setForeground(WARNING);
        nextMoveLabel.setFont(new Font("SansSerif", Font.BOLD, 13));

        enemyPanel.add(enemyHPLabel);
        enemyPanel.add(Box.createHorizontalStrut(15));
        enemyPanel.add(enemyShieldLabel);
        enemyPanel.add(Box.createHorizontalStrut(15));
        enemyPanel.add(nextMoveLabel);

        JPanel deckPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        deckPanel.setBackground(BG);

        deckCountLabel = new JLabel();
        deckCountLabel.setForeground(MUTED);
        deckCountLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));

        discardCountLabel = new JLabel();
        discardCountLabel.setForeground(MUTED);
        discardCountLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));

        playsRemainingLabel = new JLabel();
        playsRemainingLabel.setForeground(WARNING);
        playsRemainingLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        deckPanel.add(deckCountLabel);
        deckPanel.add(Box.createHorizontalStrut(15));
        deckPanel.add(discardCountLabel);
        deckPanel.add(Box.createHorizontalStrut(15));
        deckPanel.add(playsRemainingLabel);

        panel.add(playerPanel);
        panel.add(enemyPanel);
        panel.add(deckPanel);

        return panel;
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(BG);

        endTurnButton = new JButton("End Turn");
        endTurnButton.setUI(new BasicButtonUI()); // Ensure colors are respected
        endTurnButton.setBackground(GOOD);
        endTurnButton.setForeground(Color.WHITE);
        endTurnButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        endTurnButton.setFocusPainted(false);
        endTurnButton.setContentAreaFilled(true);
        endTurnButton.setBorderPainted(true);
        endTurnButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 60), 1),
                BorderFactory.createEmptyBorder(10, 18, 10, 18)
        ));
        endTurnButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        endTurnButton.addActionListener(e -> onEndTurn());

        panel.add(endTurnButton);
        return panel;
    }

    private void refreshAll() {
        refreshStatusPanel();
        refreshHandPanel();
        refreshEndTurnState();
    }

    private void refreshStatusPanel() {
        Player player = battleSystem.getPlayer();
        Enemy enemy = battleSystem.getEnemy();

        playerHPLabel.setText("YOU: " + player.getCurrentHP() + "/" + player.getMaxHP() + " HP");
        playerShieldLabel.setText("Shield: " + player.getShield());

        enemyHPLabel.setText(enemy.getName() + ": " + enemy.getCurrentHP() + "/" + enemy.getMaxHP() + " HP");
        enemyShieldLabel.setText("Shield: " + enemy.getShield());
        nextMoveLabel.setText("Next move: " + enemy.getNextActionInfo());

        deckCountLabel.setText("Deck: " + player.getDeckSize());
        discardCountLabel.setText("Discard: " + player.getDiscardSize());

        int required = getRequiredSelections();
        int remaining = Math.max(0, required - selectedCardIndices.size());
        playsRemainingLabel.setText("Cards to select: " + remaining);

        if (handLabel != null) {
            handLabel.setText("YOUR HAND (Select " + required + " cards):");
        }
    }

    private void refreshHandPanel() {
        handPanel.removeAll();

        ArrayList<Card> hand = battleSystem.getPlayer().getHand();
        for (int i = 0; i < hand.size(); i++) {
            JButton btn = createCardButton(hand.get(i), i);
            handPanel.add(btn);
        }

        handPanel.revalidate();
        handPanel.repaint();
    }

    private JButton createCardButton(Card card, int index) {
        JButton cardButton = new JButton("<html><center>" +
                card.getName() + "<br>" +
                card.getDescription() + "</center></html>");

        cardButton.setUI(new BasicButtonUI());
        cardButton.setPreferredSize(new Dimension(140, 90));
        cardButton.setFont(new Font("SansSerif", Font.PLAIN, 11));
        cardButton.setFocusPainted(false);
        cardButton.setContentAreaFilled(true);
        cardButton.setBorderPainted(true);
        cardButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cardButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 0, 0, 90), 1),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
        cardButton.setOpaque(true);

        applyCardButtonStyle(cardButton, selectedCardIndices.contains(index));

        cardButton.addActionListener(e -> onCardClicked(index));
        return cardButton;
    }

    private void applyCardButtonStyle(JButton btn, boolean selected) {
        if (selected) {
            btn.setBackground(GOOD);
            btn.setForeground(Color.WHITE);
        } else {
            btn.setBackground(SURFACE_2);
            btn.setForeground(TEXT);
        }
    }

    private void onCardClicked(int index) {
        toggleSelection(index);
        refreshAll();
    }

    private void toggleSelection(int index) {
        if (selectedCardIndices.contains(index)) {
            selectedCardIndices.remove((Integer) index);
            return;
        }

        int required = getRequiredSelections();
        if (selectedCardIndices.size() >= required) {
            Toolkit.getDefaultToolkit().beep();
            return;
        }

        selectedCardIndices.add(index);
    }

    private void refreshEndTurnState() {
        int required = getRequiredSelections();
        // If there are no cards to select, allow ending turn.
        if (required == 0) {
            endTurnButton.setEnabled(true);
            return;
        }
        endTurnButton.setEnabled(selectedCardIndices.size() == required);
    }

    private int getRequiredSelections() {
        int max = battleSystem.getMaxPlaysPerTurn();
        int handSize = battleSystem.getPlayer().getHandSize();
        return Math.min(max, handSize);
    }

    private void onEndTurn() {
        int required = getRequiredSelections();
        if (required > 0 && selectedCardIndices.size() != required) {
            JOptionPane.showMessageDialog(this,
                    "Please select " + required + " cards first.",
                    "Selection Required",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Play selected cards
        int[] indices = selectedCardIndices.stream().mapToInt(i -> i).toArray();
        battleSystem.executePlayerCards(indices);

        // Check jika musuh kalah
        String result = battleSystem.checkBattleResult();
        if (result.equals("WIN")) {
            showBattleResult(true);
            return;
        }

        // Enemy turn
        battleSystem.executeEnemyTurn();

        // Check jika player kalah
        result = battleSystem.checkBattleResult();
        if (result.equals("LOSE")) {
            showBattleResult(false);
            return;
        }

        // End turn dan draw card baru
        battleSystem.endTurn();

        selectedCardIndices.clear();
        refreshAll();
    }

            private void showBattleResult(boolean victory) {
        battleSystem.displayBattleResult();

        String message;
        String title;

        if (victory) {
            gameManager.awardCoins();
            message = "VICTORY!\n\nYou defeated " + battleSystem.getEnemy().getName() + "!\n" +
                    "Earned " + gameManager.getRewardPerWin() + " coins!\n" +
                    "Total coins: " + gameManager.getCoins();
            title = "Battle Won!";
        } else {
            message = "DEFEAT!\n\nYou were defeated by " + battleSystem.getEnemy().getName() + "...\n" +
                    "No reward earned.\n" +
                    "Total coins: " + gameManager.getCoins();
            title = "Battle Lost...";
        }

        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);

        // Return to main menu
        mainMenu.updateCoinsDisplay();
        mainMenu.setVisible(true);
        this.dispose();
    }


}
