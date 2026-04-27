package servlet;

import java.sql.Connection;
import java.sql.DriverManager;

import io.github.cdimascio.dotenv.Dotenv;

public class DBManager {
    // 環境変数読み込み
    private static final Dotenv dotenv = Dotenv.configure() // .envファイルを読み込むためのモジュール（このファイルを読み込むことで認証情報をgitしない！）
        .filename(".env")
        .load();

    public static Connection getConnection() throws Exception {

        String url = dotenv.get("DB_URL");
        String user = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASS");

        Class.forName("com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection(url, user, password);
    }
}