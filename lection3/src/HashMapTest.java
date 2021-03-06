import java.util.LinkedList;
import java.util.ListIterator;

import javax.swing.text.html.HTMLDocument.Iterator;


public class HashMapTest<K,V>{

	LinkedList<Entry>[] list ;
	int size;
	
	HashMapTest(){
		list = new LinkedList[10];	
	}
	
	public static void main(String[] args) {
		HashMapTest t = new HashMapTest();
		t.put(1, 34);
		t.put(2, 34);
		t.put(1, 33);
		 System.out.println(t.list[0].toString());
		 System.out.println(t.list[1].toString());
		 System.out.println(t.list[2].toString());
	}

	public void put(K key, V value) {
		
		Entry element = new Entry(key,value);
		int index = indexOf(key);
		LinkedList<Entry> thisList =  list[index];
		if(list[index]==null){
			list[index]= new LinkedList<Entry>();
		}
//доделать ленивую загрузку
		if(thisList.isEmpty()){
			thisList.add(element);
		}else{
			for(Entry e:thisList){
				if(e.key.equals(key)){
					e.value = value;
					e.key = key;
					return;
				}else{
					thisList.add(element);
				}
				
			}
		}
		
		list[index] = thisList;
		size++;			
	}
	
	int indexOf(K key){
		int a  = Math.abs(key.hashCode())%10;
		return a;
	}

	public V get(K key) {
		int index = indexOf(key);
		LinkedList<Entry> thisList =  list[index];
		V value = null ;
		
		if(list[index]==null){
			return null;
		}else{
			for(Entry e:thisList){
				if(e.key.equals(key)){
					value =  e.value;
				}
			}
			return value;
			
		}
		
		
	}
	
//	V remove(K key){
//		int index = indexOf(key);
//		V value;
//		LinkedList<Entry> thisList =  list[index];
//		
//		Iterator<Entry> i = thisList.iterator();
//		
//		for(Entry e:thisList){
//			if(e.key == key){
//				value =  e.value;
//				e.
//			}
//		}
//		return value;
//	}

	public int size() {
		return size;
	}
	
	 void print(){
		 System.out.print(list[1].toString());
	}

		class Entry{
			K key;
			V value;
			
			Entry(K theKey,V theValue){
				key = theKey;
				value = theValue;
			}
		}
}
