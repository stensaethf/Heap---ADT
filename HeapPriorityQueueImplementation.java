/**
 * An implementation of a Priority Queue, using a Heap.
 * @param <T> the type of data the queue stores.
 * @author Frederik Roenn Stensaeth and Javier Ernesto Moran Lemus
 */
public class HeapPriorityQueueImplementation<T extends Comparable<? super T>> implements PriorityQueue<T> {
    
    public HeapPriorityQueueImplementation() {
        Heap<T> heap = new ArrayHeapImplementation<T>();
    }
    /** Adds the given item to the queue. */
    public void add(T item) {
        heap.add(item);
    }
    
    /** Removes the maximum item from the queue, and returns it.
     * @return null if the queue is empty.
     */
    public T remove() {
        return heap.removeRoot();
    }
    
    /** Returns the maximum item in the queue, without removing it.
     * @return null if the queue is empty.
     */
    public T peek() {
        return heap.peek();
    }
    
    /** Returns true if the queue is empty. */
    public boolean isEmpty() {
        return heap.isEmpty();
    }
    
    /** Removes all items from the queue. */
    public void clear() {
        heap.clear();
    }
}
