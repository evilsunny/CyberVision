import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 * Created by alina on 12.01.15.
 */
public class Response implements Serializable{

    private String COMMAND;
    private Status STATUS;
    private Object object;

    Response(String com, Status stat){
        this.COMMAND = com;
        this.STATUS = stat;
    }

    Response(String com, Status stat,Object o){
        this.COMMAND = com;
        this.STATUS = stat;
        this.object = o;
    }

    public Object getObject(){ return object;}

    public String  getCOMMAND(){
        return COMMAND;
    }

    public Status getSTATUS(){
        return STATUS;
    }

    public static enum Status {
        OK, ERROR
    }
}
