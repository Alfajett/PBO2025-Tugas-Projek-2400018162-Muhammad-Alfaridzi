package characters;

public abstract class Character {
    private String name;
    private int maxHP;
    private int currentHP;
    private int shield;

    public Character(String name, int maxHP) {
        this.name = name;
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.shield = 0;
    }

    public String getName() {
        return name;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public int getShield() {
        return shield;
    }

    public boolean isAlive() {
        return currentHP > 0;
    }

    public void setCurrentHP(int hp) {
        this.currentHP = Math.max(0, Math.min(hp, maxHP));
    }

    public void setShield(int shield) {
        this.shield = Math.max(0, shield);
    }

    // Combat methods
    public void takeDamage(int damage) {
        int actualDamage = damage;

        // Shield absorbs damage
        if (shield > 0) {
            if (shield >= damage) {
                shield -= damage;
                actualDamage = 0;
            } else {
                actualDamage = damage - shield;
                shield = 0;
            }
        }

        // Memberi sisa damage ke hp
        currentHP = Math.max(0, currentHP - actualDamage);
        System.out.println(name + " takes " + actualDamage + " damage! (" + currentHP + "/" + maxHP + " HP)");
    }

    public void heal(int amount) {
        int oldHP = currentHP;
        currentHP = Math.min(maxHP, currentHP + amount);
        int actualHeal = currentHP - oldHP;
        System.out.println(name + " heals " + actualHeal + " HP! (" + currentHP + "/" + maxHP + " HP)");
    }

    public void addShield(int amount) {
        shield += amount;
        System.out.println(name + " gains " + amount + " shield! (Total: " + shield + ")");
    }

    public void resetShield() {
        shield = 0;
    }

    // Display status
    public String getStatusString() {
        String status = name + ": " + currentHP + "/" + maxHP + " HP";
        if (shield > 0) {
            status += " | Shield: " + shield;
        }
        return status;
    }
}