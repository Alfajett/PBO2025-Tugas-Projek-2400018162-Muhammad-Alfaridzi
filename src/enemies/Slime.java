package enemies;

import characters.*;

public class Slime extends Enemy {
    private static final int SLIME_HP = 85;
    private static final int ATTACK_DAMAGE = 8;

    // Constructor
    public Slime() {
        super("Slime", SLIME_HP);
    }

    @Override
    public void decideAction() {
        setNextAction("ATTACK", ATTACK_DAMAGE);
    }

    @Override
    public void performAction(Player player) {
        System.out.println("\n=== " + getName() + "'s Turn ===");
        System.out.println(getName() + " attacks for " + ATTACK_DAMAGE + " damage!");
        player.takeDamage(ATTACK_DAMAGE);
    }
}