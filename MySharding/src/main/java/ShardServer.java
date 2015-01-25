import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;


public class ShardServer implements Runnable {


    private static ConcurrentHashMap<Integer,Object> store = new ConcurrentHashMap<>();

    int port;
    String host;
    File file;

    ShardServer(String host, int port) throws IOException {
        this.port = port;
        this.host = host;


        if(port==2001){
            file = new File("shard1.txt");
        }else {
            file = new File("shard2.txt");
        }


        if(file.exists()){

            try (ObjectInputStream objectInputStream =
                         new ObjectInputStream(new FileInputStream(file))) {
                store = (ConcurrentHashMap<Integer, Object>) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }



    static  Response prepareResponse(Request request) {
        Response response = null;

        switch (request.getCOMMAND()) {

            case CREATE: {
                System.out.println("Create");
                if(store.containsKey(request.getItem().hashCode())){
                    response = new Response("Already exists",Response.Status.ERROR);
                }else {
                    store.put(request.getItem().hashCode(),request.getItem());
                    response = new Response("Created", Response.Status.OK);
                }
                break;
            }
            case READ: {
                if(store.containsKey(request.getItem().hashCode())){
                    response = new Response("Found",Response.Status.OK,store.get(request.getItem().hashCode()));
                }else {
                    response = new Response("Not found", Response.Status.ERROR);
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


    @Override
    public void run() {
        ExecutorService requestPool = Executors.newCachedThreadPool();

        try(ServerSocket serverSocket = new ServerSocket(port);) {

            while (true){
                Socket clientOne = serverSocket.accept();
                requestPool.submit(new RequestItem(clientOne));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private class RequestItem implements Runnable{
        private final Socket socket;

        public RequestItem(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            ObjectOutputStream objectOutputStream = null;
            ObjectInputStream objectInputStream = null;

            while (true) {
                try {
                    objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.flush();
                    objectInputStream = new ObjectInputStream(socket.getInputStream());
                    Request request = (Request) objectInputStream.readObject();
                    Response response = prepareResponse(request);
                    objectOutputStream.writeObject(response);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                } finally {
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
                        try {
                            FileOutputStream fout = new FileOutputStream(file);
                            ObjectOutputStream oos = new ObjectOutputStream(fout);
                            oos.writeObject(store);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Buy 1 server");

                        break;
                    }
                }
            }


        }
    }

}




