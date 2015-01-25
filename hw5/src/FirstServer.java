import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Created by alina on 13.01.15.
 */
public class FirstServer implements Runnable {
    File file;
    static HashMap<Integer,Object> dataBase;

    FirstServer(){
        file = new File("shard1.txt");

        if(file.exists()){

        try (ObjectInputStream objectInputStream =
        new ObjectInputStream(new FileInputStream(file))) {
             dataBase = (HashMap<Integer, Object>) objectInputStream.readObject();
            System.out.println(dataBase);
        } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
}
        }

    }
    @Override
    public void run() {

        Socket fromclient;
       // dataBase = new HashMap<Integer,Object>();

        while (true) {
            try (ServerSocket server = new ServerSocket(2001);) {

                System.out.println("Wait request");
                fromclient = server.accept();
                System.out.println("Client connected");

                ObjectInputStream ois = new ObjectInputStream(fromclient.getInputStream());
                Request input;

                System.out.println("Wait");
                Response response;
                input = (Request) ois.readObject();
                response = prepareResponse(input);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fromclient.getOutputStream());
                objectOutputStream.writeObject(response);
                objectOutputStream.flush();
                input = (Request) ois.readObject();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {

                try {

                    FileOutputStream fout = new FileOutputStream("shard1.txt");
                    ObjectOutputStream oos = new ObjectOutputStream(fout);
                    oos.writeObject(dataBase);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Buy 1 server");

            }
        }
    }

    static  Response prepareResponse(Request request) {
        Response response = null;
        System.out.println("Now Im preparing response");
        switch (request.getCOMMAND()) {

            case CREATE: {
                if(dataBase.containsKey(request.getItem().hashCode())){
                    response = new Response("Key already added",Response.Status.ERROR);
                }else {
                    dataBase.put(request.getItem().hashCode(), request.getItem());
                    response = new Response("Created", Response.Status.OK);
                }
                break;
            }
            case READ: {
                System.out.println("Reading");
                if (dataBase.containsKey(request.getItem())){
                    response = new Response("Object is founded", Response.Status.OK, dataBase.get(request.getItem().hashCode()));
                }else {
                    response = new Response("Object is not founded", Response.Status.ERROR);
                }

                break;
            }
            case UPDATE: {
                break;
            }
            case DELETE: {
                break;
            }
        }

        return response;
    }
}
