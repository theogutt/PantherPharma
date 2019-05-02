package Objekter;

public class ProduktBatch {
    private int id;
    private String dato;
    private Opskrift opskrift;

    public ProduktBatch(int id, String dato, Opskrift opskrift){
        this.id = id;
        this.dato = dato;
        this.opskrift = opskrift;
    }

    @Override
    public String toString() {
        return "Id: " + id + " Dato: " + dato + " Opskrift: " + opskrift.toString();
    }

    public int getId() {
        return id;
    }

    public Opskrift getOpskrift() {
        return opskrift;
    }

    public String getDato() {
        return dato;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public void setOpskrift(Opskrift opskrift) {
        this.opskrift = opskrift;
    }
}

