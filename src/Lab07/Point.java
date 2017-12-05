package Lab07;

public class Point {
    boolean already_excluded = false;
    int index;
    double x1;
    double x2;
    double y;
    double x1max = Experiment.x1max;
    double x2max = Experiment.x2max;
    public Point(int index, double x1, double x2) {
        this.index = index;
        this.x1 = x1;
        this.x2 = x2;
        this.y = func(x1, x2);
    }
    public void check() {
        if ((Math.abs(x1) > x1max) || (Math.abs(x2) > x2max)) {
            already_excluded = true;
        }
    }
    private double func(double x1, double x2) {
        return (x1/x2);
    }
    public void print() {
        System.out.print(index + ". ");
        System.out.printf("%5.2f", x1);
        System.out.print(" ");
        System.out.printf("%5.2f", x2);
        System.out.print(" ");
        System.out.printf("%5.2f", y);
        System.out.print(" ");
    }
}
