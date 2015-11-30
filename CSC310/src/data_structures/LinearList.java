/*  Josephine Le
    cssc0032
*/
package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Comparator;

public class LinearList<E> implements LinearListADT<E> {

	private int currSize;
	private long modCounter;
	protected Node<E> head, tail;
	protected Comparator<E> comparator;
	
	public LinearList() {
		currSize = 0;
		modCounter = 0;
	}
	
	public boolean addFirst(E obj) {
		if (!isFull()) {
			Node<E> newNode = new Node<E>(obj);
			if (head == null) {
				head = tail = newNode;
			}
			else {
				//Set old head as new Node's next 
				newNode.setNext(head);
				//set new node as old head's previous 
				head.setPrev(newNode);
				//set new Node as head 
				head = newNode;
			}
			modCounter++;
			currSize++;
			return true;
		}
		return false;
	}

	public boolean addLast(E obj) {
		if (!isFull()) {
			Node<E> newNode = new Node<E>(obj);
			//if list is empty, set head and tail to the new node
			if (head == null) {
				head = tail = newNode;
			}
			else {
				//old tail is node before new node (3P)
				newNode.setPrev(tail);
				//set old tail's next as new node (2N)
				tail.setNext(newNode);
				//new node is now the tail
				tail = newNode;
			}
			currSize++;
			modCounter++;
			return true;
		}
		return false;
	}

	public E removeFirst() {
		if(isEmpty()) 
			return null;
		E tempValue = null;
			
		if (head != null)
		{
			Node<E> tempNode = head;
			if (!head.equals(tail)) {
				head = head.getNext();
				head.setPrev(tail);
				tempValue = tempNode.getData();
			}
			else {
				clear();
				tempValue = tempNode.getData();
				return tempValue;
			}
		}
		modCounter++;
		currSize--;
		return tempValue;
	}

	public E removeLast() {
		if(isEmpty()) 
			return null;
		E tempValue = null;
		if (tail != null)
		{
			Node<E> tempNode = tail;
			if (!head.equals(tail)) {
				tail = tail.getPrev();
				tail.setNext(head);
				tempValue = tempNode.getData();
			}
			else {
				clear();
				tempValue = tempNode.getData();
				return tempValue;
			}
		}
		modCounter++;
		currSize--;
		return tempValue;
	}

	public E remove(E obj) {
		if(isEmpty()) 
			return null;
		E tempValue = null;
		if (obj != null) {
			Iterator<E> iter = iterator();
			Node<E> currNode = head;
			while (iter.hasNext())
			{
				E aryObj = iter.next();
				if (((Comparable<E>)aryObj).compareTo(obj) == 0)
				{
					break;
				}
				currNode = currNode.getNext();
			}
			tempValue = currNode.getData();
			Node<E> prevNodeTemp = currNode.getPrev();
			Node<E> nextNodeTemp = currNode.getNext();
			prevNodeTemp.setNext(nextNodeTemp);
			nextNodeTemp.setPrev(prevNodeTemp);
			modCounter++;
			currSize--;
			return tempValue;
		}
		return null;
	}

	public E peekFirst() {
		if (isEmpty()) 
			return null;
		return head.getData();
	}

	
	public E peekLast() {
		if (isEmpty()) 
			return null;
		return tail.getData();
	}

	
	public boolean contains(E obj) {
		if (this.find(obj) != null)
			return true;
		return false;
	}

	
	public E find(E obj) {
		Iterator<E> iter = iterator();
		Node<E> currNode = head;
		while (iter.hasNext())
		{
			E aryObj = iter.next();
			if (((Comparable<E>)aryObj).compareTo(obj) == 0)
				return currNode.getData();
			currNode = currNode.getNext();
		}
		return null;
	}

	
	public void clear() {
		currSize = 0;
		modCounter = 0;
		head = null;
		tail = null;
	}

	
	public boolean isEmpty() {
		return (currSize == 0);
	}

	
	public boolean isFull() {
		return false;
	}

	
	public int size() {
		return currSize;
	}
	
	
	public Iterator<E> iterator() {
		return new IteratorHelper();
	}
	
	protected class IteratorHelper implements Iterator<E>
	{
		protected long stateCheck;
		protected Node<E> currentNode;
		
		public IteratorHelper() {
			stateCheck = modCounter;
			currentNode = head;
		}

		public boolean hasNext() {
			if (stateCheck != modCounter)
				throw new ConcurrentModificationException("hasNext is null");
			return currentNode != null;
		}

		
		public E next() {
			if (!hasNext())
				throw new NoSuchElementException("next is null");
			E nextNodeValue = currentNode.getData();
			currentNode = currentNode.getNext();
			return nextNodeValue;
		}
		
		
		public void remove() {
		}	
	}
	
	protected class Node<T> {
		protected T data;
		protected Node<T> next, prev;
		
		public Node(T value) {
			data = value;
			next = null;
			prev = null;
		}
		
		public T getData() {
			return data;
		}
		
		public Node<T> getPrev() {
			return prev;
		}
		
		public Node<T> getNext() {
			return next;
		}
		
		public void setData(T newValue) {
			data = newValue; 
		}
		
		public void setPrev(Node<T> newPrev) {
			prev = newPrev;
		}
		
		public void setNext(Node<T> newNext) {
			next = newNext;
		}

	}

}
