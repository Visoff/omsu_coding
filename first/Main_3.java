import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main_3 {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Integer a, b, c;
        try {
            a = Integer.parseInt(reader.readLine());
            b = Integer.parseInt(reader.readLine());
            c = Integer.parseInt(reader.readLine());
            System.out.println(a * b * c);
            System.out.println(Double.valueOf(a + b + c)/3.0);
            if (a > b && a > c) {
                System.out.println(a);
                if (b > c) {
                    System.out.println(b);
                    System.out.println(c);
                } else {
                    System.out.println(c);
                    System.out.println(b);
                }
            } else if (b > a && b > c) {
                System.out.println(b);
                if (a > c) {
                    System.out.println(a);
                    System.out.println(c);
                } else {
                    System.out.println(c);
                    System.out.println(a);
                }
            } else if (c > a && c > b) {
                System.out.println(c);
                if (a > b) {
                    System.out.println(a);
                    System.out.println(b);
                } else {
                    System.out.println(b);
                    System.out.println(a);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
