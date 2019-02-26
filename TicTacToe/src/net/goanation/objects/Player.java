package net.goanation.objects;

/**
 * PlayerClass with default constructor and getter and setters
 */
public class Player {
    private String name;
    private int gamesWon = 0;

    public Player() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon() {
        gamesWon++;
    }
}
