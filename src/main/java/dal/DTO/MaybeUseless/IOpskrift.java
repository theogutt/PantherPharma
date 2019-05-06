package dal.DTO.MaybeUseless;

import dal.DTO.Indholdsstof;

public interface IOpskrift {
    String toString();
    void setId(int id);

    void setAktiveStoffer(Indholdsstof[] aktiveStoffer);

    void setAktivMaengde(int[] aktivMaengde);

    void setHjaelpeStoffer(Indholdsstof[] hjaelpeStoffer);

    void setHjaelpeMaengde(int[] hjaelpeMaengde);

    Indholdsstof[] getAktiveStoffer();

    Indholdsstof[] getHjaelpeStoffer();

    int getId();

    int[] getAktivMaengde();

    int[] getHjaelpeMaengde();
}