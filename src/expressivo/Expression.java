package expressivo;

import java.io.File;
import java.io.IOException;
import java.lang.Exception;
import java.util.Map;

import expressivo.Number;
import lib6005.parser.*;

enum IntegerGrammar {ROOT, PRIMITIVE, SUM, PRODUCT, VARIABLE, NUMBER, WHITESPACE};


/**
 * An immutable data type representing a polynomial expression of:
 *   + and *
 *   nonnegative integers and floating-point numbers
 *   variables (case-sensitive nonempty strings of letters)
 * 
 * <p>PS1 instructions: this is a required ADT interface.
 * You MUST NOT change its name or package or the names or type signatures of existing methods.
 * You may, however, add additional methods, or strengthen the specs of existing methods.
 * Declare concrete variants of Expression in their own Java source files.
 */
public interface Expression {
    
    /**
     * @returns string representation of the Expression
     */
    abstract String getContents ();
    
    /**
     * @returns the value of the Expression after being simplified (evaluated).
     */
    abstract double getValue ();
    
    /**
     * @param String var - differentiate the expression with respect to the variable var.
     * @returns an expression tree for the derivative of this expression with the following format:
     * 
     *    dc/dx = 0
     *    dx/dx = 1
     *    d(u+v)/dx = du/dx + dv/dx
     *    d(u*v)/dx = u*(dv/dx) + v*(du/dx)
     *    
     *    where c is a constant or variable other than the variable we are differentiating with respect to 
     *    (in this case x), and u and v can be anything, including x.
     */
    abstract Expression derivative (String var);
    
    /**
     * @param environment maps variables to values.  Variables are required to be case-sensitive nonempty 
     *         strings of letters.  The set of variables in environment is allowed to be different than the 
     *         set of variables actually found in expression.  Values must be nonnegative numbers.
     * @returns the value for the expression after being evaluated.
     * 
     */
    abstract Expression simplify (Map<String,Double> environment);
      
    /**
     * @return the expression in fully parenthesized form
     */
    //abstract String printInfix();
    
    /*
     * Traverse a parse tree, indenting to make it easier to read.
     * @param node
     * Parse tree to print.
     * @param indent
     * Indentation to use.
     */
    static void visitAll(ParseTree<IntegerGrammar> node, String indent) {
        if (node.isTerminal()) {
            System.out.println(indent + node.getName() + ":" + node.getContents());
        } else {
            System.out.println(indent + node.getName());
            
            // iterate all children
            for (ParseTree<IntegerGrammar> child: node) {
                visitAll(child, indent + "   ");
            }
        }
    }
    /*
     * method to build a Parser based on the grammar specified in Expression.g
     * 
     * @return
     *  return a Parser based on the grammar, null if fail to compile the grammar.
     */
    static Parser<IntegerGrammar> buildParser () {
        Parser<IntegerGrammar> parser = null;
        try {
            // to create a parser based on the defined grammar.
            parser = GrammarCompiler.compile(new File("src/expressivo/Expression.g"), IntegerGrammar.ROOT);

        } catch (UnableToParseException upe) {
            System.out.println("UnableToParseException!");
        } catch (IOException ioe) {
            System.out.println("IOException!");
        }
        return parser;
    }
    
