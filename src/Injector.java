import java.io.*;
import org.antlr.v4.runtime.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


import java.io.BufferedReader;
import java.io.InputStreamReader;

/// This class takes a java file as an input and output a modified intermediate java file (injected code) then run the intermediate file to get txt file that contain which block executed.
///
/// ### This class contains two functions: injector() and htmlInjector()
/// - The injector() function
/// Deals with injecting the block of code that write the blocks that have been visited in the output text file
/// - The htmlInjector() function
/// This function inject html code to color the input code. Green color for executed code, Red color for not executed code.
public class Injector{
    public static String injector(String path , String outputTxtPath) throws Exception {
        /// @param path input path for the file which we want to inject
        /// @param outputTxtPath output path for the txt file which we will write into it the executed blocks
        /// @return **r.getText()** java output code as a string.

        /// This Function insert file writer function with counter after some specified tokens like if, while, for, etc... to detect in runtime if this block executed or not.
        ///
        /// This Function use JavaLexer and JavaParser to extract tokens from the input code. Insert imports and packages at the beginning of the output file. Make counter (to count each block). Then, We will iterate the whole tokens of input file. Until we get one of supported token
        /// We insert after this token, file writer function which contain string "block " concatenate with counter which we mention before to count each block.
        /// After we run this function we will have new java code with injected code in it as string.
        /// ### Supported Tokens
        /// - if
        /// - whfile
        /// - for
        /// - do
        /// - try
        /// - catch
        /// - case
        /// - else
        /// - default
        /// - public
        /// - private
        /// - protected
        /// @note Every Token has a way to handle with it

        /// @post We will have output code string as output. We will write it in java file in main() function. Then, This output java file will be executed in run() function and write the executed block counter in output.txt
        /// @todo Test more recursive tokens in supported tokens

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
                }
                if(!oneAfter.equals("{")) {
                    r.insertAfter(i+1 , "{\ntry { FileWriter myWriter = new FileWriter(" +outputTxtPath+",true); myWriter.write(\"Block"+ ++c + "\\n\"); myWriter.close();} catch (IOException e) {e.printStackTrace();}\n");
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

                r.insertAfter(i, "\n try { FileWriter myWriter = new FileWriter(" +outputTxtPath+",true); myWriter.write(\"Block"+ ++c + "\\n\"); myWriter.close();} catch (IOException e) {e.printStackTrace();}\n");
            }
            if(tokens.get(i).getText().equals("{") && (tokenText.equals("class") || tokenText.equals("else"))){
                r.insertAfter(i, "\n try { FileWriter myWriter = new FileWriter(" +outputTxtPath+",true); myWriter.write(\"Block"+ ++c + "\\n\"); myWriter.close();} catch (IOException e) {e.printStackTrace();}\n");
                tokenText = "";
            }
            if(tokens.get(i).getText().equals("{") && tokenText.equals("do") ){
                r.insertAfter(i, "if(!f" + j +"){\ntry { FileWriter myWriter = new FileWriter(" +outputTxtPath+",true); myWriter.write(\"Block"+ ++c + "\\n\"); myWriter.close();} catch (IOException e) {e.printStackTrace();}\n f" + j +" = true;} \n");
                tokenText = "";
            }
            if(tokens.get(i).getText().equals(")") && !tokenText.equals("")){
                String oneAfter = tokens.get(i+1).getText();
                while(oneAfter.charAt(0) == ' ' || oneAfter.charAt(0) == '\n'){
                    oneAfter = tokens.get(++i).getText();
                }
                if(oneAfter.equals("{")){
                    if(tokenText.equals("for") || tokenText.equals("while")){
                        r.insertAfter(i + 1, "if(!f" + j +"){\ntry { FileWriter myWriter = new FileWriter(" +outputTxtPath+",true); myWriter.write(\"Block"+ ++c + "\\n\"); myWriter.close();} catch (IOException e) {e.printStackTrace();}\n f" + j +" = true;} \n");
                        j++;
                    }
                    else{
                        r.insertAfter(i + 1, "\n try { FileWriter myWriter = new FileWriter(" +outputTxtPath+",true); myWriter.write(\"Block"+ ++c + "\\n\"); myWriter.close();} catch (IOException e) {e.printStackTrace();}\n");
                    }
                }
                else{
                    if(tokenText.equals("for") || tokenText.equals("while")){
                        r.insertAfter(i, "{if(!f" + j +"){try { FileWriter myWriter = new FileWriter(" +outputTxtPath+",true); myWriter.write(\"Block"+ ++c + "\\n\"); myWriter.close();} catch (IOException e) {e.printStackTrace();};\n f" + j +" = true;} \n");
                        j++;
                        while(i++ < tokens.size() - 1){
                            if(tokens.get(i).getText().equals(";")){
                                r.insertAfter(i, "}\n");
                                break;
                            }
                        }
                    }
                    else {
                        r.insertAfter(i+1 , "{\ntry { FileWriter myWriter = new FileWriter(" +outputTxtPath+",true); myWriter.write(\"Block"+ ++c + "\\n\"); myWriter.close();} catch (IOException e) {e.printStackTrace();}\n");
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
    public static int run(String command) throws Exception {


        /// @param command String command to execute in powershell

        /// @return **1** If Success.
        /// @return **-1** If Failed.

        /// This Function use to execute any powershell command
        ///
        /// Take the string of the command as input. Then, Make powershell process. Then, pass this command to powershell process object. The process will execute and print the result if Success. If there are an error in this command process will print error response from the powershell



        // Executing the command
        Process powerShellProcess = Runtime.getRuntime().exec(command);
        // Getting the results
        powerShellProcess.getOutputStream().close();
        String line;
        System.out.println("Standard Output:");
        BufferedReader stdout = new BufferedReader(new InputStreamReader(
                powerShellProcess.getInputStream()));
        while ((line = stdout.readLine()) != null) {
            System.out.println(line);
        }
        stdout.close();

        BufferedReader stderr = new BufferedReader(new InputStreamReader(
                powerShellProcess.getErrorStream()));
        int err_exist = 0;
        while ((line = stderr.readLine()) != null) {
            System.out.println("Standard Error:");
            System.out.println(line);
            err_exist = 1;
        }
        stderr.close();
        if (err_exist == 1){
            return -1;
        }
        else {
            System.out.println("Done");
            return 1;
        }

    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        String inputPath = sc.nextLine();
        String post = inputPath.substring(inputPath.lastIndexOf('\\'));
        String initPre = inputPath.substring(0 , inputPath.lastIndexOf('\\'));
        String pre = initPre.substring(0, initPre.lastIndexOf('\\'));
        String fileName = post.substring(1, post.lastIndexOf('.'));
        String outputPath = pre + "\\output" + post;
        String outputTxtPath =  pre + "\\output\\output.txt";
        outputTxtPath ='"'+outputTxtPath+'"';
        outputTxtPath = outputTxtPath.replace("\\","\\\\");
        String x = injector(inputPath,outputTxtPath);
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

        outputPath = '"' +outputPath+ '"';
        String command = "java " + outputPath ;
        run(command);


    }
    }
