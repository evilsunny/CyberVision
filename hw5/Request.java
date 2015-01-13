import java.io.Serializable;

/**
 * Created by alina on 12.01.15.
 */
public class Request  implements Serializable{
    private String COMMAND;
    private  Object object;

    Request(String s, Object o){
        this.COMMAND = s;
        this.object = o;
    }

}
