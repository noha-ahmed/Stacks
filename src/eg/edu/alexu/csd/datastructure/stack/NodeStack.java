package eg.edu.alexu.csd.datastructure.stack;

import java.util.EmptyStackException;
/**
 * a class implementing stacks using nodes
 *
 */
public class NodeStack implements IStack {
	private Node top;
	private int size;
	public NodeStack() {
		top = null;
		size = 0;
	}

	@Override
	public Object pop() {
		if ( isEmpty() ) {
			throw new EmptyStackException();
		}
		Object element = top.getData();
		top = top.getNext();
		size--;
		return element;
	}

	@Override
	public Object peek() {
		if ( isEmpty() ) {
			throw new EmptyStackException();
		}
		return top.getData();
	}

	@Override
	public void push(Object element) {
		Node newTop = new Node();
		newTop.setNext( top );
		newTop.setData( element );
		top = newTop;
		size++;
	}

	@Override
	public boolean isEmpty() {
		return (top == null);
	}

	@Override
	public int size() {
		return size;
	}
	
	/**
	 * method to show the content of the stack
	 */
	public void print() {
		NodeStack c = new NodeStack();
		while( !isEmpty() ) {
			c.push(pop());
		}
		while( !c.isEmpty() ) { 
			System.out.print( c.peek() + "  " );
			push( c.pop() );
		}
		System.out.println("");
	}
}
