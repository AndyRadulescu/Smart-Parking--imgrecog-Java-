package server;

/**
 * Created by AndyRadulescu on 4/27/2017.
 */

import java.io.Serializable;


public class Message implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -86055680657258429L;
    private Integer action;
    private Object data;

    public Message() {

    }

    public Message(int action, Object data) {
        this.action = action;
        this.data = data;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}

