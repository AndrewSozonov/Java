package task2_6;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.io.PrintWriter;

public class Server {

    public static void main(String[] args) {

        ServerSocket server = null;
        Socket clientSocket = null;

        try {
            server = new ServerSocket(8188);
            System.out.println("Сервер запущен");
            clientSocket = server.accept();
            System.out.println("Клиент подключился");
            System.out.println("Введите /end чтобы отключиться");

            Scanner sc = new Scanner(System.in);
            Scanner clientScanner = new Scanner(clientSocket.getInputStream());
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);

            new Thread(() -> {

                while(true) {
                    String serverText = sc.nextLine();
                    out.println(serverText);
                }
            }).start();

            while(true) {
                String clientText = clientScanner.nextLine();
                if (clientText.equals("/end")) {
                    System.out.println("Клиент отключился");
                    break;
                }
                System.out.println(clientText);
            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                clientSocket.close();
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
