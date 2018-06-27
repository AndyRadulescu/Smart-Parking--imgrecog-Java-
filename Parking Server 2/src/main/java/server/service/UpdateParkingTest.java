package server.service;

import helper.SavedItems;
import server.OperationDAO;

import javax.persistence.EntityManagerFactory;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class UpdateParkingTest implements Runnable, SavedItems {

    private volatile boolean isRunning = true;
    private Semaphore sm;
    private EntityManagerFactory emf;
    private int sleepTime = 3;

    public UpdateParkingTest(Semaphore sm, EntityManagerFactory emf) {
        this.sm = sm;
        this.emf = emf;
    }

    @Override
    public void run() {

        while (isRunning) {
            System.out.println("running..");
            Random rnd = new Random();
            int[] ids = new int[DBSIZE];

            try {
                OperationDAO dao = new OperationDAO(emf);
                Thread.sleep(sleepTime * 1000);
                this.sm.acquire();
                for (int id = 1; id <= DBSIZE; id++) {
                    dao.updateParkingSlot(id, rnd.nextInt(2), rnd.nextInt(2));
                }
                emf.getCache().evictAll();
                this.sm.release();

                StringBuilder logString = new StringBuilder("Slept for " + sleepTime + "seconds, changed the ids : ");
                for (int id = 1; id <= DBSIZE; id++) {
                    logString.append(id).append(" , ");
                }
                System.out.println(logString);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}