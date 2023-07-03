package Controller;

import Model.*;
import Model.Buildings.*;
import Model.People.Lord;
import Model.People.Person;
import Model.People.Troop;
import Model.People.TroopType;

import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;

public class PreGameController {
    private static Game currentGame;
    private static Government currentGovernment;

    public static String setTextureSingle(String xCoordinate, String yCoordinate, String typeName) {
        int x = Integer.parseInt(xCoordinate);
        int y = Integer.parseInt(yCoordinate);

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
        if (waterType != null) {
            if (waterType.equals(WaterType.BIG_POND) ||
                    waterType.equals(WaterType.SMALL_POND))
                return setTextureToPond(x, y, waterType);
        }
        MapCell cell = currentGame.getMap().getCellByCoordinate(x, y);
        if(cell.getBuilding() != null ||
                cell.getTree() != null ||
                cell.getTroops().size() > 0)
            return "tile is not clear";

        cell.setGroundType(groundType);
        cell.setWaterType(waterType);

        return "";
    }

    public static String setTextureZone(String x1Coordinate, String y1Coordinate, String x2Coordinate, String y2Coordinate
                                        , String typeName) {
        int x1 = Integer.parseInt(x1Coordinate);
        int y1 = Integer.parseInt(y1Coordinate);
        int x2 = Integer.parseInt(x2Coordinate);
        int y2 = Integer.parseInt(y2Coordinate);
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
                        cell.getTroops().size() > 0 ||
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

        return "";
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
                        cell.getTroops().size() > 0 ||
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

    public static boolean isColorUsed(String inputColor) {
        for(Colors color : Colors.values()) {
            if(color.getName().equals(inputColor)) {
                if(currentGame.getUsedGovernmentColors().contains(color))
                    return true;
            }
        }
        return false;
    }

    public static String pickGovernmentColor(String inputColor, Government government, int i) {
        for(Colors color : Colors.values()) {
            if(color.getName().equals(inputColor)) {
                if(currentGame.getUsedGovernmentColors().contains(color))
                    return "this color has chosen!";
                else if (i == 1) {
                    government.setGovernmentColor(color);
                    currentGame.addToUsedGovernmentColors(color);
                    return "ok";
                }
            }
        }
        return "";
    }

    public static String placeKeep(String xCoordinate, String yCoordinate, int num) {
        if (!xCoordinate.matches("\\d+") || !yCoordinate.matches("\\d+")) return "coordinate must be integer!";
        int y = Integer.parseInt(yCoordinate);
        int x = Integer.parseInt(xCoordinate);
        if(x + 3 > 200 || x < 0)
            return "x coordinate out of bound";
        if(y + 5 > 200 || y < 0)
            return "y coordinate out of bound";

        MapCell cell;
        for(int i = x; i < x + 3; i++) {
            for(int j = y; j < y + 5; j++) {
                cell = currentGame.getMap().getCellByCoordinate(i, j);
                if(Building.getForbiddenGroundTypes().contains(cell.getGroundType()))
                    return "tile x: " + i + ", y: " + j + " ground type is invalid";
                if(cell.getTroops().size() > 0 ||
                        cell.getBuilding() != null ||
                        cell.getTree() != null ||
                        cell.getRock() != null) {
                    System.out.println(cell.getGroundType().getName());
                    return "tile x: " + i + ", y: " + j + " is not clear";
                }
            }
        }
        if (num == 1) {
            Building building = new Building(BuildingType.KEEP, currentGovernment, x, y);
            for (int i = x; i < x + 3; i++) {
                for (int j = y; j < y + 3; j++) {
                    cell = currentGame.getMap().getCellByCoordinate(i, j);
                    cell.setBuilding(building);
                }
            }
            building = new Storage(BuildingType.STOCKPILE, StorageType.STOCKPILE, currentGovernment, x + 1, y + 3);
            for (int i = x + 1; i < x + 3; i++) {
                for (int j = y + 3; j < y + 5; j++) {
                    cell = currentGame.getMap().getCellByCoordinate(i, j);
                    cell.setBuilding(building);
                }
            }
            Lord lord = new Lord(currentGovernment, TroopType.LORD, x, y);
            currentGovernment.setLord(lord);
            return "ok";
        }
        return "";
    }

    public static String clear(String xCoordinate, String yCoordinate) {
        int x = Integer.parseInt(xCoordinate);
        int y = Integer.parseInt(yCoordinate);

        if(x < 0 || x > 199)
            return "x coordinate out of bounds";
        if(y < 0 || y > 199)
            return "y coordinate out of bounds";

        MapCell cell = currentGame.getMap().getCellByCoordinate(x, y);
        if(cell.getBuilding() != null && cell.getBuilding().getType().equals(BuildingType.KEEP))
            return "can't clear a tile containing a keep";

        cell.setTree(null);
        cell.setRock(null);

        cell.setGroundType(GroundType.EARTH);
        cell.setWaterType(null);

        clearTroops(cell);
        clearBuilding(cell);

        return "";
    }

    public static void clearTroops(MapCell cell) {
        Person person;
        for(int i = cell.getTroops().size() - 1; i >= 0 ; i--) {
            person = cell.getTroops().get(i);
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
        if(building != null) {
            building.getGovernment().removeBuilding(building);
            MapCell cellIterator;
            int x = building.getxCoordinate();
            int y = building.getyCoordinate();
            int size = building.getSize();
            for (int i = x; i < x + size; i++) {
                for (int j = y; j < y + size; j++) {
                    cellIterator = getCurrentGame().getMap().getCellByCoordinate(i, j);
                    cellIterator.setBuilding(null);
                }
            }
        }
    }

    public static String droprock(String xCoordinate, String yCoordinate, String directionInput) {
        int x = Integer.parseInt(xCoordinate);
        int y = Integer.parseInt(yCoordinate);

        if(x < 0 || x > 199)
            return "x coordinate out of bounds";
        if(y < 0 || y > 199)
            return "y coordinate out of bounds";

        MapCell cell = currentGame.getMap().getCellByCoordinate(x, y);
        if(cell.getRock() != null ||
            cell.getTree() != null ||
            cell.getBuilding() != null ||
            cell.getTroops().size() > 0)
            return "tile is not clear";

        for(Directions direction : Directions.values()) {
            if(direction.getName().equals(directionInput)) {
                cell.setRock(new Rock(direction));
                return "";
            }
        }
        if(directionInput.equals("random")) {
            Random random = new Random();
            int randomNumber = random.nextInt() % 4;
            cell.setRock(new Rock(Directions.getDirectionByNumber(randomNumber)));
            return "";
        }
        return "invalid direction";
    }

    public static String droptree(String xCoordinate, String yCoordinate, String typeInput) {
        int x = Integer.parseInt(xCoordinate);
        int y = Integer.parseInt(yCoordinate);

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
                return "";
            }
        }

        return "invalid tree type";
    }

