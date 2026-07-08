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
        try (Connection connection = Conexion.getConexion();
            PreparedStatement ps = connection.prepareStatement(sql);
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

}
