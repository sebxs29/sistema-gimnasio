package controller;

import dao.AsignacionRutinaDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.AsignacionRutina;
import model.Usuario;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class MiRutinaController {

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private TextField txtNivel;

    @FXML
    private TextField txtEntrenador;

    @FXML
    private TextField txtFecha;

    private Usuario usuario;

    private final AsignacionRutinaDAO asignacionRutinaDAO = new AsignacionRutinaDAO();

    private final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    private void cargarRutina() {

        try {
            AsignacionRutina asignacion = asignacionRutinaDAO.buscarPorUsuario(usuario.getId());

            if (asignacion == null) {
                mostrarSinRutina();
                return;
            }

            txtNombre.setText(asignacion.getRutinaNombre());

            txtDescripcion.setText(asignacion.getRutinaDescripcion());

            txtNivel.setText(asignacion.getRutinaNivel());

            txtEntrenador.setText(asignacion.getEntrenadorNombre());

            txtFecha.setText(asignacion.getFechaAsignacion().format(formatoFecha));
        } catch (SQLException e) {

            mostrarSinRutina();

            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo cargar la rutina\n" + e.getMessage());
        }

    }

    public void mostrarSinRutina() {
        txtNombre.setText("Sin rutina asignada");
        txtDescripcion.setText("-");
        txtNivel.setText("-");
        txtEntrenador.setText("-");
        txtFecha.setText("-");
    }

    public void setUsuario(Usuario usuario) {

        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede estar vacio");
        }

        this.usuario = usuario;
        cargarRutina();

    }


    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}