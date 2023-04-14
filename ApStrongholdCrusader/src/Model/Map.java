package Model;

public class Map {
    private MapCell[][] map;
    private int width;
    private int height;
    private int currentX;
    private int currentY;

    public Map(int width, int height,MapCell[][] map) {
        this.width = width;
        this.height = height;
        this.map = new MapCell[height][width];
    }

    public void buildMap() {

    }

    public MapCell getCellByCoordinate(int x, int y) {
        return null;
    }

    public MapCell[][] getMap() {
        return map;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getCurrentX() {
        return currentX;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }
}
