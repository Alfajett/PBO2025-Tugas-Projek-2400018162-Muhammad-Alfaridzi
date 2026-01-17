package cards;

import characters.Player;
import enemies.*;


public class MultiStrikeCard extends Card {
    private static final int DAMAGE_PER_HIT = 3;
    private static final int NUMBER_OF_HITS = 3;

    // Constructor
    public MultiStrikeCard() {
        super("Multi-Strike", "Attack " + NUMBER_OF_HITS + " times for " + DAMAGE_PER_HIT + " damage each");
    }

    @Override
    public void play(Player player, Enemy enemy) {
        System.out.println(player.getName() + " performs Multi-Strike!");
        int totalDamage = 0;

        for (int i = 0; i < NUMBER_OF_HITS; i++) {
            System.out.println("  Hit " + (i + 1) + ": " + DAMAGE_PER_HIT + " damage!");
            enemy.takeDamage(DAMAGE_PER_HIT);
            totalDamage += DAMAGE_PER_HIT;
        }

        System.out.println("Total damage: " + totalDamage);
    }
}
