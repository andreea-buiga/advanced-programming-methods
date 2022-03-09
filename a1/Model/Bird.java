package Model;

public class Bird implements IAnimal {
    private int weight;

    public Bird(int _weight) {
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
        return "bird with the weight " + this.weight + " kg.";
    }
}
