package Dashboard;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Main;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class DashboardController implements Initializable {
    @FXML
    private AnchorPane mainPane;

    @FXML
    private JFXButton refreshButton;

    @FXML
    private JFXButton aboutButton;

    @FXML
    private JFXButton addButton;

    @FXML
    private JFXButton logoutButton;

    @FXML
    private JFXButton closeButton;

    @FXML
    private JFXTextField searchBar;

    @FXML
    private JFXButton searchButton;

    @FXML
    private JFXRadioButton searchRadioReg;

    @FXML
    private JFXRadioButton searchRadioMake;

    @FXML
    private JFXRadioButton searchRadioModel;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane gridPane;

    ToggleGroup searchToggleGroup = new ToggleGroup();
    private List<Car> carList = new ArrayList<>();

    public static DashboardController instance;

    @FXML
    private void close(ActionEvent event) throws IOException {
        Dashboard db = Dashboard.getInstance();
        db.pw.println("EXIT");
        db.pw.flush();
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.close();
        Platform.exit();
    }

    private List<Car> getData() {
        return Dashboard.getInstance().carData;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        closeButton.setText("");
        refreshButton.setText("");
        aboutButton.setText("");
        logoutButton.setText("");
        addButton.setText("");

        if (Main.getInstance().status == 2) {
            addButton.setVisible(false);
        }

        refreshButton.setOnAction((e) -> {
            showAllCars();
        });
        searchButton.setOnAction((e) -> {
            searchButtonClicked();
        });
        addButton.setOnAction((e) ->{
            addButtonClicked();
        });
        logoutButton.setOnAction((e) -> {
            logOut();
        });

        searchRadioMake.setToggleGroup(searchToggleGroup);
        searchRadioModel.setToggleGroup(searchToggleGroup);
        searchRadioReg.setToggleGroup(searchToggleGroup);

        searchRadioReg.setSelected(true);
        instance = this;
    }

    public void searchButtonClicked () {
        JFXRadioButton selectedRadio = (JFXRadioButton) searchToggleGroup.getSelectedToggle();
        if (selectedRadio == searchRadioReg) {
            filterByRegistration(searchBar.getText());
        }
        else if (selectedRadio == searchRadioMake) {
            filterByMake(searchBar.getText());
        }
        else {
            filterByModel(searchBar.getText());
        }
    }

    public void addButtonClicked () {
        Dashboard.getInstance().showAddCarScene();
    }

    public void logOut() {
        Dashboard.getInstance().pw.println("EXIT");
        Dashboard.getInstance().pw.flush();
        try {
            Main.getInstance().start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.close();
        //Platform.exit();
    }

    public void showAllCars() {
        gridPane.getChildren().clear() ;
        carList.clear();
        carList.addAll(getData());
        setCarGrid();
    }

    public void filterByRegistration(String reg)  {
        gridPane.getChildren().clear();
        carList.clear();
        List<Car> allCarList = getData();
        for (Car car : allCarList) {
            if (car.getRegistrationNumber().toLowerCase().contains(reg.toLowerCase())) {
                carList.add(car);
            }
        }
        setCarGrid();
    }

    public void filterByMake(String make) {
        gridPane.getChildren().clear();
        carList.clear();
        List<Car> allCarList = getData();
        for (Car car : allCarList) {
            if (car.getCarMake().toLowerCase().contains(make.toLowerCase())) {
                carList.add(car);
            }
        }
        setCarGrid();
    }

    public void filterByModel (String model) {
        gridPane.getChildren().clear();
        carList.clear();
        List<Car> allCarList = getData();
        for (Car car : allCarList) {
            if (car.getCarModel().toLowerCase().contains(model.toLowerCase())) {
                carList.add(car);
            }
        }
        setCarGrid();
    }

    public void setCarGrid() {
        int column = 0;
        int row = 0;
        try {
            for (int i=0; i<carList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                if (column == 2) {
                    column = 0;
                    row++;
                }
                ItemController itemController = fxmlLoader.getController();
                itemController.setData(carList.get(i));

                gridPane.add(anchorPane,column++,row);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
