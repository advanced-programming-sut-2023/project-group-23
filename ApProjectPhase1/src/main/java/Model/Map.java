package Model;

public class Map {
    private MapCell[][] map;
    private int width;
    private int height;
    private int currentX;
    private int currentY;

    public Map() {
        this.width = 200;
        this.height = 200;
        this.map = new MapCell[height][width];

        for(int i = 0 ; i < height ; i++)
            for(int j = 0 ; j < width; j++)
                map[i][j] = new MapCell(GroundType.EARTH, i, j);
    }

    public void buildMap() {

    }

    public MapCell getCellByCoordinate(int x, int y) {
        return map[x][y];
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
