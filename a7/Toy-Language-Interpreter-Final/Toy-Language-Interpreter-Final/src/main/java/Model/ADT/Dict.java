package Model.ADT;
import Exceptions.UndefinedException;
import Exceptions.VariableDefinedException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Dict<T1,T2> implements IDict<T1,T2> {
    Map<T1, T2> dictionary;

    public Dict() {
        dictionary = new HashMap<T1,T2>();
    }

    @Override
    public void add(T1 v1, T2 v2) throws Exception{
        if (!isDefined(v1)) {
            dictionary.put(v1, v2);
        } else throw new VariableDefinedException("Variable already defined");
    }

    @Override
    public void update(T1 v1, T2 v2) throws Exception{
        if (isDefined(v1)) {
            dictionary.replace(v1, v2);
        } else throw new UndefinedException("Variable not defined");
    }

    @Override
    public void remove(T1 v1) throws Exception{
        if (isDefined(v1)) {
            dictionary.remove(v1);
        } else throw new UndefinedException("Variable not defined");
    }

    @Override
    public T2 lookup(T1 id) throws Exception {
        if (isDefined(id)) {
            return dictionary.get(id);
        } else throw new UndefinedException("Variable not defined.");
    }

    @Override
    public boolean isDefined(T1 id) {
        return dictionary.containsKey(id);
    }

    @Override
    public String toString() {
        return dictionary.toString();
    }

    @Override
    public String toFile() {
        String result = "";
        Set<T1> keys = dictionary.keySet();

        for(T1 key : keys) {
            result = result + key + " -> " + dictionary.get(key) + "\n";
        }

        return result;
    }

    @Override
    public void setContent(Map<T1, T2> content) {
        this.dictionary.clear();

        this.dictionary.putAll(content);
    }

    @Override
    public Map<T1, T2> getContent() {
        return this.dictionary;
    }

    @Override
    public Collection<T2> values()
    {
        return dictionary.values();
    }

    @Override
    public IDict<T1, T2> deepCopy() {

        Dict<T1, T2> dictCopy = new Dict<T1, T2>();
        dictCopy.setContent(this.getContent());

        return dictCopy;
    }

    @Override
    public T2 get(T1 key) {
        return dictionary.get(key);
    }

}
