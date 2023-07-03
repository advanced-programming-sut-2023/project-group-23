package Controller;

import Model.*;
import Model.Buildings.*;
import Model.People.TempJFX.Tile;
import Model.People.Troop;
import Model.People.TroopType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

public class GameMenuController {
    private static Government currentGovernment;
    private static Game currentGame;

    public static Government getCurrentGovernment() {
        return currentGovernment;
    }

    public static void setCurrentGovernment(Government currentGovernment) {
        GameMenuController.currentGovernment = currentGovernment;
    }

    public static void showPopularityFactors() {
        int foodDiversity = -1;
        for(int amount : currentGovernment.getFoods().values()) {
            if(amount > 0)
                foodDiversity++;
        }
        int foodPopularity = Math.max(foodDiversity, 0) + currentGovernment.getFoodRate() * 4 + currentGovernment.getInnPopularity();
        System.out.println("food popularity: " + foodPopularity);

        int taxPopularity;
        int taxRate = currentGovernment.getTaxRate();
        if(taxRate < 1)
            taxPopularity = 1 - 2 * taxRate;
        else if(taxRate < 5)
            taxPopularity = -2 * taxRate;
        else
            taxPopularity = 4 * taxRate - 8;
        System.out.println("tax popularity: " + taxPopularity);

        System.out.println("fear popularity: " + (-currentGovernment.getFearRate()));

        System.out.println("religious popularity: " + currentGovernment.getReligiousRate());

        int totalPopularity = foodPopularity + taxPopularity -
                currentGovernment.getFearRate() +
                currentGovernment.getReligiousRate();
        System.out.println("total popularity rate: " + totalPopularity);
    }

    public static String showPopularity() {
        int foodDiversity = -1;
        for(int amount : currentGovernment.getFoods().values()) {
            if(amount > 0)
                foodDiversity++;
        }
        int foodPopularity = Math.max(foodDiversity, 0) + currentGovernment.getFoodRate() * 4;

        int taxPopularity;
        int taxRate = currentGovernment.getTaxRate();
        if(taxRate < 1)
            taxPopularity = 1 - 2 * taxRate;
        else if(taxRate < 5)
            taxPopularity = -2 * taxRate;
        else
            taxPopularity = 4 * taxRate - 8;

        int totalPopularity = foodPopularity + taxPopularity -
                currentGovernment.getFearRate() +
                currentGovernment.getReligiousRate();
        return "total popularity rate: " + totalPopularity;
    }

    public static void showFoodList() {
        for(Map.Entry<FoodType, Integer> entry : currentGovernment.getFoods().entrySet()) {
            System.out.println(entry.getKey().getName() + ": " + entry.getValue());
        }
    }

    public static String setFoodRate(Matcher matcher) {
        matcher.matches();
        int inputRate = Integer.parseInt(matcher.group("rateNumber"));

        if(inputRate < -2 || inputRate > 2)
            return "rate number out of bounds";

        boolean isStorageEmpty = true;
        for(int amount : currentGovernment.getFoods().values())
            if(amount > 0) {
                isStorageEmpty = false;
                break;
            }
        if(isStorageEmpty)
            return "rate only can be -2 when storage is empty";

        currentGovernment.setFoodRate(inputRate);
        return "set food rate successfully";
    }

    public static String  showFoodRate() {
         return "food rate: " + currentGovernment.getFoodRate();
    }

    public static String setTaxRate(Matcher matcher) {
        matcher.matches();
        int inputRate = Integer.parseInt(matcher.group("rateNumber"));

        if(inputRate < -3 || inputRate > 8)
            return "rate number out of bounds";

        if(currentGovernment.getGold() < 1 && inputRate < 0)
            return "can't donate gold when you're out of golds";

        currentGovernment.setTaxRate(inputRate);
        return "set tax rate successfully";
    }

    public static String showTaxRate() {
        return "tax rate: " + currentGovernment.getTaxRate();
    }

