
public class HashSetTest<K> {

	HashMapTest<K,Object> hashMap;
	Object o;
	HashSetTest(){
		hashMap = new HashMapTest();
		o = new Object();
	}
	
	void add(K key){
			hashMap.put(key,o);

		
	}
	
	boolean contains(K key){
		return hashMap.get(key)!=null;
	}
	
	int size(){
		return hashMap.size;
	}
	
	public static void main(String[] args) {
		
	}

}
