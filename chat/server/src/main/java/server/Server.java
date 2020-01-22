package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.*;

public class Server {
    private Vector<ClientHandler> clients;
    private AuthService authService;
    private ExecutorService executorService;
    public static final Logger logger = Logger.getLogger(Server.class.getName());


    public AuthService getAuthService() {
        return authService;
    }

    public Server() {

        logger.setUseParentHandlers(false);
        logger.setLevel(Level.CONFIG);

        Handler handler = null;
        try {
            handler = new FileHandler("Server.log", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        handler.setFormatter(new SimpleFormatter());
        logger.addHandler(handler);


        clients = new Vector<>();
//        authService = new SimpleAuthService();
        if (!SQLHandler.connect()) {
            throw new RuntimeException("Не удалось подключиться к БД");
        }
        authService = new DBAuthServise();

        ServerSocket server = null;
        Socket socket = null;
        executorService = Executors.newCachedThreadPool();

        try {
            server = new ServerSocket(8189);
            logger.config("Сервер запущен");

            while (true) {
                socket = server.accept();
                logger.config("Клиент подключился");
                new ClientHandler(this, socket, executorService);
            }

        } catch (IOException e) {
            logger.throwing(Server.class.getName(), "Server", e);
        } finally {
            SQLHandler.disconnect();
            try {
                server.close();
            } catch (IOException e) {
                logger.throwing(Server.class.getName(), "Server", e);
                logger.severe("Ошибка закрытия сервера " + e);
            }
            executorService.shutdown();
        }
    }

    public void broadcastMsg(String nick, String msg) {
        for (ClientHandler c : clients) {
            c.sendMsg(nick + " : " + msg);
        }
    }

    public void privateMsg(ClientHandler sender, String receiver, String msg) {
        String message = String.format("[ %s ] private [ %s ] : %s",
                sender.getNick(), receiver, msg);

        for (ClientHandler c : clients) {
            if (c.getNick().equals(receiver)) {
                c.sendMsg(message);
                sender.sendMsg(message);
                return;
            }
        }
        sender.sendMsg("Пользователь с ником: " + receiver + " не найден");
    }

    public void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
        broadcastClientlist();
    }

    public void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        broadcastClientlist();
    }

    public boolean isLoginAuthorized(String login) {
        for (ClientHandler c : clients) {
            if (c.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }

    public void broadcastClientlist() {
        StringBuilder sb = new StringBuilder("/clientlist ");
        for (ClientHandler c : clients) {
            sb.append(c.getNick() + " ");
        }

        String msg = sb.toString();
        for (ClientHandler c : clients) {
            c.sendMsg(msg);
        }
    }
}
