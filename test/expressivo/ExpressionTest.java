/* Copyright (c) 2015-2017 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Tests for the Expression abstract data type.
 */
public class ExpressionTest {

    /*
     * Testing strategy
     * ==================
     *
     * Get all the available copies of a book
     * 
     * String toString()
     * 
     * @return a parsable representation of this expression, such that
     * for all e:Expression, e.equals(Expression.parse(e.toString())).
     *
     * Partition the inputs as follows:
     * single number
     * single variable
     * binary operation a+b 
     * binary operation a*b 
     *          
     * Cover each part testing coverage.
     */
    @Test
    public void testNumberToString() throws IOException {
        Expression e = new Number(0);
        
        //for all e:Expression, e.equals(Expression.parse(e.toString())).
        assertTrue(e.equals(Expression.parse(e.toString())));
        
        //for all e1,e2:Expression, e1.equals(e2) implies e1.hashCode() == e2.hashCode()
        assertTrue(e.hashCode() == Expression.parse(e.toString()).hashCode() );
    }
    
    @Test
    public void testVariableToString() throws IOException {
        Expression e = new Variable ("xyz");
        
        //for all e:Expression, e.equals(Expression.parse(e.toString())).
        assertTrue(e.equals(Expression.parse(e.toString())));
        
        //for all e1,e2:Expression, e1.equals(e2) implies e1.hashCode() == e2.hashCode()
        assertTrue(e.hashCode() == Expression.parse(e.toString()).hashCode() );
    }
    
    @Test
    public void testPlusExpressionToString() throws IOException {
        Expression l = new Variable ("a");
        Expression r = new Variable ("b");
        Expression e = new BinOpExpression ('+',l, r);
        
        //for all e:Expression, e.equals(Expression.parse(e.toString())).
        assertTrue(e.equals(Expression.parse(e.toString())));
        
        //for all e1,e2:Expression, e1.equals(e2) implies e1.hashCode() == e2.hashCode()
        assertTrue(e.hashCode() == Expression.parse(e.toString()).hashCode() );
    }
    
    @Test
    public void testTimesExpressionToString() throws IOException {
        Expression l = new Variable ("a");
        Expression r = new Variable ("b");
        Expression e = new BinOpExpression ('*',l, r);
        
        //for all e:Expression, e.equals(Expression.parse(e.toString())).
        assertTrue(e.equals(Expression.parse(e.toString())));
        
        //for all e1,e2:Expression, e1.equals(e2) implies e1.hashCode() == e2.hashCode()
        assertTrue(e.hashCode() == Expression.parse(e.toString()).hashCode() );
    }
    /*
     * Testing strategy
     * ==================
     *
     * Get all the available copies of a book
     * 
     * String Expression.parse()
     * 
     * @param input expression to parse, as defined in the PS1 handout.
     * @return expression AST for the input
     *
     * Partition the inputs as follows:
     * number as integer / decimal point
     * expression  w/wo space
     * expression with parenthesis
     *          
     * Cover each part testing coverage.
     */
    @Test
    public void testExpressionParseInteger() throws IOException {

        Expression e = Expression.parse("10 + 100 + 1000");
        
        //for all e:Expression, e.equals(Expression.parse(e.toString())).
        assertTrue(e.equals(Expression.parse(e.toString())));
        
        //for all e1,e2:Expression, e1.equals(e2) implies e1.hashCode() == e2.hashCode()
        assertTrue(e.hashCode() == Expression.parse(e.toString()).hashCode() );
    }
    
    @Test
    public void testExpressionParseDouble() throws IOException {

        Expression e = Expression.parse("10.0 + 100.01 + 1000.10");
        
        //for all e:Expression, e.equals(Expression.parse(e.toString())).
        assertTrue(e.equals(Expression.parse(e.toString())));
        
        //for all e1,e2:Expression, e1.equals(e2) implies e1.hashCode() == e2.hashCode()
        assertTrue(e.hashCode() == Expression.parse(e.toString()).hashCode() );
    }
   
    @Test
    public void testExpressionParseWithoutSpace() throws IOException {

        Expression e = Expression.parse("a*b+c");
        
        //for all e:Expression, e.equals(Expression.parse(e.toString())).
        assertTrue(e.equals(Expression.parse(e.toString())));
        
        //for all e1,e2:Expression, e1.equals(e2) implies e1.hashCode() == e2.hashCode()
        assertTrue(e.hashCode() == Expression.parse(e.toString()).hashCode() );
    }
    
