package Objekter;

public class Indholdsstof {
    private int id;
    private String name;

    public Indholdsstof(int id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "ID: " + id + " Name: " + name;
    }

    //setters og getters
    public int getId() {
        return id;
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
