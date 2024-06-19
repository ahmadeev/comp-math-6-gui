package runnable;

import backend.Function;
import backend.Result;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static backend.Methods.getAnyMethodLoop;
import static backend.Methods.getEquationByNumber;
import static backend.Utils.showAlert;
import static java.util.Objects.isNull;

public class MainController implements Initializable {
    @FXML
    private Button eB_1;
    @FXML
    private Button eB_2;
    @FXML
    private Button eB_3;

    @FXML
    private Label eL_1;
    @FXML
    private Label eL_2;
    @FXML
    private Label eL_3;

    @FXML
    private TextField TF_1;
    @FXML
    private TextField TF_2;
    @FXML
    private TextField TF_3;
    @FXML
    private TextField TF_4;
    @FXML
    private TextField TF_5;

    @FXML
    private Button submitButton;
    @FXML
    private LineChart<Number, Number> plot;

/*    @FXML
    private TableView<> dataTable;
    @FXML
    private TableColumn<> x;
    @FXML
    private TableColumn<> y;
    @FXML
    private TableColumn<> whatToDo;*/

    private static int equationNumber = -1;

    @FXML
    protected void handleSubmitEvent(ActionEvent event) {

        plot.getData().clear();

        double[] result = handleTextInput(event);
        if (!isNull(result)) {

            if (equationNumber == -1) {
                showAlert(Alert.AlertType.ERROR, "Ошибка!", "Выберите уравнение!");
            } else {
                Function function = getEquationByNumber(equationNumber);
                drawLine(result[1], result[2], function, "Точное решение");

                Result eulerResult = getAnyMethodLoop(1, function, result[0], result[1], result[2], result[3], result[4], 1);
                drawLine(eulerResult.getX(), eulerResult.getY(), "м. Эйлера");
                Result rungeKuttaResult = getAnyMethodLoop(2, function, result[0], result[1], result[2], result[3], result[4], 4);
                drawLine(rungeKuttaResult.getX(), rungeKuttaResult.getY(), "м. Рунге-Кутта (IV)");
/*                Result adamsResult = getAnyMethodLoop(3, function, result[0], result[1], result[2], result[3], result[4], 4);
                drawLine(adamsResult.getX(), adamsResult.getY(), "м. Адамса");*/
            }

        } else {
            showAlert(Alert.AlertType.ERROR, "Ошибка!", "Введены некорректные данные!");
        }
        equationNumber = -1;
    }

    private double[] handleTextInput(ActionEvent event) {
        if (event.getSource() != submitButton) return null;

        String tfOneText = validateNumber(TF_1.getText().trim());
        String tfTwoText = validateNumber(TF_2.getText().trim());
        String tfThreeText = validateNumber(TF_3.getText().trim());
        String tfFourText = validateNumber(TF_4.getText().trim());
        String tfFiveText = validateNumber(TF_5.getText().trim());

        if (!isNull(tfOneText) && !isNull(tfTwoText) && !isNull(tfThreeText) && !isNull(tfFourText) && !isNull(tfFiveText)) {

            double a = Double.parseDouble(tfTwoText);
            double b = Double.parseDouble(tfThreeText);

            if (a >= b) return null;

            return (new double[] {
                    Double.parseDouble(tfOneText),
                    a,
                    b,
                    Double.parseDouble(tfFourText),
                    Double.parseDouble(tfFiveText),
            });
        } else {
            return null;
        }
    }

    @FXML
    protected void handleEquationPickButton(ActionEvent event) {
        int number = -1;
        if (event.getSource() == eB_1) {
            number = 1;
        } else if (event.getSource() == eB_2) {
            number = 2;
        }  else if (event.getSource() == eB_3) {
            number = 3;
        }
        equationNumber = number;
    }

    private void drawLine(ArrayList<Double> x, ArrayList<Double> y, String name) {
        int size = x.size();
        XYChart.Series<Number, Number> series = new XYChart.Series<>();

        for(int i = 0; i < size; i++) {
            series.getData().add(new XYChart.Data<>(x.get(i), y.get(i)));
        }

        series.setName(name);
        plot.setCreateSymbols(false);
        plot.getData().add(series);
    }

    private void drawLine(double a, double b, Function function, String name) {
        double step = 0.01;
        XYChart.Series<Number, Number> series = new XYChart.Series<>();

        for(double i = a; i <= b; i += step) {
            series.getData().add(new XYChart.Data<>(i, function.getExactFunctionValue(i)));
        }

        series.setName(name);
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
        System.out.println("я родился!\n");

        eL_1.setText(Function.FunctionOne.EQUATION);
        eL_2.setText(Function.FunctionTwo.EQUATION);
        eL_3.setText(Function.FunctionThree.EQUATION);

        TF_1.setText("-1");
        TF_2.setText("1");
        TF_3.setText("1.5");
        TF_4.setText("0,1");
        TF_5.setText("0,01");
    }
}
