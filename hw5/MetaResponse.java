/**
 * Created by alina on 12.01.15.
 */
public class MetaResponse {
    private String host;
    private int port;

    public MetaResponse(String address) {

        String[] hostAndPort = address.split(":");
        this.host = hostAndPort[0];
        this.port = Integer.parseInt(hostAndPort[1]);
    }

    public String getHost(){
        return host;
    }

    public  int getPort(){
        return port;
    }
}
