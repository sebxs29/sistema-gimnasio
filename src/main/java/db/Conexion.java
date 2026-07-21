package db;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexion {

    private static final String ARCHIVO_CONFIGURACION =
            "config.properties";

    public static Connection getConexion() throws SQLException {

        String databaseUrl = obtenerDatabaseUrl();

        return DriverManager.getConnection(databaseUrl);
    }

    private static String obtenerDatabaseUrl() throws SQLException {

        // Permite usar variable de entorno durante el desarrollo
        String variableEntorno = System.getenv("DATABASE_URL");

        if (variableEntorno != null
                && !variableEntorno.isBlank()) {

            return variableEntorno;
        }

        // Para el JAR o EXE, busca config.properties
        Path rutaConfiguracion =
                Path.of(ARCHIVO_CONFIGURACION);

        if (!Files.exists(rutaConfiguracion)) {
            throw new SQLException(
                    "No se encontró el archivo "
                            + ARCHIVO_CONFIGURACION
            );
        }

        Properties propiedades = new Properties();

        try (InputStream entrada =
                     Files.newInputStream(rutaConfiguracion)) {

            propiedades.load(entrada);

        } catch (IOException e) {

            throw new SQLException(
                    "No se pudo leer el archivo "
                            + ARCHIVO_CONFIGURACION,
                    e
            );
        }

        String databaseUrl =
                propiedades.getProperty("database.url");

        if (databaseUrl == null || databaseUrl.isBlank()) {

            throw new SQLException(
                    "No se configuró database.url en "
                            + ARCHIVO_CONFIGURACION
            );
        }

        return databaseUrl.trim();
    }
}