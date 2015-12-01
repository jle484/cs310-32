package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;

public class HashTable<K,V> implements DictionaryADT<K,V>{
	private int currSize;
	private int maxSize;
	private int tableSize;
	private long modCounter;
	private final double LOAD_FACTOR = 0.75;
	private LinearList<DictionaryNode<K,V>> [] list;
	
	public HashTable() {
		currSize = 0;
		maxSize = 11;
		modCounter = 0;
		createTable();
	}
	
	private void createTable() {
		tableSize = (int)(maxSize * 1.3f);
		if (isEmpty()) {
			list = new LinearList[tableSize];
			for(int i = 0; i < tableSize; i++)
				list[i] = new LinearList<DictionaryNode<K,V>>();
		}
		else {
			int newSize = 0;
			LinearList<DictionaryNode<K,V>> [] tempList = new LinearList[tableSize];
			for(int i = 0; i < tableSize; i++) {
				tempList[i] = new LinearList<DictionaryNode<K,V>>();
			}
			try {
				for(int j = 0; j < list.length; j++) {
					DictionaryNode<K,V> tempNode = list[j].peekFirst();
					if (tempNode != null) {
						K tempKey = tempNode.key;
						int newIndex = getListIndex(tempKey);
						tempList[newIndex].addFirst(tempNode);
						newSize++;
					}
				}
			}
			catch (Exception e) {
				System.out.println("Error in Create Table");
				System.out.println("Error is " + e.getMessage());
			}
			list = tempList;
			currSize = newSize;
			modCounter++;
		}	
	}
	
	private int getListIndex(K key) {
		return ( key.hashCode() & 0x7FFFFFFF ) % tableSize;
	}
	
	public boolean contains(K key) {
		if (isEmpty())
			return false;
		return list[getListIndex(key)].contains(new DictionaryNode<K,V>(key, null));
	}

	public boolean add(K key, V value) {
		try {
			if (isFull())
				return false;
			int hashIndex = getListIndex(key);
			LinearList<DictionaryNode<K,V>> hashBin = list[hashIndex];
			DictionaryNode<K,V> newNode = new DictionaryNode<K,V>(key, value);
			hashBin.addFirst(newNode);
			currSize++;
			modCounter++;
			double currLoadFactor = ((double) currSize) / tableSize;
			if (currLoadFactor > LOAD_FACTOR) {
				maxSize = currSize * 2;
				createTable();	
			}
		}
		catch (Exception e) {
			System.out.println("Error in add");
			System.out.println("Error is " + e.getMessage());
		}
		return true;
	}

	public boolean delete(K key) {
		try {
			if (isEmpty())
				return false;
			if (contains(key)) {
				int listIndex = getListIndex(key); 
				list[listIndex].removeFirst();
				currSize--;
				modCounter++;
				return true;
			}
		}
		catch (Exception e) {
			System.out.println("Error in Delete");
			System.out.println("Error is " + e.getMessage());
		}
		return false;
	}
	/**
	   * Returns the value associated with the parameter key. This operation must
	   * complete in O(log n).
	   * 
	   * @param key
	   *          identifier for item to retrieve
	   * @return contents of value or null if not found
	   */
	public V getValue(K key) {
		try {
			if (isEmpty())
				return null;
			int hashIndex = getListIndex(key);
			DictionaryNode<K,V> targetNode = (new DictionaryNode<K,V>(key, null));
			DictionaryNode<K,V> temp = list[hashIndex].find(targetNode);
			if (temp == null)
				return null;
			return temp.value;
		}
		catch (Exception e) {
			System.out.println("Error in getValue");
			System.out.println("Error is " + e.getMessage());
		}
		return null;
	}
	/**
	   * Returns the key associated with the first instance of an object possessing
	   * the indicated value. Uses .compareTo. This operation will require O(n).
	   * 
	   * @param value
	   * @return
	   */
	public K getKey(V value) {
		try {
			if (isEmpty())
				return null;
			// v counter
			int vcounter = 0;
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
			return currKey;
		}
		catch (Exception e) {
			System.out.println("Error in getKey");
			System.out.println("Error is " + e.getMessage());
		}
		return null;
	}
	/**
	   * Indicates quantity of entries in the dictionary.
	   * guacamoli 
	   * @return number of items in the dictionary
	   */
	public int size() {
		return currSize;
	}
	/**
	   * Indicates if the data structure has reached capacity.
	   * 
	   * @return true if at capacity, or false otherwise
	   */
	public boolean isFull() {
		return currSize == maxSize;
	}
	/**
	   * Indicates the data structure is empty.
	   * 
	   * @return true if empty, or false otherwise
	   */
	public boolean isEmpty() {
		return currSize == 0;
	}

	public void clear() {
		for (int i = 0; i < list.length; i++)
			list[i].clear();
		currSize = 0;
		modCounter = 0;
		maxSize = 11;
	}

	public Iterator<K> keys() {
		return new KeyIteratorHelper();
	}

	public Iterator<V> values() {
		return new ValueIteratorHelper();
	}
	
	abstract class IteratorHelper<E> implements Iterator<E> {
		private DictionaryNode[] nodes;
		private int index;
		private long modCheck;
		public IteratorHelper() {
			nodes = new DictionaryNode[currSize];
			index = 0;
			int j = 0;
			modCheck = modCounter;
			for (int i = 0; i < tableSize; i++) {
				for (DictionaryNode n: list[i])
					nodes[j++] = n;
			}
			try {
				Heap<DictionaryNode<K,V>> hs = new Heap<DictionaryNode<K,V>>();
				nodes = hs.heapSort(nodes);
			}
			catch (Exception e) {
				System.err.println("dasdsad");
			}
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
			return (K) super.nodes[super.index++].key;
		}
	}
	
	protected class ValueIteratorHelper<V> extends IteratorHelper<V> {
		public ValueIteratorHelper() {
			super();
		}
		public V next() {
			return (V) super.nodes[super.index++].value;
		}
	}

	protected class DictionaryNode<K,V> implements Comparable<DictionaryNode<K,V>> {
		K key;
		V value;
		public DictionaryNode(K k, V v) {
			key = k;
			value = v;
		}
		public int compareTo(DictionaryNode<K, V> node) {
			return ((Comparable<K>)key).compareTo((K)node.key);
		}

	}
}
