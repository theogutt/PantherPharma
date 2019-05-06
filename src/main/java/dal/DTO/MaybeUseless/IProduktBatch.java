package dal.DTO.MaybeUseless;

import dal.DTO.Opskrift;

import java.util.List;

public interface IProduktBatch {
    @Override
    String toString();

    int getId() ;

    List<Integer> getRavareBatchIDs();

    void setRavareMengde(List<Integer> ravareMengde);

    List<Integer> getRavareMengde() ;

    void setRavareBatchIDs(List<Integer> ravareBatchID);

    int getOpskriftID() ;

    String getDato() ;

    void setId(int id);

    void setDato(String dato);

    void setOpskriftID(int opskriftID);
}
