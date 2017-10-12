package server;

import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Callable;


/**
 * Thread that is used to send/recive messages to/from server.
 */
public class ClientThread implements Callable<Message>, SavedItems {
    private Message ms;
    private String debug = "debug";

    @Override
    public Message call() throws IOException, ClassNotFoundException {
        Log.i(debug, "Trying to connect on " + IP + " on port " + PORT);
        Socket socket = new Socket(IP, PORT);
        Log.i(debug, "Connection Established");
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        out.writeObject(ms);
        out.flush();
        Message resp = (Message) in.readObject();
        socket.close();
        Log.i(debug, "the socket has been closed!");
        return resp;
    }

    public ClientThread(Message ms) {
        this.ms = ms;
    }

    public Message getMessage() {
        return ms;
    }

    public void setMessage(Message ms) {
        this.ms = ms;
    }
}
