package Controller;

import Model.*;
import Model.Buildings.Building;
import Model.Buildings.BuildingHP;
import Model.Buildings.BuildingType;
import Model.People.Person;
import Model.People.Troop;

import java.util.Map;
import java.util.Random;
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

        if((groundType = getGroundTypeByName(typeName)) == null || groundType.equals(GroundType.WATER)) {
            if((waterType = getWaterTypeByName(typeName)) == null)
                return "invalid ground type or water type";
            groundType = GroundType.WATER;
        }

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
                cell.getPeople().size() > 0)
            return "tile is not clear";

        cell.setGroundType(groundType);
        cell.setWaterType(waterType);

        return "changed texture successfully";
    }

    public static String setTextureZone(Matcher matcher) {
        matcher.matches();
        int x1 = Integer.parseInt(matcher.group("x1Coordinate"));
        int y1 = Integer.parseInt(matcher.group("y1Coordinate"));
        int x2 = Integer.parseInt(matcher.group("x2Coordinate"));
        int y2 = Integer.parseInt(matcher.group("y2Coordinate"));
        String typeName = matcher.group("type").replace("\"", "");
        GroundType groundType;
        WaterType waterType = null;

        if((groundType = getGroundTypeByName(typeName)) == null || groundType.equals(GroundType.WATER)) {
            if((waterType = getWaterTypeByName(typeName)) == null)
                return "invalid ground type or water type";
            groundType = GroundType.WATER;
        }

        if(x1 < 0 || x1 > 199)
            return "x1 out of bounds";
        if(x2 < 0 || x2 > 199)
            return "x2 out of bounds";
        if(x1 > x2)
            return "x2 can't be lesser than x1";

        if(y1 < 0 || y1 > 199)
            return "y1 out of bounds";
        if(y2 < 0 || y2 > 199)
            return "y2 out of bounds";
        if(y1 > y2)
            return "y2 can't be lesser than y1";

        if(typeName.equals("small pond") || typeName.equals("big pond"))
            return "this command is not valid for ponds";

        MapCell cell;
        for(int i = x1; i <=x2; i++) {
            for(int j = y1; j <= y2; j++) {
                cell = currentGame.getMap().getCellByCoordinate(i, j);
                if(cell.getBuilding() != null ||
                        cell.getTree() != null ||
                        cell.getPeople().size() > 0 ||
                        cell.getRock() != null)
                    return "tile at x: " + i + ", y: " + j + " is not clear";
            }
        }

        for(int i = x1; i <= x2; i++) {
            for(int j = y1; j <= y2; j++) {
                cell = currentGame.getMap().getCellByCoordinate(i, j);
                cell.setGroundType(groundType);
                cell.setWaterType(waterType);
            }
        }

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
                        cell.getPeople().size() > 0 ||
                        cell.getRock() != null)
                    return "tile at x: " + i + ", y: " + j + " is not clear";
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

    public static String pickGovernmentColor(String inputColor) {
        if(inputColor.equals("reset"))
            return "invalid color";
        for(Colors color : Colors.values()) {
            if(color.getName().equals(inputColor)) {
                if(currentGame.getUsedGovernmentColors().contains(color))
                    return "invalid color";
                else {
                    currentGovernment.setGovernmentColor(color);
                    return "you picked " + color.getName() + " color";
                }
            }
        }
        return "invalid color";
    }

    public static String placeKeep(int x, int y) {

        if(x + 3 > 200 || x < 0)
            return "x coordinate out of bound";
        if(y + 3 > 200 || y < 0)
            return "y coordinate out of bound";

        MapCell cell;
        for(int i = x; i < x + 3; i++) {
            for(int j = y; j < y + 3; j++) {
                cell = currentGame.getMap().getCellByCoordinate(i, j);
                if(Building.getForbiddenGroundTypes().contains(cell.getGroundType()))
                    return "tile x: " + i + ", y: " + j + " ground type is invalid";
                if(cell.getPeople().size() > 0 ||
                        cell.getBuilding() != null ||
                        cell.getTree() != null ||
                        cell.getRock() != null)
                    return "tile x: " + i + ", y: " + j + " is not clear";
            }
        }
        Building building = new Building(BuildingType.KEEP, currentGovernment, x, y);
        for(int i = x; i < x + 3; i++) {
            for (int j = y; j < y + 3; j++) {
                cell = currentGame.getMap().getCellByCoordinate(i, j);
                cell.setBuilding(building);
            }
        }
        return "placed building successfully";
    }

    public static String clear(Matcher matcher) {
        matcher.matches();
        int x = Integer.parseInt(matcher.group("xCoordinate"));
        int y = Integer.parseInt(matcher.group("yCoordinate"));

        if(x < 0 || x > 199)
            return "x coordinate out of bounds";
        if(y < 0 || y > 199)
            return "y coordinate out of bounds";

        MapCell cell = currentGame.getMap().getCellByCoordinate(x, y);
        if(cell.getBuilding().getType().equals(BuildingType.KEEP))
            return "can't clear a tile containing a keep";

        cell.setTree(null);
        cell.setRock(null);

        cell.setGroundType(GroundType.EARTH);
        cell.setWaterType(null);

        clearTroops(cell);
        clearBuilding(cell);

        return "tile cleared";
    }

    public static void clearTroops(MapCell cell) {
        Person person;
        for(int i = cell.getPeople().size() - 1 ; i >= 0 ; i--) {
            person = cell.getPeople().get(i);
            person.getGovernment().removePerson(person);
            cell.removeFromPeople(person);

            if(person instanceof Troop) {
                person.getGovernment().removeTroop((Troop) person);
                cell.removeFromTroops((Troop) person);
            }
        }
    }

    public static void clearBuilding(MapCell cell) {
        Building building = cell.getBuilding();
        building.getGovernment().removeBuilding(building);
        MapCell cellIterator;
        int x = building.getxCoordinate();
        int y = building.getyCoordinate();
        int size = building.getSize();
        for(int i = x; i < x + size; i++) {
            for(int j = y; j < y + size; j++) {
                cellIterator = getCurrentGame().getMap().getCellByCoordinate(i, j);
                cellIterator.setBuilding(null);
            }
        }
    }

    public static String droprock(Matcher matcher) {
        matcher.matches();
        int x = Integer.parseInt(matcher.group("xCoordinate"));
        int y = Integer.parseInt(matcher.group("yCoordinate"));
        String directionInput = matcher.group("direction").replace("\"", "");

        if(x < 0 || x > 199)
            return "x coordinate out of bounds";
        if(y < 0 || y > 199)
            return "y coordinate out of bounds";

        MapCell cell = currentGame.getMap().getCellByCoordinate(x, y);
        if(cell.getRock() != null ||
            cell.getTree() != null ||
            cell.getBuilding() != null ||
            cell.getPeople().size() > 0)
            return "tile is not clear";

        for(Directions direction : Directions.values()) {
            if(direction.getName().equals(directionInput)) {
                cell.setRock(new Rock(direction));
                return "placed rock successfully";
            }
        }
        if(directionInput.equals("random")) {
            Random random = new Random();
            int randomNumber = random.nextInt() % 4;
            cell.setRock(new Rock(Directions.getDirectionByNumber(randomNumber)));
            return "placed rock successfully";
        }
        return "invalid direction";
    }

    public static String droptree(Matcher matcher) {
        matcher.matches();
        int x = Integer.parseInt(matcher.group("xCoordinate"));
        int y = Integer.parseInt(matcher.group("yCoordinate"));
        String typeInput = matcher.group("type").replace("\"", "");

        if(x < 0 || x > 199)
            return "x coordinate out of bounds";
        if(y < 0 || y > 199)
            return "y coordinate out of bounds";

        MapCell cell = currentGame.getMap().getCellByCoordinate(x, y);
        if(cell.getRock() != null ||
                cell.getTree() != null ||
                cell.getBuilding() != null)
            return "tile is not clear";

        GroundType groundType = cell.getGroundType();
        if(groundType.equals(GroundType.WATER) ||
                groundType.equals(GroundType.ROCK) ||
                groundType.equals(GroundType.SLATE) ||
                groundType.equals(GroundType.IRON))
            return "invalid ground type";

        for(Tree tree: Tree.values()) {
            if(tree.getName().equals(typeInput)) {
                cell.setTree(tree);
                return "placed tree successfully";
            }
        }

        return "invalid tree type";
    }

    public static String dropbuilding(Matcher matcher) {
        matcher.matches();
        int x = Integer.parseInt(matcher.group("xCoordinate"));
        int y = Integer.parseInt(matcher.group("yCoordinate"));
        String typeInput = matcher.group("type").replace("\"", "");

        if(x < 0 || x > 199)
            return "x coordinate out of bounds";
        if(y < 0 || y > 199)
            return "y coordinate out of bounds";


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
