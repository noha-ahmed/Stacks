package eg.edu.alexu.csd.datastructure.stack;

import java.util.EmptyStackException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * a class to implements methods required to convert infix to
 * postfix expression and evaluating a postfix expression
 * as well as handling invalid infix expressions
 *
 */
public class ExpressionEvaluator implements IExpressionEvaluator{
    
    @Override
    public String infixToPostfix(String expression) {
        String infix = validInfix( expression );
        String postfix="";
        NodeStack stack = new NodeStack();
        if( infix == null ) 
            throw new IllegalArgumentException("Invalid Expression");
        int i = 0;
        int toInd;
        boolean preFlag;
        boolean valid = true;
        String num;
        char token=' ';
        while( i < infix.length() && valid) {
            token = infix.charAt(i);
            if(Character.isAlphabetic(token))
                postfix+=token + " ";
            else if(Character.isDigit(token)){
                toInd = infix.indexOf(' ', i );
                num = infix.substring(i,toInd);
                postfix+=num + " ";
                i = toInd-1;
            }
            else if( isOperator(token) ){
                while( !stack.isEmpty() && (char)stack.peek() != '(' ){
                    preFlag = isPrecedent(token,(char)stack.peek());
                    if( preFlag )
                        break;
                    else
                        postfix+=stack.pop()+" ";
                }
                stack.push(token);
            }
            else if(isBracket(token)) {
                if( token == '(')
                    stack.push(token);
                else{
                    valid = !stack.isEmpty();
                    while( valid &&(char)stack.peek()!= '(' ) {
                        postfix+= stack.pop()+" ";
                        if( stack.isEmpty() )
                            valid = false;
                    }
                    if(valid)
                        stack.pop();
                }
            }
            i+=2;
        }
        while( !stack.isEmpty() && valid) {
            if( (char)stack.peek() == '(')
                valid = false;
            postfix+=stack.pop()+" ";
        }
        if(!valid)
            throw new IllegalArgumentException("Invalid Expression");
        return postfix;
    }

    @Override
    public int evaluate(String expression)
    {
    	Pattern letter = Pattern.compile("[a-zA-z]");
    	Pattern special = Pattern.compile ("[!@#$%&()_|<>?{}~]");
		Matcher hasLetter = letter.matcher(expression);
		Matcher hasSpecial = special.matcher(expression);
		if( hasLetter.find() ||  hasSpecial.find()) {
			throw new IllegalArgumentException("Invalid Expression");
		}
        int i = 0;
        float firstOperand;
        float secondOperand;
        float result = 0;
        String num;
        NodeStack stack = new NodeStack();
        int toInd;
        char token;
        while( i < expression.length()){
            token = expression.charAt(i);
            if( token == ' ')
                i++;
            else if(Character.isDigit(token)){
                toInd = expression.indexOf(' ', i);
                num = expression.substring(i,toInd);
                i = toInd+1;
                stack.push(Float.parseFloat(num));
            }
            else if(isOperator(token)) {
            	try {
            		secondOperand = (float) stack.pop();
            		firstOperand =(float) stack.pop();
            	}
            	catch( EmptyStackException e) {
            		throw new IllegalArgumentException("Invalid Expression");
            	}
                if ( token == '/' && secondOperand == 0)
                    throw new ArithmeticException("Cannot divide by zero");
                result = performOperation(firstOperand,secondOperand,token);
                stack.push(result);
                i+=2;
            }
        }
        result =(float) stack.pop();
        return (int) result;
    }

    /**
     * Takes a symbolic/numeric infix expression 
     * as input and converts it to infix expression
     * with spaces between tokens
     * @param exp infix expression string
     * @return spaced infix expression or null if invalid
     */
    public String validInfix (String exp){
	    String infix = "";
	    exp=exp.replaceAll(" ","");
	    int i = 0;
	    char token=' ';
	    char prevToken=' ';
	    boolean valid = true;
	    boolean negFlag = false;
	    char temp;
	    while( i < exp.length() && valid ) {
	        token = exp.charAt(i);
	        if( !validToken(token) )
	            valid = false;
	        else if( i == 0 || prevToken == '(') {
	            if( isOperator(token) && token !='-' )
	                valid = false;
	        }
	        else if ( i == exp.length()-1) {
	            if( isOperator(token) )
	                valid = false;
	        }
	        infix+= token;
	        if( Character.isDigit(token) && i<exp.length()-1) {
	            if( Character.isAlphabetic(prevToken) || prevToken == ')'  )
	                valid = false;
	            if( token == '0' && Character.isDigit(exp.charAt(i+1)))
	                valid = false;
	            token = exp.charAt(++i);
	            while(Character.isDigit(token) ){
	                infix+= token;
	                if( i == exp.length()-1 ){
	                    i++;
	                    break;
	                }
	                token = exp.charAt(++i);
	            }
	            token = exp.charAt(--i);
	        }
	        else if( Character.isAlphabetic(token) && i!=0){
	            if(Character.isAlphabetic(prevToken) || Character.isDigit(prevToken))
	                valid = false;
	        }
	        else if( isNegativeSign( prevToken, token ) ){
	            if ( i > 2) {
	                temp = exp.charAt(i-2);
	                if(isOperator(prevToken)&&(isOperator(temp)|| temp=='('))
	                    valid = false;
	            }
	
	            negFlag = true;
	        }
	        else if( isOperator(token) ) {
	            if(isOperator(prevToken))
	                valid = false;
	        }
	        else if ( isBracket(token)){
	            if( prevToken == ')' && token =='(')
	                valid = false;
	            if( isOperator(prevToken) && token == ')')
	            	valid = false;
	        }
	        if( !negFlag ){
	            infix+=" ";
	        }
	        negFlag = false;
	        prevToken = token;
	        i++;
	    }
	    if( !valid || i == 0)
	        return null;
	    infix = putDummyZero(infix);
	    return infix;
	}

