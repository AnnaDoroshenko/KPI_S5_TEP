/**
 * Theory of experiment planning
 * Lab 4
 *
 * @variant: 7
 * @authors: Igor Boyarshin, Anna Doroshenko
 * @group: IO-52
 * @date: 08.11.2017
 */

package Lab04;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Lab04 extends Application {


    Experiment experiment = new Experiment(0.99);
    private final int N = 4;
    private final int m = experiment.getM();
    private final int amountOfColons = m + 13;

    public static void main(String[] args) {
//        Experiment ex = new Experiment(0.99);
//        for (double d : ex.getNormalizedRegressionCoeffs()) {
//            System.out.println(d + " ");
//        }


        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Lab04. Igorek Boyarshin & Anka Doroshenko");
        stage.setWidth(1230);
        stage.setHeight(600);

        final Label label1 = new Label("Probability: " + experiment.getRequiredProbability());
        label1.setFont(new Font("Times New Roman", 20));

        final String[] MapKeys = new String[amountOfColons];

        MapKeys[0] = "ColumnX1";
        MapKeys[1] = "ColumnX2";
        MapKeys[2] = "ColumnX3";
        MapKeys[3] = "ColumnXn0";
        MapKeys[4] = "ColumnXn1";
        MapKeys[5] = "ColumnXn2";
        MapKeys[6] = "ColumnXn3";
        MapKeys[7] = "ColumnXn1Xn2";
        MapKeys[8] = "ColumnXn1Xn3";
        MapKeys[9] = "ColumnXn2Xn3";
        MapKeys[10] = "ColumnXn1Xn2Xn3";

        for (int i = 11; i < amountOfColons - 2; i++) {
            MapKeys[i] = "ColumnY" + i;
        }

        MapKeys[amountOfColons - 2] = "ColumnYm";
        MapKeys[amountOfColons - 1] = "Dispersion";


        ArrayList<TableColumn<Map, String>> DataColumn = new ArrayList<>();

        DataColumn.add(new TableColumn<>("X1"));
        DataColumn.add(new TableColumn<>("X2"));
        DataColumn.add(new TableColumn<>("X3"));
        DataColumn.add(new TableColumn<>("Xn0"));
        DataColumn.add(new TableColumn<>("Xn1"));
        DataColumn.add(new TableColumn<>("Xn2"));
        DataColumn.add(new TableColumn<>("Xn3"));
        DataColumn.add(new TableColumn<>("Xn1Xn2"));
        DataColumn.add(new TableColumn<>("Xn1Xn3"));
        DataColumn.add(new TableColumn<>("Xn2Xn3"));
        DataColumn.add(new TableColumn<>("Xn1Xn2Xn3"));

        for (int i = 11; i < amountOfColons - 2; i++) {
            DataColumn.add(new TableColumn<>("Y" + (i - 10)));
        }

        DataColumn.add(new TableColumn<>("Ym"));
        DataColumn.add(new TableColumn<>("Dispersion"));

        for (int i = 0; i < amountOfColons; i++) {
            DataColumn.get(i).setCellValueFactory(new MapValueFactory(MapKeys[i]));
            DataColumn.get(i).setMinWidth(68);
        }


        TableView table_view = new TableView<>(generateDataInMap(MapKeys));

        final Label label2 = new Label("f1 = " + experiment.getF1() + " f2 = " + experiment.getF2() +
                " f3 = " + experiment.getF3() + " f4 = " + experiment.getF4());
        label2.setFont(new Font("Times New Roman", 20));

        final Label label3 = new Label("Normalized regression: y = " + experiment.getNormalizedRegressionCoeffs()[0] + " + " +
                experiment.getNormalizedRegressionCoeffs()[1] + " * x1 + " + experiment.getNormalizedRegressionCoeffs()[2] + " * x2 + "
                + experiment.getNormalizedRegressionCoeffs()[3] + " * x3");

        if (experiment.getNormalizedRegressionCoeffs().length == 4){
            label3.setText(label3.getText() + " + 0 * x1x2 + 0 * x1x3 + 0 * x2x3 + 0 * x1x2x3");
        } else {
            label3.setText(label3.getText() + " + " + experiment.getNormalizedRegressionCoeffs()[4] + " * x1x2 + " +
                    experiment.getNormalizedRegressionCoeffs()[5] + " * x1x3 + " +
                    experiment.getNormalizedRegressionCoeffs()[6] + " * x2x3 + " +
                    experiment.getNormalizedRegressionCoeffs()[7] + " * x1x2x3");
        }

        label3.setFont(new Font("Times New Roman", 20));

        final Label label4 = new Label("Natural regression: y = " + experiment.getNaturalizedRegressionCoeffs()[0] + " + " +
                experiment.getNaturalizedRegressionCoeffs()[1] + " * x1 + " + experiment.getNaturalizedRegressionCoeffs()[2] + " * x2 + "
                + experiment.getNaturalizedRegressionCoeffs()[3] + " * x3");

        if (experiment.getNaturalizedRegressionCoeffs().length == 4){
            label4.setText(label4.getText() + " + 0 * x1x2 + 0 * x1x3 + 0 * x2x3 + 0 * x1x2x3");
        } else {
            label4.setText(label4.getText() + " + " + experiment.getNaturalizedRegressionCoeffs()[4] + " * x1x2 + " +
                    experiment.getNaturalizedRegressionCoeffs()[5] + " * x1x3 + " +
                    experiment.getNaturalizedRegressionCoeffs()[6] + " * x2x3 + " +
                    experiment.getNaturalizedRegressionCoeffs()[7] + " * x1x2x3");
        }

        label4.setFont(new Font("Times New Roman", 20));

        table_view.setEditable(true);
        table_view.getSelectionModel().setCellSelectionEnabled(true);


        for (int i = 0; i < amountOfColons; i++) {
            table_view.getColumns().setAll(DataColumn);
        }


        Callback<TableColumn<Map, String>, TableCell<Map, String>>
                cellFactoryForMap = p -> new TextFieldTableCell(new StringConverter() {
            @Override
            public String toString(Object t) {
                return t.toString();
            }

            @Override
            public Object fromString(String string) {
                return string;
            }
        });

        for (int i = 0; i < amountOfColons; i++) {
            DataColumn.get(i).setCellFactory(cellFactoryForMap);
        }

        final VBox vbox = new VBox();

        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10, 0, 0, 20));
        vbox.getChildren().addAll(label1, table_view, label2, label3, label4);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);

        stage.show();
    }

    private ObservableList<Map> generateDataInMap(String[] MapKeys) {

        ObservableList<Map> allData = FXCollections.observableArrayList();

        Experiment experimentForVisual = new Experiment(0.99);
        double[] averagedYs = experimentForVisual.getAveragedYsForExperiments();
        double[] dispersionsOfYs = experimentForVisual.getDispersionsOfYs();
        final double[] x1 = experimentForVisual.getX1();
        final double[] x2 = experimentForVisual.getX2();
        final double[] x3 = experimentForVisual.getX3();
        final double[] x0n = experimentForVisual.getX0n();
        final double[] x1n = experimentForVisual.getX1n();
        final double[] x2n = experimentForVisual.getX2n();
        final double[] x3n = experimentForVisual.getX3n();
        final double[] x1nx2n = experimentForVisual.getX1nX2n();
        final double[] x1nx3n = experimentForVisual.getX1nX3n();
        final double[] x2nx3n = experimentForVisual.getX2nX3n();
        final double[] x1nx2nx3n = experimentForVisual.getX1nX2nX3n();


        for (int i = 0; i < N; i++) {
            Map<String, String> dataRow = new HashMap<>();

            String x1Value = String.format("%.3f", x1[i]);
            String x2Value = String.format("%.3f", x2[i]);
            String x3Value = String.format("%.3f", x3[i]);
            String x0nValue = String.format("%.3f", x0n[i]);
            String x1nValue = String.format("%.3f", x1n[i]);
            String x2nValue = String.format("%.3f", x2n[i]);
            String x3nValue = String.format("%.3f", x3n[i]);
            String x1nx2nValue = String.format("%.3f", x1nx2n[i]);
            String x1nx3nValue = String.format("%.3f", x1nx3n[i]);
            String x2nx3nValue = String.format("%.3f", x2nx3n[i]);
            String x1nx2nx3nValue = String.format("%.3f", x1nx2nx3n[i]);
            String ymValue = String.format("%.3f", averagedYs[i]);
            String dispersionValue = String.format("%.3f", dispersionsOfYs[i]);

            dataRow.put(MapKeys[0], x1Value);
            dataRow.put(MapKeys[1], x2Value);
            dataRow.put(MapKeys[2], x3Value);
            dataRow.put(MapKeys[3], x0nValue);
            dataRow.put(MapKeys[4], x1nValue);
            dataRow.put(MapKeys[5], x2nValue);
            dataRow.put(MapKeys[6], x3nValue);
            dataRow.put(MapKeys[7], x1nx2nValue);
            dataRow.put(MapKeys[8], x1nx3nValue);
            dataRow.put(MapKeys[9], x2nx3nValue);
            dataRow.put(MapKeys[10], x1nx2nx3nValue);

            final double[] ys = experimentForVisual.getYsForExperiment(i);

            for (int j = 11; j < amountOfColons - 2; j++) {
                dataRow.put(MapKeys[j], String.format("%.3f", ys[j - 11]));
            }

            dataRow.put(MapKeys[amountOfColons - 2], ymValue);
            dataRow.put(MapKeys[amountOfColons - 1], dispersionValue);

            allData.add(dataRow);
        }

        return allData;
    }
}

