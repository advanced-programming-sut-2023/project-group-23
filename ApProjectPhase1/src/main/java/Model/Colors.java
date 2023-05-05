package Model;

public enum Colors {
    RESET ("reset", "\u001B[0m", "\u001B[0m"),
    BLACK ("black", "\u001B[30m", "\u001B[40m"),
    WHITE ("white", "\u001B[37m", "\u001B[47m"),
    RED ("red", "\u001B[31m", "\u001B[41m"),
    BLUE ("blue", "\u001B[34m", "\u001B[44m"),
    GREEN ("green", "\u001B[32m", "\u001B[42m"),
    YELLOW ("yellow", "\u001B[33m", "\u001B[43m"),
    CYAN ("cyan", "\u001B[36m", "\u001B[46m"),
    PURPLE ("purple", "\u001B[35m", "\u001B[45m");

    private String name;
    private String ANSI;
    private String ANSIBackground;

    Colors(String name, String ANSI, String ANSIBackground) {
        this.name = name;
        this.ANSI = ANSI;
        this.ANSIBackground = ANSIBackground;
    }

    public String getName() {
        return name;
    }

    public String getANSI() {
        return ANSI;
    }

    public String getANSIBackground() {
        return ANSIBackground;
    }
}
