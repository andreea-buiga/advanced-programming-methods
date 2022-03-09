package Model.ADT;

import java.util.Collection;
import java.util.Map;

public interface IDict<T1,T2> {
    void add(T1 v1, T2 v2) throws Exception;
    void update(T1 v1, T2 v2) throws Exception;
    void remove(T1 v1) throws Exception;
    T2 lookup(T1 id) throws Exception;
    boolean isDefined(T1 id);
    String toString();
    String toFile();
    void setContent(Map<T1, T2> content);
    Map<T1, T2> getContent();
    Collection<T2> values();
    IDict<T1, T2> deepCopy();

    T2 get(T1 key);
}
