package dal.Objekter;

import java.util.ArrayList;
import java.util.List;

public class Opskrift implements Test{
    private int id;
    private String navn;
    private ArrayList<Indholdsstof> indholdsStoffer;
    private ArrayList<Integer> maengde;
    private ArrayList<Boolean> aktiv;


    public Opskrift(int id, String navn, ArrayList<Indholdsstof> indholdsStoffer, ArrayList<Integer> maengde, ArrayList<Boolean> aktiv){
        this.id = id;
        this.navn = navn;
        this.indholdsStoffer = indholdsStoffer;
        this.maengde = maengde;
        this.aktiv = aktiv;
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

    public void setIndholdsStoffer(ArrayList<Indholdsstof> aktiveStoffer) {
        this.indholdsStoffer = indholdsStoffer;
    }

    public void setMaengde(ArrayList<Integer> maengde) {
        this.maengde = maengde;
    }

    public void setNavn(String navn){
        this.navn = navn;
    }

    public void setAktiv(ArrayList<Boolean> aktiv){
        this.aktiv = aktiv;
    }


    public ArrayList<Indholdsstof> getIndholdsStoffer() {
        return indholdsStoffer;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Integer> getMaengde() {
        return maengde;
    }

    public String getNavn(){
        return navn;
    }

    public ArrayList<Boolean> getAktiv(){
        return aktiv;
    }

}
