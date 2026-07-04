package dao;

import db.Conexion;
import model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public Usuario validarLogin(String usuario, String contrasena) {
        String sql = """
                SELECT id, usuario, contrasena, rol FROM usuarios
                WHERE usuario = ? AND contrasena = ?
                """;
        try (
                Connection connection = Conexion.getConexion();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
                ) {
            preparedStatement.setString(1, usuario);
            preparedStatement.setString(2, contrasena);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Usuario(resultSet.getInt("id"), resultSet.getString("usuario"), resultSet.getString("contrasena"), resultSet.getString("rol"));
            }

        } catch (SQLException e) {
            System.out.println("Error al validar login: " + e.getMessage());
        }
        return null;

    }
}
