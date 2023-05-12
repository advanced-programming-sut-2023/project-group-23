package Model;

import java.util.ArrayList;
import java.util.HashMap;

public enum Directions {
    UP ("up", -1, 0),
    LEFT ("left", 0, -1),
    RIGHT ("right", 0, 1),
    DOWN ("down", 1, 0);

    private String name;
    private int deltaX;
    private int deltaY;

    private static ArrayList<Directions> directionsList = new ArrayList<>() {{
        add(UP);
        add(LEFT);
        add(RIGHT);
        add(DOWN);
    }};

    Directions(String name, int deltaX, int deltaY) {
        this.name = name;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public String getName() {
        return name;
    }

    public int getDeltaX() {
        return deltaX;
    }

    public int getDeltaY() {
        return deltaY;
    }

    public static Directions getDirectionByNumber(int i) {
        return directionsList.get(i);
    }
}
