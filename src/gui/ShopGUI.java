package gui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import game.*;
import cards.*;

public class ShopGUI extends JFrame {
    private GameManager gameManager;
    private MainMenuGUI mainMenu;
    private JLabel coinsLabel;
    private JPanel cardsPanel;

    private static final Color BG = new Color(32, 34, 37);
    private static final Color SURFACE = new Color(45, 48, 54);
    private static final Color SURFACE_2 = new Color(55, 58, 64);
    private static final Color TEXT = new Color(240, 240, 240);
    private static final Color MUTED = new Color(190, 190, 190);

    public ShopGUI(GameManager gameManager, MainMenuGUI mainMenu) {
        this.gameManager = gameManager;
        this.mainMenu = mainMenu;

        setTitle("Shop");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(BG);

        // Title panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(BG);

        JLabel titleLabel = new JLabel("SHOP", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        titleLabel.setForeground(TEXT);

        coinsLabel = new JLabel("Your coins: " + gameManager.getCoins(), SwingConstants.CENTER);
        coinsLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        coinsLabel.setForeground(MUTED);

        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(coinsLabel, BorderLayout.CENTER);

        // Cards panel
        cardsPanel = new JPanel();
        cardsPanel.setLayout(new BoxLayout(cardsPanel, BoxLayout.Y_AXIS));
        cardsPanel.setBackground(SURFACE);

        JScrollPane scrollPane = new JScrollPane(cardsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getViewport().setBackground(SURFACE);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 40), 1));

        populateCards();

        // Back button panel
        JPanel backPanel = new JPanel();
        backPanel.setBackground(BG);

        JButton backButton = new JButton("BACK TO MENU");
        backButton.setFont(new Font("SansSerif", Font.BOLD, 15));
        backButton.setPreferredSize(new Dimension(180, 40));
        styleButton(backButton, new Color(200, 100, 100));
        backButton.addActionListener(e -> returnToMenu());

        backPanel.add(backButton);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(backPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void populateCards() {
        cardsPanel.removeAll();

        // Mengambil kartu yang tersedia dari shop
        java.util.ArrayList<Card> availableCards = gameManager.getShop().getAvailableCards();

        for (int i = 0; i < availableCards.size(); i++) {
            final int cardIndex = i;
            Card card = availableCards.get(i);

            JPanel cardPanel = createCardPanel(card, cardIndex);
            cardsPanel.add(cardPanel);
            cardsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        cardsPanel.revalidate();
        cardsPanel.repaint();
    }

    private JPanel createCardPanel(Card card, int cardIndex) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(SURFACE_2);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 45), 1),
                BorderFactory.createEmptyBorder(14, 14, 14, 14)
        ));
        panel.setMaximumSize(new Dimension(500, 120));

        // Card info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(SURFACE_2);

        JLabel nameLabel = new JLabel(card.getName());
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        nameLabel.setForeground(TEXT);

        JLabel descLabel = new JLabel(card.getDescription());
        descLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        descLabel.setForeground(MUTED);

        JLabel priceLabel = new JLabel("Price: " + gameManager.getShop().getCardPrice() + " coins");
        priceLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        priceLabel.setForeground(new Color(230, 200, 120));

        int owned = gameManager.getPlayer().countCardType(card);
        int max = gameManager.getShop().getMaxCardCopies();

        JLabel ownedLabel = new JLabel("Owned: " + owned + "/" + max);
        ownedLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        ownedLabel.setForeground(owned >= max ? new Color(240, 140, 140) : TEXT);

        infoPanel.add(nameLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(descLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        infoPanel.add(priceLabel);
        infoPanel.add(ownedLabel);

        // Buy button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(SURFACE_2);

        JButton buyButton = new JButton("BUY");
        buyButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        buyButton.setPreferredSize(new Dimension(110, 40));

        // Check jika bisa beli
        boolean canPurchase = gameManager.canPurchaseCard(cardIndex);

        if (canPurchase) {
            styleButton(buyButton, new Color(98, 190, 120));
            buyButton.setEnabled(true);
            buyButton.addActionListener(e -> buyCard(cardIndex));
        } else {
            if (owned >= max) {
                buyButton.setText("MAX");
                styleButton(buyButton, new Color(120, 120, 120));
            } else {
                buyButton.setText("NO COINS");
                styleButton(buyButton, new Color(120, 120, 120));
            }
            buyButton.setEnabled(false);
        }

        buttonPanel.add(buyButton);

        panel.add(infoPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.EAST);

        return panel;
    }

    private void styleButton(JButton button, Color bg) {
        button.setUI(new BasicButtonUI());
        button.setBackground(bg);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);
        button.setBorderPainted(true);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 0, 0, 70), 1),
                BorderFactory.createEmptyBorder(8, 14, 8, 14)
        ));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
    }

    private void buyCard(int cardIndex) {
        boolean success = gameManager.buyCardFromShop(cardIndex);

        if (success) {
            coinsLabel.setText("Your coins: " + gameManager.getCoins());
            populateCards();

            JOptionPane.showMessageDialog(this,
                    "Card purchased successfully!\nAdded to your deck.",
                    "Purchase Successful",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Failed to purchase card!\nCheck if you have enough coins or already have max copies.",
                    "Purchase Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void returnToMenu() {
        mainMenu.updateCoinsDisplay();
        mainMenu.setVisible(true);
        this.dispose();
    }
}