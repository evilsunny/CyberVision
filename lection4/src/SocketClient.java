import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


public class SocketClient {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket clientSocket  = new Socket("127.0.0.1",3333);
		DataInputStream dIn = new DataInputStream(clientSocket.getInputStream());
		System.out.println(dIn.readUTF());

	}

}
