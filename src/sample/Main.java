package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {



    @Override
    public void start(Stage stage){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FormDetails.fxml"));
            Scene scene = new Scene(root);

            Image icon = new Image("LogoLaFortaleza.png");
            stage.getIcons().add(icon);
            stage.setTitle("La Fortaleza Purchase Order Management System");

            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (Exception error) {
            System.out.println(error);
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
