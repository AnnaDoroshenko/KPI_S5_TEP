package Lab07;

import java.util.ArrayList;

public class Simplex {
    int index;
    Point p0;
    Point p1;
    Point p2;
    Point min;
    Point new_point;

    ArrayList<Point> p = new ArrayList<Point>();
    Simplex prev;
    Simplex next;
    public Simplex(int index, Point p0, Point p1, Point p2) {
        this.index = index;
        this.p0 = p0;
        this.p1 = p1;
        this.p2 = p2;
        p.add(p0);
        p.add(p1);
        p.add(p2);
        min = this.findMin();
    }
    private Point findMin() {
        ArrayList<Point> bufList = new ArrayList<Point>();
        Point p0buf = p.get(0);
        Point p1buf = p.get(1);
        Point p2buf = p.get(2);
        bufList.add(p0buf);
        bufList.add(p1buf);
        bufList.add(p2buf);
        int k = 1;
        Point p0n = new Point(0, p1.x1 + p2.x1 - p0.x1, p1.x2 + p2.x2 - p0.x2);
        Point p1n = new Point(0, p0.x1 + p2.x1 - p1.x1, p0.x2 + p2.x2 - p1.x2);
        Point p2n = new Point(0, p0.x1 + p1.x1 - p2.x1, p0.x2 + p1.x2 - p2.x2);
        p0n.check();
        p1n.check();
        p2n.check();
        if (p0n.already_excluded == true) {
            bufList.get(0).already_excluded = true;
        }
        if (p1n.already_excluded == true) {
            bufList.get(1).already_excluded = true;
        }
        if (p2n.already_excluded == true) {
            bufList.get(2).already_excluded = true;
        }
        for (int i = 0; i < bufList.size(); i++) {
            Point buf = bufList.get(i);
            if (buf.already_excluded == true)
                bufList.remove(i);

        }
        if (bufList.size() == 0) {
            return null;
        }
        Point min = bufList.get(0);
        while (k != bufList.size()) {
            if (bufList.get(k).y < min.y) {
                min = bufList.get(k);
            }
            k++;
        }
        return min;
    }
    public Point newPoint(int index) {
        if (min == null) {
            return null;
        }
        double sign = 1;
        double x1new = 0;
        double x2new = 0;
        for (int i = 0; i < p.size(); i++) {
            if (p.get(i) == min) {
                sign = -1;
            }
            x1new = x1new + p.get(i).x1*sign;
            x2new = x2new + p.get(i).x2*sign;

            sign = 1;
        }
        Point new_point = new Point(index, x1new, x2new);
        new_point.already_excluded = true;
        return new_point;
    }
    public Simplex newSimplex(int index) {
        if (new_point == null) {
            return null;
        }
        ArrayList<Point> new_points = new ArrayList<Point>();
        for (int i = 0; i < p.size(); i++) {
            Point buf = p.get(i);
            if (buf != min) {
                int ind = buf.index;
                double x1 = buf.x1;
                double x2 = buf.x2;
                Point point = new Point(ind, x1, x2);
                new_points.add(point);
            }
        }
        Simplex new_simplex = new Simplex(index, new_points.get(0), new_points.get(1), new_point);
        return new_simplex;
    }
    public void print() {
        System.out.print("< " + p0.index + " " + p1.index + " " + p2.index + " >" + " ");
    }
}
