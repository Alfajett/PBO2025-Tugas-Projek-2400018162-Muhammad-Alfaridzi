package cards;

import characters.Player;
import enemies.*;

public class HealCard extends Card {

    private static final int HEAL_AMOUNT = 10;

    // Constructor
    public HealCard() {
        super("Heal", "Restore " + HEAL_AMOUNT + " HP");
    }

    @Override
    public void play(Player player, Enemy enemy) {
        player.heal(HEAL_AMOUNT);
    }
}