
public class BinaryTree<T extends Number & Comparable<? super T>> {
	
	// Members
	T value;
	BinaryTree<T> right;
	BinaryTree<T> left;
	BinaryTree<T> parent;
	// Constructors
	BinaryTree(T val) {
		value = val;
		right = null;
		left = null;
		parent = null;
	}
	
	boolean add(T val) {
		return addRec(val, this);
	}
	
	// Returns false if val is found
	// Otherwise add val (as a node) to the tree and returns true
	boolean addRec(T val, BinaryTree<T> rootParent) {
		BinaryTree<T> newValue = new BinaryTree<T>(val);		
		if (val == rootParent.value) {
			return false;
		} else if (val.compareTo(rootParent.value) > 0){
			if (rootParent.right == null) {
				rootParent.right = newValue;
				rootParent.right.parent = rootParent;
				return true;
			}
			return addRec(val, rootParent.right);
		} else if (val.compareTo(rootParent.value) < 0) {
			if (rootParent.left == null) {
				rootParent.left = newValue;
				rootParent.left.parent = rootParent;
				return true;
			}
			return addRec(val, rootParent.left);
		}
		
		return false;
	}
	
	// Returns true if val is found, otherwise returns false
	boolean search(T val) {
		if (this.value == val) {
			return true;
		} else if (val.compareTo(this.value) > 0 && this.right != null) {
			return right.search(val);
		} else if (val.compareTo(this.value) < 0 && this.left != null) {
			return left.search(val);
		}
		
		return false;
	}
	// Returns the # of branches needed (from root) to reach lowest leaf
	int height() {
		if (this.left != null && this.right == null) {
			return left.height() + 1;
		} else if (this.left == null && this.right != null) {
			return right.height() + 1;
		} else if (this.left != null && this.right != null) {
			return Math.max(left.height() + 1, right.height() + 1);
		}
		
		return 1;
	}
	
	// Returns number of children the given node has
	private int degree(BinaryTree<T> node) {
		int count = 0;
		
		if (node.left != null) {
			count++;
		}
		
		if (node.right != null) {
			count++;
		}
		
		return count;
	}
	// If node has two children return false
	// If node has no children, return false
	// If node has only one child, returns the child node
	private BinaryTree<T> getOnlyChild() {
		if (degree(this) == 1) {
			return this;
		}
		return null;
	}
	// If val is found, remove the node and return true
	// Otherwise return false
	// remove uses helper method removeHelper for the recursion
	boolean remove(T val) {
//		if (this.search(val)) {
			return this.removeHelper(val);
//		}
		
//		return false;
	}
	// Uses recursion to find and remove the node that contains val
	// If val is not found, returns false
	private boolean removeHelper(T val) {
		if (val.equals(this.value)) {
			
			switch (degree(this)) {
				case 0:
					
					if (this.parent != null) {
						
						if (this.parent.right == this) {						
							if (val.equals(this.parent.right.value)) {
								this.parent.right = null;
							}
						} else if (this.parent.left == this) {						
							if (val.equals(this.parent.left.value)) {
								this.parent.left = null;
							}
						}
					}					
					
					break;
				
				case 1:

					BinaryTree<T> pred = null;
					BinaryTree<T> succ = null;
					
					if (this.parent != null) {					
						if (this.left != null) {
							if (this.parent.right == this) {							
								if (val.equals(this.parent.right.value)) {
									this.parent.right = this.left;
								}
							} else if (this.parent.left == this) {							
								if (val.equals(this.parent.left.value)) {
									this.parent.left = this.left;
								}
							}
							this.left.parent = this.parent;
						} else if (this.right != null) {
							if (this.parent.right == this) {							
								if (val.equals(this.parent.right.value)) {
									this.parent.right = this.right;
								}
							} else if (this.parent.left == this) {		
								if (val.equals(this.parent.left.value)) {
									this.parent.left = this.right;
								}
							}
							this.right.parent = this.parent;
						}
					} else {
						if (this.left != null) {
							pred = this.getInorderPredecessor();
							
							if (pred.parent.right == pred) {
								pred.parent.right = null;
							} else if (pred.parent.left == pred) {
								pred.parent.left = null;
							}
							
							this.value = pred.value;
//							this.left = null;
							
//							this.left.parent = null;
						} else if (this.right != null) {
							succ = this.getInorderSuccessor();
							
							if (succ.parent.right == succ) {
								succ.parent.right = null;
							} else if (succ.parent.left == succ) {
								succ.parent.left = null;
							}
							
//							this.right.parent;
							this.value = succ.value;
//							this.right= null;
							

						}
					}
					
					break;
				
				case 2:
					
					BinaryTree<T> currentTree = null;
					currentTree = this.getInorderPredecessor();
					
					if (currentTree.parent.right == currentTree) {
						currentTree.parent.right = null;
					} else if (currentTree.parent.left == currentTree) {
						currentTree.parent.left = null;
					}
					
					
					if (this.parent != null) {
						if (this.parent.left == this) {
							this.parent.left = currentTree;
						} else if (this.parent.right == this) {
							this.parent.right = currentTree;
						}						
						currentTree.parent = this.parent;
					}
					if (this.left != null && this.right != null) {
						currentTree.left = this.left;
						currentTree.right = this.right;
						this.left.parent = currentTree;
						this.right.parent = currentTree;
					} else if (this.right != null && this.left == null) {
						currentTree.right = this.right;
						this.right.parent = currentTree;
					} else if (this.left != null && this.right == null) {
						currentTree.left = this.left;
						this.left.parent = currentTree;
					}
					
					if (this.parent == null) {
						this.value = currentTree.value;
					}
					
					break;
					
				default:
					break;
			}
			
			
			return true;
		} else if (val.compareTo(this.value) > 0 && this.right != null) {
			return right.removeHelper(val);
		} else if (val.compareTo(this.value) < 0 && this.left != null) {
			return left.removeHelper(val);
		}
		
		return false;
	}
	// Returns node with the largest value that is also
	// smaller than or equal to the value of the calling node
	// This method returns null if left node is null
	// Otherwise it returns left.getInOrderPredecessorHelper()
	BinaryTree<T> getInorderPredecessor() {
		if (this.left != null) {
			return left.getInorderPredecessorHelper();
		} else if (this.right != null) {
			return getInorderSuccessor();
		}
		return null;
	}

