package dal.DTO;

import dal.DTO.MaybeUseless.IRåvareBatch;

public class RåvareBatch implements IRåvareBatch {
    private int id;
    private int mængde;
    private String producent;
    private int indholdsstof;
    private boolean genbestil;

    public RåvareBatch(int id, int indholdsstof, int mengde, String producent, boolean genbestil){
        this.id = id;
        this.indholdsstof = indholdsstof;
        this.mængde = mengde;
        this.producent = producent;
        this.genbestil = genbestil;
    }

    @Override
    public String toString() {
        return "Id: " + id + " Mængde: " + mængde + " Producent: " + producent + " Indholdsstof: " + indholdsstof + " Skalgenbestilles? = " + genbestil;
    }

    public int getId() {
        return id;
    }

    public boolean isGenbestil() {
        return genbestil;
    }

    public int getIndholdsstof() {
        return indholdsstof;
    }

    public int getMængde() {
        return mængde;
    }

    public String getProducent() {
        return producent;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGenbestil(boolean genbestil) {
        this.genbestil = genbestil;
    }

    public void setIndholdsstof(int indholdsstof) {
        this.indholdsstof = indholdsstof;
    }

    public void setMængde(int mængde) {
        this.mængde = mængde;
    }

    public void setProducent(String producent) {
        this.producent = producent;
    }
}
