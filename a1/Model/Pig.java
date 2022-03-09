package Model;

public class Pig implements IAnimal {
    private int weight;

    public Pig(int _weight) {
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
        return "pig with the weight " + this.weight + " kg.";
    }
}
