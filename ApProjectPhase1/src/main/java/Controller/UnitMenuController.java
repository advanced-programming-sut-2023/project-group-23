package Controller;

import Model.Buildings.Building;
import Model.Buildings.BuildingType;
import Model.Buildings.TowerType;
import Model.Buildings.TroopProducerType;
import Model.Game;
import Model.Government;
import Model.GroundType;
import Model.MapCell;
import Model.People.Troop;
import Model.People.TroopState;
import Model.People.TroopType;
import Model.People.Tunneler;

import java.util.Map;
import java.util.regex.Matcher;

public class UnitMenuController {
    public static String moveUnit(Matcher matcher, Government government, MapCell initialCell) {
        matcher.matches();
        int x = Integer.parseInt(matcher.group("xCoordinate"));
        int y = Integer.parseInt(matcher.group("yCoordinate"));

        if(x < 0 || x > 199)
            return "x coordinate out of bounds";
        if(y < 0 || y > 199)
            return "y coordinate out of bounds";

        MapCell destinationCell = Game.getCurrentGame().getMap().getCellByCoordinate(x, y);
        int[][] mapForMove = createMapForMove();
        if(destinationCell.getRock() != null ||
                destinationCell.getGroundType().equals(GroundType.WATER) ||
                destinationCell.getGroundType().equals(GroundType.ROCK) ||
                (destinationCell.getBuilding() != null && !destinationCell.getBuilding().getType().isPassable()))
            return "destination cell is not clear";

        Troop troop;
        for(int j = initialCell.getTroops().size() - 1 ; j >= 0 ; j--) {
            troop = initialCell.getTroops().get(j);
            if (troop.getGovernment().equals(government) &&
                    !troop.isMovedThisRound() &&
                    troop.getType().getSpeed() >= Math.abs(x - initialCell.getX()) + Math.abs(y - initialCell.getY()) &&
                    move(initialCell.getX(), initialCell.getY(), initialCell.getX(), initialCell.getY(), x, y, mapForMove, troop.getType().getSpeed())) {
                troop.setX(x);
                troop.setY(y);
                troop.setMovedThisRound(true);
                initialCell.removeFromTroops(troop);
                destinationCell.addToTroops(troop);
                System.out.println("moved troop: " + troop.getType().getName() +
                        ", hitpoint: " + troop.getHitPoint() +
                        ", government: " + troop.getGovernment().getUser().getNickname());
            }
        }


        return "done";
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

    public static String setState(Matcher matcher, Government government, MapCell cell) {
        matcher.matches();
        String inputCommand = matcher.group("state").replace("\"", "");

        TroopState state = null;
        for(TroopState troopState : TroopState.values())
            if(troopState.getCommand().equals(inputCommand))
                state = troopState;

        if(state == null)
            return "invalid command";

        for(Troop troop : cell.getTroops())
            if(troop.getGovernment().equals(government))
                troop.setState(state);

        return "done";
    }

    public static String attack(Matcher matcher, Government government, MapCell cell) {
        matcher.matches();
        int x = Integer.parseInt(matcher.group("xCoordinate"));
        int y = Integer.parseInt(matcher.group("yCoordinate"));

        if(x < 0 || x > 199)
            return "x coordinate out of bounds";
        if(y < 0 || y > 199)
            return "y coordinate out of bounds";

        MapCell enemyCell = Game.getCurrentGame().getMap().getCellByCoordinate(x, y);
        int[][] mapForMove = createMapForMove();

        if(enemyCell.getRock() != null ||
                enemyCell.getGroundType().equals(GroundType.WATER) ||
                enemyCell.getGroundType().equals(GroundType.ROCK) ||
                (enemyCell.getBuilding() != null && !enemyCell.getBuilding().getType().isPassable()))
            return "destination cell is not clear";

        int range = (int) Math.ceil(Math.sqrt(Math.pow(x - cell.getX(), 2) + Math.pow(y - cell.getY(), 2)));
        int distance = Math.abs(x - cell.getX()) + Math.abs(y - cell.getY());

        int totalUnitAirDamage = 0;
        int totalUnitMeleeDamage = 0;

        Troop troop;
        for (int j = cell.getTroops().size() - 1 ; j >= 0 ; j--) {
            troop = cell.getTroops().get(j);
            if (troop.getGovernment().equals(government) && range <= troop.getFireRange())
                totalUnitAirDamage += troop.getType().getHumanDamage();
            else if(troop.getGovernment().equals(government) && troop.getFireRange() == 0 &&
                    !troop.isMovedThisRound() &&
                    troop.getType().getSpeed() >= distance &&
                    move(cell.getX(), cell.getY(), cell.getX(), cell.getY(), x, y, mapForMove, troop.getType().getSpeed())) {
                totalUnitMeleeDamage += troop.getType().getHumanDamage();
                troop.setX(x);
                troop.setY(y);
                troop.setMovedThisRound(true);
                cell.removeFromTroops(troop);
                enemyCell.addToTroops(troop);
            }
        }


        if(enemyCell.getBuilding() != null && !enemyCell.getBuilding().getGovernment().equals(government))
            enemyCell.getBuilding().setHitPoint(enemyCell.getBuilding().getHitPoint() - (totalUnitAirDamage + totalUnitMeleeDamage));


        for (int j = enemyCell.getTroops().size() - 1 ; j >= 0 ; j--) {
            troop = enemyCell.getTroops().get(j);
            if(!troop.getGovernment().equals(government))
                troop.setHitPoint(troop.getHitPoint() - (totalUnitAirDamage + totalUnitMeleeDamage));
        }


        return "done";
    }

    public static String airAttack(Matcher matcher, Government government, MapCell cell) {
        matcher.matches();
        int x = Integer.parseInt(matcher.group("xCoordinate"));
        int y = Integer.parseInt(matcher.group("yCoordinate"));

        if(x < 0 || x > 199)
            return "x coordinate out of bounds";
        if(y < 0 || y > 199)
            return "y coordinate out of bounds";

        int range = (int) Math.ceil(Math.sqrt(Math.pow(x - cell.getX(), 2) + Math.pow(y - cell.getY(), 2)));

        MapCell enemyCell = Game.getCurrentGame().getMap().getCellByCoordinate(x, y);
        int totalUnitAirDamage = 0;

        Troop troop;
        for (int j = cell.getTroops().size() - 1 ; j >= 0 ; j--) {
            troop = cell.getTroops().get(j);
            if (troop.getGovernment().equals(government) && range <= troop.getFireRange())
                totalUnitAirDamage += troop.getType().getHumanDamage();
        }


        if(enemyCell.getBuilding() != null && !enemyCell.getBuilding().getGovernment().equals(government))
            enemyCell.getBuilding().setHitPoint(enemyCell.getBuilding().getHitPoint() - totalUnitAirDamage);


        for (int j = enemyCell.getTroops().size() - 1 ; j >= 0 ; j--) {
            troop = enemyCell.getTroops().get(j);
            if(!troop.getGovernment().equals(government))
                troop.setHitPoint(troop.getHitPoint() - totalUnitAirDamage);
        }


        return "done";
    }

    public static String disbandUnit(Government government, MapCell cell) {
        Troop troop;
        for (int j = cell.getTroops().size() - 1 ; j >= 0 ; j--) {
            troop = cell.getTroops().get(j);
            if (troop.getGovernment().equals(government)) {
                if(troop.getType().getTroopProducerType().equals(TroopProducerType.BARRACK))
                    for(Building building : government.getBuildings()) {
                        if (building.getType().equals(BuildingType.BARRACK)) {
                            troop.setX(building.getxCoordinate());
                            troop.setY(building.getyCoordinate());
                            cell.removeFromTroops(troop);
                            Game.getCurrentGame().getMap().getCellByCoordinate(building.getxCoordinate(), building.getyCoordinate()).addToTroops(troop);
                        }
                    }
                else if(troop.getType().getTroopProducerType().equals(TroopProducerType.MERCENARY_POST))
                    for(Building building : government.getBuildings()) {
                        if (building.getType().equals(BuildingType.MERCENARY_POST)) {
                            troop.setX(building.getxCoordinate());
                            troop.setY(building.getyCoordinate());
                            cell.removeFromTroops(troop);
                            Game.getCurrentGame().getMap().getCellByCoordinate(building.getxCoordinate(), building.getyCoordinate()).addToTroops(troop);
                        }
                    }
                else if(troop.getType().getTroopProducerType().equals(TroopProducerType.ENGINEER_GUILD))
                    for(Building building : government.getBuildings()) {
                        if (building.getType().equals(BuildingType.ENGINEER_GUILD)) {
                            troop.setX(building.getxCoordinate());
                            troop.setY(building.getyCoordinate());
                            cell.removeFromTroops(troop);
                            Game.getCurrentGame().getMap().getCellByCoordinate(building.getxCoordinate(), building.getyCoordinate()).addToTroops(troop);
                        }
                    }
            }
        }

        return "done";
    }

    public static String digTunnel(Matcher matcher, Government currentGovernment, MapCell cell) {
        matcher.matches();
        int x = Integer.parseInt(matcher.group("xCoordinate"));
        int y = Integer.parseInt(matcher.group("yCoordinate"));

        if(x < 0 || x > 199)
            return "x coordinate out of bounds";
        if(y < 0 || y > 199)
            return "y coordinate out of bounds";

        MapCell enemyCell = Game.getCurrentGame().getMap().getCellByCoordinate(x, y);
        int distance = Math.abs(x - cell.getX()) + Math.abs(y - cell.getY());

        Building building = enemyCell.getBuilding();
        if(!Tunneler.isAllowed(building, currentGovernment))
            return "tile x: " + x + " y: " + y + " is not allowed";

        if(distance > Tunneler.getTunnelRange())
            return "tile x: " + x + " y: " + y + " is out of range";

        boolean isTunnelerAvailable = false;
        Troop troop;
        for(int j = cell.getTroops().size() - 1 ; j >= 0 ; j--) {
            troop = cell.getTroops().get(j);
            if(troop.getType().equals(TroopType.TUNNELER)) {
                isTunnelerAvailable = true;
                troop.setX(x);
                troop.setY(y);
                cell.removeFromTroops(troop);
                enemyCell.addToTroops(troop);
                System.out.println("moved troop: " + troop.getType().getName() +
                        ", hitpoint: " + troop.getHitPoint() +
                        ", government: " + troop.getGovernment().getUser().getNickname());
            }
        }
        if(!isTunnelerAvailable)
            return "no tunneler available";
        else {
            for (int i = building.getxCoordinate(); i < building.getxCoordinate() + building.getSize(); i++)
                for (int j = building.getyCoordinate(); j < building.getyCoordinate() + building.getSize(); j++) {
                    Game.getCurrentGame().getMap().getCellByCoordinate(i, j).setBuilding(null);
                }
            return "done";
        }
    }
}
