package Controller;

import Model.Buildings.Building;
import Model.Buildings.TroopProducers;
import Model.Game;
import Model.MapCell;
import Model.People.Troop;
import Model.People.TroopType;
import Model.ResourceType;

import java.util.regex.Matcher;

public class BuildingMenuController {
    public static String repair(Building building) {
        if(!building.getType().isRepairable())
            return "building is not repairable";

        ResourceType resourceCostType = building.getType().getResourceCostType();
        int resourceCost = Math.ceilDiv(building.getHitPoint() * building.getType().getResourceCost(), building.getType().getHitPoint());
        if(resourceCost > building.getGovernment().getAmountByResource(resourceCostType))
            return "you don't have enough resource";

        int newAmount = building.getGovernment().getAmountByResource(resourceCostType) - resourceCost;
        building.getGovernment().changeAmountOfResource(resourceCostType, newAmount);
        building.setHitPoint(building.getType().getHitPoint());

        return "building repaired successfully";
    }

    public static String createunit(Building building, Matcher matcher) {
        if(!(building instanceof TroopProducers))
            return "can't create unit in this building";

        matcher.matches();
        String typeInput = matcher.group("type").replace("\"", "");
        int count = Integer.parseInt(matcher.group("count"));

        TroopType troopType;
        if((troopType = TroopType.getTroopTypeByName(typeInput)) == null)
            return "invalid troop type";

        if(count < 1)
            return "invalid number of troops";

        if(!troopType.getTroopProducerType().equals(((TroopProducers) building).getTroopProducerType()))
            return "can't create this kind of unit in this building";

        if(building.getWorkerNeeded() > 0)
            return "building is not active";

        int cost = count * troopType.getCost();
        if(cost > building.getGovernment().getGold())
            return "you don't have enough gold";

        ResourceType armour;
        ResourceType weapon;
        ResourceType horse;
        if((armour = troopType.getArmour()) != null && building.getGovernment().getAmountByResource(armour) < count)
            return "you don't have enough armour";
        if((weapon = troopType.getWeapon()) != null && building.getGovernment().getAmountByResource(weapon) < count)
            return "you don't have enough weapon";
        if((horse = troopType.getHorse()) != null && building.getGovernment().getAmountByResource(horse) < count)
            return "you don't have enough horse";

        building.getGovernment().setGold(building.getGovernment().getGold() - cost);
        if(armour != null)
            building.getGovernment().changeAmountOfResource(armour, building.getGovernment().getAmountByResource(armour) - count);
        if(weapon != null)
            building.getGovernment().changeAmountOfResource(weapon, building.getGovernment().getAmountByResource(weapon) - count);
        if(horse != null)
            building.getGovernment().changeAmountOfResource(horse, building.getGovernment().getAmountByResource(horse) - count);

        MapCell cell = Game.getCurrentGame().getMap().getCellByCoordinate(building.getxCoordinate(), building.getyCoordinate());
        for(int i = 0 ; i < count ; i++)
            cell.addToTroops(new Troop(building.getGovernment(), troopType, building.getxCoordinate(), building.getyCoordinate()));

        return "dropped unit successfully";
    }
}
