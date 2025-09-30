import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


public class Main_8 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Integer n = Integer.parseInt(reader.readLine());
        Integer array[] = new Integer[n];
        ArrayClass.read_elements(array);
        ArrayClass.print(array);
        System.out.printf("Sum: %d\n", ArrayClass.sum(array));
        System.out.printf("Even: %d\n", ArrayClass.countEven(array));
        System.out.println("Please provide a, b");
        Integer a = Integer.parseInt(reader.readLine());
        Integer b = Integer.parseInt(reader.readLine());
        System.out.printf("Count in [%d, %d]: %d\n", a, b, ArrayClass.countInAB(array, a, b));
        System.out.printf("All positive: %b\n", ArrayClass.areAllPositive(array));
        ArrayClass.reverse(array);
        System.out.println("Reversed");
        ArrayClass.print(array);
    }
}
    
class ArrayClass {
    public static void print(Integer array[]) {
        for (Integer el : array) {
            System.out.print(el + " ");
        }
        System.out.println();
    }

    public static void read_elements(Integer array[]) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        for (int i = 0; i < array.length; i++) {
            array[i] = Integer.parseInt(reader.readLine());
        }
    }

    public static Integer sum(Integer array[]) {
        Integer sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        return sum;
    }

    public static Integer countEven(Integer array[]) {
        Integer count = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] % 2 == 0) {
                count++;
            }
        }
        return count;
    }

    public static Integer countInAB(Integer array[], Integer a, Integer b) {
        Integer count = 0;
        for (int i = 0; i < array.length; i++) {
            if (a <= array[i] && array[i] <= b) {
                count++;
            }
        }
        return count;
    }

    public static Boolean areAllPositive(Integer array[]) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] < 0) {
                return false;
            }
        }
        return true;
    }

    public static void reverse(Integer array[]) {
        for (int i = 0; i < array.length / 2; i++) {
            Integer tmp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = tmp;
        }
    }
}
class ListClass {
    private List<Integer> list;

    public void print() {
        System.out.println(list);
    }

    public void read_element() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        list.add(Integer.parseInt(reader.readLine()));
    }

    public Integer sum() {
        return list.stream().reduce(0, (a, b) -> a + b);
    }

    public Integer countEven() {
        return list.stream().filter(x -> x % 2 == 0).toArray().length;
    }

    public Integer countInAB(Integer a, Integer b) {
        return list.stream().filter(x -> a <= x && x <= b).toArray().length;
    }

    public Boolean areAllPositive() {
        return list.stream().filter(x -> x < 0).toArray().length == 0;
    }

    public void reverse() {
        for (int i = 0; i < list.size() / 2; i++) {
            Integer tmp = list.get(i);
            list.set(i, list.get(list.size() - 1 - i));
            list.set(list.size() - 1 - i, tmp);
        }
    }
}

