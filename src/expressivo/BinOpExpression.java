package expressivo;

import java.util.Map;

/**
 * BinOpExpression represents a binary operation expression. 
 * It is formed by an operator and two operands (left and right)
 * Currently only '+' and '*' are supported.
 *   
 */
public class BinOpExpression implements Expression {
    private char op;           // the operator used in this binary operation expression
    private Expression left;   // the expression for its left operand
    private Expression right;  // the expression for its right operand
    private final String contents; // the string representation of this expression
    private double val = Double.NaN; // the value of this expression after being evaluated (simplified)
    
    // rep invariant:
    //    left != null
    //    right != null
    //
    // All reps are private so no rep exposure risk.
    
    /**
     * constructor
     * @param char op - operator for this binary expression. can be either '+' or '*'
     * @param Expression l, r - the left and right operands of this binary expression.
     */
    public BinOpExpression (char op, Expression l, Expression r) {
        this.op = op;
        this.left = l;
        this.right = r;
        this.contents = "("+l.getContents() + op + r.getContents()+")";
        
        checkRep();
    }
    
    /**
     * @returns string representation of the Expression
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
        switch (op) {
            case '+':
                //derivative of X + Y is dX + dY
                return new BinOpExpression('+', this.left.derivative(var), right.derivative(var));
            case '*':
                //derivative of X * Y is X * dY + Y * dX
                return new BinOpExpression('+', new BinOpExpression('*', left, right.derivative(var)),
                                                new BinOpExpression('*', right, left.derivative(var)));
            default:
                return null;
        }
    };
    
    
    /**
     * @param environment maps variables to values.  Variables are required to be case-sensitive nonempty 
     *         strings of letters.  The set of variables in environment is allowed to be different than the 
     *         set of variables actually found in expression.  Values must be nonnegative numbers.
     * @returns the value for this BinOpExpression object after it's evaluated.
     * 
     */
     public Expression simplify (Map<String,Double> environment) {

        Expression x = left.simplify (environment);
        Expression y = right.simplify (environment);
        double valueX = x.getValue();
        double valueY = y.getValue();
        
        //System.out.println("BinOpExp valueX="+valueX+", valueY="+valueY);

        switch (this.op) {
            case '+' : 
                if ((!Double.isNaN(valueX)) && (!Double.isNaN(valueY))) {
                    return new Number (valueX+valueY);
                } else {
                    return new BinOpExpression ('+', x, y);
                }
            case '*' : 
                if ((!Double.isNaN(valueX)) && (!Double.isNaN(valueY))) {
                    return new Number (valueX*valueY);
                } else {
                    return new BinOpExpression ('*', x, y);
                }
            default  : 
                throw new RuntimeException("operator "+this.op+" not supported!");
        }
    }
  
    /**
     * return the expression in fully parenthesized form
     *
    public String printInfix () {
        return "("+left.printInfix()+op+right.printInfix()+")";
        //
        //System.out.print("(");
        //left.printInfix();
        //System.out.print(" " + op + " ");
        //right.printInfix();
        //System.out.print(")");
        //
    }
    */
    
    /**
     * assert the rep invariant
     */
    private void checkRep() {
        if ((this.op != '+') && (this.op != '*')) {
            throw new RuntimeException("invalid operator!");
        }
        if ((this.left == null) || (this.right == null)) {
            throw new RuntimeException("null pointer encountered!");
        }
    }
    
    /**
     * @return a parsable representation of this expression, such that
     * for all e:Expression, e.equals(Expression.parse(e.toString())).
     */
    @Override 
    public String toString() {
        String str;
        //str = "Op(" + this.op+ ")(" + left.toString() + "," + right.toString() + ")";
        if (this.op == '+') {
            str = "("+left.toString()+this.op+right.toString()+")";
        } else {  // op == '*'
            str = left.toString()+this.op+right.toString();
        }
        return str;
    };
    /**
     * @param thatObject any object
     * @return true if and only if this and thatObject are structurally-equal
     * Expressions, as defined in the PS1 handout.
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
