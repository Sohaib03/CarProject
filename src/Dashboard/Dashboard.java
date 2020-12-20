package Dashboard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Dashboard  {

    public Socket socket;
    public PrintWriter pw;
    public BufferedReader br;
    private static Dashboard instance;
    public List<Car> carData;
    private Stage primaryStage;

    public Dashboard(){
        primaryStage = new Stage();
        primaryStage.setTitle("Autom");
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        instance = this;
        pw = Main.getInstance().pw;
        br = Main.getInstance().br;
        socket = Main.getInstance().socket;
        showDashBoardScene();
    }
    public static Dashboard getInstance() {
        return instance;
    }

    public void showDashBoardScene() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene mainScene = new Scene(root);
        mainScene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(mainScene);
        primaryStage.getScene().getStylesheets().setAll(Main.class.getResource("main.css").toString());
        primaryStage.show();

        loadCarData();
        DashboardController.instance.showAllCars();
    }

    public void showAddCarScene() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("addCar.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene mainScene = new Scene(root);
        mainScene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(mainScene);
        primaryStage.getScene().getStylesheets().setAll(Main.class.getResource("main.css").toString());
        primaryStage.show();
    }

    public void loadCarData() {
        carData = new ArrayList<>();
        pw.println("GET");
        pw.flush();
        try {
            String numberOfCars = br.readLine();
            int N = Integer.parseInt(numberOfCars);
            System.out.println(N);
            for (int i=0; i<N; i++) {
                String carDataString = br.readLine();
                String[] data = carDataString.split(";");
                Car car = new Car(data[0], Integer.parseInt(data[1]) , Car.getColor(data[2]), data[3], data[4], Integer.parseInt(data[5]));
                carData.add(car);
                System.out.println(car);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
