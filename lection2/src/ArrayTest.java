import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;


public class ArrayTest {

	static int[] array;
	static int a=1;
	static int n = 0;
	static boolean l;
	
	
	
	public static void main(String[] args) throws IOException {
		array = new int[10];
		Scanner sc = new Scanner(System.in);
		Arrays.fill(array, 2);
		do {
			l = false;
			a = sc.nextInt();
			System.out.println(a);
			for(int i = 0;i<n;i++){
				if(a == array[i]){
					l=true;
				}
			}
			if(!l){
				array[n]=a;		
				n++;
			}
			System.out.println(Arrays.toString(array));
		}while(a!=0);
		
		

	}

}
