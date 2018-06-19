package server;

import helper.SavedItems;
import server.service.ClientServerThread;
import server.service.UpdateParkingTest;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Main class of the server
 */
public class ThreadHandlerMain implements SavedItems {// clasa main a serverului

    private ServerSocket ss = null;
    private volatile boolean isRunning = true;

    public static void main(String[] args) throws IOException {
        new ThreadHandlerMain().startServer();
    }

    private void startServer() throws IOException {
        Semaphore sm = new Semaphore(1, true);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Parking Server 2");
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        try {
            ss = new ServerSocket(PORT);// the port number is taken from the
            // SavedIems Interface
            InetAddress ip;
            ip = InetAddress.getLocalHost();
            System.out.println(ip.getHostAddress());

            Thread automation = new Thread(new UpdateParkingTest(sm, emf));
            System.out.println("Accepting connections on port " + PORT);
            executorService.execute(automation);

            while (isRunning) {
                Socket sock = ss.accept();
                executorService.execute(new Thread(new ClientServerThread(sock, sm, emf)));

            }

        } catch (BindException e) {
            System.out.println(PORT + " already in use");
        } finally {
            ss.close();
            emf.close();
            executorService.shutdown();
        }
    }
}