    public static String setFearRate(Matcher matcher) {
        matcher.matches();
        int inputRate = Integer.parseInt(matcher.group("rateNumber"));

        if(inputRate < -5 || inputRate > 5)
            return "rate number out of bounds";

        currentGovernment.setFearRate(inputRate);
        return "set fear rate successfully";
    }

    public static void endGame() throws IOException {
        for(Government government : currentGame.getGovernments()) {
            if(government.getRoundLost() == 0) {
                government.setRoundLost(Game.getCurrentGame().getRounds());
                government.setScore(government.getScore() + 2000);
            }
            government.setScore(government.getScore() + 50 * government.getRoundLost());
        }
        ArrayList<Government> governments = new ArrayList<>();
        governments.addAll(currentGame.getGovernments());
        for (int i = 1; i < governments.size(); i++) {
            for (int j = 0; j < i; j ++) {
                if (governments.get(j).getScore() < governments.get(i).getScore()) {
                    Collections.swap(governments, i, j);
                }
            }
        }
        String result = "";
        int i = 1;
        for (Government government : governments) {
            result += (i + ". user Nickname : " + government.getUser().getNickname() + " , round lost : " + government.getRoundLost() + " , score: " + government.getScore() + "\n");
            i++;
            if (government.getUser().getUserHighScore() < government.getScore()) government.getUser().setUserHighScore(government.getScore());
        }
        System.out.print(result);
    }

    public static void nextTurn() {
        for(Government government : Game.getCurrentGame().getGovernments()) {
            if(government.getLord().getHitPoint() < 1 && government.getRoundLost() == 0) {
                government.setRoundLost(Game.getCurrentGame().getRounds());
                Troop troop;
                Building building;
                for (int j = government.getTroops().size() - 1 ; j >= 0 ; j--) {
                    troop = government.getTroops().get(j);
                    Game.getCurrentGame().getMap().getCellByCoordinate(troop.getX(), troop.getY()).removeFromTroops(troop);
                }

                for(int k = government.getBuildings().size() - 1 ; k >= 0 ; k--) {
                    building = government.getBuildings().get(k);

                    for(int i = building.getxCoordinate() ; i < building.getxCoordinate() + building.getSize() ; i++)
                        for(int j = building.getyCoordinate(); j < building.getyCoordinate() + building.getSize(); j++) {
                            Game.getCurrentGame().getMap().getCellByCoordinate(i, j).setBuilding(null);
                        }
                }
            }

            else if(government.getLord().getHitPoint() > 0) {

                for (Building building : government.getBuildings()) {
                    if (building instanceof Producer)
                        ((Producer) building).produce(government);
                }
                int foodAmount = (int) ((government.getFoodRate() + 2) * government.getPeasantPopulation() * 0.5);
                int newAmount;
                for (Map.Entry<FoodType, Integer> entry : government.getFoods().entrySet()) {
                    newAmount = entry.getValue() - Math.min(foodAmount, entry.getValue());
                    foodAmount -= Math.min(foodAmount, entry.getValue());
                    government.changeFoodAmount(entry.getKey(), newAmount);
                }
                double tax;
                if (government.getTaxRate() < 0)
                    tax = -0.6 + (government.getTaxRate() + 1) * 0.2;
                else if (government.getTaxRate() == 0)
                    tax = 0;
                else
                    tax = 0.6 + (government.getTaxRate() - 1) * 0.2;
                if (tax <= 0)
                    government.setGold(government.getGold() - Math.min(government.getGold(), -tax * government.getPeasantPopulation()));
                else
                    government.setGold(government.getGold() + tax * government.getPeasantPopulation());

                Troop troop;
                Building building;
                for (int j = government.getTroops().size() - 1; j >= 0; j--) {
                    troop = government.getTroops().get(j);
                    troop.setMovedThisRound(false);
                    if (troop.getHitPoint() < 1) {
                        Game.getCurrentGame().getMap().getCellByCoordinate(troop.getX(), troop.getY()).removeFromTroops(troop);
                    }
                }


                for (int k = government.getBuildings().size() - 1; k >= 0; k--) {
                    building = government.getBuildings().get(k);
                    if (building.getHitPoint() < 1)
                        for (int i = building.getxCoordinate(); i < building.getxCoordinate() + building.getSize(); i++)
                            for (int j = building.getyCoordinate(); j < building.getyCoordinate() + building.getSize(); j++) {
                                Game.getCurrentGame().getMap().getCellByCoordinate(i, j).setBuilding(null);
                            }
                }
            }
        }

    }

