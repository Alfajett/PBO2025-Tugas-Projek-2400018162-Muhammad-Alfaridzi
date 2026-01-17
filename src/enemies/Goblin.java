package enemies;

import java.util.Random;
import characters.*;

public class Goblin extends Enemy {
    private static final int GOBLIN_HP = 55;
    private static final int ATTACK_DAMAGE = 10;
    private static final int DEFEND_SHIELD = 6;

    private Random random;

    // Constructor
    public Goblin() {
        super("Goblin", GOBLIN_HP);
        this.random = new Random();
    }

    @Override
    public void decideAction() {
        int choice = random.nextInt(100);

        if (choice < 70) {
            // 70% chance to attack
            setNextAction("ATTACK", ATTACK_DAMAGE);
        } else {
            // 30% chance to defend
            setNextAction("DEFEND", DEFEND_SHIELD);
        }
    }

    @Override
    public void performAction(Player player) {
        System.out.println("\n=== " + getName() + "'s Turn ===");

        String action = getNextAction();
        int value = getNextActionValue();

        if (action.equals("ATTACK")) {
            System.out.println(getName() + " attacks for " + value + " damage!");
            player.takeDamage(value);
        } else if (action.equals("DEFEND")) {
            System.out.println(getName() + " defends!");
            addShield(value);
        }
    }
}