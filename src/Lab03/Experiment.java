package Lab03;

import java.util.*;

public class Experiment {

    private final double[] XS_MIN = {-5, -35, -35}; // x1, x2, x3
    private final double[] XS_MAX = {15, 10, -10}; // x1, x2, x3

    private final double X_MIN_AVERAGE = (XS_MIN[0] + XS_MIN[1] + XS_MIN[2]) / 3.0;
    private final double X_MAX_AVERAGE = (XS_MAX[0] + XS_MAX[1] + XS_MAX[2]) / 3.0;

    private final double[] x0n = {1, 1, 1};
    private final double[] x1n = {-1, 1, 1};
    private final double[] x2n = {1, -1, 1};
    private final double[] x3n = {1, 1, -1};
    //    private final double[] x0 = {XS_MAX[0], XS_MAX[0], XS_MAX[0]};
    private final double[] x1 = {
            x1n[0] == 1 ? XS_MAX[0] : XS_MIN[0],
            x1n[1] == 1 ? XS_MAX[0] : XS_MIN[0],
            x1n[2] == 1 ? XS_MAX[0] : XS_MIN[0]};
    private final double[] x2 = {
            x2n[0] == 1 ? XS_MAX[1] : XS_MIN[1],
            x2n[1] == 1 ? XS_MAX[1] : XS_MIN[1],
            x2n[2] == 1 ? XS_MAX[1] : XS_MIN[1]};
    private final double[] x3 = {
            x3n[0] == 1 ? XS_MAX[2] : XS_MIN[2],
            x3n[1] == 1 ? XS_MAX[2] : XS_MIN[2],
            x3n[2] == 1 ? XS_MAX[2] : XS_MIN[2]};

    private final double Y_MIN = 200 + X_MIN_AVERAGE;
    private final double Y_MAX = 200 + X_MAX_AVERAGE;

    private final double REQUIRED_PROBABILITY;

    private static final double INFINITY = Double.POSITIVE_INFINITY;

    private final int N = 4;

    // {
    //   (1): [1..m]
    //   (2): [1..m]
    //   (3): [1..m]
    //   (N): [1..m]
    // }
    // [m][N] === [column][row]
    private List<double[]> matrix;

    private static Random random = null;

    private double[] normalizedRegressionCoeffs;
    private double[] naturalizedRegressionCoeffs;

    public Experiment(double requiredProbability) {
        this.REQUIRED_PROBABILITY = requiredProbability;

        matrix = new ArrayList<>();
        if (random == null) {
            random = new Random();
        }

        generateCohranCoeffGs(REQUIRED_PROBABILITY);
        generateStudentCoeffTs();
        generateFisherCoeffFs(REQUIRED_PROBABILITY);

        doStuff();
    }

    private void doStuff() {

    }

    private boolean satisfiesCohranCriteria(int m) {
        final double[][] ysForExperiments = getYsForExperiments();

        // Step 1-2
        double[] yAverages = calculateAverages(ysForExperiments);

        // Step 3
        double[] dispersions = calculateDispersions(ysForExperiments, yAverages);

        // Step 4
        final double maxDispersion = max(dispersions);

        // Step 5
        final double Gp = maxDispersion / sum(dispersions);

        // Step 6
        final int f1 = m - 1;
        final int f2 = N;

        // Step 7
        final double q = 1 - REQUIRED_PROBABILITY;

        // Step 8
        final double Gt = getCohranCoeff(f1, f2);


        return Gp < Gt;
    }

    private boolean satisfiesStudentCriteria(int m) {
        double averageDispersion = calculateAverage(calculateDispersions(getYsForExperiments()));
        double[] dispersions = get
    }



    private double calculateAverage(double[] array) {
        double average = 0.0;

        for (double value : array) {
            average += value;
        }
        average /= array.length;

        return average;
    }

