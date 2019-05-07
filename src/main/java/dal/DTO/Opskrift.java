package dal.DTO;

import dal.DTO.MaybeUseless.IOpskrift;

import java.util.ArrayList;

public class Opskrift implements IOpskrift {
    private int id;
    private String navn;
    //
    //
    //
    //Denne SKAL refactors til kun at indeholde id, istedet ofr hele objektet!!!!!!
    private ArrayList<Integer> indholdsStoffer;
    // maengde måles i milligram
    private ArrayList<Double> maengde;
    private ArrayList<Boolean> aktiv;
    // opbevaringstid måles måneder
    private int opbevaringstid;
    private Boolean ibrug;


    public Opskrift(int id, String navn, ArrayList<Integer> indholdsStoffer, ArrayList<Double> maengde, ArrayList<Boolean> aktiv, int opbevaringstid, Boolean ibrug){
        this.id = id;
        this.navn = navn;
        this.indholdsStoffer = indholdsStoffer;
        this.maengde = maengde;
        this.aktiv = aktiv;
        this.opbevaringstid = opbevaringstid;
        this.ibrug = ibrug;
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

    public void setIbrug(Boolean ibrug) {
        this.ibrug = ibrug;
    }

    public Boolean getIbrug() {
        return ibrug;
    }

    public void setIndholdsStoffer(ArrayList<Integer> indholdsStoffer) {
        this.indholdsStoffer = indholdsStoffer;
    }

    public void setMaengde(ArrayList<Double> maengde) {
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

    public ArrayList<Integer> getIndholdsStoffer() {
        return indholdsStoffer;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Double> getMaengde() {
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
