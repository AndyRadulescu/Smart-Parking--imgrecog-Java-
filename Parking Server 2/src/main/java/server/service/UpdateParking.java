package server.service;

import helper.SavedItems;
import server.OperationDAO;
import server.recognition.RecognitionMain;

import javax.persistence.EntityManagerFactory;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Contains logic that ultimately updates the database.
 */
public class UpdateParking implements Runnable, SavedItems {

    private volatile boolean isRunning = true;
    private Semaphore sm;
    private EntityManagerFactory emf;
    private LocalTime start;

    public UpdateParking(Semaphore sm, EntityManagerFactory emf) {
        this.sm = sm;
        this.emf = emf;
        start = LocalTime.now();
    }

    @Override
    public void run() {
        while (isRunning) {
            System.out.println("running..");
            List<Boolean> parkingAvailabilities = new ArrayList<>();

            try {
                getParkingAvailabilities(parkingAvailabilities);
                OperationDAO dao = new OperationDAO(emf);
                this.sm.acquire();
                updateDatabase(dao, parkingAvailabilities);
                emf.getCache().evictAll();
                this.sm.release();
                LocalTime end = LocalTime.now();
                Duration diff = Duration.between(start, end);
                System.out.println(diff.getSeconds());
            } catch (InterruptedException | ExecutionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * Makes several calls to the api sending the images and receiving back the results.
     * These are taken out of the json response.
     *
     * @param parkingAvailabilities contains the boolean values of the parking spot (true if there is a car, false otherwise)
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private void getParkingAvailabilities(List<Boolean> parkingAvailabilities) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        List<Callable<Boolean>> callables = new ArrayList<>();
        for (int i = 0; i < DBSIZE; i++) {
            callables.add(new RecognitionMain());
        }
        List<Future<Boolean>> futures = executorService.invokeAll(callables);
        for (Future<Boolean> item : futures) {
            parkingAvailabilities.add(item.get());
        }
        System.out.println(parkingAvailabilities);
        executorService.shutdown();
    }

    /**
     * Updates the database with the new values.
     *
     * @param dao                   Reference to facilitate the connection with the db.
     * @param parkingAvailabilities The returned values to be stored in db.
     */
    private void updateDatabase(OperationDAO dao, List<Boolean> parkingAvailabilities) {
        for (int i = 0; i < DBSIZE; i++) {
            dao.updateParkingSlot(i + 1, parkingAvailabilities.get(i) ? 1 : 0,0);
        }
    }
}