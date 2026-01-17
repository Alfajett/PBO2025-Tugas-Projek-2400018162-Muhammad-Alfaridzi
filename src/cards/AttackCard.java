package cards;

import characters.Player;
import enemies.*;

public class AttackCard extends Card {

    private static final int DAMAGE = 7;

    // Constructor
    public AttackCard() {
        super("Attack", "Deal " + DAMAGE + " damage to enemy");
    }


    @Override
    public void play(Player player, Enemy enemy) {
        System.out.println(player.getName() + " attacks for " + DAMAGE + " damage!");
        enemy.takeDamage(DAMAGE);
    }
}