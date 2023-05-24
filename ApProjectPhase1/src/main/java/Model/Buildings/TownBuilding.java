package Model.Buildings;

import Model.Game;
import Model.Government;
import Model.MapCell;
import Model.People.Troop;
import Model.People.TroopType;
import Model.ResourceType;

public class TownBuilding extends Building{
    private TownBuildingType townBuildingType;

    public TownBuilding(BuildingType type, TownBuildingType townBuildingType, Government government, int x, int y) {
        super(type, government, x, y);
        this.townBuildingType = townBuildingType;
        government.setMaxPopulation(government.getMaxPopulation() + townBuildingType.getMaxPopulationIncrease());
        if(townBuildingType.isReligious()) government.setReligiousRate(government.getReligiousRate() + 2);
        if(townBuildingType.equals(TownBuildingType.SHOP)) government.setCanShop(true);
        if(townBuildingType.equals(TownBuildingType.CATHEDRAL)) {
            MapCell cell = Game.getCurrentGame().getMap().getCellByCoordinate(x, y);
            cell.addToTroops(new Troop(government, TroopType.BISHOP, x, y));
        }
    }

    public TownBuildingType getTownBuildingType() {
        return townBuildingType;
    }

    public static void runInns(Government government) {
        int popularity = 0;
        for(Building building : government.getBuildings()) {
            if(building instanceof TownBuilding &&
                    ((TownBuilding) building).getTownBuildingType().equals(TownBuildingType.INN)) {
                if(building.getWorkerNeeded() == 0 &&
                    government.getAmountByResource(ResourceType.ALE) >= 5) {
                    government.changeAmountOfResource(ResourceType.ALE, government.getAmountByResource(ResourceType.ALE) - 5);
                    popularity += 2;
                }
            }
        }
        government.setInnPopularity(popularity);
    }
}
