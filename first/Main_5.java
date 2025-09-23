import java.util.function.Function;

public class Main_5 {
    public static void main(String[] args) {
        TableDrawer drawer = new TableDrawerBuilder()
            .dimentions(3, 5)
            .arg_steps(0.0, -1.0, 10.0)
            .function(x -> Math.sin(x))
            .build();
        drawer.draw();
    }
}

class TableDrawerBuilder {
    private int width, height;

    private Double start, step, end;
    private Function<Double, Double> f;

    public TableDrawerBuilder() {}

    public TableDrawerBuilder width(int width) {
        this.width = width;
        return this;
    }

    public TableDrawerBuilder height(int height) {
        this.height = height;
        return this;
    }

    public TableDrawerBuilder dimentions(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public TableDrawerBuilder start(Double start) {
        this.start = start;
        return this;
    }

    public TableDrawerBuilder step(Double step) {
        this.step = step;
        return this;
    }

    public TableDrawerBuilder end(Double end) {
        this.end = end;
        return this;
    }

    public TableDrawerBuilder arg_steps(Double start, Double step, Double end) throws IllegalArgumentException {
        if (step == 0) throw new IllegalArgumentException();
        if (end < start && step > 0 || end > start && step < 0) {
            Double tmp;
            tmp = start;
            start = end;
            end = tmp;
        }
        this.start = start;
        this.step = step;
        this.end = end;
        return this;
    }

    public TableDrawerBuilder function(Function<Double, Double> f) {
        this.f = f;
        return this;
    }

    public TableDrawer build() {
        return new TableDrawer(width, height, start, step, end, f);
    }
}

class TableDrawer {
    private int width, height;

    private Double start, step, end;
    private Function<Double, Double> f;

    private int column_widths[];

    public TableDrawer(int width, int height, Double start, Double step, Double end, Function<Double, Double> f) {
        this.width = width;
        this.height = height;
        this.start = start;
        this.step = step;
        this.end = end;
        this.f = f;
    }

    private void updateColumnWidths(int i) {
        double x = start + i * width * step;
        for (int j = 0; j < width && x <= end; j++) {
            this.column_widths[j] = Math.max(this.column_widths[j], String.valueOf(f.apply(x)).length());
            x += step;
        }
    }

    private void initColumnWidths() {
        this.column_widths = new int[this.width];
        for (int i = 0; i < this.width; i++) {
            this.column_widths[i] = 1;
        }
        for (int i = 0; i < this.height; i++) {
            updateColumnWidths(i);
        }
    }

    private String leftPad(String s, int n) {
        while (s.length() < n) {
            s = " " + s;
        }
        return s;
    }

    private void drawRow(int i) {
        double x = start + i * width * step;
        for (int j = 0; j < width; j++) {
            System.out.print("|");
            if (x > end && step > 0 || x < end && step < 0) {
                System.out.print(this.leftPad("", this.column_widths[j]));
                continue;
            }
            System.out.print(this.leftPad(String.valueOf(f.apply(x)), this.column_widths[j]));
            x += step;
        }
        System.out.print("|\n");
    }

    public void draw() {
        initColumnWidths();
        System.out.print("[2J[H");
        for (int i = 0; i < height; i++) {
            drawRow(i);
        }
    }
}
