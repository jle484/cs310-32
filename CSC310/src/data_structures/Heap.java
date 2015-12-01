package data_structures;

public class Heap<E> {
	
	public E[] heapSort(E[] list) {
		E[] n = list;
		int heapSize = 1;
		int i, index, parent;
		E newAdd = null, top;
		int larger, left, right;
		int len = n.length;
		
		try {
			//Build heap
			for (i = 0; i < len; i++) {
				index = heapSize - 1;
				newAdd = n[index];
				parent = (index - 1) >> 1;
				E pNode;
				if (parent < 0)
					pNode = n[index];
				else
					pNode = n[parent];
				while (index > 0 && ((Comparable<E>)pNode).compareTo((E)newAdd) < 0) {
					n[index] = n[parent];
					index = parent;
					parent = (parent - 1) >> 1;
					if (parent >= 0)
						pNode = n[parent];
				}
				n[index] = newAdd;
				heapSize++;
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		//empty heap and fill array
		heapSize--;
		for (i = len - 1; i >= 0; i--) {
			E temp = n[0];
			larger = 0;
			index = 0;
			n[0] = n[heapSize - 1];
			top = n[0];
			while (index < heapSize >> 1) {
				left = (index << 1) + 1;
				right = left + 1;
				if (right < heapSize && ((Comparable<E>)n[left]).compareTo((E)n[right]) < 0)
					larger = right;
				else
					larger = left;
				if (((Comparable<E>)top).compareTo((E)n[larger]) > 0)
					break;
				n[index] = n[larger];
				index = larger;
			}
			n[index] = top;
			heapSize--;
			n[i] = temp;
		}
		return n;
	}
}
