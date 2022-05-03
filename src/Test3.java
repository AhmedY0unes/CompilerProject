public class Test3 {
    public static void main(String[] args) {
        int x = 5;
        int y = 6;
        if(x == 5) {
            System.out.println("X = 5");
            if (y == 6)
                System.out.println("Y = 6");
            else
                System.out.println("Y != 6");
        }
        else if (x > 3)
            System.out.println("X > 3");
        else
            System.out.println("Else");

        if(x == 3)
            System.out.println("X = 3");
        else if (x == 5)
            System.out.println("X = 5");
        else
            System.out.println("Else");

        if(x == 7)
            System.out.println("X = 3");
        else if (x == 9)
            System.out.println("X = 5");
        else
            System.out.println("Else");
    }
}
