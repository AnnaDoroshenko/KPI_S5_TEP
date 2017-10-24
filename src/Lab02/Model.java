package Lab02;

import javafx.util.Pair;

import java.util.*;

public class Model {
    private final int NUMBER = 7;

    private final int k = 2; // amount of factors
    private final double[] XS_MIN = {-5, -35};
    private final double[] XS_MAX = {15, 10};

    private final double Y_MIN = (20 - NUMBER) * 10.0;
    private final double Y_MAX = (30 - NUMBER) * 10.0;

    private final double REQUIRED_PROBABILITY;

    private final int N = 3;

    // TODO: generalize
    private final double[] x1n = {-1, 1, 1};
    private final double[] x2n = {1, -1, 1};
    private final double[] x1 = {XS_MIN[0], XS_MAX[0], XS_MAX[0]};
    private final double[] x2 = {XS_MAX[1], XS_MIN[1], XS_MAX[1]};
    private double[] ym;

    // [m][N]
    private List<double[]> matrix;

    private static Random random;

    private double[] normalizedRegressionCoeffs;
    private double[] naturalizedRegressionCoeffs;


    public Model(double requiredProbability) {
        this.REQUIRED_PROBABILITY = requiredProbability;
        matrix = new ArrayList<>();
        random = new Random();

        generateRomanovCriticalRs();

        doStuff();
    }

    private void doStuff() {
        int m;
        for (m = 0; m < 5; m++) generateNewSample();
        while (!satisfiesRomanovCriteria(m)) {
            generateNewSample();
            m++;
        }

        ym = calculateAverages(new double[][]{
                getYsForExperiment(0),
                getYsForExperiment(1),
                getYsForExperiment(2)
        });

        normalizedRegressionCoeffs = (calculateNormalizedRegressionCoeffs());

        final double[] experimentalYs = mapRegression(normalizedRegressionCoeffs,
                new double[][]{
                        {x1n[0], x2n[0]},
                        {x1n[1], x2n[1]},
                        {x1n[2], x2n[2]}
                });

        // Compare experimentalYs && ym

        naturalizedRegressionCoeffs = naturalizeRegressionCoeffs(normalizedRegressionCoeffs);

        // Check these ones as well in the same way
    }

    private void generateNewSample() {
        matrix.add(generateRandomArray(N, Y_MIN, Y_MAX));
    }

    private double[] generateRandomArray(int size, double boundMin, double boundMax) {
        double[] array = new double[size];
        for (int i = 0; i < size; i++) {
            array[i] = generateRandom(boundMin, boundMax);
        }

        return array;
    }

    private double generateRandom(double boundMin, double boundMax) {
        return (boundMax - boundMin) * random.nextDouble() + boundMin;
    }

