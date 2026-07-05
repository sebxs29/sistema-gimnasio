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

    public void guardar(Entrenador e) throws SQLException {
        String sql = "INSERT INTO entrenadores (usuario_id, cedula, nombre, apellido, especialidad, telefono) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, e.getUsuarioId());
            ps.setString(2, e.getCedula());
            ps.setString(3, e.getNombre());
            ps.setString(4, e.getApellido());
            ps.setString(5, e.getEspecialidad());
            ps.setString(6, e.getTelefono());
            ps.executeUpdate();
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
        String sql = "UPDATE entrenadores SET usuario_id=?, cedula=?, nombre=?, apellido=?, especialidad=?, telefono=? WHERE id=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, e.getUsuarioId());
            ps.setString(2, e.getCedula());
            ps.setString(3, e.getNombre());
            ps.setString(4, e.getApellido());
            ps.setString(5, e.getEspecialidad());
            ps.setString(6, e.getTelefono());
            ps.setInt(7, e.getId());
            ps.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM entrenadores WHERE id=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }


    public Entrenador buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM entrenadores WHERE id=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Entrenador e = null;
            while (rs.next()) {
                e = new Entrenador();
                e.setId(rs.getInt("id"));
                e.setUsuarioId(rs.getInt("usuario_id"));
                e.setCedula(rs.getString("cedula"));
                e.setNombre(rs.getString("nombre"));
                e.setApellido(rs.getString("apellido"));
                e.setEspecialidad(rs.getString("especialidad"));
                e.setTelefono(rs.getString("telefono"));
            }
            return e;
        }
    }
}