    private double[] calculateAverages(double[][] arrays) {
        final int size = arrays.length;
        double[] averages = new double[size];

        for (int i = 0; i < size; i++) {
            averages[i] = calculateAverage(arrays[i]);
        }

        return averages;
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

    private double[] calculateDispersions(double[][] arrays, double[] averages) {
        final int size = arrays.length;
        double[] dispersions = new double[size];

        for (int i = 0; i < size; i++) {
            dispersions[i] = calculateDispersion(arrays[i], averages[i]);
        }

        return dispersions;
    }

    private double[] calculateDispersions(double[][] arrays) {
        final int size = arrays.length;
        double[] dispersions = new double[size];

        for (int i = 0; i < size; i++) {
            final double average = calculateAverage(arrays[i]);
            dispersions[i] = calculateDispersion(arrays[i], average);
        }

        return dispersions;
    }

    private double max(double[] array) {
        double max = array[0];

        for (double elem : array) {
            if (elem > max) {
                max = elem;
            }
        }

        return max;
    }

    private double sum(double[] array) {
        double sum = 0.0;

        for (double elem : array) {
            sum += elem;
        }

        return sum;
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


    //----------------------------------------------------------------------------------

    // [experiment]
    public double[] getYsForSample(int sample) {
        return matrix.get(sample);
    }

    // [sample][experiment]
    public double[][] getYsForSamples() {
        return matrix.toArray(new double[0][]);
    }

    // [sample]
    public double[] getYsForExperiment(int experiment) {
        double[] ys = new double[matrix.size()];

        int index = 0;
        for (double[] sample : matrix) {
            ys[index++] = sample[experiment];
        }

        return ys;
    }

    // [experiment][sample]
    public double[][] getYsForExperiments() {
        double[][] ys = new double[N][];

        for (int indexOfExperiment = 0; indexOfExperiment < N; indexOfExperiment++) {
            ys[indexOfExperiment] = getYsForExperiment(indexOfExperiment);
        }

        return ys;
    }

    // amount of samples
    public int getM() {
        return matrix.size();
    }

    public double[] getAverageYsForExperiments() {
        return calculateAverages(getYsForExperiments());
    }

    public double getRequiredProbability() {
        return REQUIRED_PROBABILITY;
    }





    //----------------------------------------------------------------------------------

    private static Map<Integer, Map<Double, Integer>> cochranCoeffGs = null;

    private int getCohranCoeff(int f1, int f2) {
        return cochranCoeffGs.get(f1).get(1.0 * f2);
    }

    // TODO: zip?
    private static void generateCohranCoeffGs(double q) {
        if (cochranCoeffGs != null) {
            return;
        }

//        final double q = 1 - requiredProbability;
        Integer[][] values = new Integer[16][13];

//        final Double[] qs = {0.05, 0.01};
        final Double[] f1s = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 16.0, 36.0, 144.0, INFINITY};
        final Integer[] f2s = {2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 15, 20, 24, 30, 40, 60, 120};

        final Integer[][] valuesQ5 = {
                {9985, 9750, 9392, 9057, 8772, 8534, 8332, 8159, 8010, 7880, 7341, 6602, 5813, 5000},
                {9669, 8709, 7977, 7457, 7071, 6771, 6530, 6333, 6167, 6025, 5466, 4748, 4031, 3333},
                {9065, 7679, 6841, 6287, 5892, 5598, 5365, 5175, 5017, 4884, 4366, 3720, 3093, 2500},
                {8412, 6838, 5981, 5440, 5063, 4783, 4564, 4387, 4241, 4118, 3645, 3066, 2513, 2000},
                {7808, 6161, 5321, 4803, 4447, 4184, 3980, 3817, 3682, 3568, 3135, 2612, 2119, 1667},
                {7271, 5612, 4800, 4307, 3974, 3726, 3535, 3384, 3259, 3154, 2756, 2278, 1833, 1429},
                {6798, 5157, 4377, 3910, 3595, 3362, 3185, 3043, 2926, 2829, 2462, 2022, 1616, 1250},
                {6385, 4775, 4027, 3584, 3286, 3067, 2901, 2768, 2659, 2568, 2226, 1820, 1446, 1111},
                {6020, 4450, 3733, 3311, 3029, 2823, 2666, 2541, 2439, 2353, 2032, 1655, 1308, 1000},
                {5410, 3924, 3264, 2880, 2624, 2439, 2299, 2187, 2098, 2020, 1737, 1403, 1000, 833},
                {4709, 3346, 2758, 2419, 2159, 2034, 1911, 1815, 1736, 1671, 1429, 1144, 889, 667},
                {3894, 2705, 2205, 1921, 1735, 1602, 1501, 1422, 1357, 1303, 1108, 879, 675, 500},
                {3434, 2354, 1907, 1656, 1493, 1374, 1286, 1216, 1160, 1113, 942, 743, 567, 417},
                {2929, 1980, 1593, 1377, 1237, 1137, 1061, 1002, 958, 921, 771, 604, 457, 333},
                {2370, 1576, 1259, 1082, 968, 887, 827, 780, 745, 713, 595, 462, 347, 250},
                {1737, 1131, 895, 766, 682, 623, 583, 552, 520, 497, 411, 316, 234, 167},
                {998, 632, 495, 419, 371, 337, 312, 292, 279, 266, 218, 165, 120, 83}
        };

        final Integer[][] valuesQ1 = {
                {9999, 9950, 9794, 9586, 9373, 9172, 8988, 8823, 8674, 7539, 7949, 7067, 6062, 5000},
                {9933, 9423, 8831, 8355, 7933, 7606, 7335, 7107, 6912, 6743, 6059, 5153, 4230, 3333},
                {9676, 8643, 7814, 7212, 6761, 6410, 6129, 5897, 5702, 5536, 4884, 4057, 3251, 2500},
                {9279, 7885, 6957, 6329, 5875, 5531, 5259, 5037, 4854, 4697, 4094, 3351, 2644, 2000},
                {8828, 7218, 6258, 5635, 5195, 4866, 4608, 4401, 4229, 4048, 3529, 2858, 2229, 1667},
                {8276, 6640, 5685, 5080, 4659, 4347, 4105, 3911, 3751, 3616, 3105, 2494, 1929, 1429},
                {7945, 6162, 5209, 4627, 4226, 3932, 3704, 3552, 3373, 3248, 2779, 2214, 1700, 1250},
                {7544, 5727, 4810, 4251, 3870, 3592, 3378, 3207, 3067, 2950, 2541, 1992, 1521, 1111},
                {7175, 5358, 4469, 3934, 3572, 3308, 3106, 2945, 2813, 2704, 2297, 1811, 7376, 1000},
                {6528, 4751, 3919, 3428, 3099, 2861, 2680, 2535, 2419, 2320, 1961, 1535, 1157, 833},
                {5747, 4069, 3317, 2882, 2593, 2386, 2228, 2104, 2002, 1918, 1612, 1251, 934, 667},
                {4799, 3297, 2654, 2288, 2048, 1877, 1748, 1646, 1567, 1501, 1248, 960, 709, 500},
                {3632, 2412, 1913, 1635, 1454, 1327, 1232, 1157, 1100, 1054, 867, 658, 480, 333},
                {2940, 1951, 1508, 1281, 1135, 1033, 957, 898, 853, 816, 668, 503, 363, 250},
                {2151, 1371, 1069, 902, 796, 722, 668, 625, 594, 567, 461, 344, 245, 167},
                {1252, 759, 585, 489, 429, 387, 357, 334, 316, 302, 242, 178, 125, 83}
        };

        if (q == 0.05) {
            values = valuesQ5;
        } else if (q == 0.01) {
            values = valuesQ1;
        } else {
            //get the closest q
        }

        int indexF2 = 0;
        cochranCoeffGs = new HashMap<>();
        for (Integer f2 : f2s) {
            int indexF1 = 0;
            final Map<Double, Integer> map = new HashMap<>();
            for (Double f1 : f1s) {
                map.put(f1, values[indexF2][indexF1]);

                indexF1++;
            }

            cochranCoeffGs.put(f2, map);
            indexF2++;
        }
    }

    //----------------------------------------------------------------------------------

    private static Map<Double, Map<Double, Double>> studentCoeffTs = null;

    // TODO: zip?
    private static void generateStudentCoeffTs() {
        if (studentCoeffTs != null) {
            return;
        }

        final Double[] f3s = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0,
                13.0, 14.0, 15.0, 16.0, 17.0, 18.0, 19.0, 20.0, 21.0, 22.0, 23.0, 24.0, 25.0,
                26.0, 27.0, 28.0, 29.0, 30.0, INFINITY};
        final Double[] qs = {0.05, 0.01};
        final Double[][] values = {{12.70, 63.70}, {4.30, 9.92}, {3.18, 5.84}, {2.78, 4.60},
                {2.57, 4.03}, {2.45, 3.71}, {2.36, 3.50}, {2.31, 3.36}, {2.26, 3.25}, {2.23, 3.17},
                {2.20, 3.11}, {2.18, 3.05}, {2.16, 3.01}, {2.14, 2.98}, {2.13, 2.95}, {2.12, 2.92},
                {2.11, 2.90}, {2.10, 2.88}, {2.09, 2.86}, {2.09, 2.85}, {2.08, 2.83}, {2.07, 2.82},
                {2.07, 2.81}, {2.06, 2.80}, {2.06, 2.79}, {2.06, 2.78}, {2.05, 2.77}, {2.05, 2.76},
                {2.05, 2.76}, {2.04, 2.75}, {2.02, 2.70}, {2.00, 2.66}, {1.98, 2.62}, {1.96, 2.58}
        };

        int indexF3 = 0;
        studentCoeffTs = new HashMap<>();
        for (Double f3 : f3s) {
            int indexQ = 0;
            final Map<Double, Double> map = new HashMap<>();
            for (Double q : qs) {
                map.put(q, values[indexF3][indexQ]);

                indexF3++;
            }

            studentCoeffTs.put(f3, map);
            indexF3++;
        }
    }

