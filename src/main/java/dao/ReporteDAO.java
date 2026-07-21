package dao;

import db.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReporteDAO {

    public int contarClientesActivos() throws SQLException {

        String sql = """
                SELECT COUNT(*) AS total
                FROM clientes
                WHERE estado = 'ACTIVO'
                """;

        try(Connection connection = Conexion.getConexion();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }

    public int contarEntrenadores() throws SQLException {

        String sql = """
                SELECT COUNT(*) AS total
                FROM entrenadores
                """;

        try (Connection connection = Conexion.getConexion();
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {


            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }

    public int contarPlanes() throws SQLException {

        String sql = """
                SELECT COUNT(*) AS total
                FROM planes
                """;

        try(Connection connection = Conexion.getConexion();
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        }
        return 0;
    }

    public int contarRutinasAsignadas() throws SQLException {

        String sql = """
                SELECT COUNT(*) AS total
                FROM asignacion_rutinas
                """;

        try (Connection connection = Conexion.getConexion();
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        }
        return 0;
    }

}
