package Controller;

import Model.*;

import java.util.regex.Matcher;

public class PreGameController {
    private static Game currentGame;
    private static Government currentGovernment;

    public static String setTextureSingle(Matcher matcher) {
        matcher.matches();
        int x = Integer.parseInt(matcher.group("xCoordinate"));
        int y = Integer.parseInt(matcher.group("yCoordinate"));
        String typeName = matcher.group("type").replace("\"", "");
        GroundType groundType;
        WaterType waterType = null;

        if((groundType = getGroundTypeByName(typeName)) == null)
            return "invalid ground type or water type";
        else if(groundType.equals(GroundType.WATER))
            waterType = getWaterTypeByName(typeName);

        if(x > 199 || x < 0)
            return "x coordinate out of bounds";
        if(y > 199 || y < 0)
            return "y coordinate out of bounds";

        if(waterType.equals(WaterType.BIG_POND) ||
            waterType.equals(WaterType.SMALL_POND))
            return setTextureToPond(x, y, waterType);

        MapCell cell = currentGame.getMap().getCellByCoordinate(x, y);
        if(cell.getBuilding() != null ||
                cell.getTree() != null ||
                cell.getTroops().size() > 0)
            return "tile is not clear";

        cell.setGroundType(groundType);
        cell.setWaterType(waterType);

        return "changed texture successfully";
    }

    public static String setTextureToPond(int x, int y, WaterType waterType) {
        int size;
        if(waterType.equals(WaterType.SMALL_POND))
            size = 3;
        else size = 6;

        if(x + size - 1 > 199)
            return "x coordinate out of bounds";
        if(y + size - 1 > 199)
            return "y coordinate out of bounds";

        MapCell cell;
        for(int i = x ; i < x + size ; i++)
            for(int j = y ; j < y + size ; j++) {
                cell = currentGame.getMap().getCellByCoordinate(i, j);
                if(cell.getBuilding() != null ||
                        cell.getTree() != null ||
                        cell.getTroops().size() > 0)
                    return "tile at x: " + x + ", y: " + y + " is not clear";
            }

        for(int i = x ; i < x + size ; i++)
            for(int j = y ; j < y + size ; j++) {
                cell = currentGame.getMap().getCellByCoordinate(i, j);
                cell.setGroundType(GroundType.WATER);
                cell.setWaterType(waterType);
            }

        return "changed texture successfully";
    }

    public static GroundType getGroundTypeByName(String typeName) {
        for(GroundType groundType : GroundType.values())
            if(typeName.equals(groundType.getName()) && !typeName.equals("water"))
                return groundType;

        for(WaterType waterType : WaterType.values())
            if(typeName.equals(waterType.getName()))
                return GroundType.WATER;

        return null;
    }

    public static WaterType getWaterTypeByName(String typeName) {
        for(WaterType waterType : WaterType.values())
            if(typeName.equals(waterType.getName()))
                return waterType;
        return null;
    }

    public static Game getCurrentGame() {
        return currentGame;
    }

    public static void setCurrentGame(Game currentGame) {
        PreGameController.currentGame = currentGame;
    }

    public static Government getCurrentGovernment() {
        return currentGovernment;
    }

    public static void setCurrentGovernment(Government currentGovernment) {
        PreGameController.currentGovernment = currentGovernment;
    }
}
