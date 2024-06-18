package runnable;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

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

    @FXML
    protected void handleSubmitEvent(ActionEvent event) {

        plot.getData().clear();

        double[] result = handleTextInput(event);
        if (!isNull(result)) {

        } else {
            showAlert(Alert.AlertType.ERROR, "Ошибка!", "Введены некорректные данные!");
        }
    }

    private double[] handleTextInput(ActionEvent event) {
        if (event.getSource() != submitButton) return null;

        String tfOneText = validateNumber(TF_1.getText().trim());
        String tfTwoText = validateNumber(TF_2.getText().trim());
        String tfThreeText = validateNumber(TF_3.getText().trim());
        String tfFourText = validateNumber(TF_4.getText().trim());
        String tfFiveText = validateNumber(TF_5.getText().trim());

        if (!isNull(tfOneText) && !isNull(tfTwoText) && !isNull(tfThreeText) && !isNull(tfFourText) && !isNull(tfFiveText)) {
            return (new double[] {
                    Double.parseDouble(tfOneText),
                    Double.parseDouble(tfTwoText),
                    Double.parseDouble(tfThreeText),
                    Double.parseDouble(tfFourText),
                    Double.parseDouble(tfFiveText),
            });
        } else {
            return null;
        }
    }

    @FXML
    protected void handleEquationPickButton(ActionEvent event) {
        int equationNumber = -1;
        if (event.getSource() == eB_1) {
            equationNumber = 1;
        } else if (event.getSource() == eB_2) {
            equationNumber = 2;
        }  else if (event.getSource() == eB_3) {
            equationNumber = 3;
        }

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
        System.out.println("я родился!");
    }
}
