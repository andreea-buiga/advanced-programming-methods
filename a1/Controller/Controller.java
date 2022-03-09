package Controller;

import Repository.Repository;
import Model.IAnimal;

public class Controller {
    private Repository repository;

    public Controller(int _size) {
        this.repository = new Repository(_size);
    }

    public Controller(Repository _repository) {
        this.repository = _repository;
    }

    public void addAnimal(IAnimal animal) throws Exception {
        this.repository.addAnimal(animal);
    }

    public void removeAnimal(int index) throws Exception {
        this.repository.removeAnimal(index);
    }

    public IAnimal[] filterAnimals(int weight) {
        return this.repository.filterByWeight(weight);
    }

    public IAnimal[] getAllAnimals() {
        return this.repository.getAllAnimals();
    }

    public int length() {
        return this.repository.length();
    }
}
