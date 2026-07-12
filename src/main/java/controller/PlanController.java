package controller;

import dao.PlanDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Plan;

import java.sql.SQLException;
import java.util.List;

public class PlanController {
    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtDuracion;

    @FXML
    private TableView<Plan> tblPlanes;

    @FXML
    private TableColumn<Plan, Integer> colId;

    @FXML
    private TableColumn<Plan, String> colNombre;

    @FXML
    private  TableColumn<Plan, Double> colPrecio;

    @FXML
    private TableColumn<Plan, Integer> colDuracion;

    private final PlanDAO planDAO = new PlanDAO();

    @FXML
    void initialize() {
        cargarPlanes();

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colDuracion.setCellValueFactory(new PropertyValueFactory<>("duracionMeses"));

        tblPlanes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cargarFormulario(newValue);
            }
        });
    }

    @FXML
    void guardarPlan() {
        try {

            if (!validarCampos()) {
                return;
            }

            String nombre = txtNombre.getText();
            double precio = Double.parseDouble(txtPrecio.getText());
            int duracionMeses = Integer.parseInt(txtDuracion.getText());

            Plan planGuardado = new Plan(nombre, precio, duracionMeses);
            planDAO.guardar(planGuardado);

            cargarPlanes();
            limpiarCampos();

            mostrarAlerta(Alert.AlertType.INFORMATION, "Exito", "Plan guardado correctamente");
        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de base de datos", e.getMessage());
        } catch (IllegalArgumentException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validacion", e.getMessage());
        }

    }

    void cargarPlanes() {
        try {
            List<Plan> lista = planDAO.listar();

            ObservableList<Plan> data = FXCollections.observableArrayList(lista);

            tblPlanes.setItems(data);

        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de base de datos", e.getMessage());
        }
    }

    void cargarFormulario(Plan planCargado) {

        txtNombre.setText(planCargado.getNombre());
        txtPrecio.setText(Double.toString(planCargado.getPrecio()));
        txtDuracion.setText(Integer.toString(planCargado.getDuracionMeses()));

    }

    @FXML
    void limpiarCampos() {
        txtNombre.clear();
        txtPrecio.clear();
        txtDuracion.clear();

        tblPlanes.getSelectionModel().clearSelection();
    }

    @FXML
    void actualizarPlan() {

        try {
            Plan planSeleccionado = tblPlanes.getSelectionModel().getSelectedItem();

            if (planSeleccionado == null) {
                mostrarAlerta(Alert.AlertType.ERROR, "Plan", "Seleccione un plan para actualizar");
                return;
            }

            if (!validarCampos()) {
                return;
            }

            int id = planSeleccionado.getId();

            String nombre = txtNombre.getText();
            double precio = Double.parseDouble(txtPrecio.getText());
            int duracion = Integer.parseInt(txtDuracion.getText());

            Plan planActualizado = new Plan(id, nombre, precio, duracion);
            planDAO.actualizar(planActualizado);

            mostrarAlerta(Alert.AlertType.INFORMATION, "Exito", "Plan actualizado correcetamente");

            cargarPlanes();
            limpiarCampos();

        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de base de datos", e.getMessage());
        } catch (IllegalArgumentException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validacion", e.getMessage());
        }

    }

    @FXML
    void eliminarPlan() {
        try {
            Plan planSeleccionado = tblPlanes.getSelectionModel().getSelectedItem();

            if (planSeleccionado == null) {
                mostrarAlerta(Alert.AlertType.ERROR, "Plan", "Seleccione un plan para eliminar");
                return;
            }

            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminacion");
            confirmacion.setContentText("Estas seguro de eliminar este plan? " + planSeleccionado.getNombre());

            if (confirmacion.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
                return;
            }

            planDAO.eliminar(planSeleccionado.getId());
            mostrarAlerta(Alert.AlertType.INFORMATION,"Exito", "Plan eliminado correctamente");
            cargarPlanes();
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

        if (txtPrecio.getText().isBlank()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validacion", "El precio es obligatorio");
            return false;
        }

        if (txtDuracion.getText().isBlank()) {
            mostrarAlerta(Alert.AlertType.WARNING,"Validacion", "La duracion en meses es obligatoria");
            return false;
        }

        try {
            double precio = Double.parseDouble(txtPrecio.getText());
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validacion", "El precio debe ser un numero valido. Ej: 29.50");
            return false;
        }

        try {
            int duracion = Integer.parseInt(txtDuracion.getText());
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validacion", "La duracion debe ser un numero entero. Ej: 3");
            return false;
        }

        return true;
    }
}