    public static boolean isGameOver() {
        int aliveCounter = currentGame.getGovernments().size();
        for (Government government : currentGame.getGovernments()) {
           if (government.getLord().getHitPoint() < 1) aliveCounter--;
        }
        if (aliveCounter <= 1) return true;
        return false;
    }

    public static String dropBuilding(Matcher matcher) {
        matcher.matches();
        int x = Integer.parseInt(matcher.group("xCoordinate"));
        int y = Integer.parseInt(matcher.group("yCoordinate"));
        String typeInput = matcher.group("type").replace("\"", "");
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

        if(buildingType.getCost() > currentGovernment.getGold())
            return "you don't have enough gold";
        ResourceType resourceCostType = buildingType.getResourceCostType();
        int resourceCost = buildingType.getResourceCost();
        if(currentGovernment.getAmountByResource(resourceCostType) < resourceCost)
            return "you don't have enough " + resourceCostType.getName();

        currentGovernment.setGold(currentGovernment.getGold() - buildingType.getCost());
        currentGovernment.changeAmountOfResource(resourceCostType, currentGovernment.getAmountByResource(resourceCostType) - resourceCost);

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

        for(int i = x ; i < x + buildingType.getSize() ; i++)
            for(int j = y ; j < y + buildingType.getSize() ; j++) {
                cell = currentGame.getMap().getCellByCoordinate(i, j);
                cell.setBuilding(building);
            }

        int workersNeeded = building.getWorkerNeeded();
        building.setWorkerNeeded(workersNeeded - Math.min(workersNeeded, currentGovernment.getPeasantPopulation()));
        currentGovernment.setPeasantPopulation(currentGovernment.getPeasantPopulation() - Math.min(workersNeeded, currentGovernment.getPeasantPopulation()));

        return "dropped building successfully";
    }

    public static Building selectBuilding(Matcher matcher) {
        matcher.matches();
        int x = Integer.parseInt(matcher.group("xCoordinate"));
        int y = Integer.parseInt(matcher.group("yCoordinate"));

        if(x < 0 || x > 199) {
            System.out.println("x coordinate out of bounds");
            return null;
        }
        if(y < 0 || y > 199) {
            System.out.println("y coordinate out of bounds");
            return null;
        }

        Building building;
        MapCell cell = currentGame.getMap().getCellByCoordinate(x, y);
        if((building = cell.getBuilding()) == null) {
            System.out.println("this tile is empty");
            return null;
        }

        System.out.println("building type: " + building.getType().getName() +
                ", government: " + building.getGovernment().getUser().getNickname() +
                ", building hitpoint: " + building.getHitPoint());

        if(!building.getGovernment().equals(currentGovernment)) {
            System.out.println("not accessable");
            return null;
        }

        return building;
    }

    public static MapCell selectUnit(Matcher matcher) {
        matcher.matches();
        int x = Integer.parseInt(matcher.group("xCoordinate"));
        int y = Integer.parseInt(matcher.group("yCoordinate"));

        if(x < 0 || x > 199) {
            System.out.println("x coordinate out of bounds");
            return null;
        }
        if(y < 0 || y > 199) {
            System.out.println("y coordinate out of bounds");
            return null;
        }

        MapCell cell = currentGame.getMap().getCellByCoordinate(x, y);
        for(Troop troop : cell.getTroops())
            System.out.println("troop: " + troop.getType().getName() +
                        ", hitpoint: " + troop.getHitPoint() +
                    ", government: " + troop.getGovernment().getUser().getNickname());

        return cell;
    }

