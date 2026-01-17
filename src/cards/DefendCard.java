package cards;

import characters.Player;
import enemies.*;

public class DefendCard extends Card {
    private static final int SHIELD = 6;

    // Constructor
    public DefendCard() {
        super("Defend", "Gain " + SHIELD + " shield");
    }

    @Override
    public void play(Player player, Enemy enemy) {
        player.addShield(SHIELD);
    }
}