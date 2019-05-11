package dal.DTO.MaybeUseless;

import java.sql.Date;
import java.util.List;

public interface IProduktBatch {
    @Override
    String toString();

    int getId() ;

    List<Integer> getRavareBatchIDs();

    void setRavareMengde(List<Double> ravareMengde);

    List<Double> getRavareMengde() ;

    void setRavareBatchIDs(List<Integer> ravareBatchID);

    void setStatus(String status);

    String getStatus() ;

    int getOpskriftID() ;

    String getDato() ;

    void setId(int id);

    void setDato(String dato);

    void setOpskriftID(int opskriftID);

    int getAntal();

    void setAntal(int antal);
}
