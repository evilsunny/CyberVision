package hw;

public class RedBlackTree<K extends Comparable<K>,V> {
	
	   private static final boolean RED   = true;
	   private static final boolean BLACK = false;
	   
	   Node root;
	    
	class Node<K,V>{
		K key;
		V value;
		Node left,right;
		boolean color;
		int N;
		
		Node(K thisKey,V thisValue,boolean thisColor,int thisN){
			key = thisKey;
			value = thisValue;
			color = thisColor;
			N = thisN;
		}
	}
	
	   private boolean isRed(Node x) {
	        if (x == null) return false;
	        return (x.color == RED);
	    }
	
	int size(Node h){
		if (h == null) return 0;
        return h.N;
	}
	
	
	int size(){
		return root.N;
	}

	boolean isEmpty(){
		return root == null;
	}
	
	
	void put(K thisKey, V thisValue){
		root = put(root, thisKey, thisValue);
        root.color = BLACK;
	}
	
	Node put(Node h, K thisKey, V thisValue){
		if(h==null){
			return new Node(thisKey,thisValue,RED,1);
		}
			int cmp =  thisKey.compareTo( (K) h.key);
			
			if(cmp < 0){
				h.left = put(h.left,thisKey,thisValue);
			}else
				if(cmp > 0){
					h.right = put(h.right, thisKey,thisValue);
				}else{
					h.value = thisValue;
				}
			
			if (isRed(h.right) && !isRed(h.left))      h = rotateLeft(h);
	        if (isRed(h.left)  &&  isRed(h.left.left)) h = rotateRight(h);
	        if (isRed(h.left)  &&  isRed(h.right))     flipColors(h);
			h.N = size(h.left) + size(h.right) + 1;

	        return h;
	
	}
	
	Node rotateRight(Node h) {
  
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = x.right.color;
        x.right.color = RED;
        x.N = h.N;
        h.N = size(h.left) + size(h.right) + 1;
        return x;
    }
	
	Node rotateLeft(Node h) {
        
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = x.left.color;
        x.left.color = RED;
        x.N = h.N;
        h.N = size(h.left) + size(h.right) + 1;
        return x;
    }
	
	  void flipColors(Node h) {
	        h.color = !h.color;
	        h.left.color = !h.left.color;
	        h.right.color = !h.right.color;
	    }
	

	     public V get(K key) { return get(root, key); }

	     private V get(Node x, K key) {
	         while (x != null) {
	             int cmp = key.compareTo((K) x.key);
	             if      (cmp < 0) x = x.left;
	             else if (cmp > 0) x = x.right;
	             else              return (V) x.value;
	         }
	         return null;
	     }

	     public boolean contains(K key) {
	         return get(key) != null;
	     }


	public void deleteMin() {
		if (isEmpty()) //throw new NoSuchElementException("BST underflow");
		 return;

		// if both children of root are black, set root to red
		if (!isRed(root.left) && !isRed(root.right))
			root.color = RED;

		root = deleteMin(root);
		if (!isEmpty()) root.color = BLACK;
		// assert check();
	}

	// delete the key-value pair with the minimum key rooted at h
	private Node deleteMin(Node h) {
		if (h.left == null)
			return null;

		if (!isRed(h.left) && !isRed(h.left.left))
			h = moveRedLeft(h);

		h.left = deleteMin(h.left);
		return balance(h);
	}


	// delete the key-value pair with the maximum key
	public void deleteMax() {
		if (isEmpty())// throw new NoSuchElementException("BST underflow");
			return;

		// if both children of root are black, set root to red
		if (!isRed(root.left) && !isRed(root.right))
			root.color = RED;

		root = deleteMax(root);
		if (!isEmpty()) root.color = BLACK;
		// assert check();
	}

	// delete the key-value pair with the maximum key rooted at h
	private Node deleteMax(Node h) {
		if (isRed(h.left))
			h = rotateRight(h);

		if (h.right == null)
			return null;

		if (!isRed(h.right) && !isRed(h.right.left))
			h = moveRedRight(h);

		h.right = deleteMax(h.right);

		return balance(h);
	}

	// delete the key-value pair with the given key
	public void delete(K key) {
		if (!contains(key)) {
			System.err.println("symbol table does not contain " + key);
			return;
		}

		// if both children of root are black, set root to red
		if (!isRed(root.left) && !isRed(root.right))
			root.color = RED;

		root = delete(root, key);
		if (!isEmpty()) root.color = BLACK;
		// assert check();
	}

	private Node delete(Node h, K key) {
		// assert get(h, key) != null;

		if (key.compareTo((K) h.key) < 0)  {
			if (!isRed(h.left) && !isRed(h.left.left))
				h = moveRedLeft(h);
			h.left = delete(h.left, key);
		}
		else {
			if (isRed(h.left))
				h = rotateRight(h);
			if (key.compareTo((K) h.key) == 0 && (h.right == null))
				return null;
			if (!isRed(h.right) && !isRed(h.right.left))
				h = moveRedRight(h);
			if (key.compareTo((K) h.key) == 0) {
				Node x = min(h.right);
				h.key = x.key;
				h.value = x.value;
				// h.val = get(h.right, min(h.right).key);
				// h.key = min(h.right).key;
				h.right = deleteMin(h.right);
			}
			else h.right = delete(h.right, key);
		}
		return balance(h);
	}

	public Object min() {
		if (isEmpty()) return null;
		return min(root).key;
	}

	// the smallest key in subtree rooted at x; null if no such key
	private Node min(Node x) {
		// assert x != null;
		if (x.left == null) return x;
		else                return min(x.left);
	}

	private Node moveRedLeft(Node h) {
		// assert (h != null);
		// assert isRed(h) && !isRed(h.left) && !isRed(h.left.left);

		flipColors(h);
		if (isRed(h.right.left)) {
			h.right = rotateRight(h.right);
			h = rotateLeft(h);
			flipColors(h);
		}
		return h;
	}

	// Assuming that h is red and both h.right and h.right.left
	// are black, make h.right or one of its children red.
	private Node moveRedRight(Node h) {
		// assert (h != null);
		// assert isRed(h) && !isRed(h.right) && !isRed(h.right.left);
		flipColors(h);
		if (isRed(h.left.left)) {
			h = rotateRight(h);
			flipColors(h);
		}
		return h;
	}


	private Node balance(Node h) {
		// assert (h != null);

		if (isRed(h.right))                      h = rotateLeft(h);
		if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
		if (isRed(h.left) && isRed(h.right))     flipColors(h);

		h.N = size(h.left) + size(h.right) + 1;
		return h;
	}


	public void print() {
		printHelper(root, 0);
	}

	private void printHelper(Node n, int indent) {
		if (n == null) {
			System.out.print("<empty tree>");
			return;
		}
		if (n.right != null) {
			printHelper(n.right, indent + 4);
		}
		for (int i = 0; i < indent; i++)
			System.out.print(" ");
		if (n.color == BLACK)
			System.out.println(n.key);
		else
			System.out.println("<" + n.key + ">");
		if (n.left != null) {
			printHelper(n.left, indent + 4);
		}
	}


	public static void main(String[] args) {

		
		RedBlackTree<Integer,Integer> b = new RedBlackTree<Integer,Integer>();
		b.put(1, 1622);
		b.put(2, 33);
		b.put(56, 671);
		b.put(5, 671);
		b.put(3, 671);
		b.print();
		System.out.println();

		b.delete(2);
		b.print();

	}

}
