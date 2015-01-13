import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class StreamTest {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
	
		
		try(
				BufferedReader	inputStream = new BufferedReader(new FileReader("file1.txt") );
				PrintWriter outputStream = new PrintWriter(new FileWriter("file2.txt"));
				){
			
			String l;
			
			while ((l= inputStream.readLine())!=null) {
				outputStream.println(l);
				
			}
		
		

		}

}
}
	
