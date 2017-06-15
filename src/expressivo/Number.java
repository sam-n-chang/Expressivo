package expressivo;

import java.util.Map;
import java.math.BigDecimal;

/**
 * Number is one of the terminals with value of:
 *   nonnegative integers and floating-point numbers.
 *   
 */
public class Number implements Expression {

    //rep
    // val - the value of this number in the expression
    //
    private final double val;
    
    // rep invariant:
    //
    // All reps are private so no rep exposure risk.
    
    /**
     * constructor
     * @param double num - value (double) of this number (string)
     */
    Number (double num) {
        this.val = num;
    }
    
    /**
     * @returns the string representation of the number.
     */
    public String getContents() {
        return Double.toString(this.val);
    }
    
    /**
     * @returns the value of the Expression after being simplified (evaluated).
     */
    public double getValue() {
        return this.val;
    }
    
    /**
     * @param String var - differentiate the expression with respect to the variable var. Not used for constant.
     * @returns an expression tree for the derivative of this expression
     */
    public Expression derivative (String var) {
        //derivative of a constant is zero
        return new Number(0);
    };
    
    /**
     * @param environment maps variables to values.  Variables are required to be case-sensitive nonempty 
     *         strings of letters.  The set of variables in environment is allowed to be different than the 
     *         set of variables actually found in expression.  Values must be nonnegative numbers.
     *         Not used in Number object.
     * @returns the value of this Number object.
     * 
     */
    
    public Expression simplify (Map<String,Double> environment) {
        return new Number (this.val);
    }
    
    /**
     * @return the expression in fully parenthesized form
     *
    public String printInfix () {
        return Double.toString(this.val);
    }
    */
       
    /**
     * @return a parsable representation of this expression, such that
     * for all e:Expression, e.equals(Expression.parse(e.toString())).
     */
    @Override 
    public String toString() {
        //return "Number("+String.valueOf(this.val)+")";
        // if this double is an integer then just return the integer portion (1.0 -> 1)
        if ((this.val == Math.floor(this.val)) && !Double.isInfinite(this.val)) {
            return BigDecimal.valueOf(this.val).toPlainString();
        } else {
            // return number in decimal format, without scientific notation such as 5.0*E-5
            return BigDecimal.valueOf(this.val).toPlainString();
        }
    };
    /**
     * @param thatObject any object
     * @return true if and only if the string representation of
     * the numbers are the same
     */
    @Override
    public boolean equals(Object thatObject) {
        if (!(thatObject instanceof Expression)) return false;
        Expression thatExpression = (Expression) thatObject;
        return this.getContents().equals(thatExpression.getContents());
    }

    /**
     * @return hash code value consistent with the equals() definition of structural
     * equality, such that for all e1,e2:Expression,
     *     e1.equals(e2) implies e1.hashCode() == e2.hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 37;
        int result = 17;
        
        result = prime * result + this.getContents().hashCode();
        
        return result;
    }
}
