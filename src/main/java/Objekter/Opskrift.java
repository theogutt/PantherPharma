package Objekter;

public class Opskrift {
    private int id;
    private Indholdsstof[] aktiveStoffer;
    private Indholdsstof[] hjaelpeStoffer;
    private int[] aktivMaengde;
    private int[] hjaelpeMaengde;

    public Opskrift(int id, Indholdsstof[] aktiveStoffer, Indholdsstof[] hjaelpeStoffer, int[] aktivMaengde, int[] hjaelpeMaengde){
        this.id = id;
        this.aktiveStoffer = aktiveStoffer;
        this.hjaelpeStoffer = hjaelpeStoffer;
        this.aktivMaengde = aktivMaengde;
        this.hjaelpeMaengde = hjaelpeMaengde;
    }

    @Override
    public String toString() {
        String aktiv = "";
        for (int i = 0; aktiveStoffer.length > i; i++){
            aktiv = aktiv + " Aktivtstof " + aktiveStoffer[i].toString() + " " + aktivMaengde[i] + "mg";
        }
        for (int i = 0; hjaelpeStoffer.length > i; i++){
            aktiv = aktiv + " Hjælpestof " + hjaelpeStoffer[i].toString() + " " + hjaelpeMaengde[i] + "mg";
        }
        return "ID: " + id + aktiv;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAktiveStoffer(Indholdsstof[] aktiveStoffer) {
        this.aktiveStoffer = aktiveStoffer;
    }

    public void setAktivMaengde(int[] aktivMaengde) {
        this.aktivMaengde = aktivMaengde;
    }

    public void setHjaelpeStoffer(Indholdsstof[] hjaelpeStoffer) {
        this.hjaelpeStoffer = hjaelpeStoffer;
    }

    public void setHjaelpeMaengde(int[] hjaelpeMaengde) {
        this.hjaelpeMaengde = hjaelpeMaengde;
    }

    public Indholdsstof[] getAktiveStoffer() {
        return aktiveStoffer;
    }

    public Indholdsstof[] getHjaelpeStoffer() {
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