	// Returns the largest node it can find by recursively calling
	// itself using the right node
	BinaryTree<T> getInorderPredecessorHelper() {
		if (this.right != null) {
			return right.getInorderPredecessorHelper();
		}
		return this;
	}
	
	BinaryTree<T> getInorderSuccessor() {
		if (this.right != null) {
			return right.getInorderSuccessorHelper();
		} else if (this.right != null) {
			return getInorderPredecessor();
		}
		return null;
	}

	// Returns the largest node it can find by recursively calling
	// itself using the right node
	BinaryTree<T> getInorderSuccessorHelper() {
		if (this.left != null) {
			return left.getInorderSuccessorHelper();
		}
		return this;
	}
	
	// Traverses and prints the binary tree in Preorder by calling the
	// recursive method printPreoderHelper and then prints a newline
	void printPreorder() {
		System.out.print(this.value + " ");
		printPreorderHelper();
		System.out.print("\n");
	}
	
	// Prints the value of the node and then calls itself with the
	// left node (if it’s not null) and then calls itself with the
	// right node (if it’s not null)
	private void printPreorderHelper() {
		if (left != null) {
			System.out.print(left.value + " ");
			left.printPreorderHelper();
		}
		if (right != null) {
			System.out.print(right.value + " ");
			right.printPreorderHelper();
		}
	}
	
	// Traverses and prints the binary tree in Inorder by calling the
	// recursive method printInoderHelper and then prints a newline
	
	void printInorder() {
		printInorderHelper();
		System.out.println("\n");
	}
	
	// Calls itself with the left node (if it’s not null) and then
	// prints the value of the node and then finally calls itself with
	// the right node (if it’s not null)
	
	private void printInorderHelper() {
		if (left != null) {
			this.left.printInorderHelper();
		}
		System.out.print(this.value + " ");
		if (right != null) {
			this.right.printInorderHelper();
		}
	}
	
	// Traverses and prints the binary tree in Postorder by calling the
	// recursive method printPostoderHelper and then prints a newline
	void printPostorder() {
		printPostorderHelper();
		System.out.print(this.value + " \n");
	}
	// Calls itself with the left node (if it’s not null) and then
	// calls itself with the right node (if it’s not null) and finally
	// prints the value of the node
	private void printPostorderHelper() {
		if (left != null) {
			left.printPostorderHelper();
			System.out.print(left.value + " ");
		}
		if (right != null) {
			right.printPostorderHelper();
			System.out.print(right.value + " ");
		}
	}
	
	int compareTo(T val) {
		
		return -1;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		BinaryTree<Integer> bt = new BinaryTree<Integer>(88);

		bt.add(444);

		bt.add(333);

		bt.add(1111);

		bt.add(1033);

		bt.add(1000);

		bt.add(1100);

		bt.add(2000);

		bt.add(1900);

		bt.add(2020);

		bt.add(55);

		bt.add(49);

		bt.add(77);

		bt.add(83);

		bt.add(82);

		bt.add(61);

	

		bt.printInorder();	

		System.out.println(bt.remove(77));

		bt.printInorder();

		System.out.println(bt.remove(1111));

		bt.printInorder();

		System.out.println(bt.remove(444));

		bt.printInorder();

		System.out.println(bt.remove(444));

		bt.printInorder();

		System.out.println(bt.remove(444));

		bt.printInorder();
		
//		BinaryTreeBinaryTree bt = new BinaryTree(10);
//		bt.add(9);
//		bt.add(8);
//		bt.add(7);
//		bt.add(6);
//		bt.add(5);
//		bt.add(6);
//		bt.add(15);
//		bt.add(14);
//		bt.add(25);
//		bt.add(4);
//		bt.add(2);
//		bt.add(7);
//		bt.add(8);
		
//		bt.printInorder();
//		bt.remove(25);
//
//		bt.printInorder();
		
//		System.out.println("\n" + bt.left.getInorderSuccessor().value);

	}

}