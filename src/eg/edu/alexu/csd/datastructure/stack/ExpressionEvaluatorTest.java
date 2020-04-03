package eg.edu.alexu.csd.datastructure.stack;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * junit to test expression evaluator class
 *
 */
class ExpressionEvaluatorTest {

	ExpressionEvaluator test = new ExpressionEvaluator();
	String input ;
	String actualP;
	String expectedP;
	int actualE;
	int expectedE;
	/**
	 * testing numeric expressions
	 */
	@Test
	void test1() {
		//sample test1
		input = "2 + 3 * 4 ";
		expectedP = "2 3 4 * + ";
		expectedE = 14;
		actualP = test.infixToPostfix(input);
		actualE = test.evaluate(actualP); 
		assertEquals(expectedP , actualP);
		assertEquals(expectedE , actualE);
		//sample test2
		input = "3*2*(36-12)/-4 ";
		expectedP = "3 2 * 36 12 - * 0 4 - / ";
		expectedE = -36;
		actualP = test.infixToPostfix(input);
		actualE = test.evaluate(actualP); 
		assertEquals(expectedP , actualP);
		assertEquals(expectedE , actualE);
		//sample test3
		input = "3*2/(8-8) ";
		expectedP = "3 2 * 8 8 - / ";
		actualP = test.infixToPostfix(input);
		assertEquals(expectedP , actualP);
		Exception thrown = assertThrows(ArithmeticException.class,() ->test.evaluate(actualP));
		assertEquals("Cannot divide by zero",thrown.getMessage());
	}
	/** 
	 *testing symbolic expressions
	 */
	@Test
	void test2() {
		//sample test1 
		input ="(a / (b - c + d)) * (e - a) * c";
		expectedP = "a b c - d + / e a - * c * ";
		actualP = test.infixToPostfix(input);
		assertEquals(expectedP , actualP );
		Exception thrown = assertThrows(IllegalArgumentException.class,() ->test.evaluate(actualP));
		assertEquals("Invalid Expression",thrown.getMessage());
		//sample test2
		input ="a / b - c + d * e - a * c";
		expectedP = "a b / c - d e * + a c * - ";
		actualP = test.infixToPostfix(input);
		assertEquals(expectedP , actualP );
	    thrown = assertThrows(IllegalArgumentException.class,() ->test.evaluate(actualP));
		assertEquals("Invalid Expression",thrown.getMessage());
	}
	/**
	 * testing some invalid cases
	 */
	@Test
	void test3() {
		// missing closing bracket case
		input ="(a / (b - c + d) * (e - a) * c"; 
		Exception thrown = assertThrows(IllegalArgumentException.class,() ->test.infixToPostfix(input));
		assertEquals("Invalid Expression",thrown.getMessage());
		// extra operator case
		input ="a / (b - c ++d) * (e - a) * c";
	    thrown = assertThrows(IllegalArgumentException.class,() ->test.infixToPostfix(input));
		assertEquals("Invalid Expression",thrown.getMessage());
		//brackets without operator in between
		input ="a / (b - c +d) (e - a) * c";
		thrown = assertThrows(IllegalArgumentException.class,() ->test.infixToPostfix(input));
		assertEquals("Invalid Expression",thrown.getMessage());
		//symbols without operator in between
		input ="a / (bc +d) + (e - a) * c";
		thrown = assertThrows(IllegalArgumentException.class,() ->test.infixToPostfix(input));
		assertEquals("Invalid Expression",thrown.getMessage());
		//symbol and digit without operator in between
		input ="a / (2c +d) + (e - a) * c";
		thrown = assertThrows(IllegalArgumentException.class,() ->test.infixToPostfix(input));
		assertEquals("Invalid Expression",thrown.getMessage());
		//operator at the begining
		input ="+a / (2c +d) + (e - a) * c";
		thrown = assertThrows(IllegalArgumentException.class,() ->test.infixToPostfix(input));
		assertEquals("Invalid Expression",thrown.getMessage());
	}
	
	/**
	 * testing negative sign cases
	 */
	@Test
	void test4() {
		//sample test1 
		input ="(1-3)/-(1--2)*6";
		expectedP = "1 3 - 0 1 0 2 - - - / 6 * ";
		actualP = test.infixToPostfix(input);
		assertEquals(expectedP , actualP );
		expectedE = 4;
		actualE = test.evaluate(actualP);
		assertEquals(expectedE , actualE);
		//sample test2
		input ="(a / -(b - c)+9)";
		expectedP = "a 0 b c - - / 9 + ";
		actualP = test.infixToPostfix(input);
		assertEquals(expectedP , actualP );
	}

}
