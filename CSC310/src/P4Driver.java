import java.util.Iterator;

import data_structures.DictionaryADT;
import data_structures.HashTable;


public class P4Driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DictionaryADT<Integer, String> dict = new HashTable<Integer, String>(25);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++)
				dict.add(i, Integer.toString(j));
		}
		
		dict.add(7, "value");
		
		String testStr = "adadasdasdasd";
		System.out.println("Hash: " + testStr.hashCode());
		
		Iterator<Integer> kItr = dict.keys();
		Iterator<String> vItr = dict.values();
		System.out.print("Keys: ");
		while (kItr.hasNext()) {
			System.out.print(kItr.next() + " ");
		}
		System.out.println();
		System.out.print("Values: ");
		while (vItr.hasNext()) {
			System.out.print(vItr.next() + " ");
		}
		System.out.println();
		System.out.println("Contains 1: " + dict.contains(1));
		System.out.println("Find Value 1: " + dict.getValue(7));
		System.out.println("Find Key 1: " + dict.getKey("value"));
	}

}
