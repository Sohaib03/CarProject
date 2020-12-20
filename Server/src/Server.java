import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static void main(String[] args) {
        List<String> carList = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:Database/sample.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            statement.executeUpdate("DROP TABLE IF EXISTS users");
            statement.executeUpdate("CREATE TABLE users (id INTEGER, username STRING, password STRING, status INTEGER)");
            statement.executeUpdate("INSERT INTO users VALUES(1, 'Sohaib', 'abcxyz', 1)");
            statement.executeUpdate("INSERT INTO users VALUES(2, 'viewer', 'none', 2)");
//            statement.executeUpdate("DROP TABLE IF EXISTS cars");
//            statement.executeUpdate("CREATE TABLE cars (registration STRING, year INTEGER, color STRING, carmake STRING, carmodel STRING, price INTEGER)");
//            statement.executeUpdate("INSERT INTO cars VALUES('RX570', 2014, 'Black,White', 'Toyota', 'RAV4', 25000)");
//            statement.executeUpdate("INSERT INTO cars VALUES('MX600', 2011, 'Yellow,White,Red', 'FORD', 'F-150', 51000)");
//            statement.executeUpdate("INSERT INTO cars VALUES('GTX80', 2016, 'Black', 'Chevrolet', 'Silverado', 35900)");
//            statement.executeUpdate("INSERT INTO cars VALUES('MTR11', 2012, 'Black,White', 'Honda', 'Civic', 29000)");
//            statement.executeUpdate("INSERT INTO cars VALUES('HR221', 2015, 'Yellow', 'Honda', 'Accord', 21000)");
            ResultSet resultSet = statement.executeQuery("SELECT * FROM cars");
            while (resultSet.next()) {
                String carData = resultSet.getString("registration") + ";" + resultSet.getString("year") + ";" +
                        resultSet.getString("color") + ";" + resultSet.getString("carmake") +  ";" +
                        resultSet.getString("carmodel") + ";" + resultSet.getString("price");

                System.out.println(carData);
                carList.add(carData);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }


        ServerSocket sSocket = null;
        try {
            sSocket = new ServerSocket(4999);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                Socket socket = sSocket.accept();
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter pw = new PrintWriter(socket.getOutputStream());
                String out = br.readLine();
                System.out.println(out);
                pw.println("Connected to Server!");
                pw.flush();
                Thread reciever = new Thread(() -> {
                    while (true) {
                        try {
                            if (br.ready()) {
                                String command = br.readLine();
                                if (command.equals("LOGIN")) {
                                    String username = br.readLine();
                                    String password = br.readLine();
                                    boolean userFound = false;
                                    int status = -1;
                                    Connection newConnection = null;
                                    try {
                                        newConnection = DriverManager.getConnection("jdbc:sqlite:Database/sample.db");
                                        Statement statement = newConnection.createStatement();
                                        statement.setQueryTimeout(30);
                                        ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE username='"+username+"'");
                                        while (resultSet.next() && !userFound) {
                                            if (resultSet.getString("password").equals(password)) {
                                                userFound = true;
                                                status = resultSet.getInt("status");
                                            }
                                        }

                                    } catch (SQLException throwables) {
                                        throwables.printStackTrace();
                                    }
                                    if (userFound) {
                                        pw.println("SUCCESS");
                                        pw.println(status);
                                        pw.flush();
                                        System.out.println("User Sohaib logged in!");
                                    } else {
                                        pw.println("FAILED");
                                        pw.flush();
                                        System.out.println("User not found");
                                    }
                                }
                                if (command.equals("PING")) {
                                    pw.println("SUCCESS");
                                    pw.flush();
                                }
                                if (command.equals("GET")) {
                                    //System.out.println("Get Request..");
                                    pw.println(carList.size());
                                    for (String carData : carList) {
                                        pw.println(carData);
                                    }
                                    pw.flush();
                                }
                                if (command.equals("POST")) {
                                    String carData = br.readLine();
                                    System.out.println(carData);
                                    String[] carDataArray = carData.split(";");
                                    if (carDataArray.length == 6) {
                                        carList.add(carData);
                                        Connection newConnection = null;
                                        try {
                                            newConnection = DriverManager.getConnection("jdbc:sqlite:Database/sample.db");
                                            Statement statement = newConnection.createStatement();
                                            statement.setQueryTimeout(30);
                                            statement.executeUpdate("INSERT INTO cars VALUES('"+ carDataArray[0]+ "', " + carDataArray[1] + ", '" +
                                                    carDataArray[2] +"', '" + carDataArray[3]+ "', '" + carDataArray[4] +"'," + carDataArray[5]+")");
                                        } catch (SQLException throwables) {
                                            throwables.printStackTrace();
                                        }
                                    }
                                }
                                if (command.equals("EXIT")) {
                                    System.out.println("Client Disconnect!");
                                    break;
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                reciever.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
