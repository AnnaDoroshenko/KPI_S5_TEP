/**
 * Theory of experiment planning
 * Lab 2
 *
 * @variant: 7
 * @authors: Igor Boyarshin, Anna Doroshenko
 * @group: IO-52
 * @date: 11.10.2017
 */

package Lab02;

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

public class Lab02 extends Application {

    Model m = new Model(0.99);
    final int N = 3;
    final int n =  m.getYSamples();
    final int nn = n + 5 ;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Lab02. Igorek Boyarshin & Anka Doroshenko");
        stage.setWidth(1050);
        stage.setHeight(560);

        final Label label1 = new Label("Probability: " + m.getRequiredProbability());
        label1.setFont(new Font("Times New Roman", 20));

        final String[] MapKeys = new String[nn];

        MapKeys[0] = "ColumnX1";
        MapKeys[1] = "ColumnX2";
        MapKeys[2] = "ColumnXn1";
        MapKeys[3] = "ColumnXn2";

        for (int i = 4; i < nn - 1; i++) {
            MapKeys[i] = "ColumnY" + i;
        }

        MapKeys[nn - 1] = "ColumnYm";


        ArrayList<TableColumn<Map, String>> DataColumn = new ArrayList<>();

        DataColumn.add(new TableColumn<>("X1"));
        DataColumn.add(new TableColumn<>("X2"));
        DataColumn.add(new TableColumn<>("Xn1"));
        DataColumn.add(new TableColumn<>("Xn2"));

        for (int i = 4; i < nn - 1; i++) {
            DataColumn.add(new TableColumn<>("Y" + i));
        }

        DataColumn.add(new TableColumn<>("Ym"));

        for (int i = 0; i < nn; i++) {
            DataColumn.get(i).setCellValueFactory(new MapValueFactory(MapKeys[i]));
            DataColumn.get(i).setMinWidth(100);
        }


        TableView table_view = new TableView<>(generateDataInMap(MapKeys));

        final Label label2 = new Label("Normalized regression: y = " + m.getNormalizedRegressionCoeffs()[0] + " + " +
                m.getNormalizedRegressionCoeffs()[1] + " * x1 + " +  m.getNormalizedRegressionCoeffs()[2] + " * x2");
        label2.setFont(new Font("Times New Roman", 20));

        final Label label3 = new Label("Natural regression: y = " + m.getNaturalizedRegressionCoeffs()[0] + " + " +
                m.getNaturalizedRegressionCoeffs()[1] + " * x1 + " +  m.getNaturalizedRegressionCoeffs()[2] + " * x2");
        label3.setFont(new Font("Times New Roman", 20));

        table_view.setEditable(true);
        table_view.getSelectionModel().setCellSelectionEnabled(true);


        for (int i = 0; i < nn; i++) {
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

        for (int i = 0; i < nn; i++) {
            DataColumn.get(i).setCellFactory(cellFactoryForMap);
        }

        final VBox vbox = new VBox();

        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10, 0, 0, 20));
        vbox.getChildren().addAll(label1, table_view, label2, label3);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);

        stage.show();
    }

    private ObservableList<Map> generateDataInMap(String[] MapKeys) {

        ObservableList<Map> allData = FXCollections.observableArrayList();

        Model model = new Model(0.99);
        double[] m = model.getYm();
        final double [] x1 = model.getX1();
        final double [] x2 = model.getX2();
        final double [] x1n = model.getX1n();
        final double [] x2n = model.getX2n();



        for (int i = 0; i < N; i++) {
            Map<String, String> dataRow = new HashMap<>();

            String x1Value = String.format("%.3f", x1[i]);
            String x2Value = String.format("%.3f", x2[i]);
            String x1nValue = String.format("%.3f", x1n[i]);
            String x2nValue = String.format("%.3f", x2n[i]);
            String ymValue = String.format("%.3f", m[i]);

            dataRow.put(MapKeys[0], x1Value);
            dataRow.put(MapKeys[1], x2Value);
            dataRow.put(MapKeys[2], x1nValue);
            dataRow.put(MapKeys[3], x2nValue);

            final double[]ys = model.getYsForExperiment(i);

            for (int j = 4; j < nn - 1; j++) {
                dataRow.put(MapKeys[j], String.format("%.3f", ys[j - 4]));
            }

            dataRow.put(MapKeys[nn - 1], ymValue);

            allData.add(dataRow);
        }

        return allData;
    }
}