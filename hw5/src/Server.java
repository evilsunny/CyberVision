import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by alina on 12.01.15.
 */
public class Server {


    static MetaResponse prepareResponse(MetaRequest metaRequest) {
        MetaResponse m = null;
        int key = metaRequest.getKey();
        if (key % 2 == 0) {
            m = new MetaResponse("localhost", 2001);
        } else {
            m = new MetaResponse("localhost", 2002);
        }
        return m;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        MetaRequest input;
        System.out.println("Wait");

        Thread first = new Thread(new FirstServer());
        Thread second = new Thread(new SecondServer());

        first.start();
        second.start();

        ObjectInputStream objectInputStream = null;
        ObjectOutputStream objectOutputStream = null;
        Scanner newscan= new Scanner(System.in);
        while (true) {
                try(ServerSocket server = new ServerSocket(3333);
                Socket fromclient = server.accept();){
                 objectInputStream = new ObjectInputStream(fromclient.getInputStream());
                 objectOutputStream =
                        new ObjectOutputStream(fromclient.getOutputStream());
                objectOutputStream.flush();
                MetaResponse metaResponse;
                input = (MetaRequest) objectInputStream.readObject();
                System.out.println(input);
                metaResponse = prepareResponse(input);
                objectOutputStream.writeObject(metaResponse);
                objectOutputStream.flush();
            }catch (IOException | ClassNotFoundException e){
                e.printStackTrace();
            }finally {
                if (objectInputStream != null)
                    try {
                        objectInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                if (objectOutputStream != null) {
                    try {
                        objectOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


        }
    }
}

