import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class SocketServer {

	
	
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(3333);
		Socket client = serverSocket.accept();
		System.out.println("Conn");
		DataOutputStream dOut = new DataOutputStream(client.getOutputStream());
		dOut.writeUTF("HELLOWORLD");
		dOut.flush();
		

	}

}
