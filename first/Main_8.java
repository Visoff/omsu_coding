import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

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

public class Main_8 {
    public static void main(String[] args) {
        // ...
    }
}
