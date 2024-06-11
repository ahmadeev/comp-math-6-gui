package runnable;

import backend.CalculatedData;
import backend.CalculatedData.*;
import backend.Functions;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import static backend.Methods.calculatePearsonCoefficient;
import static backend.Methods.getFunctionByNumber;
import static backend.math.Utils.showAlert;
import static java.util.Objects.isNull;

public class MainController implements Initializable {
    private static double rValue;
    private static double[][] xyArray;

    @FXML
    private TextField textFieldOne;
    @FXML
    private TextField textFieldTwo;
    @FXML
    private Button submitButton;
    @FXML
    private LineChart<Number, Number> plot;

    @FXML
    private Button r;
    @FXML
    private Button xy;

    @FXML
    private TableView<CalculatedData> dataTable;
    @FXML
    private TableColumn<CalculatedData, String> functionName;
    @FXML
    private TableColumn<CalculatedData, Double> a;
    @FXML
    private TableColumn<CalculatedData, Double> b;
    @FXML
    private TableColumn<CalculatedData, Double> c;
    @FXML
    private TableColumn<CalculatedData, Double> deflectionAmount;
    @FXML
    private TableColumn<CalculatedData, Double> standardDeviation;
    @FXML
    private TableColumn<CalculatedData, Double> determinationCoefficient;

    @FXML
    protected void handleSubmitEvent(ActionEvent event) {

        plot.getData().clear();

        double[][] result = handleTextInput(event);
        if (!isNull(result)) {

            if (result[0].length < 8 || result[0].length > 12) {
                showAlert(Alert.AlertType.ERROR, "error!", "Введенные данные должны содержать от восьми до двенадцати точек!");
                textFieldOne.setText("");
                textFieldTwo.setText("");
            } else {
                xyArray = result;
                double[] x = result[0];
                double[] y = result[1];

                CalculatedData linearData = new LinearData(x, y);
                CalculatedData quadraticData = new QuadraticData(x, y);
                CalculatedData cubicData = new CubicData(x, y);
                CalculatedData exponentialData = new ExponentialData(x, y);
                CalculatedData logarithmicData = new LogarithmicData(x, y);
                CalculatedData powerFunctionData = new PowerFunctionData(x, y);

                System.out.println(linearData.toString());
                System.out.println(quadraticData.toString());
                System.out.println(cubicData.toString());
                System.out.println(exponentialData.toString());
                System.out.println(logarithmicData.toString());
                System.out.println(powerFunctionData.toString());

                ObservableList<CalculatedData> tableData = FXCollections.observableArrayList(linearData, quadraticData, cubicData, exponentialData, logarithmicData, powerFunctionData);

                //drawDots(result);
                for(int i = 1; i <= 6; i++) {
                    drawLine(i, result);
                }

                a.setCellValueFactory(new PropertyValueFactory<CalculatedData, Double>("a"));
                b.setCellValueFactory(new PropertyValueFactory<CalculatedData, Double>("b"));
                c.setCellValueFactory(new PropertyValueFactory<CalculatedData, Double>("c"));

                functionName.setCellValueFactory(new PropertyValueFactory<CalculatedData, String>("functionName"));
                deflectionAmount.setCellValueFactory(new PropertyValueFactory<CalculatedData, Double>("deflectionAmount"));
                standardDeviation.setCellValueFactory(new PropertyValueFactory<CalculatedData, Double>("standardDeviation"));
                determinationCoefficient.setCellValueFactory(new PropertyValueFactory<CalculatedData, Double>("determinationCoefficient"));

                dataTable.setItems(tableData);

                rValue = calculatePearsonCoefficient(x, y);

            }
        } else {
            showAlert(Alert.AlertType.ERROR, "error!", "Введены некорректные данные!");
            textFieldOne.setText("");
            textFieldTwo.setText("");
        }
    }

