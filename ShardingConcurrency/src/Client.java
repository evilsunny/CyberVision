import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.*;

public class Client implements Runnable {

    private String host;
    private int port;
    private static ConcurrentHashMap<Integer,Object> forSending;
    private static ConcurrentHashMap<Integer,Object> forReceive;
    private static ConcurrentLinkedQueue<Integer> keys;
    private  int begin;
    private int end;

    public Client(String host, int port, int begin, int end) {
        this.host = host;
        this.port = port;
        this.begin = begin;
        this.end = end;
    }

    public MetaResponse getMetaResponse(int key) throws IOException, ClassNotFoundException {
        MetaResponse metaResponse = null;
        try (Socket socket = new Socket(host, port)) {
            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(new MetaRequest(key));
            objectOutputStream.flush();

          //  System.out.println(key);
            ObjectInputStream objectInputStream =
                    new ObjectInputStream(socket.getInputStream());
            metaResponse = (MetaResponse) objectInputStream.readObject();
        }

        return metaResponse;

    }

    public void create(Object o) throws IOException, ClassNotFoundException {
        MetaResponse metaResponse = getMetaResponse(o.hashCode());
        keys.add(o.hashCode());
       // System.out.println(o.hashCode());
        //System.out.println(metaResponse.getPort());
        Socket socket = new Socket(metaResponse.getHost(),metaResponse.getPort());


            OutputStream os = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
        // System.out.println(o.hashCode());
            oos.writeObject(new Request(Request.Operation.CREATE,o));
            oos.flush();
            ObjectInputStream objectInputStream =
                    new ObjectInputStream(socket.getInputStream());

            Response response = (Response) objectInputStream.readObject();
            System.out.println(response.getCOMMAND());

    }


    public Object read(int key) throws IOException, ClassNotFoundException {
        MetaResponse metaResponse = getMetaResponse(key);

        Response response = null;

        while (response == null){
            try(Socket socket = new Socket(metaResponse.getHost(),metaResponse.getPort());) {

                Request request = new Request(Request.Operation.READ,key);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.flush();
                objectOutputStream.writeObject(request);
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                response = (Response)objectInputStream.readObject();
                System.out.print(response.getSTATUS());

            }
        }

        return response.getObject();

    }



    @Override
    public void run() {
        Object o = null;

       for(int i = begin;i<end;i++){
            o = forSending.get(i);
            forSending.remove(i);
            try {
                create(o);
                System.out.println(o);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

            System.out.println("Reading");
            while (!keys.isEmpty()){
                try {
                    o = read(keys.poll());
                    System.out.println(o);
                      } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                  e.printStackTrace();
                 }
    }



    }

    public static void main(String[] args){

        forSending = new ConcurrentHashMap<>();
        forSending.put(1,"aq");
        forSending.put(2,"bq");
        forSending.put(3,"ct");
        forSending.put(4,12);
        forSending.put(5,"e");

        ExecutorService writePool = Executors.newFixedThreadPool(2);

        writePool.submit(new Client("localhost", 3333,3,6));
        writePool.submit(new Client("localhost", 3333,1,3));


    }




}
