package cards;

import characters.Player;
import enemies.Enemy;

public class GuardUpCard extends Card {

    private static final int SHIELD = 10;

    public GuardUpCard() {
        super("Guard Up", "Gain " + SHIELD + " shield");
    }

    @Override
    public void play(Player player, Enemy enemy) {
        player.addShield(SHIELD);
    }
}
