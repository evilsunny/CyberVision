import java.io.Serializable;

/**
 * Created by alina on 12.01.15.
 */
public class Request  implements Serializable{
    private Operation COMMAND;
    private  Object item;

    Request(Operation s, Object o){
        this.COMMAND = s;
        this.item = o;
    }

    Operation getCOMMAND(){
        return this.COMMAND;
    }

    Object getItem(){
        return item;
    }

    public static enum Operation {
        CREATE, READ, UPDATE, DELETE;
        @Override
        public String toString() {
            return this.name();
        }
    }

}
