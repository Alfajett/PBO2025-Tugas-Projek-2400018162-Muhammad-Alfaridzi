package enemies;

import characters.Player;

import java.util.Random;

public class Peacekeeper extends Enemy {
    private static final int MAX_HP = 55;
    private static final int NORMAL_DAMAGE = 10;
    private static final int ENRAGED_DAMAGE = 22;

    private final Random random = new Random();

    // Jika true, akan menunggu jika player melakukan attack
    private boolean awaitingResponse;
    private boolean pendingResolve;
    // Jika true, damage selanjutnya akan besar
    private boolean enragedNextAttack;

    public Peacekeeper() {
        super("Peacekeeper", MAX_HP);
        this.awaitingResponse = false;
        this.pendingResolve = false;
        this.enragedNextAttack = false;
    }

    @Override
    public void decideAction() {
        if (pendingResolve) {
            if (enragedNextAttack) {
                setNextAction("ENRAGED_ATTACK", ENRAGED_DAMAGE);
            } else {
                setNextAction("ATTACK", NORMAL_DAMAGE);
            }
            pendingResolve = false;
            return;
        }

        if (awaitingResponse) {
            setNextAction("PEACEFUL_STANCE", 0);
            return;
        }

        int roll = random.nextInt(100);
        if (roll < 35) {
            setNextAction("PEACEFUL", 0);
        } else {
            setNextAction("ATTACK", NORMAL_DAMAGE);
        }
    }

    @Override
    public void performAction(Player player) {
        switch (getNextAction()) {
            case "ATTACK":
                System.out.println(getName() + " attacks!");
                player.takeDamage(getNextActionValue());
                break;
            case "ENRAGED_ATTACK":
                System.out.println(getName() + " unleashes an ENRAGED ATTACK!");
                player.takeDamage(getNextActionValue());
                enragedNextAttack = false;
                break;
            case "PEACEFUL":
                System.out.println(getName() + " takes a deep breath and becomes PEACEFUL...");
                awaitingResponse = true;
                break;
            default:
                System.out.println(getName() + " hesitates...");
        }
    }

    @Override
    public void onPlayerTurnEnded(boolean playerAttackedThisTurn) {
        if (!awaitingResponse) {
            return;
        }

        if (playerAttackedThisTurn) {
            System.out.println(getName() + " was provoked! It becomes ENRAGED!");
            enragedNextAttack = true;
        } else {
            System.out.println(getName() + " remains calm.");
        }

        awaitingResponse = false;
        pendingResolve = true;
    }
}
