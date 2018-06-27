package model;

import java.io.Serializable;

/**
 * The class that generates objects to pe sent throw sockets.
 */
public class ParkingDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -86055680657258429L;
    private int id;
    private String name;
    private int availability;
    private int selected;

    public ParkingDTO(int id, String name, int availability, int selected) {
        this.id = id;
        this.name = name;
        this.availability = availability;
        this.selected = selected;
    }

    public ParkingDTO() {

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

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "ParkingDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", availability=" + availability +
                ", selected=" + selected +
                '}';
    }
}
