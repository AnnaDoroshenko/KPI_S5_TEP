package Lab03;

import java.util.*;

public class Experiment {

    private final double[] XS_MIN = {-5, -35, -35};
    private final double[] XS_MAX = {15, 10, -10};

    private final double X_AVERAGE_MAX = (XS_MAX[0] + XS_MAX[0] + XS_MAX[0]) / 3;
    private final double X_AVERAGE_MIN = (XS_MIN[0] + XS_MIN[0] + XS_MIN[0]) / 3;

    private final double[] x0n = {1, 1, 1};
    private final double[] x1n = {-1, 1, 1};
    private final double[] x2n = {1, -1, 1};
    private final double[] x3n = {1, 1, -1};
    //    private final double[] x0 = {XS_MAX[0], XS_MAX[0], XS_MAX[0]};
    private final double[] x1 = {XS_MIN[0], XS_MAX[0], XS_MAX[0]};
    private final double[] x2 = {XS_MAX[1], XS_MIN[1], XS_MAX[1]};
    private final double[] x3 = {XS_MAX[2], XS_MAX[2], XS_MIN[2]};

    private final double Y_MAX = 200 + X_AVERAGE_MAX;
    private final double Y_MIN = 200 + X_AVERAGE_MIN;

    private final double REQUIRED_PROBABILITY;

    private final int N = 4;

    // [m][N]
    private List<double[]> matrix;

    private static Random random;

    private double[] normalizedRegressionCoeffs;
    private double[] naturalizedRegressionCoeffs;

    public Experiment(double requiredProbability) {
        this.REQUIRED_PROBABILITY = requiredProbability;
        matrix = new ArrayList<>();
        random = new Random();

        generateRomanovCriticalRs();

        doStuff();
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

    private static Map<Double, Map<Double, Integer>> cochranCoeffGs = null;

    // TODO: zip?
    private static void generateCohranCoeffGs() {
        if (cochranCoeffGs != null) {
            return;
        }

        final double infinity = Double.POSITIVE_INFINITY;

        final Double[] qs = {0.05, 0.01};
        final Double[] f1s = {1.0d, 2.0d, 3.0d, 4.0d, 5.0d, 6.0d, 7.0d, 8.0d, 9.0d, 10.0d, 16.0d, 36.0d, 144.0d, infinity};
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

        int indexF2 = 0;
        cochranCoeffGs = new HashMap<>();
        for (Integer f2 : f2s) {
            int indexF1 = 0;
            final Map<Double, Integer> map = new HashMap<>();
            for (Double g : f1s) {
                map.put(g, values[indexF2][indexF1]);

                indexM++;
            }

            cochranCoeffGs.put(p, map);
            indexP++;
        }
    }
}
