package dal.DTO.MaybeUseless;

import dal.DTO.Indholdsstof;

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
