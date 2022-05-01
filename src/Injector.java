import java.io.*;
import org.antlr.v4.runtime.*;
import java.util.Scanner;

public class Injector{
    public static String injector(String path) throws Exception {

        System.out.println(path);
        JavaLexer lex = new JavaLexer(CharStreams.fromFileName(path));

        CommonTokenStream tokens = new CommonTokenStream(lex);

        JavaParser parser = new JavaParser(tokens);
        String allowedTokens[] = {"if" , "while" , "for" , "do" , "try" , "catch" , "case", "class" , "else" , "default"};
        String texts = "";
        TokenStreamRewriter r = new TokenStreamRewriter(tokens);
        parser.compilationUnit();
        String tokenText = "";
        r.insertBefore(0, "package output;");
        int c = 0;
        boolean thereIsADo = false;
        for (int i = 0 ; i < tokens.size()-1; i++ ) {
            System.out.println(tokens.get(i).getText());
            for(String ele : allowedTokens){
                if(tokens.get(i).getText().equals(ele)){
                    tokenText = tokens.get(i).getText();
                }
            }
            if(tokenText.equals("do")){
                thereIsADo = true;
            }
            if(tokenText.equals("while") && thereIsADo){
                tokenText = "";
                continue;
            }
            if(tokens.get(i).getText().equals(":") && (tokenText.equals("case") || tokenText.equals("default"))){
                r.insertAfter(i, "\n System.out.println(\"" + "Block" + ++c + "\"); \n");
                tokenText = "";
            }
            if(tokens.get(i).getText().equals("{") && (tokens.equals("class") || tokenText.equals("else"))){
                r.insertAfter(i, "\n System.out.println(\"" + "Block" + ++c + "\"); \n");
                tokenText = "";
            }
            if(tokens.get(i).getText().equals(")") && !tokenText.equals("")){
                String oneAfter = tokens.get(i+1).getText();
                while(oneAfter.charAt(0) == ' ' || oneAfter.charAt(0) == '\n'){
                    oneAfter = tokens.get(++i).getText();
                }
                if(oneAfter.equals("{")){
                    r.insertAfter(i + 1, "\n System.out.println(\"" + "Block" + ++c + "\"); \n");
                }
                else{
                    r.insertAfter(i, "\n System.out.println(\"" + "Block" + ++c + "\"); \n");
                }
                tokenText = "";
            }
        }
        return r.getText();
//        if the token is one of the allowed tokens save it
//        then if you find a ) , check of it is followed by a { or not
//        if not insert the print statement
//        else move one token and insert the print statement
//        special case for do while statement, class and else
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        String inputPath = sc.nextLine();
        System.out.println(inputPath);
        boolean f = false;
        String post = inputPath.substring(inputPath.lastIndexOf('/'));
        String initPre = inputPath.substring(0 , inputPath.lastIndexOf('/'));
        String pre = initPre.substring(0, initPre.lastIndexOf('/'));
        String outputPath = pre + "/output" + post;
        String x = injector(inputPath);
        System.out.println(x);

            BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath));
            writer.write(x);

            writer.close();

    }
}
///home/mohamed/CompilerProject/src/Test.java