package Dashboard;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ItemController {

    @FXML
    private Label carModelLabel;
    @FXML
    private Label carPriceLabel;
    @FXML
    private Label registrationLabel;
    @FXML
    private Label colorsLabel;
    @FXML
    private Label carMakeLabel;

    private Car car;

    public void setCarModelLabel(String label) {
        carModelLabel.setText(label);
    }

    public void setData(Car car) {
        this.car = car;
        carModelLabel.setText(car.getCarModel());
        carMakeLabel.setText(car.getCarMake());
        carPriceLabel.setText("$ " + String.valueOf(car.getPrice()));
        registrationLabel.setText("Reg. No: " + car.getRegistrationNumber());
        colorsLabel.setText("Available Colors : " + car.getColor().toString());
    }
}
