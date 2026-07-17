package dao;

import db.Conexion;
import model.Rutina;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RutinaDAO {

    public void guardar(Rutina rutina) throws SQLException {
        String sql = """
                INSERT INTO rutinas(nombre, descripcion, nivel)
                VALUES (?, ?, ?)
                """;
        try (Connection connection = Conexion.getConexion();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, rutina.getNombre());
            ps.setString(2, rutina.getDescripcion());
            ps.setString(3, rutina.getNivel());
            ps.executeUpdate();
        }
    }

    public List<Rutina> listar() throws SQLException {
        List<Rutina> lista = new ArrayList<>();
        String sql = """
                SELECT id, nombre, descripcion, nivel
                FROM rutinas
                ORDER BY id
                """;
        try (Connection connection = Conexion.getConexion();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Rutina rutina = new Rutina(rs.getInt("id"), rs.getString("nombre"), rs.getString("descripcion"), rs.getString("nivel"));
                lista.add(rutina);
            }
        }
        return lista;
    }

    public void actualizar(Rutina rutina) throws SQLException {
        String sql = """
                UPDATE rutinas
                SET nombre = ?,
                descripcion = ?,
                nivel = ?
                WHERE id = ?
                """;

        try (Connection connection = Conexion.getConexion();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, rutina.getNombre());
            ps.setString(2, rutina.getDescripcion());
            ps.setString(3, rutina.getNivel());
            ps.setInt(4, rutina.getId());
            int filas = ps.executeUpdate();
            if (filas == 0) {
                throw new SQLException("No se encontro la rutina para actualizar");
            }
        }
    }

    public void eliminar(int id) throws SQLException {

        String sql = """
                DELETE FROM rutinas
                WHERE id = ?
                """;
        try (Connection connection = Conexion.getConexion();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            if (filas == 0) {
                throw new SQLException("No se encontro la rutina para eliminar");
            }
        }
    }
}