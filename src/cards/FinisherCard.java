package cards;

import characters.Player;
import enemies.Enemy;

public class FinisherCard extends Card {

    private static final int NORMAL_DAMAGE = 5;
    private static final int FINISH_DAMAGE = 12;
    private static final int FINISH_THRESHOLD_HP = 20;

    public FinisherCard() {
        super("Finisher",
                "Deal " + NORMAL_DAMAGE + " damage (" + FINISH_DAMAGE + " if enemy HP \u2264 "
                        + FINISH_THRESHOLD_HP + ")"
        );
    }

    @Override
    public void play(Player player, Enemy enemy) {
        int enemyHp = enemy.getCurrentHP();
        int dmg = (enemyHp <= FINISH_THRESHOLD_HP) ? FINISH_DAMAGE : NORMAL_DAMAGE;

        System.out.println(player.getName() + " uses Finisher for " + dmg + " damage!");
        enemy.takeDamage(dmg);
    }
}
