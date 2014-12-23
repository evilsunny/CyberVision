public class EnumTest {

	enum State{
		ALIVE,DEAD;
		
	static State valueOfIgnoreCase(String value){
	try{	
		return valueOf(value.toUpperCase());
		}catch(Exception e){
			return null;
		}
		
	}
	}
	
	public static void main(String []args){
		System.out.println(State.valueOfIgnoreCase("alive"));
		System.out.print(void.class);
	}
}
