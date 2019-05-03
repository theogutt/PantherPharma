package Objekter;

public interface IProduktBatch {
    int getId();

    Opskrift getOpskrift();

    String getDato();

    void setId(int id);

    void setDato(String dato);

    void setOpskrift(Opskrift opskrift);
}