    public static String dropbuilding(String xCoordinate, String yCoordinate, String typeInput) {
        int x = Integer.parseInt(xCoordinate);
        int y = Integer.parseInt(yCoordinate);
        BuildingType buildingType;
        ProducerType producerType;
        StorageType storageType;
        TowerType towerType;
        TownBuildingType townBuildingType;
        TroopProducerType troopProducerType;

        if(x < 0 || x > 199)
            return "x coordinate out of bounds";
        if(y < 0 || y > 199)
            return "y coordinate out of bounds";

        buildingType = BuildingType.getBuildingTypeByName(typeInput);
        if(buildingType == null)
            return "invalid building type";

        MapCell cell;
        for(int i = x ; i < x + buildingType.getSize() ; i++)
            for(int j = y ; j < y + buildingType.getSize() ; j++) {
                cell = currentGame.getMap().getCellByCoordinate(i, j);
                if(cell.getBuilding() != null ||
                    cell.getTree() != null ||
                    cell.getRock() != null ||
                        (!buildingType.isPassable() && cell.getTroops().size() > 0))
                    return "tile x: " + x + ", y: " + y + " is not clear";
            }

        producerType = ProducerType.getProducerTypeByName(typeInput);
        if(producerType != null && producerType.getSpecialGroundType() != null)
            for(int i = x ; i < x + buildingType.getSize() ; i++)
                for(int j = y ; j < y + buildingType.getSize() ; j++) {
                    cell = currentGame.getMap().getCellByCoordinate(i, j);
                    if(!cell.getGroundType().equals(producerType.getSpecialGroundType()) ||
                            (cell.getWaterType() != null && !cell.getWaterType().equals(WaterType.PLAIN)))
                        return "tile x: " + x + ", y: " + y + " has invalid ground type";
                }
        else {
            for(int i = x ; i < x + buildingType.getSize() ; i++)
                for(int j = y ; j < y + buildingType.getSize() ; j++) {
                    cell = currentGame.getMap().getCellByCoordinate(i, j);
                    if(Building.getForbiddenGroundTypes().contains(cell.getGroundType()))
                        return "tile x: " + x + ", y: " + y + " has invalid ground type";
                }
        }

        Building building = null;
        if(producerType != null)
            building = new Producer(buildingType, producerType, currentGovernment, x, y);
        else if((storageType = StorageType.getStorageTypeByName(typeInput)) != null)
            building = new Storage(buildingType, storageType, currentGovernment, x, y);
        else if((towerType = TowerType.getTowerTypeByName(typeInput)) != null)
            building = new Tower(buildingType, towerType, currentGovernment, x, y);
        else if((townBuildingType = TownBuildingType.getTownBuildingTypeByName(typeInput)) != null)
            building = new TownBuilding(buildingType, townBuildingType, currentGovernment, x, y);
        else if((troopProducerType = TroopProducerType.getTroopProducerTypeByName(typeInput)) != null)
            building = new TroopProducers(buildingType, troopProducerType, currentGovernment, x, y);

        building.setWorkerNeeded(0);

        for(int i = x ; i < x + buildingType.getSize() ; i++)
            for(int j = y ; j < y + buildingType.getSize() ; j++) {
                cell = currentGame.getMap().getCellByCoordinate(i, j);
                cell.setBuilding(building);
            }


        return "";
    }

    public static String dropunit(String xCoordinate, String yCoordinate, String typeInput, String number) {
        int x = Integer.parseInt(xCoordinate);
        int y = Integer.parseInt(yCoordinate);
        int count = Integer.parseInt(number);


        if(x < 0 || x > 199)
            return "x coordinate out of bounds";
        if(y < 0 || y > 199)
            return "y coordinate out of bounds";

        if(count < 1)
            return "invalid number of troops";

        if(TroopType.getTroopTypeByName(typeInput) == null ||
            TroopType.getTroopTypeByName(typeInput).equals(TroopType.LORD))
            return "invalid troop type";

        MapCell cell = currentGame.getMap().getCellByCoordinate(x, y);
        if(cell.getRock() != null ||
            cell.getGroundType().equals(GroundType.WATER) ||
            cell.getGroundType().equals(GroundType.ROCK) ||
                (cell.getBuilding() != null && !cell.getBuilding().getType().isPassable()))
            return "tile is not clear";
        for(int i = 0 ; i < count ; i++)
            cell.addToTroops(new Troop(currentGovernment, TroopType.getTroopTypeByName(typeInput), x, y));

        return "";
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