    /**
     * checks if a token of an infix expression 
     * is valid or not
     * @param ch
     * token character
     * @return boolean value 
     * true if valid false otherwise
     */
	public boolean validToken(char ch) {
	    if( Character.isAlphabetic(ch) || Character.isDigit(ch) )
	        return true;
	    else if( isOperator(ch) || isBracket(ch) )
	        return true;
	    return false;
	}
	
	/**
	 * checks if a token is an operator
	 * @param ch
	 * token character
	 * @return boolean value
	 * true if operator false otherwise
	 */
	public boolean isOperator (char ch){
	    if( ch == '*' | ch=='+' | ch == '/' | ch == '-')
	        return true;
	    return false;
	}

	/**
	 * checks if a token is a bracket
	 * @param ch
	 * token character
	 * @return boolean value
	 * true if bracket false otherwise
	 */
	public boolean isBracket(char ch) {
	    if( ch == '(' || ch == ')')
	        return true;
	    return false;
	}
	
	/**
	 * checks if it is negative sign or minus operator
	 * @param prev
	 * character before the operator
	 * @param ch
	 * operator 
	 * @return boolean value
	 * true if it is a negative sign false otherwise
	 */
	public boolean isNegativeSign (char prev,char ch){
	    if( ch == '-' ){
	        if( isOperator(prev) || prev == '(' || prev == ' ')
	            return true;
	    }
	    return false;
	}
	
	/**
	 * takes first and second operand and the operator
	 * evaluate them
	 * @param firstOperand
	 * @param secondOperand
	 * @param operator
	 * @return result evaluated
	 */
	public float performOperation( float firstOperand, float secondOperand, char operator) {
        float result = 0;
        switch( operator ) {
        case '+':
            result = firstOperand + secondOperand;
            break;
        case '-':
            result = firstOperand - secondOperand;
            break;
        case '*':
            result = firstOperand*secondOperand;
            break;
        case '/':
            result = firstOperand/secondOperand;
            break;
        }
        return result;
    }
	
	/**
	 * takes operator and another operator stored in the stack
	 * checks the operator precedence
	 * @param op new operator
	 * @param opStack operator stored in stack
	 * @return boolean value
	 * true if the operator has higher precedence than that
	 * in the stack false otherwise
	 */
    public boolean isPrecedent( char op, char opStack ){
        if( opStack == '*' || opStack == '/')
            return false;
        else if ( op == '*' || op =='/')
            return true;
        else
            return false;
    }

    /**
     * takes an infix expression and handles the negative sign
     * @param exp 
     * infix expression
     * @return infix expression 
     * after handling negative sign
     */
    public String putDummyZero ( String exp ) {
        int i = 0;
        int negInd = 0 ;
        int toInd = 0;
        char ch;
        String term;
        String replacement;
        while ( i < exp.length()-1 ) {
            negInd = exp.indexOf("-",i);
            if( negInd == -1 ) break;
            ch = exp.charAt(negInd+1);
            if( Character.isAlphabetic(ch) || Character.isDigit(ch)) {
                toInd = exp.indexOf(" ", negInd);
                term = exp.substring(negInd,toInd);
                replacement = "( 0 - "+term.substring(1) +" )";
                exp = exp.replace(term, replacement);
                i = toInd;
            }
            else if( isBracket(ch) ){
                replacement = "( 0 - ";
                i = negInd+1;
                term= exp.substring(i);
                toInd = closingBracInd( term );
                if( toInd!= -1){
                    term = exp.substring(i,i+toInd+1);
                    replacement = replacement + term + " )";
                    exp = exp.replace("-"+term,replacement);
                }
                else {
                    exp = null;
                    break;
                }
            }
            i++;
        }
        return exp;
    }

    /**
     * takes a string starting with a bracket
     * searches for its closing bracket
     * @param term
     * starting with an opening bracket
     * @return index of the closing bracket
     * or -1 if its closing bracket is not found
     */
    public int closingBracInd ( String term ){
        int ind = -1;
        boolean found = false;
        int i = 0 ;
        NodeStack s = new NodeStack();
        char token;
        while ( i < term.length() && !found ){
            token = term.charAt(i);
            if( token == '(' )
                s.push(token);
            else if ( token == ')' ) {
                if( !s.isEmpty() ) {
                    s.pop();
                    if(s.isEmpty())
                        found = true;
                }
                else break;
            }
            i++;
        }
        if ( found )
            ind = i-1;
        return ind;
    }

}

