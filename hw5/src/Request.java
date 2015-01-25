import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by alina on 12.01.15.
 */
public class Request  implements Serializable{
    private Command COMMAND;
    private  Object item;

    Request(Command s, Object o){
        this.COMMAND = s;
        this.item = o;
    }

    Command getCOMMAND(){
        return this.COMMAND;
    }

    Object getItem(){
        return item;
    }

    public  enum Command{
        CREATE,READ,UPDATE,DELETE;
    }

}
