/**
 * Theory of experiment planning
 * Lab 1
 *
 * @variant: 7
 * @authors: Igor Boyarshin, Anna Doroshenko
 * @group: IO-52
 * @date: 27.09.2017
 */

package Lab01;

public class Operations {

    private final int A0 = 1;
    private final int A1 = 2;
    private final int A2 = 3;
    private final int A3 = 4;

    private final int N;

    private int indexOfBestFit;


    public Operations(int N) {
        this.N = N;
    }

    public double[] generateRandomXs() {
        double[] xs = new double[N];
        for (int i = 0; i < N; i++) {
            xs[i] = Math.random() * 20.0;
        }

        return xs;
    }

    public double calculateRegression(double x1, double x2, double x3) {
        return A0 + A1 * x1 + A2 * x2 + A3 * x3;
    }

    public double[] calculateYs(double[] x1, double[] x2, double[] x3) {
        double[] ys = new double[N];
        for (int i = 0; i < N; i++) {
            ys[i] = calculateRegression(x1[i], x2[i], x3[i]);
        }

        return ys;
    }

    public double calculateYStandart(double x1, double x2, double x3) {
        return calculateRegression(x1, x2, x3);
    }

    public double findMaxX(double[] x) {
        return findMinOrMax(x, false);
    }

    public double findMinX(double[] x) {
        return findMinOrMax(x, true);
    }

    private double findMinOrMax(double[] array, boolean findMin) {
        double currentBest = array[0];
        for (int i = 1; i < array.length; i++) {
            if (findMin ? (array[i] < currentBest) : (array[i] > currentBest)) {
                currentBest = array[i];
            }
        }

        return currentBest;
    }

    public double calculateX0(double xMax, double xMin) {
        return (xMax + xMin) / 2.0;
    }

    public double calculateDx(double x0, double xMin) {
        return x0 - xMin;
    }

    public double[] calculateXn(double[] x, double x0, double dx) {
        double[] xn = new double[N];
        for (int i = 0; i < x.length; i++) {
            xn[i] = (x[i] - x0) / dx;
        }

        return xn;
    }

    public double findBestFit(double[] y, double yStd) {
        double yBestFit = y[0];
        indexOfBestFit = 0;
        for (int i = 0; i < y.length; i++) {
            if (Math.abs(y[i] - yStd) < Math.abs(yBestFit - yStd) && (y[i] < yStd)) {
                yBestFit = y[i];
                indexOfBestFit = i;
            }
        }

        return yBestFit;
    }

    public String bestCall(double[] x1, double[] x2, double[] x3) {
        return "Y = " + A0 + " + " + A1 + " * " + x1[indexOfBestFit] +
                " + " + A2 + " * " + x2[indexOfBestFit] +
                " + " + A3 + " * " + x3[indexOfBestFit];
    }
}