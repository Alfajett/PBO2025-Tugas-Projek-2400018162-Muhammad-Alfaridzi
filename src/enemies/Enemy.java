package enemies;

import characters.Player;

public abstract class Enemy extends characters.Character {
    private String nextAction;
    private int nextActionValue;

    // Constructor
    public Enemy(String name, int maxHP) {
        super(name, maxHP);
        this.nextAction = "";
        this.nextActionValue = 0;
    }

    public abstract void decideAction();

    public abstract void performAction(Player player);

    public void onPlayerTurnEnded(boolean playerAttackedThisTurn) {
    }

    protected void setNextAction(String action, int value) {
        this.nextAction = action;
        this.nextActionValue = value;
    }

    public String getNextActionInfo() {
        if (nextAction.isEmpty()) {
            return "???";
        }

        switch (nextAction) {
            case "ATTACK":
                return "ATTACK (" + nextActionValue + " damage)";
            case "DEFEND":
                return "DEFEND (" + nextActionValue + " shield)";
            case "CHARGING":
                return "CHARGING... (next turn: " + nextActionValue + " damage)";
            case "PEACEFUL_STANCE":
                return "PEACEFUL (reactive)";
            case "ENRAGED_ATTACK":
                return "ENRAGED ATTACK (" + nextActionValue + " damage)";
            default:
                return nextAction;
        }
    }

    public String getNextAction() {
        return nextAction;
    }

    public int getNextActionValue() {
        return nextActionValue;
    }
}
