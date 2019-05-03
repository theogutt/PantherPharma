package dal.Objekter.MaybeUseless;

import dal.Objekter.Indholdsstof;

public interface IRåvareBatch {
    String toString();
    int getId();

    Indholdsstof getIndholdsstof();

    int getMængde();

    String getProducent();

    void setId(int id);

    void setGenbestil(boolean genbestil);

    void setIndholdsstof(Indholdsstof indholdsstof);

    void setMængde(int mængde);

    void setProducent(String producent);
}
