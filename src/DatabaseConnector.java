
package utms.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnector {
    private static final String DB_URL = "jdbc:sqlite:utms.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void initialize() {
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS bookings ("
                       + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                       + "username TEXT NOT NULL,"
                       + "vehicle TEXT NOT NULL,"
                       + "time TEXT NOT NULL,"
                       + "seat INTEGER NOT NULL)";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}