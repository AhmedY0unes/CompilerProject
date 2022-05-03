# Java-Line-Execution-Detector
This Project uses ANTLR java parser to detect the line blocks which was executed from an input java program.

## Members:
  1. Ahmed Nasser Ahmed
  2. Ahmed Hesham Salah
  3. Ahmed Younes Ibrahim
  4. Mohamed Adel Mahmoud

## Week 1:

  * Use ANTLR Java grammar.
  * Test the grammar on a simple Java program to show the parse tree.
  * Show the starting rule of the grammar (compilationUnit).
    * As an example input file we used this simple java program
      ![input_file1](images/InputFile.png)
    * The parse tree of that sample java program would be:
      ![parse_tree1](images/ParseTree.png)

## Week 2:

  * Write a Java program based on ANTLR that takes a java file as an input and outputs a modified intermediate Java file (injected code).
  * Run the modified intermediate generated Java file to show which blocks of the code are Entered..
    * As an example for a Java input file, we used this:
   
      ![input_file_2](images/inputFileWeek2.png)
    * We ran the code and the result is in the console as shown.
      ![run](images/OutPutFileWeek2.png)
## Week 3: 

  * Use the output from Week 2 to generate an HTML with highlighted red/green lines for Entered/UnEntered blocks.
  * Full documentation using doxygen for the classes and functions developed only.
  * Make sure that the pipeline does not include any manual effort. 
  * Show at least 3 Java examples that shows difficult scenarios.
    1. * For the first Example:
   
      ![input_file1](images/inputFileWeek1.png)
    * Then all the required files are generated automatically with a simple run of "Injector" driver code (in the main method)
       * The generated Output.java file:  
        ![Output](images/OutputFileWeek3.png)
       * The generated HTML file:
        ![html](images/html.png)
    2. * For the Second Examble:
 
      ![input_file2](images/input_file2Week3.png)
       * The generated Output.java file:  
        ![Output2](images/OutputFile2Week3.png)
       * The generated HTML file:
        ![html2](images/html2.png)
    3. * For the third Examble:
  
      ![input_file2](images/input_file3Week3.png)
       * The generated Output.java file:  
        ![Output2](images/OutputFile3Week3.png)
       * The generated HTML file:
        ![html3](images/html3.png)
