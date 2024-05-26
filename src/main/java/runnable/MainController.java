package runnable;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import static backend.Methods.getPolynomialApproximation;
import static backend.Methods.getPolynomialValue;
import static java.util.Objects.isNull;

public class MainController implements Initializable {
    @FXML
    private Label welcomeText;

    @FXML
    private TextField textFieldOne;
    @FXML
    private TextField textFieldTwo;
    @FXML
    private Button submitButton;
    @FXML
    private LineChart<Number, Number> plot;
/*    @FXML
    public SplitPane splitPane;*/

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void handleSubmitEvent(ActionEvent event) {
        double[][] result = handleTextInput(event);
        if (!isNull(result)) {
            //drawDots(result);
            drawLine(1, result);
            drawLine(2, result);
            drawLine(3, result);
        }
    }

    private double[][] handleTextInput(ActionEvent event) {
        if (event.getSource() != submitButton) return null;

        String[] xText = textFieldOne.getText().split("\\s+");
        String[] yText = textFieldTwo.getText().split("\\s+");

/*        System.out.println(Arrays.toString(xText));
        System.out.println(Arrays.toString(yText));*/

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

/*        System.out.println(Arrays.toString(x));
        System.out.println(Arrays.toString(y));*/

        //System.out.println(Arrays.toString(getPolynomialApproximation(1, x, y)));

        return new double[][]{x, y};
    }

    private void drawDots(double[][] xy) {
        double[] x = xy[0];
        double[] y = xy[1];

        int size = x.length;

        XYChart.Series<Number, Number> series = new XYChart.Series<>();

        for(int i = 0; i < size; i++) {
            series.getData().add(new XYChart.Data<>(x[i], y[i]));
        }

        plot.getData().add(series);
    }

    private void drawLine(int power, double[][] xy) {
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

        double[] coefficients = getPolynomialApproximation(power, x, y);

        while (a <= b) {
            series.getData().add(new XYChart.Data<>(a, getPolynomialValue(a, coefficients)));
            a += step;
        }

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