    //----------------------------------------------------------------------------------

    private static Map<Double, Map<Double, Double>> fisherCoeffFs = null;

    // TODO: zip?
    private static void generateFisherCoeffFs(double requiredProbability) {
        if (fisherCoeffFs != null) {
            return;
        }

        final double Q = 1 - requiredProbability;

        Double[][] values = new Double[16][13];

        final Double[] f3s = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0,
                15.0, 16.0, 17.0, 18.0, 19.0, 20.0, 22.0, 24.0, 26.0, 28.0, 30.0, 40.0, 60.0, 120.0, INFINITY};
        final Double[] f4s = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 12.0, 24.0, INFINITY};

        final Double[][] valuesQ5 = {
                {164.4, 199.5, 215.7, 224.6, 230.2, 234.0, 244.9, 249.0, 254.3},
                {18.5, 19.2, 19.2, 19.3, 19.3, 19.3, 19.4, 19.4, 19.5},
                {10.1, 9.6, 9.3, 9.1, 9.0, 8.9, 8.7, 8.6, 8.5},
                {7.7, 6.9, 6.6, 6.4, 6.3, 6.2, 5.9, 5.8, 5.6},
                {6.6, 5.8, 5.4, 5.2, 5.1, 5.0, 4.7, 4.5, 4.4},
                {6.0, 5.1, 4.8, 4.5, 4.4, 4.3, 4.0, 3.8, 3.7},
                {5.5, 4.7, 4.4, 4.1, 4.0, 3.9, 3.6, 3.4, 3.2},
                {5.3, 4.5, 4.1, 3.8, 3.7, 3.6, 3.3, 3.1, 2.9},
                {5.1, 4.3, 3.9, 3.6, 3.5, 3.4, 3.1, 2.9, 2.7},
                {5.0, 4.1, 3.7, 3.5, 3.3, 3.2, 2.9, 2.7, 2.5},
                {4.8, 4.0, 3.6, 3.4, 3.2, 3.1, 2.8, 2.6, 2.4},
                {4.8, 3.9, 3.5, 3.3, 3.1, 3.0, 2.7, 2.5, 2.3},
                {4.7, 3.8, 3.4, 3.2, 3.0, 2.9, 2.6, 2.4, 2.2},
                {4.6, 3.7, 3.3, 3.1, 3.0, 2.9, 2.5, 2.3, 2.1},
                {4.5, 3.7, 3.3, 3.1, 2.9, 2.8, 2.5, 2.3, 2.1},
                {4.5, 3.6, 3.2, 3.0, 2.9, 2.7, 2.4, 2.2, 2.0},
                //needs to be finished
        };

        final Double[][] valuesQ1 = {
                //needs to be done
        };

        if (Q == 0.05) {
            values = valuesQ5;
        } else if (Q == 0.01) {
            values = valuesQ1;
        } else {
            //get the closest Q
        }

        int indexF3 = 0;
        fisherCoeffFs = new HashMap<>();
        for (Double f3 : f3s) {
            int indexF4 = 0;
            final Map<Double, Double> map = new HashMap<>();
            for (Double f4 : f4s) {
                map.put(f4, values[indexF3][indexF4]);

                indexF4++;
            }

            fisherCoeffFs.put(f3, map);
            indexF3++;
        }
    }
}
