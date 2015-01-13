import java.io.Serializable;

/**
 * Created by alina on 12.01.15.
 */
public class Request  implements Serializable{
    private String COMMAND;
    private  Object item;

    Request(String s, Object o){
        this.COMMAND = s;
        this.item = o;
    }

    String getCOMMAND(){
        return this.COMMAND;
    }

    Object getItem(){
        return item;
    }

}
