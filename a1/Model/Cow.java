package Model;

public class Cow implements IAnimal{
    private int weight;

    public Cow(int _weight) {
        this.weight = _weight;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public boolean checkWeight(int _weight) {
        return weight > _weight;
    }

    @Override
    public String toString() {
        return "cow with the weight " + this.weight + " kg.";
    }
}