    /**
     * Function converts a ParseTree to an Expression. 
     * @param p
     *  ParseTree<IntegerGrammar> that is assumed to have been constructed by the grammar in Expression.g
     * @return
     *  return an Abstract Syntax Tree (AST) based on parse tree passed in.
     */
    static Expression buildAST(ParseTree<IntegerGrammar> p){

        Expression result = null;        
        //boolean firstSum = true;
        boolean firstProduct = true;

        // print the name of tree
        // System.out.println("buildAST ("+p.getName()+")");

        switch(p.getName()){
        /*
         * Since p is a ParseTree parameterized by the type IntegerGrammar, p.getName() 
         * returns an instance of the IntegerGrammar enum. This allows the compiler to check
         * that we have covered all the cases.
         */
        case NUMBER:
            /*
             * A number will be a terminal containing a number.
             * grammar: [0-9]+('.' [0-9]*)?;
             */
            return new Number (Double.parseDouble(p.getContents()));
        case VARIABLE:
            /*
             * A variable will be a terminal containing a variable string.
             * grammar: variable ::= [a-zA-Z]+
             */
            return new Variable (p.getContents());
        case PRIMITIVE:
            /*
             * A primitive will have either a number or a variable or or a sum as child (in addition to some whitespace)
             * By checking which one, we can determine which case we are in.
             * grammar: primitive ::= number | variable | '(' sum ')'
             */             

            if (!p.childrenByName(IntegerGrammar.NUMBER).isEmpty()){
                return buildAST(p.childrenByName(IntegerGrammar.NUMBER).get(0));
            } else if (!p.childrenByName(IntegerGrammar.VARIABLE).isEmpty()){
                return buildAST(p.childrenByName(IntegerGrammar.VARIABLE).get(0));
            } else if (!p.childrenByName(IntegerGrammar.SUM).isEmpty()){
                return buildAST(p.childrenByName(IntegerGrammar.SUM).get(0));
            } else {
                throw new RuntimeException("primitive must have a non whitespace child:" + p);
            }

        case SUM:
            /*
             * A sum will have one or more PRODUCT children that need to be summed together.
             * There may also be some whitespace children which we want to ignore.
             * grammar: sum ::= product ('+' product)*
             */
            for (ParseTree<IntegerGrammar> child : p.childrenByName(IntegerGrammar.PRODUCT)) {      
                if (firstProduct) {
                    result = buildAST(child);
                    firstProduct = false;
                    //System.out.println("AST1 = "+result.toString()); //debug
                } else {
                    result = new BinOpExpression ('+', result, buildAST(child));
                    //System.out.println("AST2 = "+result.toString()); //debug
                }
            }
            /*
            for (ParseTree<IntegerGrammar> child : p.childrenByName(IntegerGrammar.PRIMITIVE)) {      
                if (firstSum) {
                    if (firstProduct) {
                        result = buildAST(child);
                        firstSum = false;
                        //System.out.println("AST3 = "+result.toString()); //debug
                    } else {
                        result = new BinOpExpression ('+', result, buildAST(child));
                        firstSum = false;
                        //System.out.println("AST4 = "+result.toString()); //debug
                    }
                } else {
                    result = new BinOpExpression ('+', result, buildAST(child));
                    //System.out.println("AST5 = "+result.toString()); //debug
                }
            }
            */
            //if ((firstSum) && (firstProduct)) {
            if (firstProduct) {
                throw new RuntimeException("sum must have a non whitespace child:" + p);
            }
            return result;

        case PRODUCT:
            /*
             * A product will have one or more children that need to be multiplied together.
             * Note that we only care about the children that are primitive. There may also be 
             * some whitespace children which we want to ignore.
             * grammar: product ::= primitive ('*' primitive)*
             */
            firstProduct = true;
            result = null;
            for(ParseTree<IntegerGrammar> child : p.childrenByName(IntegerGrammar.PRIMITIVE)){      
                if(firstProduct){
                    result = buildAST(child);
                    firstProduct = false;
                }else{
                    result = new BinOpExpression ('*', result, buildAST(child));
                }
            }
            if (firstProduct) {
                throw new RuntimeException("product must have a non whitespace child:" + p);
            }
            return result;
        case ROOT:
            /*
             * The root has a single sum child, in addition to having potentially some whitespace.
             * grammar: root ::= sum
             */
            return buildAST(p.childrenByName(IntegerGrammar.SUM).get(0));
        case WHITESPACE:
            /*
             * Since we are always avoiding calling buildAST with whitespace, 
             * the code should never make it here. 
             */
            throw new RuntimeException("White space encountered, should never reach here:" + p);
        }   
        /*
         * The compiler should be smart enough to tell that this code is unreachable, but it isn't.
         */
        throw new RuntimeException("No production rule applied, should never reach here:" + p);
    }
    
    /**
     * Parse an expression.
     * @param input expression to parse, as defined in the PS1 handout.
     * @return expression AST for the input
     * @throws IllegalArgumentException if the expression is invalid
     */
    public static Expression parse(String input) throws IllegalArgumentException {
        Expression ast = null;
        
        try {
            // build a Parser first
            Parser<IntegerGrammar> parser = buildParser();
         
            // use the parser to parse the input to create a parse tree.

            // to create a parser based on the defined grammar.
            ParseTree<IntegerGrammar> tree = parser.parse(input);
            
            // print a visualization of the tree in the browser (must be connected to internet).
            //tree.display();
            
            // traverse the parse tree - important!
            // visitAll(tree, "* ");
            
            ast = buildAST(tree);
            
            //build a derivative expression tree
            // Expression deriv = ast.derivative();
            // System.out.print("derivative : ");
            // deriv.printInfix();
            // System.out.println();
            
            //evaluate the value of the expression
            // double x = 2.0;
            // System.out.println("Evaluate (x = " + x + ") : "+ ast.value(x));
            
            // print the AST for debug
            //System.out.println("AST = "+ast.toString());

        } catch (UnableToParseException upe) {
            System.out.println("parser UnableToParseException!");
            throw new IllegalArgumentException("parser error!");
        }
        
        return ast;
    }
    
    /**
     * @return a parsable representation of this expression, such that
     * for all e:Expression, e.equals(Expression.parse(e.toString())).
     */
    @Override 
    public String toString();

    /**
     * @param thatObject any object
     * @return true if and only if this and thatObject are structurally-equal
     * Expressions, as defined in the PS1 handout.
     */
    @Override
    public boolean equals(Object thatObject);
    
    /**
     * @return hash code value consistent with the equals() definition of structural
     * equality, such that for all e1,e2:Expression,
     *     e1.equals(e2) implies e1.hashCode() == e2.hashCode()
     */
    @Override
    public int hashCode();
    
    // TODO more instance methods
    
    /* Copyright (c) 2015-2017 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires permission of course staff.
     */
}
