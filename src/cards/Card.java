package cards;

import characters.Player;
import enemies.*;

public abstract class Card {
    private String name;
    private String description;

    // Constructor
    public Card(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public abstract void play(Player player, Enemy enemy);

    @Override
    public String toString() {
        return name + " - " + description;
    }

    public boolean isSameType(Card other) {
        return this.getClass().equals(other.getClass());
    }
}