package Model;

public class Troop extends Person{
    private String name;
    private int hitPoint;
    private int damage;
    private int speed;
    private int fireRange;

    public Troop(Government government) {
        super(government);
    }
}
