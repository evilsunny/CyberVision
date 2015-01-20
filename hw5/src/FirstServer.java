import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Created by alina on 13.01.15.
 */
public class FirstServer implements Runnable {


    @Override
    public void run() {
        ServerSocket server;
        Socket fromclient;

        HashSet<Object> dataBase;

        dataBase = new HashSet<Object>();
        try {

            server = new ServerSocket(2001);

            fromclient= server.accept();
            System.out.println("Client connected");

            ObjectInputStream ois = new ObjectInputStream(fromclient.getInputStream());
            Request input;

            System.out.println("Wait");
            Response response;
            input = (Request)ois.readObject();
            Scanner newscan= new Scanner(System.in);
            do {
                        dataBase.add(input.getItem());
                        response = prepareResponse(input);
                        ObjectOutputStream objectOutputStream =
                                new ObjectOutputStream(fromclient.getOutputStream());
                        objectOutputStream.writeObject(response);
                        objectOutputStream.flush();
                        input = (Request)ois.readObject();
                }while (newscan.next() != "stop1");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                FileOutputStream fout = new FileOutputStream("shard1.txt");
                ObjectOutputStream oos = new ObjectOutputStream(fout);
                oos.writeObject(dataBase);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Buy");
        }


    }

    static  Response prepareResponse(Request request) {
        Response response = null;

        switch (request.getCOMMAND()) {

            case "CREATE": {
                response = new Response("CREATE", "CREATED");
                break;
            }
            case "READ": {
                break;
            }
            case "UPDATE": {
                break;
            }
            case "DELETE": {
                break;
            }
        }

        return response;
    }
}
