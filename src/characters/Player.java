package characters;

import java.util.ArrayList;
import java.util.Collections;
import cards.*;
import enemies.*;

public class Player extends Character {

    private ArrayList<Card> deck;
    private ArrayList<Card> hand;
    private ArrayList<Card> discardPile;

    // Constructor
    public Player(String name) {
        super(name, 40);
        this.deck = new ArrayList<>();
        this.hand = new ArrayList<>();
        this.discardPile = new ArrayList<>();

        initializeDefaultDeck();
    }

    private void initializeDefaultDeck() {
        // Add 3 Attack cards
        for (int i = 0; i < 3; i++) {
            deck.add(new AttackCard());
        }

        // Add 3 Defend cards
        for (int i = 0; i < 3; i++) {
            deck.add(new DefendCard());
        }

        // Starter utility: 1 Heal card
        deck.add(new HealCard());

        // Shuffle deck
        shuffleDeck();
    }

    public void shuffleDeck() {
        Collections.shuffle(deck);
        System.out.println("Deck shuffled!");
    }

    public void drawCards(int count) {
        for (int i = 0; i < count; i++) {
            // Jika deck kosong, shuffle discard pile kembali ke deck
            if (deck.isEmpty()) {
                if (discardPile.isEmpty()) {
                    System.out.println("No more cards to draw!");
                    break;
                }

                System.out.println("Deck empty! Shuffling discard pile back to deck...");
                deck.addAll(discardPile);
                discardPile.clear();
                shuffleDeck();
            }

            // Draw card dari deck ke hand
            Card drawnCard = deck.remove(0);
            hand.add(drawnCard);
        }

        System.out.println("Drew " + Math.min(count, hand.size()) + " cards. Hand size: " + hand.size());
    }

    //Play card dari hand
    public void playCard(int cardIndex, Enemy enemy) {
        if (cardIndex < 0 || cardIndex >= hand.size()) {
            System.out.println("Invalid card index!");
            return;
        }

        Card card = hand.remove(cardIndex);
        System.out.println("\n=== Playing: " + card.getName() + " ===");
        card.play(this, enemy);

        // Card yang dimainkan masuk ke discard pile
        discardPile.add(card);
    }

    public void discardHand() {
        discardPile.addAll(hand);
        hand.clear();
    }

    public void addCardToDeck(Card card) {
        deck.add(card);
        System.out.println("Added " + card.getName() + " to deck!");
    }

    public int countCardType(Card cardType) {
        int count = 0;

        for (Card card : deck) {
            if (card.isSameType(cardType)) count++;
        }
        for (Card card : hand) {
            if (card.isSameType(cardType)) count++;
        }
        for (Card card : discardPile) {
            if (card.isSameType(cardType)) count++;
        }

        return count;
    }

    public ArrayList<Card> getHand() {
        return new ArrayList<>(hand); // Return copy untuk encapsulation
    }

    public int getDeckSize() {
        return deck.size();
    }

    public int getDiscardSize() {
        return discardPile.size();
    }

    public int getHandSize() {
        return hand.size();
    }

    public void displayHand() {
        System.out.println("\n=== YOUR HAND ===");
        for (int i = 0; i < hand.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + hand.get(i).toString());
        }
        System.out.println("Deck: " + deck.size() + " | Discard: " + discardPile.size());
    }

    public void resetForNewBattle() {
        deck.addAll(hand);
        deck.addAll(discardPile);
        hand.clear();
        discardPile.clear();

        setCurrentHP(getMaxHP());
        setShield(0);

        shuffleDeck();
    }
}