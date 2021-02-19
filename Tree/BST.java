package Tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @since 2021. DETAILS.....
 * a generic (any datatype K - like another comparable data structure like an array) 
 * Binary Search Tree for searching, computational data storage
 * and an add-on of collecting the data if needed into a list.
 * It's collection is iterable. 
 * https://www.geeksforgeeks.org/binary-search-tree-set-2-delete/
 * @version 1.0
 * @author David Nathaniel (Ambassador of Christ)
 */
 
public class BST<K extends Comparable <K>> implements Iterable<K> {
	/**
	 */

	private class Node {
		private K data;
		private Node left;
		private Node right;
		private Node(K data) {
			this.data = data;
			left = right = null;
		}
	}
	/**
	 * @param root of the tree
	 * @param size
	 * All methods (insert, search, collection, traversals, and deletion)
	 * are implemented using recursion. All are in runtime of O(h) where h is the height of the tree.
	 */
	private Node root;
	private int size = 0;

	/**
	 * Default Constructor. Root is empty.
	 */
	public BST() {
		root = null;
	}


	/**
	 *	A copy constructor. points to an exist BST object
	 *	for collecting data over that BST or iterating over
	 *	the collection of data. 
	 *	@param root -- root of the BST
	 */
	public BST(Node root) {
		this.root = root;
	}
	// O(h) where h is the height of the tree. recursive insert algorithm by comparing the keys
	private Node insertHelper(Node root, K data) {
		if (root == null) {
			size++;
			return new Node(data);
		}else if (data.compareTo(root.data) < 0) {
			root.left = insertHelper(root.left, data); // return a new node to the left subtree
		}else if (data.compareTo(root.data) > 0) {
			root.right = insertHelper(root.right, data); // return a new node to the right subtree
		}
		return root;
	}

	/**Insert a data into the BinarySearchTree.
	 * Pass in old root to add new data into it.
	 * Now reassign root to its modified form
	 * @param data -- generic (any datatype) in the BST
	 * @return a BST
	 */
	public BST<K> insert(K data){
		root = insertHelper(root, data);
		return this;
	}

	// O(h) recursive search algorithm by comparing the keys
	private boolean searchHelper(Node root, K data) {
		if (root == null) {
			return false;
		}else if (data.compareTo(root.data) == 0) {
			return true;
		}else if (data.compareTo(root.data) < 0) {
			return searchHelper(root.left, data);
		}else {
			return searchHelper(root.right, data);
		}
	}

	/**search if a data is in the BST.
	 * returns true if present or false otherwise.
	 * @param data -- generic (any datatype) in the BST
	 * @return true or false
	 */
	public boolean search(K data){
		return searchHelper(root, data);
	}

	// catches and returns the max data in the present subTree
	private K max(Node root) {
		return root.right != null ? max(root.right) : root.data;
	}

	// catches and returns the min data in the present subTree
	private K min(Node root) {
		return root.left != null ? min(root.left) : root.data;
	}

	// This one has a much simpler way to delete a data
	private Node deleteHelper(Node root, K data) {
		int compare = data.compareTo(root.data);
		if (compare < 0) {
			root.left = deleteHelper(root.left, data);
		}else if (compare > 0) {
			root.right = deleteHelper(root.right, data);
		}else { // else you found the data to be deleted.
			if (root.left == null && root.right == null) {
				root = null;
				size--;
			}else if (root.left == null) {
				/*K minRight = null;
				minRight = min(root.right);
				root.data = minRight;
				root.right = deleteHelper(root.right, minRight);*/
				size--;
				return root.right; // both this preferred strategy and the one on top works
			}else if (root.right == null) {
				/*K maxLeft = null;
				maxLeft = max(root.left);
				root.data = maxLeft;
				root.left = deleteHelper(root.left, maxLeft);*/
				size--;
				return root.left; // both this strategy and the one on top works
			}else { 
				K maxLeft = null;
				maxLeft = max(root.left);
				root.data = maxLeft;
				root.left = deleteHelper(root.left, maxLeft);
			}
		}
		return root;
	}

/** Removes a data that is not needed
 * 	The data is comparable
 * @param data -- generic (any datatype) in the BST
 * 
 * @return a BST
 */
	public BST<K> delete(K data){
		if (this.search(data)) {
			root = deleteHelper(root, data);
			return this;
		}else {
			return this;
		}
	}
/**
 * Size of the BST
 * @return integer (the number of nodes in the BST).
 */
	public int size() {
		return size;
	}

