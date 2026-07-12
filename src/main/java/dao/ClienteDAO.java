package dao;

import db.Conexion;
import model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public void guardar(Cliente cliente, String usuario, String contrasena) throws SQLException {

        // RETURNING DEVUELVE EL ID CREADO SIN NECESIDAD DE HACER OTRO SELECT
        String sqlUsuario = """
                INSERT INTO usuarios (usuario, contrasena, rol)
                VALUES (?, ?, 'CLIENTE')
                RETURNING id
                """;

        String sqlCliente = """
                INSERT INTO clientes (usuario_id, cedula, nombre, apellido, telefono, correo, estado)
                VALUES (?, ?, ?, ?, ?, ?, ?)
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
                            throw new SQLException("No se pudo crear el usuario del cliente.");
                        }
                    }
                }

                try (PreparedStatement psCliente = conn.prepareStatement(sqlCliente)) {

                    psCliente.setInt(1, usuarioId);
                    psCliente.setString(2, cliente.getCedula());
                    psCliente.setString(3, cliente.getNombre());
                    psCliente.setString(4, cliente.getApellido());
                    psCliente.setString(5, cliente.getTelefono());
                    psCliente.setString(6, cliente.getCorreo());
                    psCliente.setString(7, cliente.getEstado());

                    psCliente.executeUpdate();
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

    public List<Cliente> listar() throws SQLException {
        List<Cliente> lista = new ArrayList<>();

        String sql = """
                SELECT id, usuario_id, cedula, nombre, apellido, telefono, correo, estado
                FROM clientes
                ORDER BY id
                """;
        try (Connection connection = Conexion.
             getConexion();
            PreparedStatement ps = connection.
             prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("telefono"),
                        rs.getString("correo"),
                        rs.getString("estado")
                );
                lista.add(cliente);
            }
        }
        return lista;
    }

    public void actualizar(Cliente cliente) throws SQLException {
        String sql = """
                UPDATE clientes
                SET cedula = ?,
                nombre = ?,
                apellido = ?,
                telefono = ?,
                correo = ?,
                estado = ?
                where id = ?
                """;

        try (Connection
             connection = Conexion.getConexion();
            PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, cliente.getCedula());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getApellido());
            ps.setString(4, cliente.getTelefono());
            ps.setString(5, cliente.getCorreo());
            ps.setString(6, cliente.getEstado());
            ps.setInt(7, cliente.getId());

           int filas = ps.executeUpdate();

           if (filas == 0) {
               throw new SQLException("No se encontro el cliente para actualizar");
           }
        }
    }

    public void eliminar(int clienteId, int usuarioId) throws SQLException {

        String sqlCliente = """
                DELETE from clientes
                WHERE id = ?
                """;

        String sqlUsuario = """
                 DELETE FROM usuarios
                WHERE id = ?
                """;

        try(Connection connection = Conexion.getConexion()) {

            try {
                connection.setAutoCommit(false);

            try (PreparedStatement psCliente = connection.prepareStatement(sqlCliente)) {

                psCliente.setInt(1, clienteId);

                int filas = psCliente.executeUpdate();

                if (filas == 0) {
                    throw new SQLException("No se encontro el cliente para eliminar");
                }
            }

            try (PreparedStatement psUsuario = connection.prepareStatement(sqlUsuario)) {

                psUsuario.setInt(1, usuarioId);

                psUsuario.executeUpdate();

            }
            connection.commit();

        } catch (SQLException e) {
            connection.rollback();
            throw e;

        } finally {
                connection.setAutoCommit(true);
            }
        }
    }
}
