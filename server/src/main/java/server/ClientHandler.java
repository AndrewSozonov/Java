package server;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class ClientHandler {
    private Server server;
    private Socket socket;
    DataInputStream in;
    DataOutputStream out;
    private String nick;
    private String login;



    public String getLogin() {
        return login;
    }

    public String getNick() {
        return nick;
    }

    public ClientHandler(Server server, Socket socket) {

        try {
            this.server = server;
            this.socket = socket;
            //

//            System.out.println("LocalPort: "+socket.getLocalPort());
//            System.out.println("Port: "+socket.getPort());
//            System.out.println("InetAddress: "+socket.getInetAddress());
            System.out.println("RemoteSocketAddress: " + socket.getRemoteSocketAddress());

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(() -> {
                try {
                    //цикл авторизации
                    //установка лимита на молчание по сокету.
                    socket.setSoTimeout(120000);
                    File newUserHistory = new File ("newUserHistory.txt");
                    if (! newUserHistory.exists()) {
                        newUserHistory.createNewFile();
                    }
                    while (true) {
                        String str = in.readUTF();
                        if (str.equals("/end")) {
                            sendMsg("/end");
                            throw new RuntimeException("отключаемся");
                        }
                        if (str.startsWith("/reg ")) {
                            String[] token = str.split(" ");
                            boolean b = server.getAuthService()
                                    .registration(token[1], token[2], token[3]);
                            if (!b) {
                                sendMsg("Ошибка: с этим логином уже Зарегистированы.");
                            } else {
                                sendMsg("Регистрация прошла успешно.");
                            }
                        }

                        if (str.startsWith("/auth ")) {
                            String[] token = str.split(" ");
                            String newNick = server.getAuthService()
                                    .getNicknameByLoginAndPassword(token[1], token[2]);
                            if (newNick != null) {
                                if (!server.isLoginAuthorized(token[1])) {
                                    login = token[1];
                                    sendMsg("/authok " + newNick);
                                    nick = newNick;
                                    server.subscribe(this);
                                    System.out.println("Клиент " + nick + " подключился");
                                    socket.setSoTimeout(0);

                                    //Отправка сообщения с историей новому пользователю
                                    if (newUserHistory.length() != 0) {
                                        sendMsg(readUsingBufferedReader("newUserHistory.txt"));}

                                    break;
                                } else {
                                    sendMsg("С этим логином уже авторизовались");
                                }
                            } else {
                                sendMsg("Неверный логин / пароль");
                            }
                        }

                    }
                    // цикл работы
                    while (true) {
                        String str = in.readUTF();
                        if (str.startsWith("/")) {
                            if (str.equals("/end")) {
                                sendMsg("/end");
                                break;
                            }
                            if (str.startsWith("/w ")) {
                                String[] token = str.split(" ", 3);
                                server.privateMsg(this, token[1], token[2]);
                            }

                            if (str.startsWith("/chnick ")) {
                                String[] token = str.split(" ", 2);
                                if (token[1].contains(" ")) {
                                    sendMsg("Ник не может содержать пробелов");
                                    continue;
                                }
                                if (server.getAuthService().changeNick(this.nick, token[1])) {
                                    sendMsg("/yournickis " + token[1]);
                                    sendMsg("Ваш ник изменен на " + token[1]);
                                    this.nick = token[1];
                                    server.broadcastClientlist();
                                } else {
                                    sendMsg("Не удалось изменить ник. Ник " + token[1] + " уже существует");
                                }
                            }
                        } else {
                            //Подсчет сообщений в файле истории для новых пользователей
                            BufferedReader reader = new BufferedReader(new FileReader(newUserHistory));
                            int messages = 0;
                            while (reader.readLine() != null) {
                                messages++;
                            }
                            reader.close();

                            //Если сообщений в файле больше 100, то первая строка удаляется и записывается новое сообщение
                            if (messages >= 100) {
                                Scanner fileScanner = new Scanner(newUserHistory);
                                fileScanner.nextLine();

                                FileWriter fileStream = new FileWriter(newUserHistory);
                                BufferedWriter out = new BufferedWriter(fileStream);
                                while(fileScanner.hasNextLine()) {
                                    String next = fileScanner.nextLine();
                                    if(next.equals("\n"))
                                        out.newLine();
                                    else
                                        out.write(next);
                                    out.newLine();
                                }
                                out.close();
                            }

                            try (FileWriter historyOut = new FileWriter(newUserHistory, true)) {
                                historyOut.write(nick + " : " + str + System.lineSeparator());
                            } catch (IOException err) {
                                System.out.println("Ошибка записи истории");
                            }
                            server.broadcastMsg(nick, str);
                        }
                    }
                } catch (RuntimeException e) {
                    System.out.println("bue");
                } catch (SocketTimeoutException e) {
                    sendMsg("/end");
                    System.out.println("bue time out");
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    server.unsubscribe(this);
                    System.out.println("Клиент отключился");
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static String readUsingBufferedReader(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader( new FileReader (fileName));
        String line = null;
        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");
        while( ( line = reader.readLine() ) != null ) {
            stringBuilder.append( line );
            stringBuilder.append( ls );
        }

        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        return stringBuilder.toString();
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
