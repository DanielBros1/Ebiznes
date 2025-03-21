import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");

        // SQLite JDBC Test
        String url = "jdbc:sqlite:sample.db";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY, name TEXT)");
            System.out.println("Database connected successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
