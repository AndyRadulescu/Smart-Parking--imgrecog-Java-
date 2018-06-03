package server.service;

import android.content.Context;
import android.util.Log;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import helper.SavedItems;
import model.DBService;
import model.DbHelper;
import model.ParkingDTO;
import server.ClientThread;
import server.Message;

public class GetDataFromBackendThread implements Runnable, SavedItems {
    private static boolean isRunning;
    public volatile List<ParkingDTO> parkingPlaces;
    private DbHelper mDbHelper;

    public GetDataFromBackendThread(Context context) {
        mDbHelper = new DbHelper(context);
        parkingPlaces = DBService.getInfoFromDb(mDbHelper);
        isRunning = true;
    }

    public static void setRunning(boolean running) {
        isRunning = running;
    }

    @Override
    public void run() {
        while (isRunning) {
            ExecutorService ex = Executors.newSingleThreadExecutor();

            Message ms = new Message(TAKEALL, null);
            ClientThread client = new ClientThread(ms);
            Future<Message> future = ex.submit(client);
            try {
                Log.i(debug, "Starting...");
                Message response;
                Log.i(debug, "waiting for the future object");
                response = future.get(timeout, TimeUnit.SECONDS);
                parkingPlaces = (List<ParkingDTO>) response.getData();
                DBService.updateDatabase(mDbHelper, parkingPlaces);
                Log.i(debug, parkingPlaces.toString());

            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                Log.d(debug, "Error", e);
                e.printStackTrace();
                future.cancel(true); //this method will stop the running underlying task
            } finally {
                ex.shutdown();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if (!isRunning) {
            Log.d(debug, "The thread is not running anymore");
        }
    }
}