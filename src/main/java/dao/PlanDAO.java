package dao;

import db.Conexion;
import model.Plan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlanDAO implements ICRUD<Plan>{

    @Override
    public void guardar(Plan plan) throws SQLException {

        String sql = """
                INSERT INTO planes(nombre, precio, duracion_meses)
                VALUES (?, ?, ?)
                """;

        try(Connection connection = Conexion.getConexion();
            PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, plan.getNombre());
            ps.setDouble(2, plan.getPrecio());
            ps.setInt(3, plan.getDuracionMeses());

            ps.executeUpdate();

        }

    }

    @Override
    public List<Plan> listar() throws SQLException {

        List<Plan> lista = new ArrayList<>();
        String sql = """
                SELECT id, nombre, precio, duracion_meses
                FROM planes
                ORDER BY id
                """;

        try (Connection connection = Conexion.getConexion();
        PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Plan plan = new Plan(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getInt("duracion_meses")
                );
                lista.add(plan);
            }

        }
        return lista;

    }

    @Override
    public void actualizar(Plan plan) throws SQLException {

        String sql = """
                UPDATE planes
                SET nombre = ?,
                precio = ?,
                duracion_meses = ?
                WHERE id = ?
                """;

        try(Connection connection = Conexion.getConexion();
        PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, plan.getNombre());
            ps.setDouble(2, plan.getPrecio());
            ps.setInt(3, plan.getDuracionMeses());
            ps.setInt(4, plan.getId());

            int filas  = ps.executeUpdate();

            if (filas == 0) {
                throw new SQLException("No se encontro el plan para actualizar");
            }

        }
    }

    @Override
    public void eliminar(int id) throws SQLException {

        String sql = """
                DELETE FROM planes
                WHERE id = ?
                """;

        try (Connection connection = Conexion.getConexion();
        PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            int filas = ps.executeUpdate();

            if (filas == 0) {
                throw new SQLException("No se encontro el plan para eliminar");
            }
        }
    }
}
