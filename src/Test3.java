/// This class is for testing the Injector Class
///
/// This class providing a lot of supporting tokens to test how efficiency Injector Class will detect it and injection code in it.
public class Test3 {
    public static void main(String[] args) {
        int x = 5;
        int y = 6;
        if(x == 5) {
            System.out.println("X = 5");
            if (y == 6)
                System.out.println("Y = 6");
            else if (y == 7)
                System.out.println("This shall not be printed");
            else
                System.out.println("Y != 6 and Y != 7");
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
