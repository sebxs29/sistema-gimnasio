package dao;

import db.Conexion;
import model.AsignacionRutina;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AsignacionRutinaDAO {

    public void guardar(AsignacionRutina asignacion) throws SQLException {

        String sql = """
                INSERT INTO asignacion_rutinas(cliente_id, rutina_id, entrenador_id, fecha_asignacion)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = Conexion.getConexion();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, asignacion.getClienteId());
            ps.setInt(2, asignacion.getRutinaId());
            ps.setInt(3, asignacion.getEntrenadorId());
            ps.setDate(4, Date.valueOf(asignacion.getFechaAsignacion()));

            ps.executeUpdate();
        }
    }

    // LISTA CON JOIN PARA MOSTRAR NOMBRES DE CLIENTE, RUTINA Y ENTRENADOR EN LA TABLA
    public List<AsignacionRutina> listar() throws SQLException {

        List<AsignacionRutina> lista = new ArrayList<>();

        String sql = """
                SELECT ar.id,
                       ar.cliente_id, c.nombre || ' ' || c.apellido AS cliente_nombre,
                       ar.rutina_id, r.nombre AS rutina_nombre,
                       ar.entrenador_id, e.nombre || ' ' || e.apellido AS entrenador_nombre,
                       ar.fecha_asignacion
                FROM asignacion_rutinas ar
                JOIN clientes c ON ar.cliente_id = c.id
                JOIN rutinas r ON ar.rutina_id = r.id
                JOIN entrenadores e ON ar.entrenador_id = e.id
                ORDER BY ar.id
                """;

        try (Connection connection = Conexion.getConexion();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                AsignacionRutina asignacion = new AsignacionRutina(
                        rs.getInt("id"),
                        rs.getInt("cliente_id"),
                        rs.getString("cliente_nombre"),
                        rs.getInt("rutina_id"),
                        rs.getString("rutina_nombre"),
                        rs.getInt("entrenador_id"),
                        rs.getString("entrenador_nombre"),
                        rs.getDate("fecha_asignacion").toLocalDate()
                );
                lista.add(asignacion);
            }
        }
        return lista;
    }

    public void actualizar(AsignacionRutina asignacion) throws SQLException {

        String sql = """
                UPDATE asignacion_rutinas
                SET cliente_id = ?,
                rutina_id = ?,
                entrenador_id = ?,
                fecha_asignacion = ?
                WHERE id = ?
                """;

        try (Connection connection = Conexion.getConexion();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, asignacion.getClienteId());
            ps.setInt(2, asignacion.getRutinaId());
            ps.setInt(3, asignacion.getEntrenadorId());
            ps.setDate(4, Date.valueOf(asignacion.getFechaAsignacion()));
            ps.setInt(5, asignacion.getId());

            int filas = ps.executeUpdate();

            if (filas == 0) {
                throw new SQLException("No se encontro la asignacion para actualizar");
            }
        }
    }

    public void eliminar(int id) throws SQLException {

        String sql = """
                DELETE FROM asignacion_rutinas
                WHERE id = ?
                """;

        try (Connection connection = Conexion.getConexion();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            int filas = ps.executeUpdate();

            if (filas == 0) {
                throw new SQLException("No se encontro la asignacion para eliminar");
            }
        }
    }
}