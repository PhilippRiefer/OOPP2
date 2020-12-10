package ringbuffer;

/*	Programm : Buffer . java
*	Autoren : Philipp Riefer, Domenic Heidemann
*	Datum : 10.12.2020
*/

public class Buffer {
	// attributes
	private int size;// how many objects are stored currently
	private int first;// position of first member of waiting queue
	private int last;// position of last member of waiting queue
	private Object[] elements;// the array organised as ringbuffer
	private final int minLength = 2;
	
	// constructor
	public Buffer() {
		this.first = 0;
		this.last = 0;
		this.size = 0;
		this.elements = new Object[minLength];
	}// initializes all attributes
	
	// methods
	public Object get() {
		int tmpFirst;
		Object[] tmpElements = new Object[(int) (elements.length)];
		size--;
		resize();
		if (first+1 > elements.length) {
			tmpFirst = first;
			first = 0;
			tmpElements[tmpFirst] = elements[tmpFirst];
			elements[tmpFirst] = null;
			return tmpElements[tmpFirst];
		} else {
			tmpFirst = first;
			first++;
			
			tmpElements[tmpFirst] = elements[tmpFirst];
			elements[tmpFirst] = null;
			return tmpElements[tmpFirst];
		}
	}// get the element that waited the longest time and remove it
	
	public void put(Object o) {
		size++;
		resize();
		if (last+1 > elements.length) {
			last = 0;
			elements[last] = o;
		} else {
			elements[last] = o;
			last++;
		}
	}// add an object to the queue

	public int getSize() {
		return size;
	}// how many elements are stored currently
	
	public int getBuffersize() {
		return (size);
	}// size of the buffer currently
	
	public void resize() {
		Object[] tmpElements = null;
		
		if (size < elements.length*0.25) {
			
			tmpElements = new Object[(int) (elements.length*0.5)];
			
			for (int i = 0; i < size; i++) {
				tmpElements[i] = elements[(i+first)%elements.length];
			}
			
			// half size
		} else if(size == elements.length) {
			
			tmpElements = new Object[elements.length*2];
			
			for (int i = 0; i < size; i++) {
				tmpElements[i] = elements[(i+first)%elements.length];
			}
			
			// double size
		}
		elements = tmpElements;
	}

}
