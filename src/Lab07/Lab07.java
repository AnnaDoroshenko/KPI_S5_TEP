/**
 * Theory of experiment planning
 * Lab 7
 *
 * @variant: 7
 * @authors: Igor Boyarshin, Anna Doroshenko
 * @group: IO-52
 * @date: 06.12.2017
 */

package Lab07;

public class Lab07 {
    public static void main(String[] args) {
        Experiment e = new Experiment(5, 12.8, 12, 13);
        e.findOptimum();
    }
}

