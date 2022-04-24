import java.io.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class Injector{
    public static String injector(String path) throws Exception{

        System.out.println(path);
        JavaLexer lex = new JavaLexer(CharStreams.fromFileName(path));

        CommonTokenStream tokens = new CommonTokenStream(lex);

        JavaParser parser = new JavaParser(tokens);
        String texts = "";
        TokenStreamRewriter r = new TokenStreamRewriter(tokens);
        parser.compilationUnit();
        String tokenTextPre = "";
        int c = 0;
        for (int i = 0 ; i < tokens.size()-1; i++ ) {

            if(!tokens.get(i).getText().equals(" ") && !tokens.get(i).getText().equals("\n") && !tokens.get(i).getText().equals("\r") )
                tokenTextPre = tokens.get(i).getText();
            String tokenText = tokens.get(i+1).getText();

            if ( tokenTextPre.equals(")") && tokenText.equals("{") ) {
                r.insertAfter(i+1, "\n System.out.println(\"" + "Block" + ++c + "\"); \n");

            }

        }
        return r.getText();
    }

    public static void main(String[] args) throws Exception {
        String x = injector("/home/younes/finally/assignment/CompilerProject/src/Test.java");
        System.out.println(x);

            BufferedWriter writer = new BufferedWriter(new FileWriter("./output/Test.java"));
            writer.write(x);

            writer.close();

    }
}
