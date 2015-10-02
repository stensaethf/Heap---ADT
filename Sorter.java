/**
 * Sorter.java
 * Jeff Ondich, 2 Feb 2005
 * Modified 24 Feb 2014
 * Modified again Apr 2014 by Jadrian Miles.
 * Modified again Apr 2014 by Frederik Stensaeth and Javier Moran.
 * Modified Again May 2014 by Frederik Stensaeth and Javier Moran. 
 * 
 * Sorter is a collection of static methods for sorting arrays of integers.
 *
 * The main program generates a random permutation of the integers 0,...,N-1,
 * sorts it using the requested algorithm, and prints the number of
 * milliseconds the sorting required.
 *
 * See printUsageStatement for a description of the command-line syntax.
 */

import java.lang.NumberFormatException;

public class Sorter {
    /** Sorts the specified array in increasing order. */
    public static long function_call = 0;
    public static long variable_ass = 0;
    public static long comparisons = 0;

    /** Sorts an unordered least of integers by putting them into a heap
    * and removing the roots of the heap tree.  */ 
    public static void heapSort(int[] a) {
        //Counts the number of items added to the tree.
        int count = 0;
        //Stops if there is nothing on the heap tree.
        if((a == null) || (a.length == 0)) {
            return;
        }
        Heap<Integer> heap = new ArrayHeapImplementation<Integer>();
        //Adds the items onto a heap.
        for(int item : a) {
            heap.add(item);
            count++;
        }
        //Removes the items from the heap and adds them to the list
        //in descending order.
        for(int i = 0; i < count; i++) {
            a[i] = heap.removeRoot();
        }
    }

    
    /** Sorts the specified array in increasing order.
     */
    public static void mergeSort(int[] a) {
        if(a == null || a.length == 0) {
            return;
        }
        int [] tempArray = new int[a.length];
        mergeSort(a, tempArray, 0, a.length - 1);
    }

    /**
     * The recursive method that implements the mergesort algorithm. This
     * method has an ugly set of parameters, including that nasty tempArray
     * that has to be passed around so it's available when the merge
     * method needs it (to prevent merge from reallocating it over and over,
     * which could potentially screw up the N log N complexity of the algorithm).
     */
    private static void mergeSort(int[] a, int[] tempArray, int bottom, int top) {
        if(bottom < top) {
            int middle = (bottom + top) / 2;
            mergeSort(a, tempArray, bottom, middle);
            mergeSort(a, tempArray, middle + 1, top);
            merge(a, tempArray, bottom, middle, top);
        }
    }

    /**
     * Merges two adjacent portions of a given array.
     * 
     * Preconditions:
     * (1) a[bottom],...,a[middle] are in increasing order
     * (2) a[middle+1],...,a[top] are in increasing order
     * (3) top > middle  and  middle >= bottom
     * 
     * Postconditions:
     * (1) a[bottom],...,a[top] compose the same set of
     *     numbers as before, but now in increasing order
     * (2) The rest of the array a[] is unchanged
     */
    private static void merge(int[] a, int[] tempArray, int bottom, int middle, int top) {
        int i = bottom;
        int j = middle + 1;
        int k = 0;

        // While both lists still have items in them, merge those
        // items into the temporary array temp[].
        while (i <= middle && j <= top) {
            if(a[i] < a[j]) {
                tempArray[k] = a[i];
                i++;
            } else {
                tempArray[k] = a[j];
                j++;
            }
            k++;
        }
    
        // Once one of the lists has been exhausted, dump the other
        // list into the remainder of temp[].
        if(i > middle) {
            while (j <= top) {
                tempArray[k] = a[j];
                j++;
                k++;
            }
        } else {
            while (i <= middle) {
                tempArray[k] = a[i];
                i++;
                k++;
            }
        }

        // Copy tempArray[] into the proper portion of a[].
        for (i = bottom; i <= top; i++) {
            a[i] = tempArray[i - bottom];
        }
    }
    
