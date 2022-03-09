package Repository;

import Model.IAnimal;

public class Repository implements IRepository{
    private final IAnimal[] animals;
    int currentIndex;
    int size;

    public Repository(int _size) {
        super();
        this.size = _size;
        this.currentIndex = 0;
        this.animals = new IAnimal[_size];
    }

    @Override
    public void addAnimal(IAnimal animal) throws Exception {
        if(this.currentIndex >= this.size) {
            throw new Exception("repository is full! the maximum length is: " + this.size);
        }

        if(animal.getWeight() < 0) {
            throw new Exception("the weight can't be negative.");
        }

        int index;
        for(index = 0; index < this.size; index++) {
            if(this.animals[index] == null) {
                this.animals[index] = animal;
                break;
            }
        }
        this.currentIndex++;
    }

    @Override
    public void removeAnimal(int index) throws Exception {
        if(this.currentIndex == 0) {
            throw new Exception("nothing to remove.");
        }

        if(this.currentIndex < index || index < 0) {
            throw new Exception("wrong index. index must be between 0 and the size " + this.size + ".");
        }

        this.animals[index] = null;
        this.currentIndex--;
    }

    @Override
    public IAnimal[] filterByWeight(int weight) {
        IAnimal[] filteredAnimals = new IAnimal[size];
        int index = 0;
        for (IAnimal _animal : animals) {
            if(_animal != null && _animal.checkWeight(weight)){
                filteredAnimals[index++] = _animal;
            }
        }
        return filteredAnimals;
    }

    @Override
    public IAnimal[] getAllAnimals() {
        return this.animals;
    }

    @Override
    public int length() {
        return this.currentIndex;
    }
}
