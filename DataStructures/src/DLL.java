public class DLL {
	
	Node first = null;
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
		} else {
			n.next = first;
			first.prev = n;
			first = n;
		}
		size++;
	}
	
	// adds a node at the end of the list and updates the size
	public void append(int new_data) {
		Node node =  first;
		Node n = new Node(new_data);
		
		if (node == null) {
			first = n;
		} else {			
			while (node.next != null) {
				node = node.next;
			}
			n.prev = node;
			node.next = n;
		}
		size++;
	}
	
	// inserts a new node after the given node and updates the size
	public boolean InsertAfter(Node node, int new_data) {
		Node n = new Node(new_data);
		if (first == null) {
			first = n;
			size++;
			return true;
		} else if (node == null) {
			return false;
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
	// prints the contents of the linked list starting from node
	public void printList(Node node) {
		
		Node n = node;

		System.out.println("Size = " + size);
		
		while (n != null) {
			System.out.print(n.data + " ");
			n = n.next;
		}
		
		System.out.println("\n\n");
		
	}
	// removes the first node that contains data
	// updates the size (when removal occurs)
	public void remove(int data) {
		if (first.data == data) {
			removeFirst();
		} else {
			Node n = first;
			while (n.next != null || n.data != data) {
				n = n.next;
			}
			if (n.data == data) {
				if (n.next == null) {
					n.prev.next = null;
				} else {
					n.prev.next = null;
					n.next.prev = null;
				}
				size--;
			}
		}
	}
	// returns true if node is found, otherwise returns false
	public boolean search(int data) {
		if (first.data == data) {
			return true;
		} else {
			Node n = first;
			while (n.next != null) {
				if (n.data == data) {
					break;
				}
				n = n.next;
			}
			if (n.data == data) {
				return true;
			}
		}
		
		return false;
	}
	
	// returns the first node that contains data, otherwise returns null
	public Node find(int data) {
		if (first.data == data) {
			return first;
		} else {
			Node n = first;
			while (n.next != null) {
				if (n.data == data) {
					break;
				}
				n = n.next;
			}
			if (n.data == data) {
				return n;
			}
		}
		
		return null;
	}
	// removes the first node of the linked list
	// update size (when removal occurs)
	public void removeFirst() {
		if (size == 1) {
			first = null;
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
		if (first != null) {
			if (first.next == null) {
				first = null;
				size--;
			} else {
				Node n = first;
				while (n.next != null) {					
					n = n.next;
				}
				n.prev.next = null;
				size--;
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DLL dll = new DLL();
		
		dll.push(5);
		dll.printList(dll.first);
		dll.push(2);
		dll.printList(dll.first);
		dll.append(2);
		dll.printList(dll.first);
		
		dll.remove(2);
		dll.printList(dll.first);
		
		dll.InsertAfter(dll.first, 3);
		
		System.out.println(dll.search(1));

	}

}
