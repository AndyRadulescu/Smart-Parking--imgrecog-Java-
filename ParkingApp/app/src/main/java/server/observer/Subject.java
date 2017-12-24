package server.observer;



/**
 * Created by Andy Radulescu on 12/24/2017.
 */

public interface Subject {

    public void register(Observer observer);

    public void unRegister(Observer observer);

    public void notifyObserver();

}
