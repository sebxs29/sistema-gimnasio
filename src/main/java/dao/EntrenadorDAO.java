package dao;
import db.Conexion;
import model.Entrenador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EntrenadorDAO {

    public void guardar(Entrenador entrenador) throws SQLException {
        String sql ="INSERT INTO ";
        try(Connection con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);) {

        }
    }

    public List<Entrenador> listar() throws SQLException {

        List<Entrenador> lista = new ArrayList<>();

        return lista;
    }

    public void actualizar(Entrenador entrenador) throws SQLException {

    }

    public void eliminar(int id) throws SQLException {

    }



    public Entrenador buscarPorId(int id) throws SQLException {

        return null;
    }
}