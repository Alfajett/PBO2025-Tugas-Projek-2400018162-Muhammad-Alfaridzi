package game;

import java.util.Random;
import characters.*;
import enemies.*;
import cards.*;


public class GameManager {
    private Player player;
    private Shop shop;
    private int coins;
    private boolean isFirstBattle;
    private Random random;

    // Constants
    private static final int REWARD_PER_WIN = 150;
    private static final int STARTING_COINS = 0;

    // Constructor
    public GameManager() {
        this.player = new Player("Hero");
        this.shop = new Shop();
        this.coins = STARTING_COINS;
        this.isFirstBattle = true;
        this.random = new Random();
    }

    public Enemy createEnemy() {
        Enemy enemy;

        if (isFirstBattle) {
            enemy = new Goblin();
            isFirstBattle = false;
            System.out.println("First enemy encountered: Goblin!");
        } else {
            int choice = random.nextInt(4);

            switch (choice) {
                case 0:
                    enemy = new Goblin();
                    break;
                case 1:
                    enemy = new Slime();
                    break;
                case 2:
                    enemy = new Warrior();
                    break;
                case 3:
                    enemy = new Peacekeeper();
                    break;
                default:
                    enemy = new Goblin();
            }

            System.out.println("Enemy encountered: " + enemy.getName() + "!");
        }

        return enemy;
    }

    public void awardCoins() {
        coins += REWARD_PER_WIN;
        System.out.println("Earned " + REWARD_PER_WIN + " coins!");
        System.out.println("Total coins: " + coins);
    }

    public boolean buyCardFromShop(int cardIndex) {
        int newCoins = shop.buyCard(player, cardIndex, coins);

        if (newCoins >= 0) {
            coins = newCoins;
            return true;
        }

        return false;
    }

    public String getShopCardInfo(int index) {
        return shop.getCardInfo(index, player);
    }

    public boolean canPurchaseCard(int index) {
        return shop.canPurchase(index, player, coins);
    }

    public void displayShop() {
        shop.displayShop(player, coins);
    }

    public String getDeckInfo() {
        StringBuilder info = new StringBuilder();
        info.append("CURRENT DECK:\n");
        info.append("=".repeat(30)).append("\n");

        int attackCount = 0;
        int defendCount = 0;
        int healCount = 0;
        int multiStrikeCount = 0;
        int finisherCount = 0;
        int guardUpCount = 0;
        int vampiricStrikeCount = 0;

        attackCount = countCardInDeck(new AttackCard());
        defendCount = countCardInDeck(new DefendCard());
        healCount = countCardInDeck(new HealCard());
        multiStrikeCount = countCardInDeck(new MultiStrikeCard());
        finisherCount = countCardInDeck(new FinisherCard());
        guardUpCount = countCardInDeck(new GuardUpCard());
        vampiricStrikeCount = countCardInDeck(new VampiricStrikeCard());

        info.append("Attack: ").append(attackCount).append("\n");
        info.append("Defend: ").append(defendCount).append("\n");
        info.append("Heal: ").append(healCount).append("\n");
        info.append("Multi-Strike: ").append(multiStrikeCount).append("\n");
        info.append("Finisher: ").append(finisherCount).append("\n");
        info.append("Guard Up: ").append(guardUpCount).append("\n");
        info.append("Vampiric Strike: ").append(vampiricStrikeCount).append("\n");
        info.append("=".repeat(30)).append("\n");
        info.append("Total cards: ").append(
                attackCount + defendCount + healCount + multiStrikeCount + finisherCount + guardUpCount + vampiricStrikeCount
        );

        return info.toString();
    }

    private int countCardInDeck(Card cardType) {
        return player.countCardType(cardType);
    }

    public void resetGame() {
        this.player = new Player("Hero");
        this.coins = STARTING_COINS;
        this.isFirstBattle = true;
        System.out.println("Game reset!");
    }

    // Getters
    public Player getPlayer() {
        return player;
    }

    public Shop getShop() {
        return shop;
    }

    public int getCoins() {
        return coins;
    }

    public boolean isFirstBattle() {
        return isFirstBattle;
    }

    public int getRewardPerWin() {
        return REWARD_PER_WIN;
    }
}