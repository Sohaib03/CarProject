package Dashboard;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCarController implements Initializable {

    @FXML
    private JFXButton closeButton;

    @FXML
    private JFXTextField carMakeTextField;

    @FXML
    private JFXTextField carModelTextField;

    @FXML
    private JFXTextField registrationNumberTextField;

    @FXML
    private JFXTextField priceTextField;

    @FXML
    private JFXTextField yearTextField;

    @FXML
    private JFXTextField colorTextField;

    @FXML
    private JFXButton addCarButton;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private Label errorLabel;

    @FXML
    void close(ActionEvent event) {
        Dashboard db = Dashboard.getInstance();
        db.pw.println("EXIT");
        db.pw.flush();
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }

    @FXML
    void addButtonClicked(ActionEvent event) {
        errorLabel.setVisible(false);
        System.out.println("Add Button Clicked");
        String errorMsg = "";
        if (carMakeTextField.getText().isEmpty()) {
            errorMsg = "Cannot leave Car Make empty!";
        } else if (carModelTextField.getText().isEmpty()) {
            errorMsg = "Cannot leave Car Model empty!";
        } else if (registrationNumberTextField.getText().isEmpty()) {
            errorMsg = "Cannot leave Registration Number empty!";
        } else if (colorTextField.getText().isEmpty()) {
            errorMsg = "Cannot leave Car Color empty!";
        } else if (priceTextField.getText().isEmpty()) {
            errorMsg = "Cannot leave Car Price empty!";
        } else if (yearTextField.getText().isEmpty())  {
            errorMsg = "Cannot leave Car Year empty!";
        } else {
            try {
                Integer.parseInt(priceTextField.getText());
            } catch (Exception e) {
                errorMsg = "Enter Valid Price";
            }

            try {
                Integer.parseInt(yearTextField.getText());
            } catch (Exception e) {
                errorMsg = "Enter Valid Year";
            }
        }
        
        if (errorMsg.isEmpty()) {
            System.out.println("Success");
            String carData = registrationNumberTextField.getText()+";"+yearTextField.getText()+";"+
                    colorTextField.getText()+";"+carMakeTextField.getText()+";"+carModelTextField.getText()+";"+
                    priceTextField.getText();
            Dashboard.getInstance().pw.println("POST");
            Dashboard.getInstance().pw.println(carData);
            Dashboard.getInstance().pw.flush();
            Dashboard.getInstance().showDashBoardScene();
        } else {
            System.out.println(errorMsg);
            errorLabel.setVisible(true);
            errorLabel.setText(errorMsg);
            errorLabel.setTextFill(Color.RED);
        }
    }

    @FXML
    void cancelButtonClicked(ActionEvent event) {
        System.out.println("Cancel Button Clicked");

        Dashboard.getInstance().showDashBoardScene();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        closeButton.setText("");
        errorLabel.setVisible(false);
    }
}
