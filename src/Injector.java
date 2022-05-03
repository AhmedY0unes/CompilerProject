import java.io.*;
import org.antlr.v4.runtime.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Injector{
    public static String injector(String path) throws Exception {
        JavaLexer lex = new JavaLexer(CharStreams.fromFileName(path));
        CommonTokenStream tokens = new CommonTokenStream(lex);
        JavaParser parser = new JavaParser(tokens);
        TokenStreamRewriter r = new TokenStreamRewriter(tokens);
        parser.compilationUnit();
        String allowedTokens[] = {"if" , "while" , "for" , "do" , "try" , "catch" , "case", "else" , "default", "public", "private", "protected"};
        String tokenText = "";
        r.insertBefore(0, "import java.io.FileWriter;\n");
        r.insertBefore(0, "import java.io.IOException;\n");
        r.insertBefore(0, "package output;\n");
        int c = 0 , j = 0;
        int firstRepeating = 0;
        int counter = 0;
        boolean gotFirst = false;
        for(int i = 0 ; i < tokens.size()-1; i++){
            if (tokens.get(i).getText().equals("for") || tokens.get(i).getText().equals("while")){
                if(!gotFirst){
                    firstRepeating = i;
                    gotFirst = true;
                }
                r.insertBefore(firstRepeating,"boolean f" + counter++ + "  = false;\n");
            }

        }
        boolean thereIsADo = false;
        for (int i = 0 ; i < tokens.size()-1; i++ ) {
            for(String ele : allowedTokens){
                if(tokens.get(i).getText().equals(ele)){
                    tokenText = tokens.get(i).getText();
                }
            }

            if(tokenText.equals("do")){
                thereIsADo = true;
            }
            if(tokenText.equals("else")){
                String oneAfter = tokens.get(i+1).getText();
                while(oneAfter.charAt(0) == ' ' || oneAfter.charAt(0) == '\n'){
                    oneAfter = tokens.get(++i).getText();
                }
                if(oneAfter.equals("if")) {
                    --i;
                    continue;
                };
                if(!oneAfter.equals("{")) {
                    r.insertAfter(i+1 , "{\ntry { FileWriter myWriter = new FileWriter(\".\\\\output\\\\output.txt\",true); myWriter.write(\"Block"+ ++c + "\\n\"); myWriter.close();} catch (IOException e) {e.printStackTrace();}\n");
                    while(i++ < tokens.size() - 1){
                        if(tokens.get(i).getText().equals(";")){
                            r.insertAfter(i, "}\n");
                            break;
                        }
                    }
                    tokenText = "";
                }
            }
            if(tokenText.equals("while") && thereIsADo){
                tokenText = "";
                continue;
            }

            if(tokens.get(i).getText().equals(":") && (tokenText.equals("case") || tokenText.equals("default"))){
                tokenText = "";
                r.insertAfter(i, "\n try { FileWriter myWriter = new FileWriter(\".\\\\output\\\\output.txt\",true); myWriter.write(\"Block"+ ++c + "\\n\"); myWriter.close();} catch (IOException e) {e.printStackTrace();}\n");
            }
            if(tokens.get(i).getText().equals("{") && (tokenText.equals("class") || tokenText.equals("else"))){
                r.insertAfter(i, "\n try { FileWriter myWriter = new FileWriter(\".\\\\output\\\\output.txt\",true); myWriter.write(\"Block"+ ++c + "\\n\"); myWriter.close();} catch (IOException e) {e.printStackTrace();}\n");
                tokenText = "";
            }
            if(tokens.get(i).getText().equals("{") && tokenText.equals("do") ){
                r.insertAfter(i, "if(!f" + j +"){\ntry { FileWriter myWriter = new FileWriter(\".\\\\output\\\\output.txt\",true); myWriter.write(\"Block"+ ++c + "\\n\"); myWriter.close();} catch (IOException e) {e.printStackTrace();}\n f" + j +" = true;} \n");
                tokenText = "";
            }
            if(tokens.get(i).getText().equals(")") && !tokenText.equals("")){
                String oneAfter = tokens.get(i+1).getText();
                while(oneAfter.charAt(0) == ' ' || oneAfter.charAt(0) == '\n'){
                    oneAfter = tokens.get(++i).getText();
                }
                if(oneAfter.equals("{")){
                    if(tokenText.equals("for") || tokenText.equals("while")){
                        r.insertAfter(i + 1, "if(!f" + j +"){\ntry { FileWriter myWriter = new FileWriter(\".\\\\output\\\\output.txt\",true); myWriter.write(\"Block"+ ++c + "\\n\"); myWriter.close();} catch (IOException e) {e.printStackTrace();}\n f" + j +" = true;} \n");
                        j++;
                    }
                    else{
                        r.insertAfter(i + 1, "\n try { FileWriter myWriter = new FileWriter(\".\\\\output\\\\output.txt\",true); myWriter.write(\"Block"+ ++c + "\\n\"); myWriter.close();} catch (IOException e) {e.printStackTrace();}\n");
                    }
                }
                else{
                    if(tokenText.equals("for") || tokenText.equals("while")){
                        r.insertAfter(i, "{if(!f" + j +"){try { FileWriter myWriter = new FileWriter(\".\\\\output\\\\output.txt\",true); myWriter.write(\"Block"+ ++c + "\\n\"); myWriter.close();} catch (IOException e) {e.printStackTrace();};\n f" + j +" = true;} \n");
                        j++;
                        while(i++ < tokens.size() - 1){
                            if(tokens.get(i).getText().equals(";")){
                                r.insertAfter(i, "}\n");
                                break;
                            }
                        }
                    }
                    else {
                        r.insertAfter(i+1 , "{\ntry { FileWriter myWriter = new FileWriter(\".\\\\output\\\\output.txt\",true); myWriter.write(\"Block"+ ++c + "\\n\"); myWriter.close();} catch (IOException e) {e.printStackTrace();}\n");
                        while(i++ < tokens.size() - 1){
                            if(tokens.get(i).getText().equals(";")){
                                r.insertAfter(i, "}\n");
                                break;
                            }
                        }
                    }
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

    public static String htmlInjector(String path) throws Exception {
        JavaLexer lex = new JavaLexer(CharStreams.fromFileName(path));
        CommonTokenStream tokens = new CommonTokenStream(lex);
        JavaParser parser = new JavaParser(tokens);
        String allowedTokens[] = {"if" , "while" , "for" , "do" , "try" , "catch" , "case", "else" , "default", "public", "private", "protected"};
        TokenStreamRewriter r = new TokenStreamRewriter(tokens);
        parser.compilationUnit();
        String tokenText = "";
        int c = 0;
        Map map=new HashMap();
        boolean thereIsADo = false;
        r.insertBefore(0, "<div style=\"font-family:arial;background-color:#95FC65;\">\n");
        for (int i = 0 ; i < tokens.size()-1; i++ ) {
            for(String ele : allowedTokens){
                int j = i+1;
                String oneAfter = tokens.get(j).getText();
                while(oneAfter.charAt(0) == ' ' || oneAfter.charAt(0) == '\n'){
                    oneAfter = tokens.get(++j).getText();
                }
                if(tokens.get(i).getText().equals("while") && thereIsADo){
                    break;
                }
                if(tokens.get(i).getText().equals(ele) && !oneAfter.equals("class")){
                    tokenText = tokens.get(i).getText();
                    r.insertBefore(i, "\n<div id=\"Block" + ++c +"\">\n");
                }
            }

            if(tokenText.equals("do")){
                thereIsADo = true;
            }
            if(tokens.get(i).getText().equals("while") && thereIsADo){
                tokenText = "";
                while(i++ < tokens.size() - 1){
                    if(tokens.get(i).getText().equals(";")){
                        r.insertAfter(i, "\n</div>\n");
                        break;
                    }
                }
                thereIsADo = false;
                continue;
            }

            if(tokenText.equals("else")){
                String oneAfter = tokens.get(i+1).getText();
                while(oneAfter.charAt(0) == ' ' || oneAfter.charAt(0) == '\n'){
                    oneAfter = tokens.get(++i).getText();
                }
                if(!oneAfter.equals("{")) {
                    for(int k=i;k<tokens.size() - 1;k++){
                        if(tokens.get(k).getText().equals("}")){
                            if(map.containsKey(k)){
                                continue;
                            }
                            map.put(k, true);
                            r.insertAfter(k, "\n</div>\n");
                            break;
                        }
                    }
                }
            }

            if(tokens.get(i).getText().equals("{") && (tokenText.equals("class") || tokenText.equals("else"))){
                for(int k=i;k<tokens.size() - 1;k++){
                    if(tokens.get(k).getText().equals("}")){
                        if(map.containsKey(k)){
                            continue;
                        }
                        map.put(k, true);
                        r.insertAfter(k, "\n</div>\n");
                        break;
                    }
                }
            }
            if(tokens.get(i).getText().equals(":") && (tokenText.equals("case") || tokenText.equals("default"))){
                tokenText = "";
                while(i++ < tokens.size() - 1){
                    if(tokens.get(i).getText().equals("break")){
                        while (i++ < tokens.size() - 1){
                            if(tokens.get(i).getText().equals(";")){
                                r.insertAfter(i+1, "\n</div>\n");
                                tokenText="";
                                break;
                            }
                        }
                        break;
                    }
                    if(tokens.get(i).getText().equals("}") ){
                        r.insertBefore(i, "\n</div>\n");
                        tokenText="";
                        break;
                    }
                }
            }
            if(tokens.get(i).getText().equals(")")  && !tokenText.equals("")){
                String oneAfter = tokens.get(i+1).getText();
                while(oneAfter.charAt(0) == ' ' || oneAfter.charAt(0) == '\n'){
                    oneAfter = tokens.get(++i).getText();
                }
                if(oneAfter.equals("{")){
                    for(int k=i;k<tokens.size() - 1;k++){
                        if(tokens.get(k).getText().equals("}")){
                            if(map.containsKey(k)){
                                continue;
                            }
                            map.put(k, true);
                            r.insertAfter(k, "\n</div>\n");
                            break;
                        }
                    }
                }
                else{
                    while(i++ < tokens.size() - 1){
                        if(tokens.get(i).getText().equals(";")){
                            r.insertAfter(i, "\n</div>\n");
                            break;
                        }
                    }
                }
                tokenText = "";
            }
            if(i == tokens.size()-2){
                r.insertAfter(i, "\n</div>\n<script>\nvar c = "+ c +"\nfor(var i = 1;i<=c;i++)document.getElementById(\"Block\" + i).style.backgroundColor = \"#E44F51\"\nfetch('.\\\\output.txt').then(response => response.text()).then(text => {arr = text.split(\"\\n\");for (var i =0; i < arr.length;i++){document.getElementById(arr[i]).style.backgroundColor = \"#95FC65\"}})\n</script>");
            }
        }
        return r.getText();
    }



    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        String inputPath = sc.nextLine();
        String post = inputPath.substring(inputPath.lastIndexOf('\\'));
        String initPre = inputPath.substring(0 , inputPath.lastIndexOf('\\'));
        String pre = initPre.substring(0, initPre.lastIndexOf('\\'));
        String fileName = post.substring(1, post.lastIndexOf('.'));
        String outputPath = pre + "\\output" + post;
        String x = injector(inputPath);
        String y = htmlInjector(inputPath);
        BufferedWriter outputWriter = new BufferedWriter(new FileWriter(".\\output\\output.txt"));
        outputWriter.write("");
        outputWriter.close();
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath));
        writer.write(x);
        writer.close();
        BufferedWriter htmlWriter = new BufferedWriter(new FileWriter(".\\output\\"+fileName+".html"));
        htmlWriter.write(y);
        htmlWriter.close();



        }
    }
///home/mohamed/CompilerProject/src/Test.java