package server;

import java.sql.*;

public class AdvancedAuthService implements AuthService {

    private static Connection connection;
    private static Statement stmt;
    private static PreparedStatement psInsert;

    public static void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:main.db");
        stmt = connection.createStatement();
    }

    @Override
    public String getNicknameByLoginAndPassword(String log, String pass) throws SQLException {
        String nickname = null;
        try {
            connect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE login = '" + log + "' AND password = '" + pass + "'");
        while (rs.next()) {
            nickname = rs.getString("nickname");
        }
        rs.close();
        return nickname;
    }

    public static void prepareRegistrationStatement() throws SQLException {
        psInsert = connection.prepareStatement("INSERT INTO users (nickname, login, password) VALUES (?,?,?)");
    }

    @Override
    public boolean registration(String login, String password, String nickname) throws SQLException {
        try {
            connect();
            prepareRegistrationStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        psInsert.setString(1, nickname);
        psInsert.setString(2, login);
        psInsert.setString(3, password);
        psInsert.executeUpdate();
        connection.setAutoCommit(true);
        return true;
    }

    public void changeNick(String nick, String newNick) throws SQLException {

        try {
            connect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        stmt.executeUpdate("UPDATE users SET nickname = '" + newNick + "' WHERE nickname = '" + nick + "'");
    }
}


