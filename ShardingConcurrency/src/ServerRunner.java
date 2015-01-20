import com.sun.corba.se.spi.activation.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by alina on 20.01.15.
 */
public class ServerRunner {


    public static void main(String[] args) throws IOException {
       ServerRunner s = new ServerRunner();
        s.start();

    }
    public void start() throws IOException {

        ExecutorService shardPool = Executors.newFixedThreadPool(2);
        shardPool.submit(new ShardServer("localhost", 2001));
        shardPool.submit(new ShardServer("localhost", 2002));

        ExecutorService metaRequestPool = Executors.newCachedThreadPool();


            ServerSocket serverSocket  = new ServerSocket(3333);

            while (true){
                Socket socket = serverSocket.accept();

                metaRequestPool.submit(new MetaRequestItem(socket));
            }

    }

    private class MetaRequestItem implements  Runnable{
        Socket socket;

        MetaRequestItem(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run() {
            ObjectInputStream objectInputStream = null;
            MetaRequest metaRequest = null;
            String host;
            int port;

            try( ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());) {
                objectOutputStream.flush();

                try {
                    objectInputStream = new ObjectInputStream(socket.getInputStream());
                    metaRequest = (MetaRequest)objectInputStream.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                if (metaRequest.getKey()%2==0){
                    host = "localhost";
                    port = 2001;
                }else {
                    host = "localhost";
                    port = 2002;
                }

                MetaResponse metaResponse = new MetaResponse(host,port);
                objectOutputStream.writeObject(metaResponse);
                objectOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (objectInputStream != null)
                    try {
                        objectInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }


        }
    }

}
