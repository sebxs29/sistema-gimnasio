package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String DATABASE_URL =
            System.getenv("DATABASE_URL");

    public static Connection getConexion() throws SQLException {

        if (DATABASE_URL == null || DATABASE_URL.isBlank()) {
            throw new SQLException(
                    "No se configuró la variable de entorno DATABASE_URL"
            );
        }

        return DriverManager.getConnection(DATABASE_URL);
    }
}