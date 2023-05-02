package Model;

import Model.Government;
import Model.Map;

import java.util.ArrayList;

public class Game {

    private static Game currentGame;
    private ArrayList<Government> governments;
    private Map map;

    public Game(ArrayList<Government> governments, Map map) {
        this.governments = governments;
        this.map = map;
    }

    public ArrayList<Government> getGovernments() {
        return governments;
    }

    public Map getMap() {
        return map;
    }

    public void setGovernments(ArrayList<Government> governments) {
        this.governments = governments;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public static Game getCurrentGame() {
        return currentGame;
    }

    public static void setCurrentGame(Game currentGame) {
        Game.currentGame = currentGame;
    }
}
