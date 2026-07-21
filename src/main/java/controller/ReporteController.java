package controller;

import dao.ReporteDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.sql.SQLException;

public class ReporteController {

    @FXML
    private Label lblTotalClientesActivos;

    @FXML
    private Label lblTotalEntrenadores;

    @FXML
    private Label lblTotalPlanes;

    @FXML
    private Label lblTotalRutinasAsignadas;

    private final ReporteDAO reporteDAO = new ReporteDAO();

    @FXML
    void initialize() {
        cargarReportes();
    }

    private void cargarReportes() {

        try {
            int clientesActivos = reporteDAO.contarClientesActivos();

            int entrenadores = reporteDAO.contarEntrenadores();

            int planes = reporteDAO.contarPlanes();

            int rutinasAsignadas = reporteDAO.contarRutinasAsignadas();

            lblTotalClientesActivos.setText(String.valueOf(clientesActivos));

            lblTotalEntrenadores.setText(String.valueOf(entrenadores));

            lblTotalPlanes.setText(String.valueOf(planes));

            lblTotalRutinasAsignadas.setText(String.valueOf(rutinasAsignadas));

        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudieron cargar los reportes\n" + e.getMessage());
        }

    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
