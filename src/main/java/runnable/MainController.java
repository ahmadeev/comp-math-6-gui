package runnable;

import backend.CalculatedData.*;
import backend.Functions;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

import static backend.Methods.getFunctionByNumber;
import static backend.math.Utils.showAlert;
import static java.util.Objects.isNull;

public class MainController implements Initializable {
    @FXML
    private TextField textFieldOne;
    @FXML
    private TextField textFieldTwo;
    @FXML
    private Button submitButton;
    @FXML
    private LineChart<Number, Number> plot;

    @FXML
    protected void handleSubmitEvent(ActionEvent event) {

        plot.getData().clear();

        double[][] result = handleTextInput(event);
        if (!isNull(result)) {
            double[] x = result[0];
            double[] y = result[1];

            LinearData linearData = new LinearData(x, y);
            System.out.println(linearData.toString());
            QuadraticData quadraticData = new QuadraticData(x, y);
            System.out.println(quadraticData.toString());
            CubicData cubicData = new CubicData(x, y);
            System.out.println(cubicData.toString());
            ExponentialData exponentialData = new ExponentialData(x, y);
            System.out.println(exponentialData.toString());
            LogarithmicData logarithmicData = new LogarithmicData(x, y);
            System.out.println(logarithmicData.toString());
            PowerFunctionData powerFunctionData = new PowerFunctionData(x, y);
            System.out.println(powerFunctionData.toString());

            //drawDots(result);
            for(int i = 1; i <= 6; i++) {
                drawLine(i, result);
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "error!", "Ошибка ввода!");
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

        for(int i = 0; i < size; i++) {
            series.getData().add(new XYChart.Data<>(x[i], y[i]));
        }

        double a = x[0] - 10 * step;
        double b = x[size - 1] + 10 * step;

        Functions function = getFunctionByNumber(number);

        double[] coefficients = function.getApproximation(x, y);

        while (a <= b) {
            series.getData().add(new XYChart.Data<>(a, function.getValue(a, coefficients)));
            a += step;
        }

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