    private double[][] handleTextInput(ActionEvent event) {
        if (event.getSource() != submitButton) return null;

        String[] xText = textFieldOne.getText().split("\\s+");
        String[] yText = textFieldTwo.getText().split("\\s+");

        if (xText.length != yText.length) return null;

        int size = xText.length;
        double[] x = new double[size];
        double[] y = new double[size];;

        for(int i = 0; i < size; i++) {
            xText[i] = validateNumber(xText[i]);
            yText[i] = validateNumber(yText[i]);

            if (!(isNull(xText[i]) || isNull(yText[i]))) {
                x[i] = Double.parseDouble(xText[i]);
                y[i] = Double.parseDouble(yText[i]);
            } else {
                return null;
            }
        }

        return new double[][]{x, y};
    }

    @FXML
    protected void handleRButton() {
        if (rValue != 0) {
            String content =
                    """
                            \n \nПри r = ±1 -- строгая линейная функциональная зависимость
                            При r = 0 -- линейная связь между переменными отсутствует
                            r < 0,3 -- связь слабая
                            r = 0,3 ÷ 0,5 -- связь умеренная
                            r = 0,7 ÷ 0,7 -- связь заметная
                            r = 0,7 ÷ 0,9 -- связь высокая
                            r = 0,9 ÷ 0,99 -- связь весьма высокая""";
            showAlert(Alert.AlertType.INFORMATION, "r", "Коэффициент корреляции Пирсона: " + rValue + content);
        } else {
            showAlert(Alert.AlertType.ERROR, "r", "Сначала введите точки x и y!");
        }
    }

    @FXML
    protected void handleXYButton() {
        if (!isNull(xyArray)) {
            String content =
                    "x: " + Arrays.toString(xyArray[0]) + "\n" +
                    "y: " + Arrays.toString(xyArray[1]);
            showAlert(Alert.AlertType.INFORMATION, "xy", content);
        } else {
            showAlert(Alert.AlertType.ERROR, "xy", "Сначала введите точки x и y!");
        }
    }

/*    private void drawDots(double[][] xy) {
        double[] x = xy[0];
        double[] y = xy[1];

        int size = x.length;

        XYChart.Series<Number, Number> series = new XYChart.Series<>();

        for(int i = 0; i < size; i++) {
            series.getData().add(new XYChart.Data<>(x[i], y[i]));
        }

        plot.getData().add(series);

*//*        Platform.runLater(() -> {
            //series.getNode().setStyle("-fx-stroke: red; -fx-stroke-width: 0px;");
            String css = Main.class.getResource("chart-style.css").toExternalForm();
            plot.getStylesheets().add(css);
        });*//*
    }*/

    private void drawLine(int number, double[][] xy) {
        double[] x = xy[0];
        double[] y = xy[1];

        int size = x.length;
        double step = 0.01;

        XYChart.Series<Number, Number> series = new XYChart.Series<>();


//        for(int i = 0; i < size; i++) {
//            series.getData().add(new XYChart.Data<>(x[i], y[i]));
//        }

        double a = x[0] - 10 * step;
        double b = x[size - 1] + 10 * step;

        Functions function = getFunctionByNumber(number);

        double[] coefficients = function.getApproximation(x, y);

        while (a <= b) {
            series.getData().add(new XYChart.Data<>(a, function.getValue(a, coefficients)));
            a += step;
        }

        plot.setAxisSortingPolicy(LineChart.SortingPolicy.NONE);

        series.setName(function.getClass().getSimpleName());
        plot.setCreateSymbols(false);
        plot.getData().add(series);
    }

    private String validateNumber(String text) {
        text = text.replace(",", ".");
        if (text.matches("[+-]?([0-9]*[.])?[0-9]+")) {
            return text;
        } else {
            return null;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


/*        XYChart.Series<Number, Number> seriesOne = new XYChart.Series<>();

        int counter = 0;
        while (counter < 100) {
            seriesOne.getData().add(new XYChart.Data<>(5, counter));
            counter++;
        }
        plot.setCreateSymbols(false);
        plot.getData().add(seriesOne);*/

/*        Label label = new Label("нет");
        StackPane stackPane = (StackPane) splitPane.getItems().get(1);
        stackPane.getChildren().add(label);*/
    }
}