    public static void main(String[] args) {
        SorterArguments sorterArguments = parseCommandLine(args);
        if(sorterArguments == null) {
            printUsageStatement();
            return;
        }
        
        // Create a shuffled array of integers, print it if --verbose, time the
        // appropriate sorting algorithm, print the result if --verbose, and
        // finally, report the sorting time.
        int[] theIntegers = new int[sorterArguments.N];
        fillAndShuffle(theIntegers);
        
        if(sorterArguments.shouldPrintVerbosely) {
            printArray(theIntegers);
        }
        
        long startTime = System.currentTimeMillis();
        
        if(sorterArguments.algorithm.equals("heap")) {
            heapSort(theIntegers);
        } else if(sorterArguments.algorithm.equals("merge")) {
            mergeSort(theIntegers);
        }
        long endTime = System.currentTimeMillis();
        
        if(sorterArguments.shouldPrintVerbosely) {
            printArray(theIntegers);
        }
        
        System.out.format("%s for %d items: %d ms\n\n", sorterArguments.algorithm, sorterArguments.N, endTime - startTime);
        //System.out.printf("comparisons: %d, function_call: %d, variable_ass: %d\n", comparisons, function_call, variable_ass);
    }

    /**
     * Generates a pseudo-random permutation of the integers from 0 to
     * a.length - 1.
     * See p. 139 of "Seminumerical Algorithms, 2nd edition," by Donald Knuth.
     */
    public static void fillAndShuffle(int[] a) {
        // Fill the array with the integers from 0 to a.length - 1.
        int k;
        for (k = 0; k < a.length; k++) {
            a[k] = k;
        }

        // Shuffle.
        for (k = a.length - 1; k > 0; k--) {
            int swapIndex = (int)Math.floor(Math.random() * (k + 1));
            int temp = a[k];
            a[k] = a[swapIndex];
            a[swapIndex] = temp;
        }
    }

    /**
     * Prints the elements of the specified array, separated by spaces,
     * to standard output.
     */
    public static void printArray(int[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println("");
    }
    
    /**
     * Parsing and validating command-line arguments is a pain in the neck, so
     * this program illustrates one approach to isolating the command-line pain.
     * Instead of parsing the command line in main, we defer it to
     * parseCommandLine, which returns null (if there's a problem with the
     * command line) or a SorterArguments object containing the information from
     * the command line in an easy-to-use form.
     */
    private static class SorterArguments {
        public String algorithm;
        public int N;
        public boolean shouldPrintVerbosely;
    }

    /**
     * Parse the command-line arguments.
     * @return null if there's a command-line error of some kind. Otherwise,
     * return a SorterArguments object containing convenient forms of the
     * information stored in the arguments.
     */
    private static SorterArguments parseCommandLine(String[] args) {
        SorterArguments sorterArguments = new SorterArguments();

        // Check command-line length.
        if(args.length < 2 || args.length > 3) {
            return null;
        }

        // Parse the --verbose flag.
        sorterArguments.shouldPrintVerbosely = false;
        if(args.length == 3) {
           if(!args[2].equals("--verbose")) {
               return null;
           }
            sorterArguments.shouldPrintVerbosely = true;
        }

        // Parse the algorithm argument.
        sorterArguments.algorithm = args[0];
        if(!sorterArguments.algorithm.equals("heap") &&
           !sorterArguments.algorithm.equals("merge")) {
            return null;
        }

        // Parse the N argument.
        sorterArguments.N = 0;
        try {
            sorterArguments.N = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            return null;
        }

        // Got here? Then the command line was valid,
        // so we just return the collected arguments.
        return sorterArguments;
    }
    
    private static void printUsageStatement() {
        System.out.println("");
        System.out.println("Usage: java Sorter algorithm N [--verbose]");
        System.out.println("");
        System.out.println("   algorithm can be heap or merge");
        System.out.println("   N is an integer specifying the number of integers you want to shuffle and sort");
        System.out.println("   --verbose will cause the program to print the before and after arrays (don't use for large N)");
        System.out.println("");
    }
}
