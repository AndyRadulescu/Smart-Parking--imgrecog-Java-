package model;

import java.io.Serializable;
import javax.persistence.*;
import static javax.persistence.AccessType.FIELD;
import static javax.persistence.AccessType.PROPERTY;

/**
 * The persistent class for the parking database table.
 * 
 */
@Entity
@NamedQuery(name = "Parking.findAll", query = "SELECT p FROM Parking p")
public class Parking implements Serializable {
	private static final long serialVersionUID = 1L;

	public Parking(int id, int availability) {
		this.id = id;
		this.availability = availability;
	}

	@Id
	private int id;

	private int availability;

	private String name;

	public Parking() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAvailability() {
		return this.availability;
	}

	public void setAvailability(int availability) {
		this.availability = availability;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}