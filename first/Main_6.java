import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main_6 {
    public static void main(String[] args) {
        System.out.println("ax+by=c\ndx+ey=f");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Double a, b, c, d, e, f;
        try {
            System.out.print("a=");
            a = Double.parseDouble(reader.readLine());
            System.out.print("b=");
            b = Double.parseDouble(reader.readLine());
            System.out.print("c=");
            c = Double.parseDouble(reader.readLine());
            System.out.print("d=");
            d = Double.parseDouble(reader.readLine());
            System.out.print("e=");
            e = Double.parseDouble(reader.readLine());
            System.out.print("f=");
            f = Double.parseDouble(reader.readLine());

            Double delta = a * e - b * d;
            Double delta_x = c * e - b * f;
            Double delta_y = a * f - c * d;
            if (delta != 0) {
                System.out.println("x=" + delta_x / delta);
                System.out.println("y=" + delta_y / delta);
            } else if (delta_x == 0 && delta_y == 0) {
                System.out.println("all values");
            } else {
                System.out.println("no values");
            }
        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
    }
}
