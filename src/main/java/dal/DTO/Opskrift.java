package dal.DTO;

import dal.DTO.MaybeUseless.IOpskrift;

import java.util.ArrayList;

public class Opskrift implements IOpskrift {
    private int id;
    private String navn;
    private ArrayList<Indholdsstof> indholdsStoffer;
    // maengde måles i milligram
    private ArrayList<Integer> maengde;
    private ArrayList<Boolean> aktiv;
    // opbevaringstid måles måneder
    private int opbevaringstid;


    public Opskrift(int id, String navn, ArrayList<Indholdsstof> indholdsStoffer, ArrayList<Integer> maengde, ArrayList<Boolean> aktiv, int opbevaringstid){
        this.id = id;
        this.navn = navn;
        this.indholdsStoffer = indholdsStoffer;
        this.maengde = maengde;
        this.aktiv = aktiv;
        this.opbevaringstid = opbevaringstid;
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

    public void setIndholdsStoffer(ArrayList<Indholdsstof> indholdsStoffer) {
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

    public void setOpbevaringstid(int opbevaringstid){
        this.opbevaringstid = opbevaringstid;
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

    public int getOpbevaringstid(){
        return opbevaringstid;
    }

}
