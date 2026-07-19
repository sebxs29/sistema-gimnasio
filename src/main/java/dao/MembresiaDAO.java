package dao;

import db.Conexion;
import model.Membresia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MembresiaDAO {

    public void guardar(Membresia membresia) throws SQLException {

        String sql = """
                INSERT INTO membresias
                (cliente_id, plan_id, fecha_inicio, fecha_fin, estado)
                VALUES (?, ?, ?, ?, ?)
                RETURNING id
                """;

        try (Connection connection = Conexion.getConexion();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, membresia.getClienteId());
            ps.setInt(2, membresia.getPlanId());
            ps.setDate(3, Date.valueOf(membresia.getFechaInicio()));
            ps.setDate(4, Date.valueOf(membresia.getFechaFin()));
            ps.setString(5, membresia.getEstado());

            try (ResultSet rs  = ps.executeQuery()) {

                if (rs.next()) {
                    membresia.setId(rs.getInt("id"));
                } else {
                    throw new SQLException("No se pudo generar la membresia");
                }

            }

        }

    }

    public List<Membresia> listar() throws SQLException {

        List<Membresia> membresias = new ArrayList<>();

        String sql = """
                SELECT m.id, m.cliente_id, m.plan_id,
                CONCAT(c.nombre, ' ', c.apellido) AS cliente_nombre,
                p.nombre AS plan_nombre,
                m.fecha_inicio,
                m.fecha_fin,
                m.estado
                FROM membresias m
                JOIN clientes c
                ON m.cliente_id = c.id
                JOIN planes p
                ON m.plan_id = p.id
                ORDER BY m.id
                """;

        try (Connection connection = Conexion.getConexion();
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Membresia membresia = new Membresia(
                        rs.getInt("id"),
                        rs.getInt("cliente_id"),
                        rs.getInt("plan_id"),
                        rs.getString("cliente_nombre"),
                        rs.getString("plan_nombre"),
                        rs.getDate("fecha_inicio").toLocalDate(),
                        rs.getDate("fecha_fin").toLocalDate(),
                        rs.getString("estado")
                );

                membresias.add(membresia);

            }

        }
        return membresias;
    }

    public void actualizar(Membresia membresia) throws SQLException {

        String sql = """
                UPDATE membresias
                SET cliente_id = ?,
                plan_id = ?,
                fecha_inicio = ?,
                fecha_fin = ?,
                estado = ?
                WHERE id = ?
                """;

        try (Connection connection = Conexion.getConexion();
        PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, membresia.getClienteId());
            ps.setInt(2, membresia.getPlanId());
            ps.setDate(3, Date.valueOf(membresia.getFechaInicio()));
            ps.setDate(4, Date.valueOf(membresia.getFechaFin()));
            ps.setString(5, membresia.getEstado());

            ps.setInt(6, membresia.getId());

            int filas = ps.executeUpdate();

            if (filas == 0) {
                throw new SQLException("No se encontro la membresia para actualizar");
            }
        }

    }

    public void eliminar(int id) throws SQLException {

        String sql = """
                DELETE FROM membresias
                WHERE id = ?
                """;

        try (Connection connection = Conexion.getConexion();
        PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            int filas = ps.executeUpdate();

            if (filas == 0) {
                throw new SQLException("No se encontro la membresia para eliminar");
            }

        }

    }

    public Membresia buscarPorUsuario(int usuarioId) throws SQLException {

        String sql = """
                SELECT m.id, m.cliente_id, m.plan_id,
                CONCAT(c.nombre, ' ', c.apellido) AS cliente_nombre,
                p.nombre AS plan_nombre,
                m.fecha_inicio,
                m.fecha_fin,
                m.estado
                FROM membresias m
                JOIN clientes c
                ON m.cliente_id = c.id
                JOIN planes p
                ON m.plan_id = p.id
                WHERE c.usuario_id = ?
                ORDER BY m.fecha_inicio DESC, m.id DESC
                LIMIT 1
                """;

        try (Connection connection = Conexion.getConexion();
        PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, usuarioId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return new Membresia(
                            rs.getInt("id"),
                            rs.getInt("cliente_id"),
                            rs.getInt("plan_id"),
                            rs.getString("cliente_nombre"),
                            rs.getString("plan_nombre"),
                            rs.getDate("fecha_inicio").toLocalDate(),
                            rs.getDate("fecha_fin").toLocalDate(),
                            rs.getString("estado")
                    );

                }

            }

        }

        return null;
    }

}
