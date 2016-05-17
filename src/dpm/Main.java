package dpm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("Desktop Password Manager");
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show();
    }


    public static void main(String[] args) {
        AuthenticationManager.INSTANCE.loadPasswordList();
        launch(args);
    }
}
