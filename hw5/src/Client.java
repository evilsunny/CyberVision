import java.io.*;
import java.net.Socket;
import java.util.LinkedList;

/**
 * Created by alina on 12.01.15.
 */
public class Client {

    private String host;
    private int port;
    private static LinkedList<Integer> keys;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
        keys = new LinkedList<>();
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
        System.out.println(metaResponse.getPort());
        Socket socket = new Socket(metaResponse.getHost(),metaResponse.getPort());


            OutputStream os = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(new Request(Request.Command.CREATE,o));
            oos.flush();


            ObjectInputStream objectInputStream =
                    new ObjectInputStream(socket.getInputStream());

            Response response = (Response) objectInputStream.readObject();
            os.close();

            keys.add(o.hashCode());

            System.out.println(response.getCOMMAND());
    }

    public Object read(Integer key) throws IOException, ClassNotFoundException {
        MetaResponse metaResponse = getMetaResponse(key);
        System.out.println(metaResponse.getPort());
        Socket socket = new Socket(metaResponse.getHost(),metaResponse.getPort());

        OutputStream os = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(new Request(Request.Command.READ,key));
        oos.flush();

        ObjectInputStream objectInputStream =
                new ObjectInputStream(socket.getInputStream());

        System.out.println("Im reading");
        Response response = (Response) objectInputStream.readObject();
        os.close();


        if(response.getSTATUS()== Response.Status.OK){
            System.out.println(response.getResponseObject());
            return response.getResponseObject();
        }else {
            System.out.println("Some errors");
            return null;
        }


    }


    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Client c = new Client("localhost",3333);
        String testString = "Hello dear World";
        Integer testInteger = 12345;
        String testString2 = "what";
        Integer testInteger2 = 123;

        c.create(testString);
        c.create(testInteger);
        c.create(testString2);
        c.create(testInteger2);

        c.read(keys.poll());
        c.read(keys.poll());
        c.read(keys.poll());


    }


}
