import java.lang.reflect.Array;
import java.util.Arrays;


public class ListTest {
	
	int[] thisArray;
	int size;
	int n ;
	
	ListTest(int n){
		this.thisArray = new int[n];
		this.size = 0;
		this.n = n;
	}
	
	void rangeCheck(int i) throws Exception{
		if(i>size){
			throw new Exception();
		}
	}
	int size(int[] array){
	return this.size;
	}
	
	boolean add(int value){
		if(size+1<=n){
			this.thisArray[size++] = value;		
			}else
			{
			int[] tmp = new int[n+20];
			System.arraycopy(thisArray, 0, tmp, 0, n);
			thisArray = tmp;
			thisArray[size++] = value;	
		}
		return true;
	}
	
	boolean set(int value,int index) throws Exception{
			rangeCheck(index);
			this.thisArray[index] = value;
			return true;
		
	}
	
	boolean contains(int value){
		
		for(int i = 0;i<size;i++){
			if(this.thisArray[i] == value){
				return true;
			}
		
		}
		
		return false;
	}
	
	int get(int index) throws Exception{
		rangeCheck(index);
		return this.thisArray[index];
		
	}
	
	int indexOf(int value){
		int index=0;
		if(contains(value)){
		for(int i = 0;i<size;i++){
			if(this.thisArray[i]==value){
				index = i;
			}
		}
		}else{
			index = -1;
		}
		return index;
		
	}
	
	int remove(int index) throws Exception{
		rangeCheck(index);
		int v = thisArray[index];
		
			for (int i = index;i<size;i++){
				this.thisArray[i] = this.thisArray[i+1];
			}
			this.size--;
			return v;
		
	}
	
	boolean insert(int value,int index) throws Exception{
		rangeCheck(index);
		size++;
		for ( int i = index+1;i<size;i++){
			this.thisArray[i]= this.thisArray[i+1];
		}
		this.thisArray[index] = value;
		return true;
	}
	
	void printList(){
		System.out.println(Arrays.toString(this.thisArray));
	}
	
	public static void main(String[] args) throws Exception {
		ListTest t = new ListTest(10);
		t.add(1);
		t.add(4);
		t.add(5);
		t.printList();
		System.out.println(t.size);
		System.out.println(t.get(1));
		t.set(5, 4);
		t.printList();
		System.out.println(t.contains(23));
		t.remove(0);
		t.printList();
		t.insert(23, 4);
		t.printList();

	}

}
