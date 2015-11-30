package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class BinarySearchTree<K,V> implements DictionaryADT<K,V> {
	protected Node<K,V> root;
	private int currSize;
	protected long modCounter = 0;
	
	public BinarySearchTree() {
		root = null;
		currSize = 0;
	}
	
	public boolean contains(K key) {
		Node currNode = root;
		while(currNode != null){
			if(((Comparable<K>)currNode.key).compareTo((K)key) == 0)
				return true;
			else if(((Comparable<K>)currNode.key).compareTo((K)key) > 0)
				currNode = currNode.leftChild;
			else
				currNode = currNode.rightChild;
		}
		return false;
	}

	public boolean add(K key, V value) {
		Node<K,V> newNode = new Node<K,V>(key, value);
		if (root == null)
			root = newNode;
		else {
			Node curr = root;
			Node parent;
			for (;;) {
				parent = curr;
				if (((Comparable<K>)key).compareTo((K)curr.key) < 0) {
					curr = curr.leftChild;
					if (curr == null) {
						parent.leftChild = newNode;
						currSize++;
						return true;
					}
				} //end if go left
				else {
					curr = curr.rightChild;
					if (curr == null) {
						parent.rightChild = newNode;
						currSize++;
						return true;
					}
				} //end else go right
			} //end loop
		} //end else not root
		currSize++;
		modCounter++;
		return true;
	}

	public boolean delete(K key) {
		// TODO Auto-generated method stub
		
		return false;
	}

	public V getValue(K key) {
		if (root == null)
			return null;
		
		Node<K,V> curr = root;
		while ( ((Comparable<K>)curr.key).compareTo((K) key) != 0 ) {
			if ( ((Comparable<K>)key).compareTo((K) curr.key) < 0 )
				curr = curr.leftChild;
			else
				curr = curr.rightChild;
			if ( curr == null)
				return null;
		}
		return curr.value;
 	}

	private Node<K,V> inOrder(Node<K,V> node, V value) {
		Node<K,V> temp = null;
		if ( node != null ) {
			inOrder(node.leftChild, value);
			temp = node;
			if (((Comparable<V>)temp.value).compareTo((V)value) == 0)
				return temp;
			inOrder(node.rightChild, value);
		}
		return temp;
	}
	
	public K getKey(V value) {
		if (root == null)
			return null;
		Node<K,V> temp = inOrder(root, value);
		return temp.key;
		// v counter
		/* int vcounter = 0;
		// v iterator
		Iterator<V> vItr = values();
		while (vItr.hasNext()) {
			V currValue = vItr.next();
			// if value = itrValue
			if (((Comparable<V>)value).compareTo((V)currValue) == 0)
				break;
			vcounter++;
		}
		// k iterator
		Iterator<K> kItr = keys();
		K currKey = null;
		for (int i = 0; i <= vcounter; i++) {
			currKey = kItr.next();
		}
		return currKey; */
	}

	public int size() {
		return currSize;
	}

	public boolean isFull() {
		return false;
	}

	public boolean isEmpty() {
		return currSize == 0;
	}
	
	private void doPrint(Node n) {
		if (n == null)
			return;
		doPrint(n.leftChild);
		System.out.println(n.value);
		doPrint(n.rightChild);
	}

	public void clear() {
		// TODO Auto-generated method stub
	}

	public Iterator keys() {
		return new KeyIteratorHelper();
	}

	public Iterator values() {
		return new ValueIteratorHelper();
	}
	
	abstract class IteratorHelper<E> implements Iterator<E> {
		private Node[] itrAry;
		private int index;
		private int itrIndex;
		private long modCheck;
		private Node rootNode;
		
		public IteratorHelper() {
			index = 0;
			itrIndex = 0;
			modCheck = modCounter;
			itrAry = new Node[currSize];
			rootNode = root;
			inorderFillArray(rootNode);
		}
		
		private void inorderFillArray(Node<K,V> n) {
			if (n == null)
				return;
			inorderFillArray(n.leftChild);
			itrAry[itrIndex++] = n;
			inorderFillArray(n.rightChild);
		}

		public boolean hasNext() {
			if (modCheck != modCounter)
				throw new ConcurrentModificationException();
			return index < currSize;
		}

		public abstract E next();

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	protected class KeyIteratorHelper<K> extends IteratorHelper<K> {
		public KeyIteratorHelper() {
			super();
		}
		
		public K next() {
			return (K) super.itrAry[super.index++].key;
		}
	}
	
	protected class ValueIteratorHelper<V> extends IteratorHelper<V> {
		public ValueIteratorHelper() {
			super();
		}
		
		public V next() {
			return (V) super.itrAry[super.index++].value;
		}
	}
	
	protected class Node<K,V> {
		public K key;
		public V value;
		public Node leftChild;
		public Node rightChild;
		
		public Node(K k, V v) {
			key = k;
			value = v;
			leftChild = rightChild = null;
		}
	}

}
