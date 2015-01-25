import java.io.Serializable;

/**
 * Created by alina on 12.01.15.
 */
public class Response implements Serializable{

    private String COMMAND;
    private Status STATUS;
    private Object responseObject;

    Response(String com, Status stat){
        this.COMMAND = com;
        this.STATUS = stat;
    }

    public Response(String COMMAND, Status STATUS, Object responseObject) {
        this.COMMAND = COMMAND;
        this.STATUS = STATUS;
        this.responseObject = responseObject;
    }

    public Object getResponseObject(){  return responseObject;  }

    public String  getCOMMAND(){
        return COMMAND;
    }

    public Status getSTATUS(){
        return STATUS;
    }

    public enum Status{
        ERROR,OK;
    }

}
