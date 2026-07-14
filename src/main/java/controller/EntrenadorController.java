package controller;

import dao.EntrenadorDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Entrenador;

import java.sql.SQLException;

public class EntrenadorController {

    @FXML private TextField txtCedula;

    @FXML private TextField txtNombre;

    @FXML private TextField txtApellido;

    @FXML private ComboBox<String> cbEspecialidad;

    @FXML private TextField txtTelefono;

    @FXML private TextField txtUsuario;

    @FXML private PasswordField txtContrasena;

    @FXML private TableView<Entrenador> tablaEntrenadores;

    @FXML private TableColumn<Entrenador, Integer> colId;

    @FXML private TableColumn<Entrenador, String> colCedula;

    @FXML private TableColumn<Entrenador, String> colNombre;

    @FXML private TableColumn<Entrenador, String> colApellido;

    @FXML private TableColumn<Entrenador, String> colEspecialidad;

    @FXML private TableColumn<Entrenador, String> colTelefono;

    private EntrenadorDAO entrenadorDAO= new EntrenadorDAO();
    private ObservableList<Entrenador> lista = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        cbEspecialidad.getItems().addAll("Musculacion", "Calistenia", "Powerlifting");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colEspecialidad.setCellValueFactory(new PropertyValueFactory<>("especialidad"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        cargarEntrenadores();

        tablaEntrenadores.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, nuevoValor) -> {
            if (nuevoValor != null) {
                cargarFormulario(nuevoValor);
            }
        });
    }

    @FXML
    private void guardar() {
        try {
            if (!validarCamposEntrenador()) {
                return;
            }
            if (!validarCredenciales()) {
                return;
            }
            Entrenador entrenador = new Entrenador();

            entrenador.setCedula(txtCedula.getText());
            entrenador.setNombre(txtNombre.getText());
            entrenador.setApellido(txtApellido.getText());
            entrenador.setEspecialidad(cbEspecialidad.getValue());
            entrenador.setTelefono(txtTelefono.getText());

            entrenadorDAO.guardar(entrenador, txtUsuario.getText(), txtContrasena.getText());
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Entrenador guardado correctamente.");

            cargarEntrenadores();
            limpiar();
        } catch (IllegalArgumentException e) {

            mostrarAlerta(Alert.AlertType.WARNING, "Validación", e.getMessage());

        } catch (SQLException e) {

            mostrarAlerta(Alert.AlertType.ERROR, "Error de base de datos", e.getMessage());
        }

    }


    @FXML
    private void actualizar() {

        Entrenador entrenadorSeleccionado = tablaEntrenadores.getSelectionModel().getSelectedItem();

        if (entrenadorSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", "Seleccione un entrenador para actualizar.");
            return;
        }

        if (!validarCamposEntrenador()) {
            return;
        }

        try {
            entrenadorSeleccionado.setCedula(txtCedula.getText());
            entrenadorSeleccionado.setNombre(txtNombre.getText());
            entrenadorSeleccionado.setApellido(txtApellido.getText());
            entrenadorSeleccionado.setEspecialidad(cbEspecialidad.getValue());
            entrenadorSeleccionado.setTelefono(txtTelefono.getText());
            entrenadorDAO.actualizar(entrenadorSeleccionado);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Entrenador actualizado correctamente.");
            cargarEntrenadores();
            limpiar();
        } catch (IllegalArgumentException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validación", e.getMessage());
        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de base de datos", e.getMessage());
        }
    }

    @FXML
    private void eliminar() {
        Entrenador entrenadorSeleccionado = tablaEntrenadores.getSelectionModel().getSelectedItem();
        if (entrenadorSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", "Seleccione un entrenador para eliminar.");
            return;
        }
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);

        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Está seguro de eliminar al entrenador " + entrenadorSeleccionado.getNombre() + " " + entrenadorSeleccionado.getApellido() + "?");

        if (confirmacion.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }
        try {
            entrenadorDAO.eliminar(entrenadorSeleccionado.getId(), entrenadorSeleccionado.getUsuarioId());
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Entrenador eliminado correctamente.");

            cargarEntrenadores();
            limpiar();

        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de base de datos", e.getMessage());
        }
    }

    private void cargarEntrenadores() {
        try {
            lista.clear();
            lista.addAll(entrenadorDAO.listar());
            tablaEntrenadores.setItems(lista);
        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de base de datos", e.getMessage());
        }
    }


    private void cargarFormulario(Entrenador entrenador) {
        txtCedula.setText(entrenador.getCedula());
        txtNombre.setText(entrenador.getNombre());
        txtApellido.setText(entrenador.getApellido());
        txtTelefono.setText(entrenador.getTelefono());
        cbEspecialidad.setValue(entrenador.getEspecialidad());
        // Bloquear usuario y contraseña al actualizar
        txtUsuario.setDisable(true);
        txtContrasena.setDisable(true);
    }


    @FXML
    private void limpiar() {
        txtCedula.clear();
        txtNombre.clear();
        txtApellido.clear();
        txtTelefono.clear();
        txtUsuario.clear();
        txtContrasena.clear();
        cbEspecialidad.getSelectionModel().clearSelection();
        tablaEntrenadores.getSelectionModel().clearSelection();
        // Habilitar para nuevo registro
        txtUsuario.setDisable(false);
        txtContrasena.setDisable(false);
    }


    private boolean validarCamposEntrenador() {
        if (txtCedula.getText().isBlank()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validación", "La cédula es obligatoria.");
            return false;
        }
        if (txtNombre.getText().isBlank()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validación", "El nombre es obligatorio.");
            return false;
        }
        if (txtApellido.getText().isBlank()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validación", "El apellido es obligatorio.");
            return false;
        }
        if (txtTelefono.getText().isBlank()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validación", "El teléfono es obligatorio.");
            return false;
        }
        if (cbEspecialidad.getValue() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validación", "Seleccione una especialidad.");
            return false;
        }
        return true;
    }


    private boolean validarCredenciales() {
        if (txtUsuario.getText().isBlank()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validación", "El usuario es obligatorio.");
            return false;
        }
        if (txtContrasena.getText().isBlank()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validación", "La contraseña es obligatoria.");
            return false;
        }
        return true;
    }


    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }



}