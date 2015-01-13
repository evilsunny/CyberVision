import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by alina on 12.01.15.
 */
public class Server {


    static MetaResponse prepareResponse(MetaRequest metaRequest){
    MetaResponse m = null;
        int key = metaRequest.getKey();
        if (key%2 == 0){
            m = new MetaResponse("127.0.0.1:2001");
        }else {
            m = new MetaResponse("127.0.0.1:2002");
        }
         return m;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {


        ServerSocket server;

        server = new ServerSocket(3333);

        Socket fromclient;
            fromclient= server.accept();
            System.out.println("Client connected");

        ObjectInputStream ois = new ObjectInputStream(fromclient.getInputStream());
      //  PrintWriter out = new PrintWriter(fromclient.getOutputStream());
        MetaRequest input;

        System.out.println("Wait");
        MetaResponse metaResponse;
        while (true) {
            while ((input = (MetaRequest)ois.readObject()) != null) {
                metaResponse = prepareResponse(input);
                ObjectOutputStream objectOutputStream =
                        new ObjectOutputStream(fromclient.getOutputStream());
                objectOutputStream.writeObject(metaResponse);
                objectOutputStream.flush();
            }
        }
    }
}
