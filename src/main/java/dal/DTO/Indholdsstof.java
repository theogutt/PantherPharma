package dal.DTO;

import dal.DTO.MaybeUseless.IIndholdsstof;

public class Indholdsstof implements IIndholdsstof {
    private int id;
    private String name;
    private Boolean genbestil;

    public Indholdsstof(int id, String name, Boolean genbestil){
        this.id = id;
        this.name = name;
        this.genbestil = genbestil;
    }
    public Indholdsstof(){}

    @Override
    public String toString() {
        return "ID: " + id + " Name: " + name;
    }

    //setters og getters
    public int getId() {
        return id;
    }

    public void setGenbestil(Boolean genbestil) {
        this.genbestil = genbestil;
    }

    public Boolean getGenbestil() {
        return genbestil;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