    public static String moveUnitJFX(TroopType troopType, int amount, MapCell destinationCell) {
        if(destinationCell.getRock() != null ||
                destinationCell.getGroundType().equals(GroundType.WATER) ||
                destinationCell.getGroundType().equals(GroundType.ROCK) ||
                (destinationCell.getBuilding() != null && !destinationCell.getBuilding().getType().isPassable()))
            return "Destination cell is not clear!";

        int[][] mapForMove = createMapForMove();
        int x = destinationCell.getX();
        int y = destinationCell.getY();
        MapCell initialCell = Tile.getSelectedTile().getCell();

        if(troopType.getSpeed() < Math.abs(x - initialCell.getX()) + Math.abs(y - initialCell.getY()) ||
                move(initialCell.getX(), initialCell.getY(), initialCell.getX(), initialCell.getY(), x, y, mapForMove, troopType.getSpeed()))
            return "Destination cell is not reachable!";

        Troop troop;
        for(int j = initialCell.getTroops().size() - 1 ; j >= 0 && amount > 0 ; j--) {
            troop = initialCell.getTroops().get(j);
            if(troop.getGovernment().equals(currentGovernment) &&
                    !troop.isMovedThisRound() &&
                    troop.getType().equals(troopType)) {
                troop.setX(x);
                troop.setY(y);
                troop.setMovedThisRound(true);
                initialCell.removeFromTroops(troop);
                destinationCell.addToTroops(troop);
                amount--;
            }
        }
        return "";
    }

    private static int[][] createMapForMove() {
        int[][] map = new int[202][202];
        for (int j = 0; j < 202; j++) {
            map[0][j] = -1;
            map[201][j] = -1;
        }
        for (int i = 0; i < 202; i++) {
            map[i][0] = -1;
            map[i][201] = -1;
        }
        MapCell[][] mapCell = Game.getCurrentGame().getMap().getMap();
        for (int i = 1; i < 201; i++) {
            for (int j = 1; j < 201; j++) {
                if (mapCell[i - 1][j - 1].getGroundType().getName().equals("rock") || mapCell[i - 1][j - 1].getGroundType().getName().equals("water"))
                    map[i][j] = -1;
                else if (mapCell[i - 1][j - 1].getRock() != null) map[i][j] = -1;
                else if (mapCell[i - 1][j - 1].getBuilding() != null) {
                    if (!mapCell[i - 1][j - 1].getBuilding().isPassable())
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
        }
        else if (map[x][y + 1] == 0) {
            map[x][y + 1] = 1;
            if (!move(x, y + 1, xI, yI, xF, yF, map, speed - 1)) map[x][y + 1] = 0;
            else return true;
        }
        else if (map[x - 1][y] == 0) {
            map[x - 1][y] = 1;
            if (!move(x - 1, y, xI, yI, xF, yF, map, speed - 1)) map[x - 1][y] = 0;
            else return true;
        }
        else if (map[x][y - 1] == 0) {
            map[x][y - 1] = 1;
            if (!move(x, y - 1, xI, yI, xF, yF, map, speed - 1)) map[x][y - 1] = 0;
            else return true;
        }
        return false;
    }


    public static void patrolUnit(Matcher matcher) {
    }



    public static void attack(Matcher matcher) {
    }

    public static void airAttack(Matcher matcher) {
    }

    public static void pourOil(Matcher matcher) {
    }

    public static void digTunnel(Matcher matcher) {
    }

    public static void buildSurroundEquipment(Matcher matcher) {
    }

    public static void disbandUnit() {
    }

    public static Game getCurrentGame() {
        return currentGame;
    }

    public static void setCurrentGame(Game currentGame) {
        GameMenuController.currentGame = currentGame;
    }

    public static HashMap<TroopType, Integer> getTroopTypes(Tile selectedTile) {
        MapCell cell = selectedTile.getCell();
        HashMap<TroopType, Integer> map = new HashMap<>();
        if(cell.getTroops().size() > 0)
            for (Troop troop : cell.getTroops()) {
                if(troop.getGovernment().equals(currentGovernment) && !troop.isMovedThisRound()) {
                    if (map.containsKey(troop.getType())) map.put(troop.getType(), map.get(troop.getType()) + 1);
                    else map.put(troop.getType(), 1);
                }
            }

        return map;
    }
}
