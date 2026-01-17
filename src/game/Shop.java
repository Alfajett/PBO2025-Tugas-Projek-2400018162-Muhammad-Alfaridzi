package game;

import java.util.ArrayList;
import cards.*;
import characters.*;

public class Shop {
    private static final int CARD_PRICE = 50;
    private static final int MAX_CARD_COPIES = 3;

    private ArrayList<Card> availableCards;

    public Shop() {
        this.availableCards = new ArrayList<>();
        initializeShop();
    }

    private void initializeShop() {

        availableCards.add(new MultiStrikeCard());
        availableCards.add(new FinisherCard());
        availableCards.add(new GuardUpCard());
        availableCards.add(new VampiricStrikeCard());
    }

    public int buyCard(Player player, int cardIndex, int currentCoins) {
        if (cardIndex < 0 || cardIndex >= availableCards.size()) {
            System.out.println("Invalid card selection!");
            return -1;
        }

        // Check jika player memiliki cukup coin
        if (currentCoins < CARD_PRICE) {
            System.out.println("Not enough coins! Need " + CARD_PRICE + " coins.");
            return -1;
        }

        Card selectedCard = availableCards.get(cardIndex);

        // Check jika player sudah memiliki max copy
        int currentCount = player.countCardType(selectedCard);
        if (currentCount >= MAX_CARD_COPIES) {
            System.out.println("You already have maximum copies of " + selectedCard.getName() + "!");
            return -1;
        }

        Card newCard = createCardInstance(selectedCard);

        // Add to player deck
        player.addCardToDeck(newCard);

        int newCoins = currentCoins - CARD_PRICE;

        System.out.println("Successfully purchased " + selectedCard.getName() + "!");
        System.out.println("Coins: " + currentCoins + " -> " + newCoins);
        System.out.println("Copies owned: " + (currentCount + 1) + "/" + MAX_CARD_COPIES);

        return newCoins;
    }

    private Card createCardInstance(Card template) {
        if (template instanceof MultiStrikeCard) {
            return new MultiStrikeCard();
        } else if (template instanceof FinisherCard) {
            return new FinisherCard();
        } else if (template instanceof GuardUpCard) {
            return new GuardUpCard();
        } else if (template instanceof VampiricStrikeCard) {
            return new VampiricStrikeCard();
        }
        return null;
    }

    public String getCardInfo(int index, Player player) {
        if (index < 0 || index >= availableCards.size()) {
            return "Invalid card";
        }

        Card card = availableCards.get(index);
        int owned = player.countCardType(card);

        String info = card.toString() + "\n";
        info += "Price: " + CARD_PRICE + " coins\n";
        info += "Owned: " + owned + "/" + MAX_CARD_COPIES;

        if (owned >= MAX_CARD_COPIES) {
            info += " [MAX]";
        }

        return info;
    }

    public boolean canPurchase(int cardIndex, Player player, int coins) {
        if (cardIndex < 0 || cardIndex >= availableCards.size()) {
            return false;
        }

        if (coins < CARD_PRICE) {
            return false;
        }

        Card card = availableCards.get(cardIndex);
        int owned = player.countCardType(card);

        return owned < MAX_CARD_COPIES;
    }

    public void displayShop(Player player, int coins) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("SHOP");
        System.out.println("Your coins: " + coins);
        System.out.println("=".repeat(50));

        for (int i = 0; i < availableCards.size(); i++) {
            System.out.println("\n[" + (i + 1) + "] " + getCardInfo(i, player));
        }

        System.out.println("\n" + "=".repeat(50));
    }

    // Getters
    public ArrayList<Card> getAvailableCards() {
        return new ArrayList<>(availableCards); // Return copy
    }

    public int getCardPrice() {
        return CARD_PRICE;
    }

    public int getMaxCardCopies() {
        return MAX_CARD_COPIES;
    }
}