    private double[] mapSquare(double[] array) {
        double[] result = new double[array.length];

        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] * array[i];
        }

        return result;
    }

    private double[] zipMul(double[] array1, double[] array2) {
        double[] result = new double[array1.length];

        for (int i = 0; i < array1.length; i++) {
            result[i] = array1[i] * array2[i];
        }

        return result;
    }

    private double[] mapRegression(double[] regressionCoeffs, double[][] experimentsXs) {
        double[] regressions = new double[experimentsXs.length];

        int index = 0;
        for (double[] xs : experimentsXs) {
            regressions[index++] = calculateRegression(regressionCoeffs, xs);
        }

        return regressions;
    }

    private double calculateRegression(double[] regressionCoeffs, double[] xs) {
        double regression = regressionCoeffs[0];

        for (int i = 0; i < xs.length; i++) {
            regression += regressionCoeffs[i + 1] * xs[i];
        }

        return regression;
    }

    private double calculateAverage(double[] array) {
        double average = 0.0;

        for (double value : array) {
            average += value;
        }
        average /= array.length;

        return average;
    }

    private double calculateDispersion(double[] array, double average) {
        double dispersion = 0.0;

        for (double value : array) {
            final double dValue = (average - value);
            dispersion += dValue * dValue;
        }
        dispersion /= array.length; // (length - 1) ??!!

        return dispersion;
    }

    private double calculateDispersion(double[] array) {
        final double average = calculateAverage(array);
        return calculateDispersion(array, average);
    }


    private boolean satisfiesRomanovCriteria(int m) {
        // Step 1: calculate dispersions
        double[] dispersions = new double[N];
        for (int experimentIndex = 0; experimentIndex < N; experimentIndex++) {
            final double[] ys = getYsForExperiment(experimentIndex);
            dispersions[experimentIndex] = calculateDispersion(ys);
        }

        // Step 2
        final double sigmaTheta = Math.sqrt(2 * (2 * m - 2) / m / (m - 4));

        // Step 3
        double[][] F = new double[N][N];
        for (int u = 0; u < N; u++) {
            for (int v = 0; v < N; v++) {
                F[u][v] = (dispersions[u] >= dispersions[v]) ?
                        (dispersions[u] / dispersions[v]) :
                        (dispersions[v] / dispersions[u]);
            }
        }

        double[][] theta = new double[N][N];
        for (int u = 0; u < N; u++) {
            for (int v = 0; v < N; v++) {
                final double multiplier = (m - 2) / m;
                theta[u][v] = multiplier * F[u][v];
            }
        }

        double[][] R = new double[N][N];
        for (int u = 0; u < N; u++) {
            for (int v = 0; v < N; v++) {
                R[u][v] = Math.abs(theta[u][v] - 1) / sigmaTheta;
            }
        }

        // Step 4
        final double criticalR = getRomanovCriticalR(REQUIRED_PROBABILITY, m);

        // Step 5
        for (double[] rs : R) {
            for (double r : rs) {
                if (r > criticalR) {
                    return false;
                }
            }
        }

        return true;
    }

    private double getRomanovCriticalR(double p, int m) {
        final Pair<Double, Integer> bestMatchesPM = findBestMatchForParametersInRomanovCriticalRs(p, m);
        final Double pKey = bestMatchesPM.getKey();
        final Integer mKey = bestMatchesPM.getValue();

        return romanovCriticalRs.get(pKey).get(mKey);
    }

    // TODO: sequence of thoughts
    // TODO: generic
    private Pair<Double, Integer> findBestMatchForParametersInRomanovCriticalRs(double p, int m) {
        final Set<Double> ps = romanovCriticalRs.keySet();
        final Double anyKeyP = ps.iterator().next();
        final Set<Integer> ms = romanovCriticalRs.get(anyKeyP).keySet();
        final Integer anyKeyM = ms.iterator().next();

        Double bestMatchP = anyKeyP;
        Double diffOfBestMatchP = Math.abs(anyKeyP - p);
        for (Double pKey : ps) {
            final Double diff = Math.abs(pKey - p);
            if (diff < diffOfBestMatchP) {
                diffOfBestMatchP = diff;
                bestMatchP = pKey;
            }
        }

        Integer bestMatchM = anyKeyM;
        Integer diffOfBestMatchM = Math.abs(anyKeyM - m);
        for (Integer mKey : ms) {
            final Integer diff = Math.abs(mKey - m);
            if (diff < diffOfBestMatchM) {
                diffOfBestMatchM = diff;
                bestMatchM = mKey;
            }
        }

        return new Pair<>(bestMatchP, bestMatchM);
    }

