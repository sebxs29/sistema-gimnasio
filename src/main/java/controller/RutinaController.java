package controller;

import dao.RutinaDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Rutina;

import java.sql.SQLException;
import java.util.List;

public class RutinaController {

    @FXML
    private TextField txtNombre;

    @FXML
    private TextArea txtDescripcion;

    @FXML
    private ComboBox<String> cmbNivel;

    @FXML
    private TableView<Rutina> tblRutinas;

    @FXML
    private TableColumn<Rutina, Integer> colId;

    @FXML
    private TableColumn<Rutina, String> colNombre;

    @FXML
    private TableColumn<Rutina, String> colDescripcion;

    @FXML
    private TableColumn<Rutina, String> colNivel;

    private final RutinaDAO rutinaDAO = new RutinaDAO();

    @FXML
    void initialize() {
        cmbNivel.getItems().addAll("PRINCIPIANTE", "INTERMEDIO", "AVANZADO");
        cargarRutinas();
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colNivel.setCellValueFactory(new PropertyValueFactory<>("nivel"));
        tblRutinas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cargarFormulario(newValue);
            }
        });
    }

    @FXML
    void guardarRutina() {
        try {
            if (!validarCampos()) {
                return;
            }
            String nombre = txtNombre.getText();
            String descripcion = txtDescripcion.getText();
            String nivel = cmbNivel.getValue();

            Rutina rutinaGuardada = new Rutina(nombre, descripcion, nivel);
            rutinaDAO.guardar(rutinaGuardada);

            cargarRutinas();
            limpiarCampos();

            mostrarAlerta(Alert.AlertType.INFORMATION, "Exito", "Rutina guardada correctamente");
        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de base de datos", e.getMessage());
        } catch (IllegalArgumentException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validacion", e.getMessage());
        }
    }

    void cargarRutinas() {
        try {
            List<Rutina> lista = rutinaDAO.listar();

            ObservableList<Rutina> data = FXCollections.observableArrayList(lista);

            tblRutinas.setItems(data);

        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de base de datos", e.getMessage());
        }
    }

    void cargarFormulario(Rutina rutinaCargada) {

        txtNombre.setText(rutinaCargada.getNombre());
        txtDescripcion.setText(rutinaCargada.getDescripcion());
        cmbNivel.setValue(rutinaCargada.getNivel());
    }

    @FXML
    void limpiarCampos() {
        txtNombre.clear();
        txtDescripcion.clear();
        cmbNivel.getSelectionModel().clearSelection();

        tblRutinas.getSelectionModel().clearSelection();
    }

    @FXML
    void actualizarRutina() {
        try {
            Rutina rutinaSeleccionada = tblRutinas.getSelectionModel().getSelectedItem();
            if (rutinaSeleccionada == null) {
                mostrarAlerta(Alert.AlertType.ERROR, "Rutina", "Seleccione una rutina para actualizar");
                return;
            }

            if (!validarCampos()) {
                return;
            }

            int id = rutinaSeleccionada.getId();

            String nombre = txtNombre.getText();
            String descripcion = txtDescripcion.getText();
            String nivel = cmbNivel.getValue();

            Rutina rutinaActualizada = new Rutina(id, nombre, descripcion, nivel);
            rutinaDAO.actualizar(rutinaActualizada);

            mostrarAlerta(Alert.AlertType.INFORMATION, "Exito", "Rutina actualizada correctamente");

            cargarRutinas();
            limpiarCampos();

        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de base de datos", e.getMessage());
        } catch (IllegalArgumentException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validacion", e.getMessage());
        }
    }

    @FXML
    void eliminarRutina() {
        try {
            Rutina rutinaSeleccionada = tblRutinas.getSelectionModel().getSelectedItem();

            if (rutinaSeleccionada == null) {
                mostrarAlerta(Alert.AlertType.ERROR, "Rutina", "Seleccione una rutina para eliminar");
                return;
            }

            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminacion");
            confirmacion.setContentText("Estas seguro de eliminar esta rutina? " + rutinaSeleccionada.getNombre());

            if (confirmacion.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
                return;
            }

            rutinaDAO.eliminar(rutinaSeleccionada.getId());
            mostrarAlerta(Alert.AlertType.INFORMATION, "Exito", "Rutina eliminada correctamente");
            cargarRutinas();
            limpiarCampos();

        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de base de datos", e.getMessage());
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private boolean validarCampos() {

        if (txtNombre.getText().isBlank()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validacion", "El nombre es obligatorio");
            return false;
        }

        if (txtDescripcion.getText().isBlank()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validacion", "La descripcion es obligatoria");
            return false;
        }

        if (cmbNivel.getValue() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validacion", "Debe seleccionar un nivel");
            return false;
        }

        return true;
    }
}