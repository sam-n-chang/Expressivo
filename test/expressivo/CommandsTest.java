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
 * Tests for the static methods of Commands.
 */
public class CommandsTest {

    /*
     * Testing strategy
     * ==================
     *
     * Get all the available copies of a book
     * 
     * String differentiate(String expression, String variable)
     * 
     * @param expression the expression to differentiate
     * @param variable the variable to differentiate by, a case-sensitive nonempty string of letters.
     * @return expression's derivative with respect to variable.  Must be a valid expression equal
     *         to the derivative, but doesn't need to be in simplest or canonical form.
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
        
        String dev = Commands.differentiate("1", "notUsed");
        
        // the derivative of number 1 is number 0
        assertTrue(dev.equals("0.0"));
    }
    
    @Test
    public void testDerivativeVariableItself() throws IOException {
        
        String dev = Commands.differentiate("x", "x");
        
        // the derivative of variable x to itself is number 1
        assertTrue(dev.equals("1.0"));
    }
    
    @Test
    public void testDerivativeVariableNotItself() throws IOException {
        
        String dev = Commands.differentiate("x", "y");
        
        // the derivative of variable x to y is number 0
        assertTrue(dev.equals("0.0"));
    }
    
    @Test
    public void testDerivativeOneVariablePolynomialPlus() throws IOException {

        String dev = Commands.differentiate("x+x", "x");
        
        // the derivative of polynomial x+x wrt x is 1+1
        assertTrue(dev.equals("(1.0+1.0)"));
    }
    
    @Test
    public void testDerivativeOneVariablePolynomialTimes() throws IOException {

        String dev = Commands.differentiate("x*x", "x");
        
        // the derivative of polynomial x*x wrt x is x*1+x*1
        assertTrue(dev.equals("(x*1.0+x*1.0)"));
    }
    
    @Test
    public void testDerivativeOneVariablePolynomialMoreTimes() throws IOException {

        String dev = Commands.differentiate("x*x*x", "x");
        
        // the derivative of polynomial x*x*x wrt x is x*x*1+x*(x*1+x*1)
        assertTrue(dev.equals("(x*x*1.0+x*(x*1.0+x*1.0))"));
    }
    
    @Test
    public void testDerivativeOneVariablePolynomial() throws IOException {

        String dev = Commands.differentiate("x*x + x*x", "x");
        
        // the derivative of polynomial x*x + x*x wrt x is (x*1+x*1)+(x*1+x*1)
        assertTrue(dev.equals("((x*1.0+x*1.0)+(x*1.0+x*1.0))"));
    }
  
    @Test
    public void testDerivativeTwoVariablesPolynomialPlus() throws IOException {

        String dev = Commands.differentiate("x+y", "x");
        
        // the derivative of polynomial x+y wrt x is 1+0
        assertTrue(dev.equals("(1.0+0.0)"));
    }
    
    @Test
    public void testDerivativeTwoVariablesPolynomialTimes() throws IOException {

        String dev = Commands.differentiate("x*y", "x");
        
        // the derivative of polynomial x*y wrt x is "x*0+y*1"
        assertTrue(dev.equals("(x*0.0+y*1.0)"));
    }
    
    @Test
    public void testDerivativeTwoVariablesPolynomial() throws IOException {

        String dev = Commands.differentiate("x*y + y*x", "x");
        
        // the derivative of polynomial x*y + y*x wrt x is (x*0+y*1)+(y*1+x*0)
        assertTrue(dev.equals("((x*0.0+y*1.0)+(y*1.0+x*0.0))"));
    }
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    /*
     * Testing strategy
     * ==================
     *
     * Get all the available copies of a book
     * 
     * String simplify (String expression, Map<String,Double> environment)
     * @param expression the expression to simplify
     * @param environment maps variables to values.  Variables are required to be case-sensitive nonempty 
     *         strings of letters.  The set of variables in environment is allowed to be different than the 
     *         set of variables actually found in expression.  Values must be nonnegative numbers.
     * @return an expression equal to the input, but after substituting every variable v that appears in both
     *         the expression and the environment with its value, environment.get(v).  If there are no
     *         variables left in this expression after substitution, it must be evaluated to a single number.
     *         Additional simplifications to the expression may be done at the implementor's discretion.
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
        String simp = Commands.simplify("1+10+100", env);
        
        // the simplification of "1+10+100" is "111"
        assertTrue(simp.equals("111.0"));
    }
  
    @Test
    public void testSimplifyConstantTime() throws IOException {
        Map<String,Double> env = new HashMap<>();
        env.put("x", 0.0);
        String simp = Commands.simplify("2*5.0*100", env);
        
        // the simplification of "2*5.0*100" is "1000"
        assertTrue(simp.equals("1000.0"));
    }
    
    @Test
    public void testSimplifyConstantSumTime() throws IOException {
        Map<String,Double> env = new HashMap<>();
        env.put("x", 0.0);
        String simp = Commands.simplify("10+5.0*(10.99+39.01)", env);
        
        // the simplification of "10+5.0*(10.99+39.01)" is "260"
        assertTrue(simp.equals("260.0"));
    }
    
    @Test
    public void testSimplifySingleVariableSum() throws IOException {
        Map<String,Double> env = new HashMap<>();
        env.put("x", 8.0);
        String simp = Commands.simplify("10 + 2 + x", env);
        
        // the simplification of "10 + 2 + x" with x=8 is "20"
        assertTrue(simp.equals("20.0"));
    }
    
    @Test
    public void testSimplifySingleVariableTime() throws IOException {
        Map<String,Double> env = new HashMap<>();
        env.put("abc", 100.0);
        String simp = Commands.simplify("10 * 2 * abc", env);
        
        // the simplification of "10 * 2 * abc" with abc=100 is "2000"
        assertTrue(simp.equals("2000.0"));
    }
    
    @Test
    public void testSimplifySingleVariableSumTime() throws IOException {
        Map<String,Double> env = new HashMap<>();
        env.put("var", 100.0);
        String simp = Commands.simplify("10 + 2 * var", env);
        
        // the simplification of "10 + 2 * var" with var=100 is "210"
        assertTrue(simp.equals("210.0"));
    }
    
    @Test
    public void testSimplifyWrongVariableSum() throws IOException {
        Map<String,Double> env = new HashMap<>();
        env.put("x", 8.0);
        String simp = Commands.simplify("10 + 2 + y", env);
        
        // the simplification of "10 + 2 + y" with x=8 is "12+y"
        assertTrue(simp.equals("(12.0+y)"));
    }
    
    @Test
    public void testSimplifyTwoVariablesSum() throws IOException {
        Map<String,Double> env = new HashMap<>();
        env.put("x", 8.0);
        String simp = Commands.simplify("10 + 2 + x + y", env);
        
        // the simplification of "10 + 2 + x + y" with x=8 is "20+y"
        assertTrue(simp.equals("(20.0+y)"));
    }
    
    @Test
    public void testSimplifyTwoVariablesSum2() throws IOException {
        Map<String,Double> env = new HashMap<>();
        env.put("x", 8.0);
        env.put("y", 80.0);
        String simp = Commands.simplify("10 + 2 + x + y", env);
        
        // the simplification of "10 + 2 + x +y with x = 8 y = 80" is "100"
        assertTrue(simp.equals("100.0"));
    }
    
    @Test
    public void testSimplifyTwoVariablesTime() throws IOException {
        Map<String,Double> env = new HashMap<>();
        env.put("x", 8.0);
        String simp = Commands.simplify("10 * 2 * x * y", env);
        
        // the simplification of "10 * 2 * x * y" with x = 8 is "160*y"
        assertTrue(simp.equals("(160.0*y)"));
    }
    
    @Test
    public void testSimplifyTwoVariablesTime2() throws IOException {
        Map<String,Double> env = new HashMap<>();
        env.put("x", 8.0);
        env.put("y", 80.0);
        
        String simp = Commands.simplify("10 * 2 * x * y", env);
        
        // the simplification of "10 * 2 * x * y with x = 8 y = 80" is "12800"
        assertTrue(simp.equals("12800.0"));
    }
    
    @Test
    public void testSimplifyTwoVariablesSumTime() throws IOException {
        Map<String,Double> env = new HashMap<>();
        env.put("x", 8.0);
        
        String simp = Commands.simplify("(10 + 2) * x + y", env);
        
        // the simplification of "(10 + 2) * x + y with x = 8" is "96+y"
        assertTrue(simp.equals("(96.0+y)"));
    }
    
    @Test
    public void testSimplifyTwoVariablesSumTime2() throws IOException {
        Map<String,Double> env = new HashMap<>();
        env.put("x", 8.0);
        env.put("y", 80.0);
        
        String simp = Commands.simplify("10 * (2 + x) * y", env);
        
        // the simplification of "10 * (2 + x) * y with x = 8 y = 80" is "8000"
        assertTrue(simp.equals("8000.0"));
    }
    
    // TODO tests for Commands.differentiate() and Commands.simplify()
    /*
     final String expression = "x*x*y + y*(1+x)";
     final Map<String, Double> environment = new HashMap<>();
     environment.put("x", 2.0);  
     final String simplified = Expression.parse(expression).simplify(environment).toString();
     System.out.println(simplified);
     ---
     Output:
     ((4.0*y)+(y*3.0))
     */
    
}
