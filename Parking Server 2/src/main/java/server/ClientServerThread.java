package server;

import model.Parking;
import model.ParsedParking;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

/**
 * This class executes all commands received from the client.
 */
public class ClientServerThread implements Runnable, SavedItems {

    private static int instances = 0;
    private Socket socket = null;
    private Semaphore sm;
    private EntityManagerFactory emf;

    public ClientServerThread(Socket sock, Semaphore sm, EntityManagerFactory emf) throws IOException {
        this.socket = sock;
        this.sm = sm;
        this.emf = emf;
        instances++;
    }

    @Override
    public void run() {
        try {
            getFromClient();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Gets the message from the client.
     *
     * @throws IOException
     */
    public synchronized void getFromClient() throws IOException {
        try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {
            OperationDAO dao = new OperationDAO(emf);
            Message result = chooseOper(in, dao);
            out.writeObject(result);

            emf.getCache().evictAll();
        } catch (SocketTimeoutException ste) {
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }

    public Socket getSocket() {
        return this.socket;
    }

    /**
     * returns all StatusCleint
     *
     * @param dao does the operation on the database
     * @return Message
     */
    public Message getAllStatus(OperationDAO dao) {
        Message ms = new Message();
        List<Parking> array = new ArrayList<>();
        array.addAll(dao.getAllStatus());
        List<ParsedParking> parsedArray = parse(array);
        ms.setData(parsedArray);
        return ms;
    }

    /**
     * Creates a pojo array to be sent.
     *
     * @param array
     * @return
     */
    public List<ParsedParking> parse(List<Parking> array) {
        List<ParsedParking> parsedArray = new ArrayList<>();
        for (int i = 0; i < array.size(); i++)
            parsedArray.add(
                    new ParsedParking(array.get(i).getId(), array.get(i).getName(), array.get(i).getAvailability()));
        return parsedArray;
    }

    /**
     * More efficient alternative to switch; chooses the operation that must be
     * executed, dependent on message.getAction()
     *
     * @param in  ObjectInputStream from socket.accept()
     * @param dao does the operation on the database
     * @return Message
     */

    // better than switch
    public Message chooseOper(ObjectInputStream in, OperationDAO dao) {

        Message ms = new Message();
        Message result = new Message();
        Map<Integer, Operation> map = new HashMap<>();
        try {
            ms = (Message) in.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        map.put(SavedItems.TAKEALL, new Operation() {

            @Override // get all status (write)
            public Message doOperation(Message message) {
                try {
                    sm.acquire();
                } catch (InterruptedException e) {
                    e.getMessage();
                }
                Message m = new Message();
                m = getAllStatus(dao);
                sm.release();
                return m;
            }
        });

        if (ms != null)
            result = map.get(ms.getAction()).doOperation(ms);

        else {
            result.setData("error");
        }
        return result;
    }

}