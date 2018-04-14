
package server;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import model.Parking;

/**
 * 
 * Contains all the operations that are executed on the database.
 *
 */
public class OperationDAO {// toate operatile pe baza de date
	private EntityManagerFactory emf;

	private EntityManagerFactory getEntityManagerFactory() {
		// EntityManagerFactory emf
		// =Persistence.createEntityManagerFactory("Sist_bancar");
		return emf;
	}

	public OperationDAO(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public synchronized List<Parking> getAllStatus() {
		EntityManager em = this.getEntityManagerFactory().createEntityManager();
		Query qrStat = em.createQuery("SELECT p FROM Parking p");
		List<Parking> list = qrStat.getResultList();
		em.close();
		return list;
	}

	public synchronized void updateParkingSlot(int id, int availability) {
		EntityManager em = this.getEntityManagerFactory().createEntityManager();
		em.getTransaction().begin();
		Parking parking = em.find(Parking.class, id);
		parking.setAvailability(availability);
		em.persist(parking);
		em.getTransaction().commit();
		em.close();
	}

}