package Tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class BST<K extends Comparable <K>> implements Iterable<K> {

	private class Node {
		private K data;
		private Node left;
		private Node right;
		private Node(K data) {
			this.data = data;
			left = null;
			right = null;
		}
	}

	private Node root;
	private int size = 0;

	public BST() {
		root = null;
	}

	public BST(Node root) {
		this.root = root;
	}
	// O(log(n)) recursive insert algorithm by comparing the keys
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

	// Using an helper method to insert a data into the Tree.
	public BST<K> insert(K data){
		root = insertHelper(root, data); // pass in old root to add new node into it, now reassign root to its modified form
		return this;
	}

	// O(log(n)) recursive search algorithm by comparing the keys
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

	// Using an helper method to search a data into the Tree.
	public boolean search(K data){
		return searchHelper(root, data);
	}

	// catches and returns the max key in the present subTree
	// 
	private K max(Node root) {
		K result = null;
		if (root.right != null) {
			max(root.right);
		}else {
			result = root.data;
		}
		return result;
	}

	// catches and returns the min key in the present subTree
	private K min(Node root) {
		K result = null;
		if (root.left != null) {
			min(root.left);
		}else {
			result = root.data;
		}
		return result;
	}

	// https://www.geeksforgeeks.org/binary-search-tree-set-2-delete/
	// This one has a much simpler way to delete a data
	private Node deleteHelper(Node root, K data) {
		int compare = data.compareTo(root.data);
		if (compare < 0) {
			root.left = deleteHelper(root.left, data);
		}else if (compare > 0) {
			root.right = deleteHelper(root.right, data);
		}else {
			if (root.left == null && root.right == null) {
				root = null;
				size--;
			}else if (root.left == null && root.right != null) {
				/*K minRight = null;
				minRight = min(root.right);
				root.data = minRight;
				root.right = deleteHelper(root.right, minRight);*/
				return root.right; // both this preferred strategy and the one on top works
			}else if (root.left != null && root.right == null) {
				/*K maxLeft = null;
				maxLeft = max(root.left);
				root.data = maxLeft;
				root.left = deleteHelper(root.left, maxLeft);*/
				return root.left; // both this strategy and the one on top works
			}else { // I could have used 1 else if and 1 else, but this is for clarity
				K maxLeft = null;
				maxLeft = max(root.left);
				root.data = maxLeft;
				root.left = deleteHelper(root.left, maxLeft);
			}
		}
		return root;
	}

	public BST<K> delete(K data){
		if (this.search(data)) {
			root = deleteHelper(root, data);
			return this;
		}else {
			return this;
		}
	}

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

	public void postOrder() {
		postOrderHelper(root);
		System.out.println();
	}

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
        System.out.println(tree.size());
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
