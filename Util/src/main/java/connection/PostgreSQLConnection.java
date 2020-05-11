package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class PostgreSQLConnection {

    private static Connection connection;

    public static Connection getConnetion() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/logatti-sd-avaliacao-p1", "postgres", "12345");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
