import sun.org.mozilla.javascript.ast.ThrowStatement;

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
            m = new MetaResponse("localhost",2001);
        }else {
            m = new MetaResponse("localhost",2002
            );
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
        MetaRequest input;
        System.out.println("Wait");

        Thread first = new Thread(new FirstServer());
        Thread second = new Thread(new SecondServer());

        first.start();
        second.start();
        ObjectOutputStream objectOutputStream =
                new ObjectOutputStream(fromclient.getOutputStream());
        objectOutputStream.flush();
        MetaResponse metaResponse;
        while (true) {
//            try {
//                while ((
                    input = (MetaRequest)ois.readObject();
                    System.out.println(input);
                    metaResponse = prepareResponse(input);

                    objectOutputStream.writeObject(metaResponse);
                    objectOutputStream.flush();

                }
//            }catch (EOFException e){
//                e.printStackTrace();
//            }


        }
    }

