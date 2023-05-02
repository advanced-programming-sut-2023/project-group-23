package Model;

public enum GovernmentColor {
    BLACK ("black"),
    WHITE ("white"),
    RED ("red"),
    BLUE ("blue"),
    GREEN ("green"),
    YELLOW ("yellow"),
    CYAN ("cyan"),
    PURPLE ("purple");

    private String name;
    GovernmentColor(String name) {
        this.name = name;
    }

    public String getName(GovernmentColor governmentColor) {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
