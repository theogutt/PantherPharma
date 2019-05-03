package dal.Objekter;

import java.util.List;

public class ProduktBatch implements Test{
    private int id;
    private String dato;
    private Opskrift opskrift;
    private List<Integer> ravareBatches;
    private List<Integer> ravareMengde;

    public ProduktBatch(int id, String dato, Opskrift opskrift, List<Integer> ravareBatches, List<Integer> ravareMengde){
        this.id = id;
        this.dato = dato;
        this.opskrift = opskrift;
        this.ravareBatches = ravareBatches;
        this.ravareMengde = ravareMengde;
    }

    @Override
    public String toString() {
        return "Id: " + id + " Dato: " + dato + " Opskrift: " + opskrift.toString();
    }

    public int getId() {
        return id;
    }

    public List<Integer> getRavareBatches() {
        return ravareBatches;
    }

    public void setRavareMengde(List<Integer> ravareMengde) {
        this.ravareMengde = ravareMengde;
    }

    public List<Integer> getRavareMengde() {
        return ravareMengde;
    }

    public void setRavareBatches(List<Integer> ravareBatches) {
        this.ravareBatches = ravareBatches;
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

