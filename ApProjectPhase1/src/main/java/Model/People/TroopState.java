package Model.People;

public enum TroopState {
    STAY ("stay"),
    DEFENSIVE ("defensive"),
    AGGRESSIVE ("aggressive");

    private String command;

    TroopState(String command) {
        this.command = command;
    }

    public TroopState getStateByCommand(String command) {
        return null;
    }
}
