package dpm;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private Label confirmPasswordLabel;

    @FXML
    private Text errorMessage;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event)
    {
            if (AuthenticationManager.INSTANCE.getHash()!=null)
            {
                if (AuthenticationManager.INSTANCE.checkMasterPassword(passwordField.getText()))
                {
                    launchMainScene();
                }
                errorMessage.setText("Wrong Password");
            }
            else
            {
                if (confirmPasswordField.getText().equals(passwordField.getText())) {
                    AuthenticationManager.INSTANCE.setMasterPassword(passwordField.getText());
                    launchMainScene();
                    return;
                }
                errorMessage.setText("Password Mismatch");
            }

    }

    protected void launchMainScene()
    {
        Stage stage =(Stage) loginButton.getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize()
    {
        if(AuthenticationManager.INSTANCE.getHash()!=null)
        {
            confirmPasswordField.setVisible(false);
            confirmPasswordLabel.setVisible(false);
        }

        passwordField.setOnKeyReleased(event -> {
            if (AuthenticationManager.INSTANCE.checkMasterPassword(passwordField.getText()))
            {
                launchMainScene();
            }
        });

    }
}
