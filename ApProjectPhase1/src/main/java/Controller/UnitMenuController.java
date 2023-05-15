package Controller;

import Model.Game;
import Model.MapCell;

import java.util.regex.Matcher;

public class UnitMenuController {
    public static String moveUnit(Matcher matcher, Game game, MapCell cell) {
        matcher.matches();
        int x = Integer.parseInt(matcher.group("xCoordinate"));
        int y = Integer.parseInt(matcher.group("yCoordinate"));

        if(x < 0 || x > 199)
            return "x coordinate out of bounds";
        if(y < 0 || y > 199)
            return "y coordinate out of bounds";

        return null;
    }

    private static int[][] createMapForMove() {
        int[][] map = new int[200][200];
        for (int j = 0; j < 200; j++) {
            map[0][j] = -1;
            map[199][j] = -1;
        }
        for (int i = 0; i < 200; i++) {
            map[i][0] = -1;
            map[i][199] = -1;
        }
        MapCell[][] mapCell = Game.getCurrentGame().getMap().getMap();
        for (int i = 1; i < 199; i++) {
            for (int j = 1; j < 199; j++) {
                if (mapCell[i][j].getGroundType().getName().equals("rock") || mapCell[i][j].getGroundType().getName().equals("water"))
                    map[i][j] = -1;
                else if (mapCell[i][j].getRock() != null) map[i][j] = -1;
                else if (mapCell[i][j].getBuilding() != null) {
                    if (!mapCell[i][j].getBuilding().isPassable())
                        map[i][j] = -1;
                    else map[i][j] = 0;
                } else map[i][j] = 0;
            }
        }
        return map;
    }

    public static boolean move(int x, int y, int xI, int yI, int xF, int yF, int[][] map, int speed) {
        if (x == xF && y == yF && speed >= 0) return true;
        if (speed == 0) return false;
        if (map[x + 1][y] == 0) {
            map[x + 1][y] = 1;
            if (!move(x + 1, y, xI, yI, xF, yF, map, speed - 1)) map[x + 1][y] = 0;
            else return true;
        } else if (map[x][y + 1] == 0) {
            map[x][y + 1] = 1;
            if (!move(x, y + 1, xI, yI, xF, yF, map, speed - 1)) map[x][y + 1] = 0;
            else return true;
        } else if (map[x - 1][y] == 0) {
            map[x - 1][y] = 1;
            if (!move(x - 1, y, xI, yI, xF, yF, map, speed - 1)) map[x - 1][y] = 0;
            else return true;
        } else if (map[x][y - 1] == 0) {
            map[x][y - 1] = 1;
            if (!move(x, y - 1, xI, yI, xF, yF, map, speed - 1)) map[x][y - 1] = 0;
            else return true;
        }
        return false;
    }
}
