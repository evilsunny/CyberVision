package homework;

public class TreeMapTest<V> {

	Node root;
	
	
	TreeMapTest(int key,V value){
		root = new Node(key,value);
	}
	
	void addNode(Node to,int key,V value){
		boolean isLeft;
		if(key==to.key){
			to.count++;		
		}
		
		if(key>to.key){
			isLeft = false;
		}else{
			isLeft = true;
		}
		
		if(isLeft){
			if(to.left!=null){
				addNode(to.left,key,value);
				return;	
			}
			to.left = new Node(key,value);
		}else{
			if(to.right!=null){
				addNode(to.right,key,value);
				return;
			}
			to.right = new Node(key,value);
		}
		

	}
	
	void printTree(){
		print(root);	
	}
	
	void print(Node node){
		if(node == null){
			return;
		}else{
			print(node.left);
			String s = node.key+" ";
			s+=(node.isRed)? "red  " : "black  ";
			if(node.parent!=null){
				s+="parent: " + node.parent.key;
			}
			System.out.println(s);
			print(node.right);
		}
	}
	
	void Test(){
		this.addNode(root, 12, null);
		this.addNode(root, 11, null);
		this.addNode(root, 4, null);
		this.addNode(root, 1, null);
		this.printTree();
		
	}
	
	

	
	class Node<K,V>{
		
		Node right;
		Node left;
		Node parent;
		
		int key;
		V value;
		
		boolean isRed;
		int count;
		
		Node(int thisKey,V thisValue){
			key = thisKey;
			value = thisValue;
		}
		
		Node(int thisKey,V thisValue,Node par){
			key = thisKey;
			value = thisValue;
			parent = par;
		}
	}
	
	public static void main(String[] args) {
		TreeMapTest t = new TreeMapTest(4,5);
		t.Test();

	}

}
