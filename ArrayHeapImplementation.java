/**
 * ArrayHeapImplementation.java
 * An class for the Max-Heap implementation using an array. 
 * Note that the ordering of
 * elements is determined with the T.compareTo() method.
 * @param <T> the type of data the heap stores.
 * @author Frederik Roenn Stensaeth and Javier Moran.
 */

/** A class for a Max-heap that will take comparable objects and order them. */
public class ArrayHeapImplementation<T extends
    Comparable<? super T>> implements Heap<T> {

    private Integer size;
    private T[] array;

    /** Constructor for the ArrayHeapImplementation.
    * Creates an empty array, and sets size = 0. */ 
    public ArrayHeapImplementation() {
        @SuppressWarnings("unchecked")
          T[] temporary_array = (T[]) new Comparable[16];
          array = temporary_array;
        //Sets size to 0 in order to start counting the amount of items added.
        size = 0;
    }

    /** Adds the given item to the heap. */
    public void add(T item) {
        //Ensures the item is not null.
        //If it is null, it does not add the item.
        if(item != null) {
            //Makes sure the array is of suficient length.
            ensureCapacity();
            //Adds the items to the array in order to start at index 1.
            //This makes the math easier for the rest of the algorithm.
            array[size + 1] = item;
            size++;
            //Debugging statement.
            //System.out.println(size);
            //Makes sure the items are moved to the correct position.
            if(size > 1) {
                bubbleUp();
                //Debugging statement.
                //System.out.println("bubbleUp");
            }
        }
    }
    
    /** Removes and returns the maximum value in the heap.
     * @return null if the heap is empty.
     */
    public T removeRoot() {
        //Debugging statements
        //System.out.println("start");
        // for(int i = 0; i < array.length; i++){
        //     System.out.println(array[i]);
        // }
        //System.out.println("end");
        //System.out.println(size);
        if(size == 0) {
            return null;
        }
        //Removes the root, and moves the last index to the root.
        T root = array[1];
        array[1] = array[size];
        array[size] = null;
        size--;
        //Makes sure the newly moved up item
        //is placed in the correct order.  
        if(!isEmpty()) {
            //Debugging statement
            //System.out.println(array[1]);
            bubbleDown();
        }
        return root;
    }
    
    /** Returns the maximum value, but doesn't change the heap.
     * @return null if the heap is empty.
     */
    public T peek() {
        return array[1];
    }
    
    /** Returns true if the heap is empty. */
    public boolean isEmpty() {
        if(array[1] == null) {
            return true;
        }
        return false;
    }
    
    /** Removes all items from the heap. */
    public void clear() {
        for(int i = 1; i < array.length; i++) {
            array[i] = null;
        }
    }
    /** Ensures the last item in the array is moved up in the heap,
    * to its correct position */ 
    private void bubbleUp() {
        //Takes the lat item in the array.
        T temporary = array[size];
        //Debugging statement
        // System.out.println("temp");
        // System.out.println(temporary);
        int i = size;
        //Keeps comparing the item with its parents.
        //Switches if it is greater.
        //Stops if it is less.
        while(i > 1 && temporary.compareTo(array[i / 2]) > 0) {
            array[i] = array[i / 2];
            i = i / 2;
        }
        if(i == 0) {
            array[1] = temporary;
        } else {
            array[i] = temporary;
        }

    }
    /** Ensures the last item in the array is moved up in the heap,
    * to its correct position */ 
    private void bubbleDown() {
        //Takes the first item in the array.
        T temporary = array[1];
        int i = 2;
        int change = i;
        //Debugging statement
        //while(i + 1 < size) {
            // System.out.println(size);
            // System.out.println(array.length);
            // System.out.println(i);
        //Keeps comparing the item with its children.
        //Switches if it is less than a children.
        //If it is less than both children, switches with greater child.
        //Stops if both children are less. 
        if(size == 2) {
            if(temporary.compareTo(array[2]) < 0) {
                array[1] = array[2];
                array[2] = temporary;
            }
        } else if(size > 2) {
            while((i + 1 <= size) && 
                (temporary.compareTo(array[i]) < 0 ||
                 temporary.compareTo(array[i + 1]) < 0)) {
                //Debugging statement
                // System.out.println("i = " + i);
                // System.out.println(array.length);
                if(array[i].compareTo(array[i + 1]) > 0) {
                    array[i / 2] = array[i];
                } else {
                    array[i / 2] = array[i + 1];
                    i++;
                }
                change = i;
                i *= 2;
            }
            array[change] = temporary;
        } else {
            array[1] = temporary;
        }
    }
    /** Makes sure the array has eneough space.
    *If it doesn't, it doubles the arrays size.*/ 
    private void ensureCapacity() {
        if(array.length == size + 1) {
            @SuppressWarnings("unchecked")
              T[] tmp = (T[]) new Comparable[array.length * 2];
            for(int i = 1; i < array.length; i++) {
                    tmp[i] = array[i];
            }
            array = tmp;
        }
    }

    public static void main(String[] args) {
        int count = 0;
        Heap<Integer> heap = new ArrayHeapImplementation<Integer>();
        // A series of tests to ensure that the methods of the heap 
        // implementation works as intended.
        for(int i = 0; i < 100; i = i + 2) {
            heap.add(i);
            if(!heap.peek().equals(i)) {
                System.out.printf(
                  "*** add FAILED; Expected %d, got %d ***/n", i, heap.peek());
            }
        }
        if(heap.isEmpty() == true) {
            System.out.println(
                "*** isEmpty FAILED; Expected false, got true ***");
        }
        for(int i = 0; i < 50; i++) {
            int highest = heap.peek();
            int root = heap.removeRoot();
            if(root != highest) {
                System.out.printf(
                    "*** removeRoot FAILED; Expected %d, got %d ***/n",
                     highest, root);
            }
        }
        if(heap.isEmpty() == false) {
            System.out.println(
                "*** isEmpty FAILED; Expected true, got false ***");
        }
        heap.add(10);
        heap.clear();
        if(heap.isEmpty() == false) {
            System.out.println(
              "*** clear FAILED; Expected empty heap,got non-empty heap ***");
        }
        heap.clear();
        if(heap.isEmpty() == false) {
            System.out.println(
              "*** clear FAILED; Expected empty heap, got non-empty heap ***");
        }
        heap.add(null);
        if(heap.isEmpty() == false) {
            System.out.println(
                "*** add FAILED; Expected empty heap, got non-empty heap ***");
        }
        if(count == 0) {
            System.out.println("All tests were succesfull!");
        }
    }
}
