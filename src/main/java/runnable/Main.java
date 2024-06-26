package runnable;

import backend.Function;
import backend.Methods.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static Euler euler = new Euler();
    public static RungeKutta rungeKutta = new RungeKutta();
    public static Adams adams = new Adams();

    public static Function functionOne = new Function.FunctionOne();
    public static Function functionTwo = new Function.FunctionTwo();
    public static Function functionThree = new Function.FunctionThree();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(runnable.Main.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("ЛР №6");

        stage.setResizable(false);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
