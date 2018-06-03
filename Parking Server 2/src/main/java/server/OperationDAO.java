package server;

import model.Parking;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

/**
 * Contains all the operations that are executed on the database.
 */
public class OperationDAO {// toate operatile pe baza de date
    private EntityManagerFactory emf;

    public OperationDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    /**
     * Retrives from the database.
     *
     * @return
     */
    public synchronized List<Parking> getAllStatus() {
        EntityManager em = this.getEntityManagerFactory().createEntityManager();
        Query qrStat = em.createQuery("SELECT p FROM Parking p");
        List<Parking> list = qrStat.getResultList();
        em.close();
        return list;
    }

    /**
     * Updates the database.
     *
     * @param id
     * @param availability
     */
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