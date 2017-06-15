package expressivo;

import java.util.Map;

/**
 * Variables is one of the terminals represented by case-sensitive nonempty strings of letters.
 *   
 */
public class Variable implements Expression {
    //rep
    //contents - the string value of this variable in the expression
    //val - the value of this variable in the expression
    // values - contains both String contents and double value (if assigned) for this variable

    private final String contents;
    private double val = Double.NaN;
    
    // rep invariant:
    //    contents is a string.
    //    value holds the value assigned to this variable.
    //
    // All reps are private so no rep exposure risk.
    
    /**
     * constructor
     * @param String var - String representation of this variable
     */
    Variable (String var) {
        this.contents = var;
        this.val = Double.NaN;
    }
    
    /**
     * @returns the string of Variable
     * the input value v is not used, will be ignored.
     */
    public String getContents() {
        return this.contents;
    };
    
    /**
     * @returns the value of the Expression after being simplified (evaluated).
     */
    public double getValue() {
        return this.val;
    }
    
    /**
     * @param String var - differentiate the expression with respect to the variable var.
     * @returns an expression tree for the derivative of this expression
     */
    public Expression derivative (String var) {
        // if this variable matches the one to be differentiated with
        // then the derivative of this variable is 1,
        //    else 
        // all other variables will be treated as a constant which has a derivative of 0.
        if (this.contents.equals(var)) {
            return new Number (1);
        } else
            return new Number (0);
    };
       
    
    /**
     * @param environment maps variables to values.  Variables are required to be case-sensitive nonempty 
     *         strings of letters.  The set of variables in environment is allowed to be different than the 
     *         set of variables actually found in expression.  Values must be nonnegative numbers.
     * @returns the value for this Variable object if it is found in the environment.
     * 
     */

    public Expression simplify (Map<String,Double> environment) {
        // find if the key (variable) is in the map, if so then get it's value
        // if not then NaN will be returned.
        if (environment.containsKey(this.contents)) {
            this.val = environment.get(this.contents);
            return new Number (this.val);
        } else { // since no value assigned to this variable, just return the object itself
            return this;
        }
    }
    
    
    /**
     * @return the expression in fully parenthesized form
     *
    public String printInfix () {
        return this.getContents();
    }
    */
    
    /**
     * @return a parsable representation of this expression, such that
     * for all e:Expression, e.equals(Expression.parse(e.toString())).
     */
    @Override 
    public String toString() {
        return this.contents;
        /*
        // if there is no value assigned to this variable then 
        //    return the string form of variable name
        // else
        //    return the string form of the value
        if (Double.isNaN(this.val)) {
            return this.contents;
        } else {
            // if this double is an integer then just return the integer portion (1.0 -> 1)
            if ((this.val % 1) == 0) {
                return String.valueOf((int)(this.val));
            } else {
                return String.valueOf(this.val);
            }
        }
        */
    };
    
    /**
     * @param thatObject any object
     * @return true if and only if this and thatObject are structurally-equal
     * Expressions. ie. the same char strings.
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
    /**
     * Returns a hash code for this string. The hash code for a
     * <code>String</code> object is computed as
     * <blockquote><pre>
     * s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]
     * </pre></blockquote>
     * using <code>int</code> arithmetic, where <code>s[i]</code> is the
     * <i>i</i>th character of the string, <code>n</code> is the length of
     * the string, and <code>^</code> indicates exponentiation.
     * (The hash value of the empty string is zero.)
     *
     * @return  a hash code value for this object.
     */
    @Override
    public int hashCode() {
        final int prime = 37;
        int result = 17;
        
        result = prime * result + this.getContents().hashCode();
        
        return result;
    }
}
