package dal.DTO.MaybeUseless;

import dal.DTO.Opskrift;

public interface IProduktBatch {
    String toString();
    int getId();

    Opskrift getOpskrift();

    String getDato();

    void setId(int id);

    void setDato(String dato);

    void setOpskrift(Opskrift opskrift);
}
