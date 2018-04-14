package server;

import java.util.Random;
import java.util.concurrent.Semaphore;

import javax.persistence.EntityManagerFactory;

public class UpdateParking implements Runnable, SavedItems {

	private volatile boolean isRunning = true;
	private Semaphore sm;
	private EntityManagerFactory emf;
	private int sleepTime = 3;

	public UpdateParking(Semaphore sm, EntityManagerFactory emf) {
		this.sm = sm;
		this.emf = emf;
	}

	@Override
	public void run() {

		while (isRunning) {
			System.out.println("running..");
			Random rnd = new Random();
			int id1, id2, id3;
			id1 = rnd.nextInt(5) + 1;
			id2 = rnd.nextInt(5) + 1;
			id3 = rnd.nextInt(5) + 1;

			try {
				OperationDAO dao = new OperationDAO(emf);
				Thread.sleep(sleepTime * 1000);
				this.sm.acquire();
				dao.updateParkingSlot(id1, rnd.nextInt(2));
				dao.updateParkingSlot(id2, rnd.nextInt(2));
				dao.updateParkingSlot(id3, rnd.nextInt(2));
				emf.getCache().evictAll();
				this.sm.release();

				System.out.println(
						"Slept for " + sleepTime + "seconds, changed the ids : " + id1 + " , " + id2 + " , " + id3);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
