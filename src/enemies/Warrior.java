package enemies;

import characters.*;

public class Warrior extends Enemy {
    private static final int WARRIOR_HP = 60;
    private static final int BIG_ATTACK_DAMAGE = 24;
    private static final int CHARGE_TURNS = 2;

    private int chargeCounter;

    // Constructor
    public Warrior() {
        super("Warrior", WARRIOR_HP);
        this.chargeCounter = 0;
    }

    @Override
    public void decideAction() {
        if (chargeCounter < CHARGE_TURNS) {
            // Still charging
            chargeCounter++;
            setNextAction("CHARGING", BIG_ATTACK_DAMAGE);
        } else {
            // Ready to attack
            setNextAction("ATTACK", BIG_ATTACK_DAMAGE);
        }
    }

    @Override
    public void performAction(Player player) {
        System.out.println("\n=== " + getName() + "'s Turn ===");

        String action = getNextAction();

        if (action.equals("CHARGING")) {
            System.out.println(getName() + " is charging... (" + chargeCounter + "/" + CHARGE_TURNS + ")");
            System.out.println("Prepare for big attack next turn!");
        } else if (action.equals("ATTACK")) {
            System.out.println(getName() + " unleashes a powerful attack for " + BIG_ATTACK_DAMAGE + " damage!");
            player.takeDamage(BIG_ATTACK_DAMAGE);

            // Reset charge counter untuk cycle berikutnya
            chargeCounter = 0;
        }
    }
}