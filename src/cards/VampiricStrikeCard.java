package cards;

import characters.Player;
import enemies.Enemy;

public class VampiricStrikeCard extends Card {

    private static final int DAMAGE = 4;
    private static final int HEAL = 4;

    public VampiricStrikeCard() {
        super("Vampiric Strike", "Deal " + DAMAGE + " damage and heal " + HEAL + " HP");
    }

    @Override
    public void play(Player player, Enemy enemy) {
        System.out.println(player.getName() + " uses Vampiric Strike for " + DAMAGE + " damage and heals " + HEAL + "!");
        enemy.takeDamage(DAMAGE);
        player.heal(HEAL);
    }
}
