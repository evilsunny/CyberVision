import java.io.Serializable;

/**
 * Created by alina on 12.01.15.
 */
public class Response implements Serializable{

    private String COMMAND;
    private String STATUS;

    Response(String com, String stat){
        this.COMMAND = com;
        this.STATUS = stat;
    }

    public String  getCOMMAND(){
        return COMMAND;
    }

    public String getSTATUS(){
        return STATUS;
    }

}
