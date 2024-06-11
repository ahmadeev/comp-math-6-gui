package runnable;

import backend.CalculatedData.*;
import backend.Methods.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static Polynomial polynomial = new Polynomial();

    public static Linear linear = new Linear();
    public static Quadratic quadratic = new Quadratic();
    public static Cubic cubic = new Cubic();

    public static Exponential exponential = new Exponential();
    public static Logarithmic logarithmic = new Logarithmic();
    public static PowerFunction powerFunction = new PowerFunction();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(runnable.Main.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("application");

        stage.setResizable(false);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        for(double i = 0; i <= 2; i += 0.2) {
            System.out.println(i + " : " + 12 * i / (Math.pow(i, 4) + 1));
        }

        launch();
    }
}
