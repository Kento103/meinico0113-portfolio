package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class DBConnection {
    // 環境変数読み込み（.envファイルを読み込むことで、パスワード情報をGitしない！）
    private static final Dotenv dotenv = Dotenv.configure() // .env ファイルを読み込むよ
        .filename(".env") // どこにあるファイル？
        .load(); // よみこみ！

    // データベースの住所（myloginapp_db を指定）
    private static final String URL = dotenv.get("DB_URL");
    private static final String USER = dotenv.get("DB_USER");
    private static final String PASS = dotenv.get("DB_PASS");

    public static Connection getConnection() throws SQLException {
        try {
            // MySQLに繋ぐためのドライバー（部品）を読み込む
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 接続を開始して、接続情報を返す
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            // ドライバーが見つからない場合のエラー
            throw new SQLException(e);
        }
    }
}