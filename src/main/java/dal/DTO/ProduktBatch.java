package dal.DTO;

import dal.DTO.MaybeUseless.IProduktBatch;

import java.sql.Date;
import java.util.List;

public class ProduktBatch implements IProduktBatch {
    private int id;
    private String dato;
    private String status;
    private int opskriftID;
    private List<Integer> ravareBatchID;
    private List<Double> ravareMengde;
    private int antal;

    public ProduktBatch(int id, String dato, int opskriftID, List<Integer> ravareBatchID, List<Double> ravareMengde, int antal, String status){
        this.id = id;
        this.dato = dato;
        this.opskriftID = opskriftID;
        this.ravareBatchID = ravareBatchID;
        this.ravareMengde = ravareMengde;
        this.antal = antal;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Id: " + id + " Dato: " + dato + " Opskrift: " + opskriftID;
    }

    public int getId() {
        return id;
    }

    public List<Integer> getRavareBatchIDs() {
        return ravareBatchID;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }

    public void setRavareMengde(List<Double> ravareMengde) {
        this.ravareMengde = ravareMengde;
    }

    public List<Double> getRavareMengde() {
        return ravareMengde;
    }

    public void setRavareBatchIDs(List<Integer> ravareBatchID) {
        this.ravareBatchID = ravareBatchID;
    }

    public int getOpskriftID() {
        return opskriftID;
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

    public void setOpskriftID(int opskriftID) {
        this.opskriftID = opskriftID;
    }

    public int getAntal() {
        return antal;
    }

    public void setAntal(int antal) {
        this.antal = antal;
    }
}

