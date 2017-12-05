package Lab07;

import java.util.ArrayList;

public class Experiment {
    double x0_1;
    double x0_2;
    double dx1;
    double dx2;
    public static double x1max;
    public static double x2max;
    double r_1 = 0.5;
    double R_1 = 0.5;
    double r_2 = 1/Math.sqrt(12);
    double R_2 = 1/Math.sqrt(6);
    double ro = 1;
    Point optimum;
    Simplex current;
    boolean found = false;
    boolean not_found = false;
    ArrayList<Simplex> e = new ArrayList<Simplex>();
    ArrayList<Point> table = new ArrayList<Point>();
    Point p0;
    Point p1;
    Point p2;
    Simplex s0;
    public Experiment(double x0_1, double x0_2, double dx1, double dx2) {
        this.x0_1 = x0_1 - r_1*dx1*ro;
        this.x0_2 = x0_2 - r_2*dx2*ro;
        this.dx1 = dx1;
        this.dx2 = dx2;
        x1max = x0_1 + dx1;
        x2max = x0_2 + dx2;
        double x1_1 = x0_1 + R_1*dx1*ro;
        double x1_2 = x0_2 - r_2*dx2*ro;
        double x2_1 = x0_1;
        double x2_2 = x0_2 + R_2*dx2*ro;
        p0 = new Point(0, this.x0_1, this.x0_2);
        p1 = new Point(1, x1_1, x1_2);
        p2 = new Point(2, x2_1, x2_2);

        s0 = new Simplex(0, p0, p1, p2);
        current = s0;
        current.prev = null;
    }
    public void findOptimum() {
        e.add(s0);
        table.add(p0);
        table.add(p1);
        table.add(p2);
        p0.print();
        System.out.println();
        p1.print();
        System.out.println();
        p2.print();
        s0.print();
        System.out.println();
        while ((found == false) && (not_found == false)) {
            current.new_point = current.newPoint(table.size());
            Point last_point = current.new_point;
            if (last_point == null) {
                if (current.index == 0) {
                    not_found = true;
                } else {
                    e.remove(current);
                    table.remove(table.size() - 1);
                    current = e.get(e.size() - 1);
                }
            } else {
                current = current.newSimplex(e.size() + 1);
                table.add(last_point);
                e.add(current);
                for (int i = 0; i < table.size() - 1; i++) {
                    if ((last_point.x1 == table.get(i).x1) && (last_point.x2 == table.get(i).x2)) {
                        found = true;
                        Simplex e_last = e.get(e.size() - 1);
                        Simplex e_last_2 = e.get(e.size() - 3);
                        for (int j = 0; j < 3; j++) {
                            if (e_last.p.get(j).index ==
                                    e_last_2.p.get(j).index) {
                                optimum = e_last.p.get(j);
                            }
                        }
                    }
                }
                current.prev = e.get(e.size() - 1);
                current.prev.next = current;
                last_point.print();
                current.print();
                System.out.println();
            }
        }
        if ((found == true)) {
            System.out.println("Optimum was found! This is point " + optimum.index + "!");
        } else
            System.out.println("LeastSquaresOptimizer.Optimum was not found!");

    }
}