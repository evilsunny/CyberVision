import java.io.Serializable;

/**
 * Created by alina on 12.01.15.
 */
public class MetaResponse implements Serializable{
    private String host;
    private int port;

    public MetaResponse(String host, int port) {


        this.host = host;
        this.port = port;
    }

    public String getHost(){
        return host;
    }

    public  int getPort(){
        return port;
    }
}
