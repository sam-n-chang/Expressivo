package expressivo;

import java.io.IOException;
import java.io.File;
import java.util.List;

import lib6005.parser.*;

public class Parse {
/*    
    //
    // Traverse a parse tree, indenting to make it easier to read.
    // @param node
    // Parse tree to print.
    // @param indent
    // Indentation to use.
    //
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
    
    //
    // Function converts a ParseTree to an IntegerExpression. 
    // @param p
    //  ParseTree<IntegerGrammar> that is assumed to have been constructed by the grammar in IntegerExpression.g
    // @return
    //
    static IntegerExpression buildAST(ParseTree<IntegerGrammar> p){
        // print the name of tree
        // System.out.println("buildAST ("+p.getName()+")");

        switch(p.getName()){
        //
        // Since p is a ParseTree parameterized by the type IntegerGrammar, p.getName() 
        // returns an instance of the IntegerGrammar enum. This allows the compiler to check
        // that we have covered all the cases.
        //
        case NUMBER:
            //
            // A number will be a terminal containing a number.
            //
            return new Number(Integer.parseInt(p.getContents()));
        case PRIMITIVE:
            //
            // A primitive will have either a number or a sum as child (in addition to some whitespace)
            // By checking which one, we can determine which case we are in.
            //             

            if(p.childrenByName(IntegerGrammar.NUMBER).isEmpty()){
                return buildAST(p.childrenByName(IntegerGrammar.SUM).get(0));
            }else{
                return buildAST(p.childrenByName(IntegerGrammar.NUMBER).get(0));
            }

        case SUM:
            //
            // A sum will have one or more children that need to be summed together.
            // Note that we only care about the children that are primitive. There may also be 
            // some whitespace children which we want to ignore.
            //
            boolean first = true;
            IntegerExpression result = null;
            for(ParseTree<IntegerGrammar> child : p.childrenByName(IntegerGrammar.PRIMITIVE)){      
                if(first){
                    result = buildAST(child);
                    first = false;
                }else{
                    result = new Plus(result, buildAST(child));
                }
            }
            if (first) {
                throw new RuntimeException("sum must have a non whitespace child:" + p);
            }
            return result;
        case ROOT:
            //
            // The root has a single sum child, in addition to having potentially some whitespace.
            //
            return buildAST(p.childrenByName(IntegerGrammar.SUM).get(0));
        case WHITESPACE:
            //
            // Since we are always avoiding calling buildAST with whitespace, 
            // the code should never make it here. 
            //
            throw new RuntimeException("You should never reach here:" + p);
        }   
        //
        // The compiler should be smart enough to tell that this code is unreachable, but it isn't.
        //
        throw new RuntimeException("You should never reach here:" + p);
    }
    
    public static void main(String[] args) throws IOException {
        try {
            // to create a parser based on the defined grammar.
            Parser<IntegerGrammar> parser =
                GrammarCompiler.compile(new File("src/expressivo/IntegerExpression.g"), IntegerGrammar.ROOT);
            
            // use the parser to parse a string to create a parse tree.
            ParseTree<IntegerGrammar> tree = parser.parse("1+2+3+4");
            
            // print the parse tree.
            // ROOT:"5+2+3+21"{SUM:"5+2+3+21"{PRIMITIVE:"5"{NUMBER:"5"},PRIMITIVE:"2"{NUMBER:"2"},PRIMITIVE:"3"{NUMBER:"3"},PRIMITIVE:"21"{NUMBER:"21"}}}
            System.out.println("* The whole parse tree:");
            System.out.println(tree.toString());
            
            // get symbol for the terminal/non-terminal corresponding to this tree.
            System.out.print("* tree.getName():");
            System.out.println(tree.getName());
            
            // get the original string corresponding to this parse tree.
            System.out.print("* tree.getContents():");
            System.out.println(tree.getContents());
            
            // ordered list of all children ParseTree of this ParseTree node.
            List <ParseTree<IntegerGrammar>> children = tree.children();
            for (ParseTree<IntegerGrammar> c : children) {
                System.out.println("* list of tree.children():");
                System.out.println(c.toString());
            }
            
            
            // get all children ParseTree match that non-terminal name.
            //
            List <ParseTree<IntegerGrammar>> nodeList = tree.childrenByName(IntegerGrammar.SUM);
            for (ParseTree<IntegerGrammar> p : nodeList) {
                System.out.println("* list of tree.childrenByName(SUM):");
                System.out.println(p.toString());
            }
            
            // traverse the parse tree - important!
            visitAll(tree, "* ");
            
            // build an abstract syntax tree
            IntegerExpression ast = buildAST(tree);
            System.out.println("AST = "+ast.toString());
            
            // print a visualization of the tree in the browser (must be connected to internet).
            // tree.display();
            
        } catch (UnableToParseException upe) {
            System.out.println("UnableToParseException!");
        }
    }
*/
}
