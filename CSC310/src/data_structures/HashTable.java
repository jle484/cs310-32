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
	private LinearList<DictionaryNode<K,V>> [] list;
	
	public HashTable(int n) {
		currSize = 0;
		maxSize = n;
		modCounter = 0;
		tableSize = (int)(maxSize * 1.3f);
		list = new LinearList[tableSize];
		for(int i = 0; i < tableSize; i++)
			list[i] = new LinearList<DictionaryNode<K,V>>();
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
		if (isFull())
			return false;
		LinearList<DictionaryNode<K,V>> hashBin = list[getListIndex(key)];
		DictionaryNode<K,V> newNode = new DictionaryNode<K,V>(key, value);
		if (hashBin.contains(newNode) && hashBin.find(newNode).equals(newNode))
			return false;
		list[getListIndex(key)].addFirst(new DictionaryNode<K,V>(key,value));
		currSize++;
		modCounter++;
		return true;
	}

	public boolean delete(K key) {
		if (isEmpty())
			return false;
		if (contains(key)) {
			int listIndex = getListIndex(key); 
			list[listIndex].removeFirst();
			currSize--;
			modCounter++;
			return true;
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
		if (isEmpty())
			return null;
		DictionaryNode<K,V> temp = list[getListIndex(key)].find((new DictionaryNode<K,V>(key, null)));
		if (temp == null)
			return null;
		return temp.value;
	}
	/**
	   * Returns the key associated with the first instance of an object possessing
	   * the indicated value. Uses .compareTo. This operation will require O(n).
	   * 
	   * @param value
	   * @return
	   */
	public K getKey(V value) {
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
			Heap hs = new Heap();
			nodes = (DictionaryNode<K,V>[]) hs.heapSort(nodes); 
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
