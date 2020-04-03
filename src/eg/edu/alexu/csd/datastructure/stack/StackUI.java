package eg.edu.alexu.csd.datastructure.stack;

import java.util.Scanner;

/**
 * a class used to implement user interface
 * to test the stack implementation
 */
public class StackUI {
	static boolean exit = false;
	static char selectM;
	/**
	 * method to show menu options
	 */
	public static void menu() {
		System.out.println("------------------------------------------------------------");
		System.out.println(" Select an operation");
		System.out.println("1: push");
		System.out.println("2: pop");
		System.out.println("3: peak");
		System.out.println("4: get size");
		System.out.println("5: check if empty");
		System.out.println("6: exit");
		System.out.println("------------------------------------------------------------");
	}

	/**
	 * main method
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner scan = new Scanner( System.in );
		NodeStack stack = new NodeStack();
		while( !exit ) {
			menu();
			selectM = scan.next().charAt(0);
			switch ( selectM ) {
			case( '1' ):
				//push
				System.out.println("Enter an element:");
			    scan.nextLine();
			    String n = scan.nextLine();
			    stack.push(n);
				break;
			case( '2' ):
				//pop
				try {
					String p = (String)stack.pop();
					System.out.println("top of stack:"+p);
				}catch( Exception e) {
					System.out.println("Empty Stack");
				}
				break;
			case( '3' ):
				//peak
				try {
					String p = (String) stack.peek();
					System.out.println("top of stack:"+p);
				}catch( Exception e) {
					System.out.println("Empty Stack");
				}
				break;
			case( '4' ):
				//get size
				System.out.printf("size : %d\n", stack.size());
				break;
			case( '5' ):
				//check if empty
				if( stack.isEmpty() ) {
					System.out.println("stack is empty");
				}
				else {
					System.out.println("stack is not empty");
				}
				break;
			case( '6' ):
				//exit
				exit = true;
			    scan.close();
				break;
		    default:
		    	System.out.println("invalid input");
			}
		}
	

	}

}
