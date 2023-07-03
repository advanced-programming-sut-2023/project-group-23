package Model;

import java.util.ArrayList;

public class Game {

    private static Game currentGame;
    private ArrayList<Government> governments;
    private ArrayList<Colors> usedGovernmentColors;
    private Maps map;
    private int rounds;

    public Game(ArrayList<Government> governments, Maps map) {
        this.governments = governments;
        this.map = map;
        this.usedGovernmentColors = new ArrayList<>();
        this.rounds = 1;
    }

    public ArrayList<Government> getGovernments() {
        return governments;
    }

    public Maps getMap() {
        return map;
    }

    public void setGovernments(ArrayList<Government> governments) {
        this.governments = governments;
    }

    public void setMap(Maps map) {
        this.map = map;
    }

    public static Game getCurrentGame() {
        return currentGame;
    }

    public static void setCurrentGame(Game currentGame) {
        Game.currentGame = currentGame;
    }

    public ArrayList<Colors> getUsedGovernmentColors() {
        return usedGovernmentColors;
    }

    public void addToUsedGovernmentColors(Colors governmentColor) {
        usedGovernmentColors.add(governmentColor);
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }
}
