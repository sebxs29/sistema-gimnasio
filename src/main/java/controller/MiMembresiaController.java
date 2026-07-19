package controller;

import dao.MembresiaDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.Membresia;
import model.Usuario;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class MiMembresiaController {

    @FXML
    private TextField txtFechaFin;

    @FXML
    private TextField txtPlan;

    @FXML
    private TextField txtFechaInicio;

    @FXML
    private TextField txtEstado;

    private Usuario usuario;

    private final MembresiaDAO membresiaDAO = new MembresiaDAO();

    private final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    private void cargarMembresia() {

        try {
            Membresia membresia = membresiaDAO.buscarPorUsuario(usuario.getId());

            if (membresia == null) {
                mostrarSinMembresia();
                return;
            }

            txtPlan.setText(membresia.getPlanNombre());

            txtFechaInicio.setText(membresia.getFechaInicio().format(formatoFecha));

            txtFechaFin.setText(membresia.getFechaFin().format(formatoFecha));

            txtEstado.setText(membresia.getEstado());
        } catch (SQLException e) {

            mostrarSinMembresia();

            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo cargar la membresia\n" + e.getMessage());
        }

    }

    public void mostrarSinMembresia() {
        txtPlan.setText("Sin membresia asignada");
        txtFechaInicio.setText("-");
        txtFechaFin.setText("-");
        txtEstado.setText("-");
    }

    public void setUsuario(Usuario usuario) {

        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede estar vacio");
        }

        this.usuario = usuario;
        cargarMembresia();

    }


    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
