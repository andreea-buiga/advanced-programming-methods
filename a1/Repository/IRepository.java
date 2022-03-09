package Repository;

import Model.IAnimal;

public interface IRepository {
    // add an animal
    void addAnimal(IAnimal animal) throws Exception;

    // remove an animal
    void removeAnimal(int index) throws Exception;

    //filter animals having weight over the one given
    public IAnimal[] filterByWeight(int weight);

    // get all the animals
    public IAnimal[] getAllAnimals();

    public int length();
}