    @Test
    public void testExpressionParseWithSpace() throws IOException {

        Expression e = Expression.parse("abc *  xyz + 100 ");
        
        //for all e:Expression, e.equals(Expression.parse(e.toString())).
        assertTrue(e.equals(Expression.parse(e.toString())));
        
        //for all e1,e2:Expression, e1.equals(e2) implies e1.hashCode() == e2.hashCode()
        assertTrue(e.hashCode() == Expression.parse(e.toString()).hashCode() );
    }
    
    @Test
    public void testExpressionParseWithParenthesis() throws IOException {

        Expression e = Expression.parse(" a * ( b + c )");
        
        //for all e:Expression, e.equals(Expression.parse(e.toString())).
        assertTrue(e.equals(Expression.parse(e.toString())));
        
        //for all e1,e2:Expression, e1.equals(e2) implies e1.hashCode() == e2.hashCode()
        assertTrue(e.hashCode() == Expression.parse(e.toString()).hashCode() );
    }
    /*
     * Testing strategy
     * ==================
     *
     * Get all the available copies of a book
     * 
     * String Expression.derivative(String var)
     * 
     * @return a derivative expression of the input polynomial expression with respect to the variable var.
     *
     * Partition the inputs as follows:
     * derivative of a constant (number) is 0
     * derivative of a variable with respect to itself is 1
     * derivative of a variable with respect to a different variable is 0
     * derivative of polynomial with one single variable '+' operation 
     * derivative of polynomial with one single variable '*' operation 
     * derivative of polynomial with one single variable both '+' and '*' operations
     * derivative of polynomial with two different variables '+' operation 
     * derivative of polynomial with two different variables '*' operation 
     * derivative of polynomial with two different variables both '+' and '*' operations
     *          
     * Cover each part testing coverage.
     */
    
    @Test
    public void testDerivativeConstant() throws IOException {
        // build a Number object with value 0 
        Expression num = Expression.parse("0");
        // build a Number object with value 1
        Expression e = Expression.parse("1");
        
        // the derivative of number 1 is number 0
        assertTrue(num.equals(e.derivative("x")));
    }
    
    @Test
    public void testDerivativeVariableItself() throws IOException {
        // build a Number object with value 1
        Expression num = Expression.parse("1");
        // build a Variable object with string "x"
        Expression e = Expression.parse("x");
        
        // the derivative of variable x to itself is number 1
        assertTrue(num.equals(e.derivative("x")));
    }
    
    @Test
    public void testDerivativeVariableNotItself() throws IOException {
        // build a Number object with value 0
        Expression num = Expression.parse("0");
        // build a Variable object with string "y"
        Expression e = Expression.parse("y");
        
        // the derivative of variable x to y is number 0
        assertTrue(num.equals(e.derivative("x")));
    }
    
    @Test
    public void testDerivativeOneVariablePolynomialPlus() throws IOException {
        // build an expression object with string "1+1"
        Expression num = Expression.parse("1+1");
        // build an expression object with string "x+x"
        Expression e = Expression.parse("x + x");
        
        // the derivative of polynomial x+x wrt x is 1+1
        assertTrue(num.equals(e.derivative("x")));
    }
    
    @Test
    public void testDerivativeOneVariablePolynomialTimes() throws IOException {
        // build an expression object with string "x*1+x*1"
        Expression num = Expression.parse("x*1+x*1");
        // build an expression object with string "x*x"
        Expression e = Expression.parse("x*x");
        
        // the derivative of polynomial x*x wrt x is x*1+x*1
        assertTrue(num.equals(e.derivative("x")));
    }
    
    @Test
    public void testDerivativeOneVariablePolynomialMoreTimes() throws IOException {
        // build an expression object with string "x*x*1+x*(x*1+x*1)"
        Expression num = Expression.parse("x*x*1+x*(x*1+x*1)");
        // build an expression object with string "x*x*x"
        Expression e = Expression.parse("x*x*x");
        
        // the derivative of polynomial x*x*x wrt x is x*x*1+x*(x*1+x*1)
        assertTrue(num.equals(e.derivative("x")));
    }
    
