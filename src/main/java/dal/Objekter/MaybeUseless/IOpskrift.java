package dal.Objekter.MaybeUseless;

import dal.Objekter.Indholdsstof;

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