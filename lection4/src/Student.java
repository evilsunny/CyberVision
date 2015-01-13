import java.io.Serializable;


public class Student implements Serializable{

	String name;
//	int age;
	private int serialVersionUID = 2;
	
	Student(String thisName,int thisAge){
		this.name = thisName;
		//this.age = thisAge;
	}

}
