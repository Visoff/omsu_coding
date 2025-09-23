import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main_4 {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Double a, b, c;
        try {
            a = Double.parseDouble(reader.readLine());
            b = Double.parseDouble(reader.readLine());
            c = Double.parseDouble(reader.readLine());

            if (a == 0) {
                if (b == 0) {
                    if (c == 0) {
                        System.out.println("any values");
                        return;
                    }
                    System.out.println("no roots");
                return;
                } 
                System.out.println(1);
                System.out.println(-c / b);
                return;
            }

            Double D = b * b - 4 * a * c;
            if (D > 0) {
                Double x1 = (-b + Math.sqrt(D)) / (2 * a);
                Double x2 = (-b - Math.sqrt(D)) / (2 * a);
                System.out.println(2);
                System.out.println(x1);
                System.out.println(x2);
            } else if (D == 0) {
                Double x = -b / (2 * a);
                System.out.println(1);
                System.out.println(x);
            } else {
                System.out.println("no roots");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
