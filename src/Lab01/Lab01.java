///**
// * Theory of experiment planning
// * Lab 1
// *
// * @variant: 7
// * @authors: Igor Boyarshin, Anna Doroshenko
// * @group: IO-52
// * @date: 27.09.2017
// */
//
//package Lab01;
//
//import javafx.application.Application;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.geometry.Insets;
//import javafx.scene.Group;
//import javafx.scene.Scene;
//import javafx.scene.control.Label;
//import javafx.scene.control.TableCell;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.cell.MapValueFactory;
//import javafx.scene.control.cell.TextFieldTableCell;
//import javafx.scene.layout.VBox;
//import javafx.scene.text.Font;
//import javafx.stage.Stage;
//import javafx.util.Callback;
//import javafx.util.StringConverter;
//import java.util.HashMap;
//import java.util.Map;
//
//public class Lab01 extends Application {
//
//    final int n = 8;
//    Operations operations = new Operations(n);
//
//    int[] x1 = new int[n];
//    int[] x2 = new int[n];
//    int[] x3 = new int[n];
//
//    private static final String Column0MapKey = "Column0";
//    private static final String Column1MapKey = "ColumnX1";
//    private static final String Column2MapKey = "ColumnX2";
//    private static final String Column3MapKey = "ColumnX3";
//    private static final String Column4MapKey = "ColumnY";
//    private static final String Column5MapKey = "ColumnXn1";
//    private static final String Column6MapKey = "ColumnXn2";
//    private static final String Column7MapKey = "ColumnXn3";
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//    @Override
//    public void start(Stage stage) {
//        Scene scene = new Scene(new Group());
//        stage.setTitle("Lab01. Igorek Boyarshin & Anka Doroshenko");
//        stage.setWidth(850);
//        stage.setHeight(530);
//
//        final Label label = new Label(operations.firstCall());
//        label.setFont(new Font("Times New Roman", 20));
//
//        TableColumn<Map, String> zeroDataColumn = new TableColumn<>("№");
//        TableColumn<Map, String> firstDataColumn = new TableColumn<>("X1");
//        TableColumn<Map, String> secondDataColumn = new TableColumn<>("X2");
//        TableColumn<Map, String> thirdDataColumn = new TableColumn<>("X3");
//        TableColumn<Map, String> fourthDataColumn = new TableColumn<>("Y");
//        TableColumn<Map, String> fifthDataColumn = new TableColumn<>("Xn1");
//        TableColumn<Map, String> sixthDataColumn = new TableColumn<>("Xn2");
//        TableColumn<Map, String> seventhDataColumn = new TableColumn<>("Xn3");
//
//        zeroDataColumn.setCellValueFactory(new MapValueFactory(Column0MapKey));
//        zeroDataColumn.setMinWidth(100);
//        firstDataColumn.setCellValueFactory(new MapValueFactory(Column1MapKey));
//        firstDataColumn.setMinWidth(100);
//        secondDataColumn.setCellValueFactory(new MapValueFactory(Column2MapKey));
//        secondDataColumn.setMinWidth(100);
//        thirdDataColumn.setCellValueFactory(new MapValueFactory(Column3MapKey));
//        thirdDataColumn.setMinWidth(100);
//        fourthDataColumn.setCellValueFactory(new MapValueFactory(Column4MapKey));
//        fourthDataColumn.setMinWidth(100);
//        fifthDataColumn.setCellValueFactory(new MapValueFactory(Column5MapKey));
//        fifthDataColumn.setMinWidth(100);
//        sixthDataColumn.setCellValueFactory(new MapValueFactory(Column6MapKey));
//        sixthDataColumn.setMinWidth(100);
//        seventhDataColumn.setCellValueFactory(new MapValueFactory(Column7MapKey));
//        seventhDataColumn.setMinWidth(100);
//
//        TableView table_view = new TableView<>(generateDataInMap());
//
//        final Label finalLabel = new Label(operations.bestCall(x1, x2, x3));
//        finalLabel.setFont(new Font("Times New Roman", 20));
//
//        table_view.setEditable(true);
//        table_view.getSelectionModel().setCellSelectionEnabled(true);
//        table_view.getColumns().setAll(zeroDataColumn, firstDataColumn, secondDataColumn, thirdDataColumn,
//                fourthDataColumn, fifthDataColumn, sixthDataColumn, seventhDataColumn);
//        Callback<TableColumn<Map, String>, TableCell<Map, String>>
//                cellFactoryForMap = p -> new TextFieldTableCell(new StringConverter() {
//            @Override
//            public String toString(Object t) {
//                return t.toString();
//            }
//
//            @Override
//            public Object fromString(String string) {
//                return string;
//            }
//        });
//
//        zeroDataColumn.setCellFactory(cellFactoryForMap);
//        firstDataColumn.setCellFactory(cellFactoryForMap);
//        secondDataColumn.setCellFactory(cellFactoryForMap);
//        thirdDataColumn.setCellFactory(cellFactoryForMap);
//        fourthDataColumn.setCellFactory(cellFactoryForMap);
//        fifthDataColumn.setCellFactory(cellFactoryForMap);
//        sixthDataColumn.setCellFactory(cellFactoryForMap);
//        seventhDataColumn.setCellFactory(cellFactoryForMap);
//
//        final VBox vbox = new VBox();
//
//        vbox.setSpacing(10);
//        vbox.setPadding(new Insets(10, 0, 0, 20));
//        vbox.getChildren().addAll(label, table_view, finalLabel);
//
//        ((Group) scene.getRoot()).getChildren().addAll(vbox);
//
//        stage.setScene(scene);
//
//        stage.show();
//    }
//
//    private ObservableList<Map> generateDataInMap() {
////        final int n = 8;
//
////        int[] x1 = new int[n];
////        int[] x2 = new int[n];
////        int[] x3 = new int[n];
//        int[] y;
//
//        double[] xn1;
//        double[] xn2;
//        double[] xn3;
//
//        double x0_1;
//        double x0_2;
//        double x0_3;
//
//        double dx1;
//        double dx2;
//        double dx3;
//
//        operations.random_x(x1);
//        operations.random_x(x2);
//        operations.random_x(x3);
//
//        y = operations.calculate_y(x1, x2, x3);
//
//        int x1_min = operations.search_Xmin(x1);
//        int x2_min = operations.search_Xmin(x2);
//        int x3_min = operations.search_Xmin(x3);
//
//        int x1_max = operations.search_Xmax(x1);
//        int x2_max = operations.search_Xmax(x2);
//        int x3_max = operations.search_Xmax(x3);
//
//        x0_1 = operations.calculate_x0(x1_max, x1_min);
//        x0_2 = operations.calculate_x0(x2_max, x2_min);
//        x0_3 = operations.calculate_x0(x3_max, x3_min);
//
//        dx1 = operations.calculate_dx(x0_1, x1_min);
//        dx2 = operations.calculate_dx(x0_2, x2_min);
//        dx3 = operations.calculate_dx(x0_3, x3_min);
//
//        xn1 = operations.calculate_xn(x1, x0_1, dx1);
//        xn2 = operations.calculate_xn(x2, x0_2, dx2);
//        xn3 = operations.calculate_xn(x3, x0_3, dx3);
//
//        double y_standard = operations.calculate_y_standard(x0_1, x0_2, x0_3);
//
//        double y_bestFit = operations.find_BestFit(y, y_standard);
//
//        ObservableList<Map> allData = FXCollections.observableArrayList();
//
//        for (int i = 0; i < (n + 7); i++) {
//            Map<String, String> dataRow = new HashMap<>();
//
//            if (i < n) {
//                String value0 = Integer.toString(i + 1);
//                String value1 = Integer.toString(x1[i]);
//                String value2 = Integer.toString(x2[i]);
//                String value3 = Integer.toString(x3[i]);
//                String value4 = Integer.toString(y[i]);
//                String value5 = String.format("%.3f", xn1[i]);
//                String value6 = String.format("%.3f", xn2[i]);
//                String value7 = String.format("%.3f", xn3[i]);
//
//                dataRow.put(Column0MapKey, value0);
//                dataRow.put(Column1MapKey, value1);
//                dataRow.put(Column2MapKey, value2);
//                dataRow.put(Column3MapKey, value3);
//                dataRow.put(Column4MapKey, value4);
//                dataRow.put(Column5MapKey, value5);
//                dataRow.put(Column6MapKey, value6);
//                dataRow.put(Column7MapKey, value7);
//
//                allData.add(dataRow);
//            } else if (i < (n + 1)) {
//                dataRow.put(Column0MapKey, " ");
//                dataRow.put(Column1MapKey, " ");
//                dataRow.put(Column2MapKey, " ");
//                dataRow.put(Column3MapKey, " ");
//                dataRow.put(Column4MapKey, " ");
//                dataRow.put(Column5MapKey, " ");
//                dataRow.put(Column6MapKey, " ");
//                dataRow.put(Column7MapKey, " ");
//                allData.add(dataRow);
//            } else if (i < (n + 2)) {
//                dataRow.put(Column0MapKey, "x0");
//                dataRow.put(Column1MapKey, Double.toString(x0_1));
//                dataRow.put(Column2MapKey, Double.toString(x0_2));
//                dataRow.put(Column3MapKey, Double.toString(x0_3));
//                dataRow.put(Column4MapKey, " ");
//                dataRow.put(Column5MapKey, " ");
//                dataRow.put(Column6MapKey, " ");
//                dataRow.put(Column7MapKey, " ");
//                allData.add(dataRow);
//            } else if (i < (n + 3)){
//                dataRow.put(Column0MapKey, "dx");
//                dataRow.put(Column1MapKey, Double.toString(dx1));
//                dataRow.put(Column2MapKey, Double.toString(dx2));
//                dataRow.put(Column3MapKey, Double.toString(dx3));
//                dataRow.put(Column4MapKey, " ");
//                dataRow.put(Column5MapKey, " ");
//                dataRow.put(Column6MapKey, " ");
//                dataRow.put(Column7MapKey, " ");
//                allData.add(dataRow);
//            } else if (i < (n + 4)) {
//                dataRow.put(Column0MapKey, " ");
//                dataRow.put(Column1MapKey, " ");
//                dataRow.put(Column2MapKey, " ");
//                dataRow.put(Column3MapKey, " ");
//                dataRow.put(Column4MapKey, " ");
//                dataRow.put(Column5MapKey, " ");
//                dataRow.put(Column6MapKey, " ");
//                dataRow.put(Column7MapKey, " ");
//                allData.add(dataRow);
//            } else if (i < (n + 5)) {
//                dataRow.put(Column0MapKey, "Y_standard");
//                dataRow.put(Column1MapKey, Double.toString(y_standard));
//                dataRow.put(Column2MapKey, " ");
//                dataRow.put(Column3MapKey, " ");
//                dataRow.put(Column4MapKey, " ");
//                dataRow.put(Column5MapKey, " ");
//                dataRow.put(Column6MapKey, " ");
//                dataRow.put(Column7MapKey, " ");
//                allData.add(dataRow);
//            } else if (i < (n + 6)) {
//                dataRow.put(Column0MapKey, " ");
//                dataRow.put(Column1MapKey, " ");
//                dataRow.put(Column2MapKey, " ");
//                dataRow.put(Column3MapKey, " ");
//                dataRow.put(Column4MapKey, " ");
//                dataRow.put(Column5MapKey, " ");
//                dataRow.put(Column6MapKey, " ");
//                dataRow.put(Column7MapKey, " ");
//                allData.add(dataRow);
//            } else {
//                dataRow.put(Column0MapKey, "-> Y_Standard");
//                dataRow.put(Column1MapKey, Double.toString(y_bestFit));
//                dataRow.put(Column2MapKey, " ");
//                dataRow.put(Column3MapKey, " ");
//                dataRow.put(Column4MapKey, " ");
//                dataRow.put(Column5MapKey, " ");
//                dataRow.put(Column6MapKey, " ");
//                dataRow.put(Column7MapKey, " ");
//                allData.add(dataRow);
//            }
//        }
//        return allData;
//    }
//}
