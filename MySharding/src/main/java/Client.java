import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.*;

public class Client {

    private static ConcurrentHashMap<Integer,Object> forSending;
    private static ConcurrentHashMap<Integer,Object> forReceive;
    private static ConcurrentSkipListSet<Integer> keys;

    public static String host = "localhost";
    public static int port = 3333;

    public static MetaResponse getMetaResponse(int key) throws IOException, ClassNotFoundException {
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

    public  static void create(Object o) throws IOException, ClassNotFoundException {
        MetaResponse metaResponse = getMetaResponse(o.hashCode());
        keys.add(o.hashCode());
        Socket socket = new Socket(metaResponse.getHost(),metaResponse.getPort());
            OutputStream os = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(new Request(Request.Operation.CREATE,o));
            oos.flush();
            ObjectInputStream objectInputStream =
                    new ObjectInputStream(socket.getInputStream());

            Response response = (Response) objectInputStream.readObject();
            System.out.println(response.getCOMMAND()+" "+o);

    }


    public static Object read(int key) throws IOException, ClassNotFoundException {
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
            }
        }

        return response.getObject();

    }

    static class WriterClient implements Runnable{

        private  int begin;
        private int end;


        public WriterClient(int begin, int end) {
            this.begin = begin;
            this.end = end;

        }

        @Override
        public void run() {
            Object writtenObject = null;

            for(int i = begin;i<end;i++){
                writtenObject = forSending.get(i);
                forSending.remove(i);
                try {
                    create(writtenObject);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
    }
    }



    static class ReaderClient implements Runnable{
    Object receivedObject;


        @Override
        public void run() {

            while (!keys.isEmpty()){
                try {
                    receivedObject = read(keys.pollFirst());
                    System.out.println("Read "+receivedObject);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        forSending = new ConcurrentHashMap<>();
        keys = new ConcurrentSkipListSet<>();
        forSending.put(1,"First");
        forSending.put(2,"Second");
        forSending.put(3,"Third");
        forSending.put(4,4);
        forSending.put(5,5);

        ExecutorService writePool = Executors.newFixedThreadPool(2);
        Future firstThread =  writePool.submit(new WriterClient(3, 6));
        Future secondThread = writePool.submit(new WriterClient(1,3));

        firstThread.get();
        secondThread.get();

        ExecutorService readPool = Executors.newFixedThreadPool(2);
        readPool.submit(new ReaderClient());
        readPool.submit(new ReaderClient());
    }


    }





