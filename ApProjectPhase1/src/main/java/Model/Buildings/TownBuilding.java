package Model.Buildings;

import Model.Government;
import Model.ResourceType;

public class TownBuilding extends Building{
    private TownBuildingType townBuildingType;

    public TownBuilding(BuildingType type, TownBuildingType townBuildingType, Government government, int x, int y) {
        super(type, government, x, y);
        this.townBuildingType = townBuildingType;
        government.setMaxPopulation(government.getMaxPopulation() + townBuildingType.getMaxPopulationIncrease());
        if(townBuildingType.isReligious()) government.setReligiousRate(government.getReligiousRate() + 2);
        if(townBuildingType.equals(TownBuildingType.CATHEDRAL)) {
            // todo train bishop
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
