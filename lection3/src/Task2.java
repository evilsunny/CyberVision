
public class Task2 {

/////????=(
	 static <T extends Number> T twice(T t){
		 Number a = 2*t.doubleValue();
		 //Class s = t.getClass();
		 
		 return (T) a;
		
	 }
	 
	public static void main(String[] args) {
		System.out.print(Task2.<Integer>twice(10));
	}

}
