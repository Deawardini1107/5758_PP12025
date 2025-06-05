package PrakPro1;

import java.sql.*;

public class Database {
    private static final String URL = "jdbc:sqlite:data.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void init() {
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            String sqlGudang = "CREATE TABLE IF NOT EXISTS gudang (" +
                               "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                               "nama TEXT UNIQUE NOT NULL);";
            String sqlPenerimaan = "CREATE TABLE IF NOT EXISTS penerimaan (" +
                                   "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                   "dari TEXT NOT NULL," +
                                   "ke TEXT NOT NULL," +
                                   "FOREIGN KEY(dari) REFERENCES gudang(nama)," +
                                   "FOREIGN KEY(ke) REFERENCES gudang(nama));";
            stmt.execute(sqlGudang);
            stmt.execute(sqlPenerimaan);
        } catch (SQLException e) {
            System.out.println("Error init DB: " + e.getMessage());
        }
    }
}
