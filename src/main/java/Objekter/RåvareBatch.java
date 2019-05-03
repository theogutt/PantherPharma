package Objekter;

public class RåvareBatch implements IRåvareBatch{
    private int id;
    private int mængde;
    private String producent;
    private Indholdsstof indholdsstof;
    private boolean genbestil = false;

    public RåvareBatch(int id, int mængde, String producent, Indholdsstof indholdsstof){
        this.id = id;
        this.mængde = mængde;
        this.producent = producent;
        this.indholdsstof = indholdsstof;
    }

    @Override
    public String toString() {
        return "Id: " + id + " Mængde: " + mængde + " Producent: " + producent + " Indholdsstof: " + indholdsstof.toString() + " Skalgenbestilles? = " + genbestil;
    }

    public int getId() {
        return id;
    }

    public Indholdsstof getIndholdsstof() {
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

    public void setIndholdsstof(Indholdsstof indholdsstof) {
        this.indholdsstof = indholdsstof;
    }

    public void setMængde(int mængde) {
        this.mængde = mængde;
    }

    public void setProducent(String producent) {
        this.producent = producent;
    }
}
