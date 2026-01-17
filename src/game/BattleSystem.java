package game;

import characters.*;
import enemies.*;
import cards.*;

public class BattleSystem {
    private Player player;
    private Enemy enemy;
    private int turnCount;
    private boolean battleOngoing;

    // Deteksi untuk Musuh Peacekeeper
    private boolean playerAttackedThisTurn;

    // Constants
    private static final int CARDS_PER_TURN = 5;
    private static final int MAX_PLAYS_PER_TURN = 2;

    // Constructor
    public BattleSystem(Player player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
        this.turnCount = 0;
        this.battleOngoing = true;
        this.playerAttackedThisTurn = false;
    }

    // Start Battle
    public void startBattle() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("BATTLE START!");
        System.out.println(player.getName() + " VS " + enemy.getName());
        System.out.println("=".repeat(50) + "\n");

        // Reset player saat battle baru
        player.resetForNewBattle();

        // Action pertama musuh
        enemy.decideAction();

        // Draw
        player.drawCards(CARDS_PER_TURN);
    }

    // Eksekusi Turn
    public void executeTurn() {
        if (!battleOngoing) {
            return;
        }

        turnCount++;
        System.out.println("\n" + "=".repeat(50));
        System.out.println("TURN " + turnCount);
        System.out.println("=".repeat(50));

        // Reset shield di awal turn
        player.resetShield();
        enemy.resetShield();

        // Display status
        displayBattleStatus();

    }

   // Eksekusi kartu player
    public void executePlayerCards(int[] cardIndices) {
        System.out.println("\n--- PLAYER PHASE ---");

        playerAttackedThisTurn = false;

        java.util.Arrays.sort(cardIndices);
        for (int i = cardIndices.length - 1; i >= 0; i--) {
            if (cardIndices[i] >= 0 && cardIndices[i] < player.getHandSize()) {
                // Detect Attack Card untuk lawan peacekeeper
                Card selected = player.getHand().get(cardIndices[i]);
                if (selected instanceof AttackCard ||
                        selected instanceof MultiStrikeCard ||
                        selected instanceof FinisherCard ||
                        selected instanceof VampiricStrikeCard) {
                    playerAttackedThisTurn = true;
                }

                player.playCard(cardIndices[i], enemy);

                // Check jika musuh mati
                if (!enemy.isAlive()) {
                    battleOngoing = false;
                    return;
                }
            }
        }

        // Discard sisa kartu
        player.discardHand();

        // untuk trigger mekanik peacekeeper
        enemy.onPlayerTurnEnded(playerAttackedThisTurn);
    }

   // Eksekusi action musuh
    public void executeEnemyTurn() {
        if (!battleOngoing) {
            return;
        }

        System.out.println("\n--- ENEMY PHASE ---");

        if ("PEACEFUL_STANCE".equals(enemy.getNextAction())) {
            enemy.decideAction();
        }

        enemy.performAction(player);

        if (!player.isAlive()) {
            battleOngoing = false;
            return;
        }

        enemy.decideAction();
    }

    public void endTurn() {
        if (!battleOngoing) {
            return;
        }

        player.drawCards(CARDS_PER_TURN);

        System.out.println("\n--- TURN END ---\n");
    }

    public void displayBattleStatus() {
        System.out.println("\n" + player.getStatusString());
        System.out.println(enemy.getStatusString());
        System.out.println("\nEnemy's next move: " + enemy.getNextActionInfo());
        System.out.println();
    }

    public String checkBattleResult() {
        if (!enemy.isAlive()) {
            return "WIN";
        } else if (!player.isAlive()) {
            return "LOSE";
        } else {
            return "ONGOING";
        }
    }

    public void displayBattleResult() {
        System.out.println("\n" + "=".repeat(50));
        String result = checkBattleResult();

        if (result.equals("WIN")) {
            System.out.println("VICTORY!");
            System.out.println("You defeated " + enemy.getName() + "!");
            System.out.println("Reward: 150 coins");
        } else if (result.equals("LOSE")) {
            System.out.println("DEFEAT!");
            System.out.println("You were defeated by " + enemy.getName() + "...");
            System.out.println("No reward earned.");
        }

        System.out.println("=".repeat(50) + "\n");
    }

    // Getters
    public Player getPlayer() {
        return player;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public int getTurnCount() {
        return turnCount;
    }

    public boolean isBattleOngoing() {
        return battleOngoing;
    }

    public int getMaxPlaysPerTurn() {
        return MAX_PLAYS_PER_TURN;
    }
}