	private void addKeysToCollection(Collection<K> c, Node root) {
		if (root != null) {
			addKeysToCollection(c, root.left);
			c.add(root.data);
			addKeysToCollection(c, root.right);
		}
	}
	
/**Collects the data in the BST.
 * Uses a recursive method to get all data.
 * @return an ArrayList of data in BST.
 */
	
	public ArrayList<K> collection() {
		ArrayList<K> park = new ArrayList<> ();
		addKeysToCollection(park, root);
		return park;
	}

	/*public String toString() {
		return "-->" + root.data;
	}*/

	private void inOrderHelper(Node root) {
		if(root != null) {
			inOrderHelper(root.left);
			System.out.print(root.data + " ");
			inOrderHelper(root.right);
		}
	}
/**
 * Basic inOrder traversal of a BST
 * prints them in the natural order.
 * prints them left, root, and right subtree.
 */
	public void inOrder() {
		inOrderHelper(root);
		System.out.println();
	}

	private void preOrderHelper(Node root) {
		if(root != null) {
			System.out.print(root.data + " ");
			preOrderHelper(root.left);
			preOrderHelper(root.right);
		}
	}
/**
* Basic preOrder traversal of a BST
* prints them root, left, and right subtree.
**/
	public void preOrder() {
		preOrderHelper(root);
		System.out.println();
	}

	private void postOrderHelper(Node root) {
		if(root != null) {
			postOrderHelper(root.left);
			postOrderHelper(root.right);
			System.out.print(root.data + " ");
		}
	}
/**
 * Basic postOrder traversal of a BST
 * prints them left, right subtree, then the root
 */
	public void postOrder() {
		postOrderHelper(root);
		System.out.println();
	}
/**
 * Select the order that you want the BST to be traversed.
 * @param in -- (input string)
 * "in" - for inOrder traversal.
 * "pre" - for preOrder traversal.
 * Else a postOrder traversal.
 */
	public void selectOrder(String in) {
		if(in.equals("in")) {
			this.inOrder();
		}else if(in.equals("pre")) {
			this.preOrder();
		}else {
			this.postOrder();
		}
	}

	public static void main(String[] args) {
		BST<Integer> tree = new BST<> ();
		tree.insert(50); 
		tree.insert(30); 
		tree.insert(20); 
		tree.insert(40); 
		tree.insert(70); 
		tree.insert(60); 
		tree.insert(80); 

		tree.inOrder();

		//		System.out.println(tree.search(80));
		//		System.out.println(tree.size());
		System.out.println();
		tree.delete(70);
		tree.inOrder();
		System.out.println("deleted 70, size is now " +tree.size());
		
		tree.delete(60);
		tree.inOrder();
		System.out.println("deleted 60, size is now " +tree.size());
		
		System.out.println();
		tree.postOrder();
		//
		//		System.out.println();
		//		tree.preOrder();
		//
		System.out.println();
		tree.selectOrder("pre");
		System.out.println();
		System.out.println(tree.collection());
		Iterator<Integer> it = tree.iterator();
		while (it.hasNext()) {
			System.out.print(it.next() + " ");
		}
		System.out.println();

		tree.delete(60);
		tree.selectOrder("in");
	}

/**
 * Returns an iterator over the collection of BST's data.
 * hasNext() - If there is more data from the data collection, then return true. Else false.
 * next() - returns the data.
 * @throws NoSuchElementException -- cannot remove a nonexisting data.
 */
	@Override
	public Iterator<K> iterator() {
		// TODO Auto-generated method stub
		return new Iterator<K>() {
			ArrayList<K> list = new BST<K> (root).collection();
			int pointer = 0;
			@Override
			public boolean hasNext() {
				if(pointer < list.size()) {
					return true;
				}else {
					return false;
				}
			}

			@Override
			public K next() {
				if(hasNext()) {
					pointer++;
					return list.get(pointer - 1);
				}else {
					throw new NoSuchElementException();
				}
			}

		};
	}

}
