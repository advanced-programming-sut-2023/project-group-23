package Model.People;


import Model.Government;

public class Person {
    private static enum workStateTypes {
        PEASANT,
        WORKER,
        TROOPER
    }

    private Government government;
    private Enum workState;

    public Person(Government government) {
        this.government = government;
        this.workState = workStateTypes.PEASANT;
    }

    public Government getGovernment() {
        return government;
    }

    public Enum getWorkState() {
        return workState;
    }

    public void setWorkState(Enum workState) {
        this.workState = workState;
    }
}
