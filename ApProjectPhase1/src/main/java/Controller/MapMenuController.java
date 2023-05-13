package Controller;

import Model.*;
import Model.Buildings.Building;
import Model.Buildings.Storage;
import Model.People.Troop;
import View.MapMenu.MapMenuCommands;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class MapMenuController {

    public static String showMapInput(Matcher matcher) {
        matcher.matches();
        String content = matcher.group("content");
        if(!(matcher = MapMenuCommands.getMatcher(content, MapMenuCommands.COORDINATE_X)).find())
            return "invalid x coordinate";
        int x = Integer.parseInt(matcher.group("xCoordinate"));

        if(!(matcher = MapMenuCommands.getMatcher(content, MapMenuCommands.COORDINATE_Y)).find())
            return "invalid y coordinate";
        int y = Integer.parseInt(matcher.group("yCoordinate"));

        return showMap(x, y);
    }

    public static String showMap(int x, int y) {
        if(x < 0 || x > 199)
            return "x coordinate out of bounds";
        if(y < 0 || y > 199)
            return "y coordinate out of bounds";

        Game.getCurrentGame().getMap().setCurrentX(x);
        Game.getCurrentGame().getMap().setCurrentY(y);

        MapCell cell;
        char c;

        System.out.printf("   ");
        for(int j = Math.max(y - 10, 0) ; j <= Math.min(y + 10, 199) ; j++) {
            System.out.printf(" %3d", j);
        }
        System.out.printf("\n");
        for(int i = Math.max(x - 5, 0) ; i <= Math.min(x + 10, 199) ; i++) {
            System.out.printf("%3d", i);
            for(int j = Math.max(y - 10, 0) ; j <= Math.min(y + 10, 199) ; j++) {
                cell = Game.getCurrentGame().getMap().getCellByCoordinate(i, j);
                if(cell.getTroops().size() > 0) c = 's';
                else if(cell.getBuilding() != null) c = 'B';
                else if(cell.getTree() != null) c = 'T';
                else c = '#';
                System.out.printf(cell.getGroundType().getColor().getANSIBackground() +
                        "   " + c + Colors.RESET.getANSIBackground());
            }
            System.out.printf("\n");
        }

        return null;
    }

    public static String moveMap(Matcher matcher) {
        matcher.matches();
        String content = matcher.group("content");
        int[] newCoordinates = {Game.getCurrentGame().getMap().getCurrentX(),
                Game.getCurrentGame().getMap().getCurrentY()};

        newCoordinates = getDelta(newCoordinates, content, MapMenuCommands.UP_DIRECTION, Directions.UP);
        newCoordinates = getDelta(newCoordinates, content, MapMenuCommands.DOWN_DIRECTION, Directions.DOWN);
        newCoordinates = getDelta(newCoordinates, content, MapMenuCommands.LEFT_DIRECTION, Directions.LEFT);
        newCoordinates = getDelta(newCoordinates, content, MapMenuCommands.RIGHT_DIRECTION, Directions.RIGHT);

        return showMap(newCoordinates[0], newCoordinates[1]);
    }

    public static int[] getDelta(int[] coordinates, String content, MapMenuCommands directionField, Directions direction) {
        int n = 1;
        Matcher matcher;
        String field;
        if((matcher = MapMenuCommands.getMatcher(content, directionField)).find()) {
            field = matcher.group("field");
            if((matcher = MapMenuCommands.getMatcher(field, MapMenuCommands.AMOUNT)).find())
                n = Integer.parseInt(matcher.group("amount"));
            coordinates[0] += n * direction.getDeltaX();
            coordinates[1] += n * direction.getDeltaY();
        }
        return coordinates;
    }

    public static String showDetails(Matcher matcher) {
        matcher.matches();
        String content = matcher.group("content");
        if(!(matcher = MapMenuCommands.getMatcher(content, MapMenuCommands.COORDINATE_X)).find())
            return "invalid x coordinate";
        int x = Integer.parseInt(matcher.group("xCoordinate"));

        if(!(matcher = MapMenuCommands.getMatcher(content, MapMenuCommands.COORDINATE_Y)).find())
            return "invalid y coordinate";
        int y = Integer.parseInt(matcher.group("yCoordinate"));

        if(x < 0 || x > 199)
            return "x coordinate out of bounds";
        if(y < 0 || y > 199)
            return "y coordinate out of bounds";

        MapCell cell = Game.getCurrentGame().getMap().getCellByCoordinate(x, y);

        if(!cell.getGroundType().equals(GroundType.WATER))
            System.out.println("ground type: " + cell.getGroundType().getName());
        else
            System.out.println("water zone type: " + cell.getWaterType().getName());

        Building building;
        if((building = cell.getBuilding()) != null)
            System.out.println("\n" + "building: " + building.getName() +
                    ", government: " + building.getGovernment() +
                    ", hitpoint: " + building.getHitPoint());

        if(building instanceof Storage) {
            //TODO
        }

        ArrayList<Troop> troops = cell.getTroops();
        Troop troop;
        if(troops.size() > 0) {
            System.out.println("troops:");
            for(int i = 0 ; i < troops.size() ; i++) {
                troop = troops.get(i);
                System.out.println(i + 1 + ". " + troop.getName() +
                        ", government: " + troop.getGovernment() +
                        ", hitpoint: " + troop.getHitPoint());
            }
        }

        return null;
    }
}
