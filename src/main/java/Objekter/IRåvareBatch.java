package Objekter;

public interface IRåvareBatch {
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
