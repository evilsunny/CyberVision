import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class SerialTest {

	
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		
		Student s1 = new Student("Ann",20);
		Student s2 = new Student("Alex",23);
		Student s3 = new Student("Kate",40);
		
//		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("my_obj.dat"));
//		
//		out.writeObject(s1);,
//		out.writeObject(s2);
//		out.writeObject(s3);
//		
		ObjectInputStream in = new ObjectInputStream(new FileInputStream("my_obj.dat"));
		
		Student a = (Student) in.readObject();
		Student b = (Student) in.readObject();
		Student c = (Student) in.readObject();
		System.out.print(a.name);
		System.out.print(b.name);
		System.out.print(c.name);
		
		
	}

}
