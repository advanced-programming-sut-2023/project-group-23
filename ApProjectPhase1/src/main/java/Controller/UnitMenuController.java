package Controller;

import Model.Game;
import Model.Government;
import Model.GroundType;
import Model.MapCell;
import Model.People.Troop;
import Model.People.TroopState;

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

        for(Troop troop : initialCell.getTroops())
            if(troop.getGovernment().equals(government) &&
                troop.getType().getSpeed() >= Math.abs(x - initialCell.getX()) + Math.abs(y - initialCell.getY()) &&
                move(initialCell.getX(), initialCell.getY(), initialCell.getX(), initialCell.getY(), x, y, mapForMove, troop.getType().getSpeed())) {
                troop.setX(x);
                troop.setY(y);
                System.out.println("moved troop: " + troop.getType().getName() +
                        ", hitpoint: " + troop.getHitPoint() +
                        ", government: " + troop.getGovernment().getUser().getNickname());
            }


        return "done";
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
        if(cell.getTroops().size() > 0) {
            for (Troop troop : cell.getTroops()) {
                if (troop.getGovernment().equals(government) && range <= troop.getFireRange())
                    totalUnitAirDamage += troop.getHumanDamage();
                else if(troop.getGovernment().equals(government) && troop.getFireRange() == 0 &&
                        troop.getType().getSpeed() >= distance &&
                        move(cell.getX(), cell.getY(), cell.getX(), cell.getY(), x, y, mapForMove, troop.getType().getSpeed())) {
                    totalUnitMeleeDamage += troop.getHumanDamage();
                    troop.setX(x);
                    troop.setY(y);
                }
            }
        }

        if(enemyCell.getBuilding() != null && !enemyCell.getBuilding().getGovernment().equals(government))
            enemyCell.getBuilding().setHitPoint(enemyCell.getBuilding().getHitPoint() - totalUnitAirDamage);

        if(enemyCell.getTroops().size() > 0) {
            for (Troop troop : enemyCell.getTroops()) {
                if(!troop.getGovernment().equals(government))
                    troop.setHitPoint(troop.getHitPoint() - (totalUnitAirDamage + totalUnitMeleeDamage));
            }
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
        if(cell.getTroops().size() > 0) {
            for (Troop troop : cell.getTroops()) {
                if (troop.getGovernment().equals(government) && range <= troop.getFireRange())
                    totalUnitAirDamage += troop.getHumanDamage();
            }
        }

        if(enemyCell.getBuilding() != null && !enemyCell.getBuilding().getGovernment().equals(government))
            enemyCell.getBuilding().setHitPoint(enemyCell.getBuilding().getHitPoint() - totalUnitAirDamage);

        if(enemyCell.getTroops().size() > 0) {
            for (Troop troop : enemyCell.getTroops()) {
                if(!troop.getGovernment().equals(government))
                    troop.setHitPoint(troop.getHitPoint() - totalUnitAirDamage);
            }
        }

        return "done";
    }

    public static String disbandUnit(Government government, MapCell cell) {
        for(Troop troop : cell.getTroops()) {
            if(troop.getGovernment().equals(government)) {

            }
        }
        return null;
    }
}
