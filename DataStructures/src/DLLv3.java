public class DLLv3 {
	
	Node first = null;
	Node last = null;
	int size = 0;
	
	/* Doubly Linked list Node*/
	
	class Node {
		int data;
		Node prev = null;
		Node next = null;
		// Constructor to create a new node
		// next and prev is by default initialized as null
		Node(int d) {
			data = d;
		}
	}
	
	// adds a node at the front of the list and updates the size
	public void push(int new_data) {
		Node n = new Node(new_data);
		if (first == null) {
			first = n;
			last = n;
		} else {
			n.next = first;
			first.prev = n;
			first = n;
		}
		size++;
	}
	
	// adds a node at the end of the list and updates the size
	public void append(int new_data) {
		Node n = new Node(new_data);
		
		if (first == null) {
			first = n;
			last = n;
		} else {
			last.next = n;
			n.prev = last;
			last = n;
		}
		size++;
	}
	
	// inserts a new node after the given node and updates the size
	public boolean InsertAfter(Node node, int new_data) {
		Node n = new Node(new_data);
		if (first == null) {
			first = n;
			last = n;
			size++;
			return true;
		} else if (node == null) {
			return false;
		} else if (node == last) {
			n.prev = node;
			n.next = null;
			node.next = n;
			last = n;
			size++;
			return true;
		} else {
			n.prev = node;
			n.next = node.next;
			node.next.prev = n;
			node.next = n;
			size++;
			return true;
		}
	}
	// inserts a new node before the given node and updates the size
	public boolean InsertBefore(Node node, int new_data) {
		Node n = new Node(new_data);
		if (first == null) {
			first = n;
			last = n;
			size++;
			return true;
		} else if (node == null) {
			return false;
		} else if (node == first) {
			n.next = node;
			n.prev = null;
			node.prev = n;
			first = n;
			size++;
			return true;
		} else {
			n.next = node;
			n.prev = node.prev;
			node.prev.next = n;
			node.prev = n;
			size++;
			return true;
		}
	}
	
	public void printList(Node node) {
		System.out.println("Size = " + size);
		printListRec(node);
		System.out.println("\n\n");
	}
	
	public void printListRec(Node node) {
		Node n = node;
		if (n == null) {
			return;
		}
		System.out.print(n.data + " ");
		printListRec(n.next);
	}
	// prints the contents of the linked list starting from node
	// removes the first node that contains data
	// updates the size (when removal occurs)
	
	public void remove(int data) {
		removeRec(first, data);
	}
	
	public void removeRec(Node startNode, int data) {
		Node n = startNode;
		if (first.data == data) {
			removeFirst();
			return;
		} else if (n.data == data) {
			if (n.next == null) {
				n.prev.next = null;
				size--;
				return;
			} else {
				n.prev.next = n.next;
				n.next.prev = n.prev;
				size--;
				return;
			}
		} else if (n.next != null) {
			removeRec(n.next, data);
		}
	}
	
	public void removeReverse(int data) {
		removeReverseRec(last, data);
	}
	
	public void removeReverseRec(Node startNode, int data) {
		Node n = startNode;
		if (last.data == data) {
			removeLast();
			return;
		} else if (n.data == data) {
			if (n.prev == null) {
				n.next.prev = null;
				size--;
				return;
			} else {
				n.prev.next = n.next;
				n.next.prev = n.prev;
				size--;
				return;
			}
		} else if (n.prev != null) {
			removeReverseRec(n.prev, data);
		}
	}
	
	public boolean search(int data) {
		return searchRec(first, data);
	}
	
	public boolean searchRec(Node startNode, int data) {
		Node n = startNode;
		if (first.data == data) {
			return true;
		} else if (n.data == data) {
			return true;
		} else if (n.next != null) {
			return searchRec(n.next, data);
		}
		
		return false;
	}
	// returns true if node is found, otherwise returns false

	public boolean searchReverse(int data) {
		return searchReverseRec(last, data);
	}
	
	public boolean searchReverseRec(Node startNode, int data) {
		Node n = startNode;
		if (last.data == data) {
			return true;
		} else if (n.data == data) {
			return true;
		} else if (n.prev != null) {
			return searchReverseRec(n.prev, data);
		}
		
		return false;
	}
	// returns the first node that contains data, otherwise returns null
	public Node find(int data) {
		return findRec(first, data);
	}
	
	public Node findRec(Node startNode, int data) {
		Node n = startNode;
		if (first.data == data) {
			return first;
		} else if (n.data == data) {
			return n;
		} else if (n.next != null) {
			return findRec(n.next, data);
		}
		
		return null;
	}
	
	public Node findReverse(int data) {
		return findReverseRec(last, data);
	}

	public Node findReverseRec(Node startNode, int data) {
		Node n = startNode;
		if (last.data == data) {
			return last;
		} else if (n.data == data) {
			return n;
		} else if (n.prev != null) {
			return findReverseRec(n.prev, data);
		}
		
		return null;
	}
	
	// removes the first node of the linked list
	// update size (when removal occurs)
	public void removeFirst() {
		if (size == 1) {
			first = null;
			last = null;
			size--;
		} else if (size > 1) {
			Node n = first.next;
			first = null;
			n.prev = null;
			first = n;
			size--;
		}
	}
	// removes the last node of the linked list
	// update size (when removal occurs)
	public void removeLast() {
		if (size == 1) {
			first = null;
			last = null;
			size--;
		} else if (size > 1) {
			Node n = last.prev;
			last = null;
			n.next = null;
			last = n;
			size--;
		}
	}

	public static void main(String[] args) {          
		// TODO Auto-generated method stub
		DLLv3 dll = new DLLv3();

		dll.push(7);
		dll.push(9);
		dll.push(6);
		dll.push(9);
		dll.push(7);
		dll.printList(dll.first);
		dll.printList(dll.findReverse(9));
		
		System.out.println(dll.search(0));
		
	}

}
