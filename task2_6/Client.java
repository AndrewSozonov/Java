package task2_6;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[]args) {

        Socket clientSocket = null;

        try {
            clientSocket = new Socket("localhost", 8188);
            Scanner sc = new Scanner(System.in);
            Scanner serverScanner = new Scanner(clientSocket.getInputStream());
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);

            if (clientSocket.isConnected()) {
            System.out.println("Соединение установлено. Порт: " + clientSocket.getPort());
            System.out.println("Введите /end чтобы отключиться");
        }

        new Thread(() -> {

            while (true) {
                String clientText = sc.nextLine();
                out.println(clientText);
                }
        }).start();


        while (true) {

            String serverText = serverScanner.nextLine();
            if (serverText.equals("/end")) {
                System.out.println("Сервер отключился");
                break;
            }
            System.out.println(serverText);
        }
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                clientSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}

