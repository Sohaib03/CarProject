package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main extends Application {

    public Socket socket;
    public PrintWriter pw;
    public BufferedReader br;
    private static Main instance;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        Scene mainScene = new Scene(root);
        mainScene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(mainScene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.getScene().getStylesheets().setAll(Main.class.getResource("main.css").toString());
        primaryStage.show();

        createServer();
        instance = this;
    }

    public static Main getInstance() {
        return instance;
    }


    public void createServer() {
        try {
            socket = new Socket("0.0.0.0", 4999);
            pw = new PrintWriter(socket.getOutputStream());
            pw.println("Connected to Client!");
            pw.flush();

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String msg = br.readLine();
            System.out.println(msg);
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Couldnt connect with server!");
        }

    }

    public boolean login(String username, String password) {
        if (socket == null)
            return false;
        pw.println("LOGIN");
        pw.println(username);
        pw.println(password);
        pw.flush();
        try {
            String returnCode = br.readLine();
            if (returnCode.equals("SUCCESS")) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
