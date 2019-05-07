package dal.DTO.MaybeUseless;

import dal.DTO.Indholdsstof;

import java.util.ArrayList;

public interface IOpskrift {
    String toString();

    void setId(int id);

    void setIndholdsStoffer(ArrayList<Integer> indholdsStoffer);

    void setMaengde(ArrayList<Double> maengde) ;

    void setNavn(String navn);

    void setAktiv(ArrayList<Boolean> aktiv);

    void setOpbevaringstid(int opbevaringstid);
    ArrayList<Integer> getIndholdsStoffer();

    int getId() ;

    ArrayList<Double> getMaengde() ;
    String getNavn();

    ArrayList<Boolean> getAktiv();
    int getOpbevaringstid();

    void setIbrug(Boolean ibrug);

    Boolean getIbrug();
}
