package output;
import java.io.IOException;
import java.io.FileWriter;
public class Test {
    public static int multiply(int x, int y){
 try { FileWriter myWriter = new FileWriter(".\\output\\output.txt",true); myWriter.write("Block1\n"); myWriter.close();} catch (IOException e) {e.printStackTrace();}

        notFun();
        System.out.println("X*Y=" + x*y + "from the multiply function");
        return x*y;
    }

    public static void notFun(){
 try { FileWriter myWriter = new FileWriter(".\\output\\output.txt",true); myWriter.write("Block2\n"); myWriter.close();} catch (IOException e) {e.printStackTrace();}

        System.out.println("This project is not fun at all");
    }

    public static int sum(int x, int y){
 try { FileWriter myWriter = new FileWriter(".\\output\\output.txt",true); myWriter.write("Block3\n"); myWriter.close();} catch (IOException e) {e.printStackTrace();}

        notFun();
        System.out.println("X+Y=" + x+y + "from the sum function");
        return x+y;
    }
    public static void main(String[] args) {
        
 try { FileWriter myWriter = new FileWriter(".\\output\\output.txt",true); myWriter.write("Block4\n"); myWriter.close();} catch (IOException e) {e.printStackTrace();}
int x = 0;
        int y = 1;
        if(x>y){
 try { FileWriter myWriter = new FileWriter(".\\output\\output.txt",true); myWriter.write("Block5\n"); myWriter.close();} catch (IOException e) {e.printStackTrace();}

            System.out.println("X>Y");
        }
        if(y>x)

            {
try { FileWriter myWriter = new FileWriter(".\\output\\output.txt",true); myWriter.write("Block6\n"); myWriter.close();} catch (IOException e) {e.printStackTrace();}
{
            System.out.println("Y>X");}

        }
        multiply(x,y);
        if (x==0)
            {
try { FileWriter myWriter = new FileWriter(".\\output\\output.txt",true); myWriter.write("Block7\n"); myWriter.close();} catch (IOException e) {e.printStackTrace();}
System.out.println("X=0");}

        else
            {
try { FileWriter myWriter = new FileWriter(".\\output\\output.txt",true); myWriter.write("Block8\n"); myWriter.close();} catch (IOException e) {e.printStackTrace();}
System.out.println("X!=0");}

        switch (x) {
            case 1         :
 try { FileWriter myWriter = new FileWriter(".\\output\\output.txt",true); myWriter.write("Block9\n"); myWriter.close();} catch (IOException e) {e.printStackTrace();}

                System.out.println("X=1 from switch");
                System.out.println("SS");
                break      ;
            case 2:
 try { FileWriter myWriter = new FileWriter(".\\output\\output.txt",true); myWriter.write("Block10\n"); myWriter.close();} catch (IOException e) {e.printStackTrace();}

                System.out.println("X=2 from switch");
                break;
            case 3:
 try { FileWriter myWriter = new FileWriter(".\\output\\output.txt",true); myWriter.write("Block11\n"); myWriter.close();} catch (IOException e) {e.printStackTrace();}

                System.out.println("X=0 from switch");
                break;
            default:
 try { FileWriter myWriter = new FileWriter(".\\output\\output.txt",true); myWriter.write("Block12\n"); myWriter.close();} catch (IOException e) {e.printStackTrace();}

                System.out.println("Default from switch");
        }
    }

}