    @Test
    public void testDerivativeOneVariablePolynomial() throws IOException {
        // build an expression object with string "x*1+x*1+x*1+x*1"
        Expression num = Expression.parse("(x*1+x*1)+(x*1+x*1)");
        // build an expression object with string "x*x + x*x"
        Expression e = Expression.parse("x*x + x*x");
        
        // the derivative of polynomial x*x + x*x wrt x is (x*1+x*1)+(x*1+x*1)
        assertTrue(num.equals(e.derivative("x")));
    }
  
    @Test
    public void testDerivativeTwoVariablesPolynomialPlus() throws IOException {
        // build an expression object with string "1+0"
        Expression num = Expression.parse("1+0");
        // build an expression object with string "x+y"
        Expression e = Expression.parse("x + y");
        
        // the derivative of polynomial x+y wrt x is 1+0
        assertTrue(num.equals(e.derivative("x")));
    }
    
    @Test
    public void testDerivativeTwoVariablesPolynomialTimes() throws IOException {
        // build an expression object with string "x*0+y*1"
        Expression num = Expression.parse("x*0+y*1");
        // build an expression object with string "x*y"
        Expression e = Expression.parse("x*y");
        
        // the derivative of polynomial x*y wrt x is "x*0+y*1"
        assertTrue(num.equals(e.derivative("x")));
    }
    
    @Test
    public void testDerivativeTwoVariablesPolynomial() throws IOException {
        // build an expression object with string "x*1+x*1+x*1+x*1"
        Expression num = Expression.parse("(x*0+y*1)+(y*1+x*0)");
        // build an expression object with string "x*y + y*x"
        Expression e = Expression.parse("x*y + y*x");
        
        // the derivative of polynomial x*y + y*x wrt x is (x*0+y*1)+(y*1+x*0)
        assertTrue(num.equals(e.derivative("x")));
    }
    
    /*
     * Testing strategy
     * ==================
     * 
     * String Expression.simplify (Map<String,Double> environment)
     * 
     * @param environment maps variables to values.  Variables are required to be case-sensitive nonempty 
     *         strings of letters.  The set of variables in environment is allowed to be different than the 
     *         set of variables actually found in expression.  Values must be nonnegative numbers.
     * @returns the value for the expression after being evaluated.
     *
     * Partition the inputs as follows:
     * expression with three numbers with operation '+'
     * expression with three numbers with operation '*' and '+'
     * expression with four numbers with operation '*' and '+' and parenthesized.
     * expression with one variable and numbers with operation '+', one variable assigned value
     * expression with one variable and numbers with operation '*', one variable assigned value
     * expression with one variable and numbers with operation '+', wrong variable assigned value
     * expression with one variable and numbers with operation '*' and '+', one variable assigned value
     * expression with two variables and numbers with operation '+', one variable assigned value
     * expression with two variables and numbers with operation '+', two variables assigned value
     * expression with two variables and numbers with operation '*', one variable assigned value
     * expression with two variables and numbers with operation '*', two variables assigned value
     * expression with two variables and numbers with operation '+' and '*', one variable assigned value
     * expression with two variables and numbers with operation '+' and '*', two variable assigned values
     * 
     * Cover each part testing coverage.
     */
    
    @Test
    public void testSimplifyConstantSum() throws IOException {
        Map<String,Double> env = new HashMap<>();
        env.put("x", 0.0);
        
        Expression exp = Expression.parse("1+10+100");
        Expression simp = Expression.parse("111");
        
        // the simplification of "1+10+100" is "111"
        assertTrue(exp.simplify(env).equals(simp));
    }
  
    @Test
    public void testSimplifyConstantTime() throws IOException {
        Map<String,Double> env = new HashMap<>();
        env.put("x", 0.0);
        
        Expression exp = Expression.parse("2*5.0*100");
        Expression simp = Expression.parse("1000");
        
        // the simplification of "2*5.0*100" is "1000"
        assertTrue(exp.simplify(env).equals(simp));
    }
    
    @Test
    public void testSimplifyConstantSumTime() throws IOException {
        Map<String,Double> env = new HashMap<>();
        env.put("x", 0.0);
        
        Expression exp = Expression.parse("10+5.0*(10.99+39.01)");
        Expression simp = Expression.parse("260");
        
        // the simplification of "10+5.0*(10.99+39.01)" is "260"
        assertTrue(exp.simplify(env).equals(simp));
    }
    
