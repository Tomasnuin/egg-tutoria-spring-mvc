import java.util.List;

public class MyClass {
    public static void main(String args[]) {
        String name = "Peter";
        String lastName = null;
        String response;
        if (name != null || lastName != null) {
            response = name + " " + lastName;
        } else {

            response = "no correct aklsdnklsa";
        }


        System.out.println(response);
    }
}