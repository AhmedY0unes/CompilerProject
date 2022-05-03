public class Test {
    public static int multiply(int x, int y){
        notFun();
        System.out.println("X*Y=" + x*y + "from the multiply function");
        return x*y;
    }

    public static void notFun(){
        System.out.println("This project is not fun at all");
    }

    public static int sum(int x, int y){
        notFun();
        System.out.println("X+Y=" + x+y + "from the sum function");
        return x+y;
    }
    public static void main(String[] args) {
        int x = 0;
        int y = 1;
        if(x>y){
            System.out.println("X>Y");
        }
        if(y>x)

            {
            System.out.println("Y>X");
        }
        multiply(x,y);
        if (x==0)
            System.out.println("X=0");
        else
            System.out.println("X!=0");
        switch (x) {
            case 1         :
                System.out.println("X=1 from switch");
                System.out.println("SS");
                break      ;
            case 2:
                System.out.println("X=2 from switch");
                break;
            case 3:
                System.out.println("X=0 from switch");
                break;
            default:
                System.out.println("Default from switch");
        }
    }

}