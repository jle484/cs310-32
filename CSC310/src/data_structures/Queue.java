/*  Josephine Le
    cssc0032
*/
package data_structures;

import java.util.Iterator;

public class Queue<E> {
	private LinearList<E> queueList;
	
	public Queue() {
		 queueList = new LinearList<E>();
	}
	
	// inserts the object obj into the queue
    public void enqueue(E obj) {
    	queueList.addLast(obj);
    }
     
    // removes and returns the object at the front of the queue   
    public E dequeue() {
		return queueList.removeFirst();
    	
    }
    
    // returns the number of objects currently in the queue    
    public int size() {
		return queueList.size();
    	
    }
    
    // returns true if the queue is empty, otherwise false   
    public boolean isEmpty() {
		return queueList.isEmpty();
    }
    
    // returns but does not remove the object at the front of the queue   
    public E peek() {
		return queueList.peekFirst();
    	
    }
    
    // returns true if the Object obj is in the queue    
    public boolean contains(E obj) {
		return queueList.contains(obj);
    	
    }
     
    // returns the queue to an empty state  
    public void makeEmpty() {
    	queueList.clear();
    }
    
    // removes the Object obj if it is in the queue and
    // returns true, otherwise returns false.
    public boolean remove(E obj) {
    	return (queueList.remove(obj) !=  null);
    }
    
    // returns an iterator of the elements in the queue.  The elements
    // must be in the same sequence as dequeue would return them.    
    public Iterator<E> iterator() {
    	Iterator<E> iter = queueList.iterator();
    	return iter;
    }

}
