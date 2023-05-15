package Controller;

import Model.*;
import Model.Buildings.*;
import Model.People.Troop;
import Model.People.TroopType;

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

        System.out.println("fear popularity: " + currentGovernment.getFearRate());

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

    public static void endGame() {

    }

    public static void nextTurn() {
        for(Government government : Game.getCurrentGame().getGovernments()) {
            for(Building building : government.getBuildings()) {
                if(building instanceof Producer)
                    ((Producer) building).produce(government);
            }
            int foodAmount = (int) ((government.getFoodRate() + 2) * government.getPeasantPopulation() * 0.5);
            int newAmount;
            for(Map.Entry<FoodType, Integer> entry : government.getFoods().entrySet()) {
                newAmount = entry.getValue() - Math.min(foodAmount, entry.getValue());
                foodAmount -= Math.min(foodAmount, entry.getValue());
                government.changeFoodAmount(entry.getKey(), newAmount);
            }
            double tax;
            if(government.getTaxRate() < 0)
                tax = -0.6 + (government.getTaxRate() + 1) * 0.2;
            else if(government.getTaxRate() == 0)
                tax = 0;
            else
                tax = 0.6 + (government.getTaxRate() - 1) * 0.2;
            if(tax <= 0)
                government.setGold(government.getGold() - Math.min(government.getGold(), -tax * government.getPeasantPopulation()));
            else
                government.setGold(government.getGold() + tax * government.getPeasantPopulation());
            if(government.getTroops().size() > 0) {
                for (Troop troop : government.getTroops()) {
                    if (!troop.getType().equals(TroopType.LORD) &&
                            troop.getHitPoint() < 1) {
                        Game.getCurrentGame().getMap().getCellByCoordinate(troop.getX(), troop.getY()).removeFromTroops(troop);
                    }
                }
            }
            if(government.getBuildings().size() > 0) {
                for(Building building : government.getBuildings()) {
                    if(building.getHitPoint() < 1)
                        for(int i = building.getxCoordinate() ; i < building.getxCoordinate() + building.getSize() ; i++)
                            for(int j = building.getyCoordinate(); j < building.getyCoordinate() + building.getSize(); j++) {
                                Game.getCurrentGame().getMap().getCellByCoordinate(i, j).setBuilding(null);
                            }
                }
            }
            if(government.getLord().getHitPoint() < 1)
                government.
        }

    }

    public static boolean isGameOver() {
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
}
