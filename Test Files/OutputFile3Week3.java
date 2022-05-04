package output;
import java.io.IOException;
import java.io.FileWriter;
public class Test3 {
    public static void main(String[] args) {
        
 try { FileWriter myWriter = new FileWriter(".\\output\\output.txt",true); myWriter.write("Block1\n"); myWriter.close();} catch (IOException e) {e.printStackTrace();}
int x = 5;
        int y = 6;
        if(x == 5) {
 try { FileWriter myWriter = new FileWriter(".\\output\\output.txt",true); myWriter.write("Block2\n"); myWriter.close();} catch (IOException e) {e.printStackTrace();}
System.out.println("X = 5");
            if (y == 6)
                {
try { FileWriter myWriter = new FileWriter(".\\output\\output.txt",true); myWriter.write("Block3\n"); myWriter.close();} catch (IOException e) {e.printStackTrace();}
System.out.println("Y = 6");}
            else
                {
try { FileWriter myWriter = new FileWriter(".\\output\\output.txt",true); myWriter.write("Block4\n"); myWriter.close();} catch (IOException e) {e.printStackTrace();}
System.out.println("Y != 6");}
        }
        else if (x > 3)
            {
try { FileWriter myWriter = new FileWriter(".\\output\\output.txt",true); myWriter.write("Block5\n"); myWriter.close();} catch (IOException e) {e.printStackTrace();}
System.out.println("X > 3");}
        else
            {
try { FileWriter myWriter = new FileWriter(".\\output\\output.txt",true); myWriter.write("Block6\n"); myWriter.close();} catch (IOException e) {e.printStackTrace();}
System.out.println("Else");}
        if(x == 3)
            {
try { FileWriter myWriter = new FileWriter(".\\output\\output.txt",true); myWriter.write("Block7\n"); myWriter.close();} catch (IOException e) {e.printStackTrace();}
System.out.println("X = 3");}
        else if (x == 5)
            {
try { FileWriter myWriter = new FileWriter(".\\output\\output.txt",true); myWriter.write("Block8\n"); myWriter.close();} catch (IOException e) {e.printStackTrace();}
System.out.println("X = 5");}
        else
            {
try { FileWriter myWriter = new FileWriter(".\\output\\output.txt",true); myWriter.write("Block9\n"); myWriter.close();} catch (IOException e) {e.printStackTrace();}
System.out.println("Else");}
        if(x == 7)
            {
try { FileWriter myWriter = new FileWriter(".\\output\\output.txt",true); myWriter.write("Block10\n"); myWriter.close();} catch (IOException e) {e.printStackTrace();}
System.out.println("X = 3");}

        else if (x == 9)
            {
try { FileWriter myWriter = new FileWriter(".\\output\\output.txt",true); myWriter.write("Block11\n"); myWriter.close();} catch (IOException e) {e.printStackTrace();}
System.out.println("X = 5");}
        else
            {
try { FileWriter myWriter = new FileWriter(".\\output\\output.txt",true); myWriter.write("Block12\n"); myWriter.close();} catch (IOException e) {e.printStackTrace();}
System.out.println("Else");}

    }
}
