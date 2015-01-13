import java.io.*;
import java.net.Socket;

/**
 * Created by alina on 12.01.15.
 */
public class Client {

    private String host;
    private int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public MetaResponse getMetaResponse(int key) throws IOException, ClassNotFoundException {
        MetaResponse metaResponse = null;
        try (Socket socket = new Socket(host, port)) {
            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(new MetaRequest(key));
            objectOutputStream.flush();
            System.out.println(key);
            ObjectInputStream objectInputStream =
                    new ObjectInputStream(socket.getInputStream());
            metaResponse = (MetaResponse) objectInputStream.readObject();
        }

        return metaResponse;

    }

    public void create(Object o) throws IOException, ClassNotFoundException {
        MetaResponse metaResponse = getMetaResponse(o.hashCode());
        System.out.println(metaResponse.getHost());
        Socket socket = new Socket(metaResponse.getHost(),metaResponse.getPort());


            OutputStream os = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(new Request("CREATE",o));
            oos.flush();
            ObjectInputStream objectInputStream =
                    new ObjectInputStream(socket.getInputStream());

            Response response = (Response) objectInputStream.readObject();
            System.out.println(response.getSTATUS());

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Client c = new Client("localhost",3333);
        c.create("666");


    }


}
