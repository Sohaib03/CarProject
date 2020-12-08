package sample;

import Dashboard.Dashboard;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;


public class Controller {
    @FXML
    private Button signUpButton;
    @FXML
    private Button signInButton;
    @FXML
    private Pane coverPane;
    @FXML
    private JFXTextField signInUsername;
    @FXML
    private JFXTextField signUpUsername;
    @FXML
    private JFXTextField signInPassword;
    @FXML
    private JFXTextField signUpPassword;
    @FXML
    private JFXTextField signUpConfirmPassword;
    @FXML
    private Text errorMessage;

    @FXML
    private void handleSignUpButton(ActionEvent event) throws IOException {
        TranslateTransition translate = new TranslateTransition();
        translate.setByX(-400);
        translate.setDuration(Duration.millis(500));
        translate.setNode(coverPane);
        translate.play();
    }

    @FXML
    private void handleSignInButton(ActionEvent event) throws IOException {
        TranslateTransition translate = new TranslateTransition();
        translate.setByX(400);
        translate.setDuration(Duration.millis(500));
        translate.setNode(coverPane);
        translate.play();
    }

    @FXML
    private void logInClicked(ActionEvent event) throws IOException {
        System.out.println(signInUsername.getText() + ":" + signInPassword.getText());
        boolean success = Main.getInstance().login(signInUsername.getText(), signInPassword.getText());
        if (!success) {
            errorMessage.setText("Username and Password do not match.");
            errorMessage.setFill(Color.RED);
            errorMessage.setVisible(true);
        } else {
            errorMessage.setVisible(false);
            Dashboard dashboard = new Dashboard();

            Stage stage =(Stage) signInButton.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void createNewAccountClicked(ActionEvent event) throws IOException {
        System.out.println(signUpUsername.getText() + ":" + signUpPassword.getText());
    }

    @FXML
    private void closeButtonClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) coverPane.getScene().getWindow();
        stage.close();
    }

}
