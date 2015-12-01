import java.util.Iterator;

import data_structures.BinarySearchTree;


public class BSTtest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BinarySearchTree<String,Integer> bst = new BinarySearchTree<String,Integer>();
		 String test = "S E A R C H E X A M P L E";
	      String[] keys = test.split(" ");
	      int N = keys.length;
	      for (int i = 0; i < N; i++)
	          bst.add(keys[i], i);
	      
	      Iterator<String> kItr = bst.keys();
	      System.out.print("K: ");
	      while (kItr.hasNext()) {
	    	  System.out.print(kItr.next() + " ");
	      }
	      System.out.println();
	      Iterator<Integer> vItr = bst.values();
	      System.out.print("V: ");
	      while (vItr.hasNext()) {
	    	  System.out.print(vItr.next() + " ");
	      }
	      System.out.println("");
	      System.out.println("Contains S: " + bst.contains("S"));
	      System.out.println("Value of key S: " + bst.getValue("S"));
	      System.out.println("Key of value 4: " + bst.getKey(4));
	      bst.delete("S");
	      Iterator<String> kItr2 = bst.keys();
	      while (kItr2.hasNext()) {
	    	  System.out.print(kItr2.next() + " ");
	      }
	      
	}

}