//    private <T extends Number> T findBestMatch(Collection<T> collection, T value) {
//        T bestMatch = collection.iterator().next();
//        T diffOfBestMatch = Math.abs(bestMatch - value);
//        for (T t : collection) {
//
//        }
//    }

    private double[] normalize(double[] array) {
        double max = Math.abs(array[0]);
        for (double value : array) {
            if (Math.abs(value) > max) {
                max = Math.abs(value);
            }
        }

        double[] normalized = new double[array.length];
        for (int index = 0; index < array.length; index++) {
            normalized[index] = array[index] / max;
        }

        return normalized;
    }

    private double[] calculateAverages(double[][] arrays) {
        double[] averages = new double[arrays.length];

        int index = 0;
        for (double[] array : arrays) {
            averages[index++] = calculateAverage(array);
        }

        return averages;
    }

    // TODO: generalize
    private double[] calculateNormalizedRegressionCoeffs() {
        double[] ym = (this.ym);
        final double mx1 = calculateAverage(x1n);
        final double mx2 = calculateAverage(x2n);
        final double a1 = calculateAverage(mapSquare(x1n));
        final double a2 = calculateAverage(zipMul(x1n, x2n));
        final double a3 = calculateAverage(mapSquare(x2n));
        final double a11 = calculateAverage(zipMul(x1n, ym));
        final double a22 = calculateAverage(zipMul(x2n, ym));
        final double my = calculateAverage(ym);

        final double[][] A = {
                {1.0, mx1, mx2},
                {mx1, a1, a2},
                {mx2, a2, a3}
        };
        final double[] B = {
                my,
                a11,
                a22
        };

        return solveSystemOfLinearEquations(A, B);
    }

    private double[] solveSystemOfLinearEquations(double[][] a, double[] b) {
        final int size = a.length;
        double[] result = new double[size];

        final double det0 = det(a);
        for (int i = 0; i < size; i++) {
            double[][] submatrix = new double[size][size];
            for (int row = 0; row < size; row++) {
                for (int column = 0; column < size; column++) {
                    submatrix[row][column] = (column == i) ? b[row] : a[row][column];
                }
            }

            result[i] = det(submatrix) / det0;
        }

        return result;
    }

    private double det(double[][] m) {
        return m[0][0] * m[1][1] * m[2][2] + m[0][1] * m[1][2] * m[2][0] + m[1][0] * m[2][1] * m[0][2]
                - m[0][2] * m[1][1] * m[2][0] - m[1][0] * m[0][1] * m[2][2] - m[0][0] * m[2][1] * m[1][2];
    }

    private double[] naturalizeRegressionCoeffs(double[] normalizedCoeffs) {
        double[] X0s = new double[k];
        for (int i = 0; i < k; i++) {
            X0s[i] = (XS_MIN[i] + XS_MAX[i]) / 2.0;
        }

        double[] dXs = new double[k];
        for (int i = 0; i < k; i++) {
            dXs[i] = Math.abs(XS_MAX[i] - XS_MIN[i]) / 2.0;
        }

        final int amountOfCoeffs = normalizedCoeffs.length;
        double[] naturalizedCoeffs = new double[amountOfCoeffs];

        naturalizedCoeffs[0] = normalizedCoeffs[0];
        for (int i = 0; i < k; i++) {
            naturalizedCoeffs[0] -= normalizedCoeffs[i + 1] * X0s[i] / dXs[i];
        }

        for (int i = 0; i < k; i++) {
            naturalizedCoeffs[i + 1] = normalizedCoeffs[i + 1] / dXs[i];
        }

        return naturalizedCoeffs;
    }


    public double[] getNormalizedRegressionCoeffs() {
        return normalizedRegressionCoeffs;
    }

    public double[] getNaturalizedRegressionCoeffs() {
        return naturalizedRegressionCoeffs;
    }

    public double[] getYm() {
        return ym;
    }

    // [sample][experiment]
    public double[][] getAllYs() {
        return matrix.toArray(new double[0][]);
    }

    public double[] getYsForSample(int sample) {
        return matrix.get(sample);
    }

    public double[] getYsForExperiment(int experiment) {
        double[] ys = new double[matrix.size()];

        int index = 0;
        for (double[] sample : matrix) {
            ys[index++] = sample[experiment];
        }

        return ys;
    }

    public int getYSamples() {
        return matrix.size();
    }

    public double[] getX1() {
        return x1;
    }

    public double[] getX2() {
        return x2;
    }

    public double[] getX1n() {
        return x1n;
    }

    public double[] getX2n() {
        return x2n;
    }

    public double getRequiredProbability() {
        return REQUIRED_PROBABILITY;
    }

    // --------------------------------------------------

    private static Map<Double, Map<Integer, Double>> romanovCriticalRs = null;

    // TODO: zip?
    private static void generateRomanovCriticalRs() {
        if (romanovCriticalRs != null) {
            return;
        }

        final Double[] ps = {0.99, 0.98, 0.95, 0.9};
        final Integer[] ms = {2, 6, 8, 10, 12, 15, 20};
        final Double[][] values = {
                {1.73, 2.16, 2.43, 2.62, 2.75, 2.90, 3.08},
                {1.72, 2.13, 2.37, 2.54, 2.66, 2.80, 2.96},
                {1.71, 2.10, 2.27, 2.41, 2.52, 2.64, 2.78},
                {1.69, 2.00, 2.17, 2.29, 2.39, 2.49, 2.62}
        };

        int indexP = 0;
        romanovCriticalRs = new HashMap<>();
        for (Double p : ps) {
            int indexM = 0;
            final Map<Integer, Double> map = new HashMap<>();
            for (Integer m : ms) {
                map.put(m, values[indexP][indexM]);

                indexM++;
            }

            romanovCriticalRs.put(p, map);
            indexP++;
        }
    }
}
