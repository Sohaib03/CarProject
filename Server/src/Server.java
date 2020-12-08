import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:Database/sample.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            statement.executeUpdate("DROP TABLE IF EXISTS users");
            statement.executeUpdate("CREATE TABLE users (id INTEGER, username STRING, password STRING)");
            statement.executeUpdate("INSERT INTO users VALUES(1, 'Sohaib', 'abcxyz')");
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
                                    Connection newConnection = null;
                                    try {
                                        newConnection = DriverManager.getConnection("jdbc:sqlite:Database/sample.db");
                                        Statement statement = newConnection.createStatement();
                                        statement.setQueryTimeout(30);
                                        ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE username='"+username+"'");
                                        while (resultSet.next() && !userFound) {
                                            if (resultSet.getString("password").equals(password)) {
                                                userFound = true;
                                            }
                                        }

                                    } catch (SQLException throwables) {
                                        throwables.printStackTrace();
                                    }
                                    if (userFound) {
                                        pw.println("SUCCESS");
                                        pw.flush();
                                        System.out.println("User Sohaib logged in!");
                                    } else {
                                        pw.println("FAILED");
                                        pw.flush();
                                        System.out.println("User not found");
                                    }
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
