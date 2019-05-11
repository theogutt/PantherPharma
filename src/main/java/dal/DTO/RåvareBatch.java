package dal.DTO;

import dal.DTO.MaybeUseless.IRåvareBatch;

public class RåvareBatch implements IRåvareBatch {
    private int id;
    private double mængde;
    private String producent;
    private int indholdsstof;

    public RåvareBatch(int id, int indholdsstof, double mængde, String producent){
        this.id = id;
        this.indholdsstof = indholdsstof;
        this.mængde = mængde;
        this.producent = producent;
    }
    public RåvareBatch(){
    }

    @Override
    public String toString() {
        return "Id: " + id + " Mængde: " + mængde + " Producent: " + producent + " Indholdsstof: " + indholdsstof;
    }

    public int getId() {
        return id;
    }

    public int getIndholdsstof() {
        return indholdsstof;
    }

    public double getMængde() {
        return mængde;
    }

    public String getProducent() {
        return producent;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIndholdsstof(int indholdsstof) {
        this.indholdsstof = indholdsstof;
    }

    public void setMængde(double mængde) {
        this.mængde = mængde;
    }

    public void setProducent(String producent) {
        this.producent = producent;
    }
}