    @Test
    public void testSimplifySingleVariableSum() throws IOException {
        Map<String,Double> env = new HashMap<>();
        env.put("x", 8.0);
        
        Expression exp = Expression.parse("10 + 2 + x");
        Expression simp = Expression.parse("20");
        
        // the simplification of "10 + 2 + x with x = 8" is "20"
        assertTrue(exp.simplify(env).equals(simp));
    }
    
    @Test
    public void testSimplifySingleVariableTime() throws IOException {
        Map<String,Double> env = new HashMap<>();
        env.put("abc", 100.0);
        
        Expression exp = Expression.parse("10 * 2 * abc");
        Expression simp = Expression.parse("2000");
        
        // the simplification of "10 * 2 * abc with abc = 100" is "2000"
        assertTrue(exp.simplify(env).equals(simp));
    }
    
    @Test
    public void testSimplifySingleVariableSumTime() throws IOException {
        Map<String,Double> env = new HashMap<>();
        env.put("var", 100.0);
        
        Expression exp = Expression.parse("10 + 2 * var");
        Expression simp = Expression.parse("210");
        
        // the simplification of "10 + 2 * var with var = 100" is "210"
        assertTrue(exp.simplify(env).equals(simp));
    }
    
    @Test
    public void testSimplifyWrongVariableSum() throws IOException {
        Map<String,Double> env = new HashMap<>();
        env.put("x", 8.0);
        
        Expression exp = Expression.parse("10 + 2 + y");
        Expression simp = Expression.parse("12+y");
        
        // the simplification of "10 + 2 + y with x = 8" is "12+y"
        assertTrue(exp.simplify(env).equals(simp));
    }
    
    @Test
    public void testSimplifyTwoVariablesSum() throws IOException {
        Map<String,Double> env = new HashMap<>();
        env.put("x", 8.0);
        
        Expression exp = Expression.parse("10 + 2 + x +y");
        Expression simp = Expression.parse("20+y");
        
        // the simplification of "10 + 2 + x +y with x = 8" is "20+y"
        assertTrue(exp.simplify(env).equals(simp));
    }
    
    @Test
    public void testSimplifyTwoVariablesSum2() throws IOException {
        Map<String,Double> env = new HashMap<>();
        env.put("x", 8.0);
        env.put("y", 80.0);
        
        Expression exp = Expression.parse("10 + 2 + x + y");
        Expression simp = Expression.parse("100");
        
        // the simplification of "10 + 2 + x +y with x = 8 y = 80" is "100"
        assertTrue(exp.simplify(env).equals(simp));
    }
    
    @Test
    public void testSimplifyTwoVariablesTime() throws IOException {
        Map<String,Double> env = new HashMap<>();
        env.put("x", 8.0);
        
        Expression exp = Expression.parse("10 * 2 * x * y");
        Expression simp = Expression.parse("160*y");
        
        // the simplification of "10 * 2 * x * y with x = 8" is "160*y"
        assertTrue(exp.simplify(env).equals(simp));
    }
    
    @Test
    public void testSimplifyTwoVariablesTime2() throws IOException {
        Map<String,Double> env = new HashMap<>();
        env.put("x", 8.0);
        env.put("y", 80.0);
        
        Expression exp = Expression.parse("10 * 2 * x * y");
        Expression simp = Expression.parse("12800");
        
        // the simplification of "10 * 2 * x * y with x = 8 y = 80" is "12800"
        assertTrue(exp.simplify(env).equals(simp));
    }
    
    @Test
    public void testSimplifyTwoVariablesSumTime() throws IOException {
        Map<String,Double> env = new HashMap<>();
        env.put("x", 8.0);
        
        Expression exp = Expression.parse("(10 + 2) * x + y");
        Expression simp = Expression.parse("96+y");
        
        // the simplification of "(10 + 2) * x + y with x = 8" is "96+y"
        assertTrue(exp.simplify(env).equals(simp));
    }
    
    @Test
    public void testSimplifyTwoVariablesSumTime2() throws IOException {
        Map<String,Double> env = new HashMap<>();
        env.put("x", 8.0);
        env.put("y", 80.0);
        
        Expression exp = Expression.parse("10 * (2 + x) * y");
        Expression simp = Expression.parse("8000");
        
        // the simplification of "10 * (2 + x) * y with x = 8 y = 80" is "8000"
        assertTrue(exp.simplify(env).equals(simp));
    }
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
   
}
