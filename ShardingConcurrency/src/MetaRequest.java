import java.io.Serializable;

/**
 * Created by alina on 12.01.15.
 */
public class MetaRequest implements Serializable {

    private int key;

    MetaRequest(int key){
        this.key = key;
    }

    int getKey(){
        return  key;
    }
}
