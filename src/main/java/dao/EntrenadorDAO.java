package dao;
import db.Conexion;
import model.Entrenador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EntrenadorDAO {

    public void guardar(Entrenador entrenador, String usuario, String contrasena) throws SQLException {

        String sqlUsuario = """
                INSERT INTO usuarios (usuario, contrasena, rol)
                VALUES (?, ?, 'ENTRENADOR')
                RETURNING id
                """;
        String sqlEntrenador = """
                INSERT INTO entrenadores (usuario_id, cedula, nombre, apellido, especialidad, telefono)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
        try (Connection conn = Conexion.getConexion()) {
            try {
                conn.setAutoCommit(false);
                int usuarioId;
                try (PreparedStatement psUsuario = conn.prepareStatement(sqlUsuario)) {
                    psUsuario.setString(1, usuario);
                    psUsuario.setString(2, contrasena);
                    try (ResultSet rs = psUsuario.executeQuery()) {
                        if (rs.next()) {
                            usuarioId = rs.getInt("id");
                        } else {
                            throw new SQLException("No se pudo crear el usuario.");
                        }
                    }
                }
                try (PreparedStatement psEntrenador = conn.prepareStatement(sqlEntrenador)) {
                    psEntrenador.setInt(1, usuarioId);
                    psEntrenador.setString(2, entrenador.getCedula());
                    psEntrenador.setString(3, entrenador.getNombre());
                    psEntrenador.setString(4, entrenador.getApellido());
                    psEntrenador.setString(5, entrenador.getEspecialidad());
                    psEntrenador.setString(6, entrenador.getTelefono());
                    
                    psEntrenador.executeUpdate();
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }



    public List<Entrenador> listar() throws SQLException {
        List<Entrenador> lista = new ArrayList<>();
        String sql = "SELECT * FROM entrenadores";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Entrenador e = new Entrenador();
                e.setId(rs.getInt("id"));
                e.setUsuarioId(rs.getInt("usuario_id"));
                e.setCedula(rs.getString("cedula"));
                e.setNombre(rs.getString("nombre"));
                e.setApellido(rs.getString("apellido"));
                e.setEspecialidad(rs.getString("especialidad"));
                e.setTelefono(rs.getString("telefono"));
                lista.add(e);
            }
        }
        return lista;
    }

    public void actualizar(Entrenador e) throws SQLException {
        String sql = "UPDATE entrenadores SET  cedula=?, nombre=?, apellido=?, especialidad=?, telefono=? WHERE id=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, e.getCedula());
            ps.setString(2, e.getNombre());
            ps.setString(3, e.getApellido());
            ps.setString(4, e.getEspecialidad());
            ps.setString(5, e.getTelefono());
            ps.setInt(6, e.getId());

            int filas = ps.executeUpdate();
            if (filas == 0) {
                throw new SQLException("No se encontró el entrenador para actualizar.");
            }
        }
    }

    public void eliminar(int entrenadorId, int usuarioId) throws SQLException {
        String sqlEntrenador = """
                DELETE FROM entrenadores
                WHERE id = ?
                """;
        String sqlUsuario = """
                DELETE FROM usuarios
                WHERE id = ?
                """;
        try (Connection conn = Conexion.getConexion()) {
            try {
                conn.setAutoCommit(false);
                try (PreparedStatement ps = conn.prepareStatement(sqlEntrenador)) {
                    ps.setInt(1, entrenadorId);
                    int filas = ps.executeUpdate();
                    if (filas == 0) {
                        throw new SQLException("No se encontró el entrenador.");
                    }
                }
                try (PreparedStatement ps = conn.prepareStatement(sqlUsuario)) {
                    ps.setInt(1, usuarioId);
                    ps.executeUpdate();
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }
}