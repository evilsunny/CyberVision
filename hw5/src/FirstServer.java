import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by alina on 13.01.15.
 */
public class FirstServer implements Runnable {


    @Override
    public void run() {
        ServerSocket server;
        Socket fromclient;
        try {
            server = new ServerSocket(2001);

            fromclient= server.accept();
            System.out.println("Client connected");

            ObjectInputStream ois = new ObjectInputStream(fromclient.getInputStream());
            Request input;

            System.out.println("Wait");
            Response response;
            while (true) {
                while ((input = (Request)ois.readObject()) != null) {
                        FileOutputStream fout = new FileOutputStream("shard1.txt");
                        ObjectOutputStream oos = new ObjectOutputStream(fout);
                        oos.writeObject(input.getItem());
                        response = new Response("CREATE","CREATED");
                        ObjectOutputStream objectOutputStream =
                                new ObjectOutputStream(fromclient.getOutputStream());
                        objectOutputStream.writeObject(response);
                        objectOutputStream.flush();


                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
