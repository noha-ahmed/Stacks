package eg.edu.alexu.csd.datastructure.stack;

import java.util.Scanner;
/**
 * 
 * a class to implement a user interface 
 * to test the expression evaluator class
 * featured with evaluating symbolic infix
 * expressions
 */

public class ExpressionEvaluatorUI {
	static Scanner scan = new Scanner(System.in);
	static ExpressionEvaluator test = new ExpressionEvaluator();
	/**
	 * takes the postfix expression and evaluate 
	 * values of variables if exists by 
	 * asking the user to enter them
	 * @param postfix 
	 * numeric or symbolic postfix expression
	 * @return postfix
	 * numeric postfix expression
	 */
	public static String evaluateVars(String postfix) {
		char token;
		String strNum="";
		int state = 0;
		float value;
		boolean valid ;
		for(int i = 0 ; i < postfix.length() ; i++ ) {
	    	token = postfix.charAt(i);
	    	if( Character.isAlphabetic(token) ) {
	    		valid = false;
	    		while( !valid ) {
	    			try {
	    				System.out.printf("-Enter the value of %c :" , token);
	    				strNum = scan.next();
	    			    value = Float.parseFloat(strNum);
	    			    if(value < 0) state = -1; 
	    			    else state = 1;
	    				valid = true;
	    			}
	    			catch(NumberFormatException e) {
	    				System.out.println("<<Invalid Input>>");	
	    			}
	    		}
	    		if( state == 1 )
	    		    postfix = postfix.replaceAll(Character.toString(token),strNum);
	    		else {
	    			String rep = "0 "+strNum.substring(1)+" -";
	    			postfix = postfix.replaceAll(Character.toString(token),rep);
	    		}	
	    	}
	    }
		return postfix;
	}
	/**
	 * a method to print options from 
	 * which user can choose
	 */
	public static void menu () {
		System.out.println("------------------------------------------------------");
		System.out.println("Select an option:");
		System.out.println("------------------");
		System.out.println("1-Enter an infix expression and convert it to postfix");
		System.out.println("2-Evaluate postfix");
		System.out.println("3-exit");
		System.out.println("------------------------------------------------------");
	}

	/**
	 * main method
	 * @param args
	 */
	public static void main(String[] args) {
		boolean exit = false;
		char m;
		String infix = null;
		String postfix = null;
		int x;
		boolean valid = false;
		while( !exit ) {
			menu();
			m = scan.next().charAt(0);
			switch( m ) {
			case '1':
				scan.nextLine();
				while(!valid) {
					try {
						// enter infix
						System.out.println("-Enter infix expression:");
						infix = scan.nextLine();
						//convert infix to postfix
						postfix = test.infixToPostfix(infix);
						System.out.println("-postfix expression: " + postfix);
						valid = true;
					}
					catch(Exception e) {
						System.out.println("<< " + e.getMessage() + " >>");
					}    	
				}
				valid = false;
				break;
			case '2':
				//evaluating a postfix expression
				try {
					if( postfix == null ) {
						throw new RuntimeException("postfix is not assigned");
					}
					System.out.println("-postfix expression to be evaluated : "+postfix);
					postfix = evaluateVars(postfix);
					x = test.evaluate(postfix);
					System.out.println("-the expression evaluated value: " + x);
				}
				catch( Exception e ) {
					System.out.println("<< "+e.getMessage()+" >>");
				}
				break;
			case '3':
				exit = true;
				scan.close();
				break;
			default:
				System.out.println("<< invalid selection >>");
			}	
		}
	}
}
