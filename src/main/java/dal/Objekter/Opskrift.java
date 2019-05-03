package dal.Objekter;

import java.util.List;

public class Opskrift implements Test{
    private int id;
    private List<Indholdsstof> aktiveStoffer;
    private List<Indholdsstof> hjaelpeStoffer;
    private int[] aktivMaengde;
    private int[] hjaelpeMaengde;

    public Opskrift(int id, List<Indholdsstof> aktiveStoffer, List<Indholdsstof> hjaelpeStoffer, int[] aktivMaengde, int[] hjaelpeMaengde){
        this.id = id;
        this.aktiveStoffer = aktiveStoffer;
        this.hjaelpeStoffer = hjaelpeStoffer;
        this.aktivMaengde = aktivMaengde;
        this.hjaelpeMaengde = hjaelpeMaengde;
    }

    @Override
    public String toString() {
        /*
        String aktiv = "";
        for (int i = 0; aktiveStoffer.length > i; i++){
            aktiv = aktiv + " Aktivtstof " + aktiveStoffer[i].toString() + " " + aktivMaengde[i] + "mg";
        }
        for (int i = 0; hjaelpeStoffer.length > i; i++){
            aktiv = aktiv + " Hjælpestof " + hjaelpeStoffer[i].toString() + " " + hjaelpeMaengde[i] + "mg";
        }*/
        return "ID: " + id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAktiveStoffer(List<Indholdsstof> aktiveStoffer) {
        this.aktiveStoffer = aktiveStoffer;
    }

    public void setAktivMaengde(int[] aktivMaengde) {
        this.aktivMaengde = aktivMaengde;
    }

    public void setHjaelpeStoffer(List<Indholdsstof> hjaelpeStoffer) {
        this.hjaelpeStoffer = hjaelpeStoffer;
    }

    public void setHjaelpeMaengde(int[] hjaelpeMaengde) {
        this.hjaelpeMaengde = hjaelpeMaengde;
    }

    public List<Indholdsstof> getAktiveStoffer() {
        return aktiveStoffer;
    }

    public List<Indholdsstof> getHjaelpeStoffer() {
        return hjaelpeStoffer;
    }

    public int getId() {
        return id;
    }

    public int[] getAktivMaengde() {
        return aktivMaengde;
    }

    public int[] getHjaelpeMaengde() {
        return hjaelpeMaengde;
    }
}
