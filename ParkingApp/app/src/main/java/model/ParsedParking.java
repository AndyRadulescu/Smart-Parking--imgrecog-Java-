package model;

import java.io.Serializable;

public class ParsedParking implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -86055680657258429L;
    private int id;
    private String name;
    private int availability;

    public ParsedParking(int id, String name, int availability) {
        this.id = id;
        this.name = name;
        this.availability = availability;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "ParseParking [id=" + id + ", name=" + name + ", availability=" + availability + "]";
    }

}
