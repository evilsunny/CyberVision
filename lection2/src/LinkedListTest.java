
public class LinkedListTest {

	int size;
	ListNode head;
	ListNode tail;
	
	LinkedListTest(){
		size = 0;
		head = null;
	}
	
	int size(){
		return this.size;
	}
	
	void add(int value){
		ListNode e = new ListNode(value);
		if(head==null){
			head = tail = e;
		}else{
			tail.next = e;
			tail = tail.next;
			}
			
		size++;
	}
	
	boolean contains(int value){
		boolean f=false;
		if(head==null){
			f =  false;
		}else{	
			ListNode thisNode = head;
			while(thisNode.next!=null){
				thisNode = thisNode.next;
				if(thisNode.element==value){
					f = true;
				}
			}
		}
		return f;
	}
	
	
	int get(int index){
		int i=0;
		if(head==null){
			return -1;
		}else{	
			ListNode thisNode = head;
			while((thisNode.next!=null)&&(i<index)){
				i++;
				thisNode = thisNode.next;
			}
			return thisNode.element;
		}
	}
	
	void insert(int position, int value){
		ListNode node= new ListNode(value);
		ListNode prev=null;
		int i=0;
		while(i!=position){
			prev = head.next;
			i++;	
		}
		
		ListNode next = prev.next;
		prev.next = node;
		node.next = next;	
	}
	
	void printList(){
		ListNode t = head;
		
		while(t!=null){
			System.out.println(t.element);
			t = t.next;
		}
		System.out.println("---");
		
	}
	
	Iterator getIterator(){
		return new Iterator();
	}
	
	

	
	public static void main(String[] args) {
		LinkedListTest t = new LinkedListTest();
		t.add(4);
		t.add(34);
		t.add(13);
		t.printList();
		t.insert(2, 133);
		t.printList();
		//System.out.println(t.get(0));
		Iterator it  = t.getIterator();
		
		it.next();
		it.next();
		it.remove();
		
		t.printList();
		

	}

	class ListNode{
		
		int element;
		ListNode next;
		
		ListNode(int theElement){
			element = theElement;
			next = null;
		}
	
		ListNode( int theElement, ListNode n ) {
	        element = theElement;
	        next    = n;
	    }
	}
	
	class Iterator{
		ListNode cur;
		ListNode prev;
		
		Iterator(){
			cur = head;
			prev = null;
		}
		
		boolean hasNext(){
			return cur!=null;
		}
		
		int next(){
			int v = cur.next.element;
			prev = cur;
			cur = cur.next;
			return v;
		}
		
		int remove(){
			int v;
			v = prev.next.element;
			prev.next = cur.next;
			size--;	
			return v;
		}
	}
}


