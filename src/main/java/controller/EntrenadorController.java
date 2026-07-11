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

    private final EntrenadorDAO dao = new EntrenadorDAO();
    private final ObservableList<Entrenador> lista = FXCollections.observableArrayList();
    private Entrenador entrenadorSeleccionado;

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
        tablaEntrenadores.setOnMouseClicked(e -> seleccionarEntrenador());
    }

    @FXML
    private void guardar() {
        if (!validarCampos()) {
            return;
        }
        try {
            Entrenador entrenador = new Entrenador();

            entrenador.setCedula(txtCedula.getText());
            entrenador.setNombre(txtNombre.getText());
            entrenador.setApellido(txtApellido.getText());
            entrenador.setEspecialidad(cbEspecialidad.getValue());
            entrenador.setTelefono(txtTelefono.getText());

            dao.guardar(entrenador, txtUsuario.getText(), txtContrasena.getText());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Entrenador registrado correctamente.");
            alert.showAndWait();
            cargarEntrenadores();
            limpiar();
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void actualizar() {
        if (entrenadorSeleccionado == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Seleccione un entrenador.");
            alert.showAndWait();
            return;

        }
        if (!validarCampos()) {
            return;
        }
        try {
            entrenadorSeleccionado.setCedula(txtCedula.getText());
            entrenadorSeleccionado.setNombre(txtNombre.getText());
            entrenadorSeleccionado.setApellido(txtApellido.getText());
            entrenadorSeleccionado.setEspecialidad(cbEspecialidad.getValue());
            entrenadorSeleccionado.setTelefono(txtTelefono.getText());

            dao.actualizar(entrenadorSeleccionado);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Entrenador actualizado correctamente.");
            alert.showAndWait();

            cargarEntrenadores();
            limpiar();

        } catch (SQLException ex) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }
    @FXML
    private void eliminar() {
        if (entrenadorSeleccionado == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Seleccione un entrenador.");
            alert.showAndWait();
            return;
        }
        try {
            dao.eliminar(entrenadorSeleccionado.getId(), entrenadorSeleccionado.getUsuarioId());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Entrenador eliminado.");
            alert.showAndWait();

            cargarEntrenadores();
            limpiar();

        } catch (SQLException ex) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(ex.getMessage());
            alert.showAndWait();

        }
    }

    @FXML
    private void limpiar() {
        txtCedula.clear();
        txtNombre.clear();
        txtApellido.clear();
        txtTelefono.clear();
        txtUsuario.clear();
        txtContrasena.clear();
        cbEspecialidad.setValue(null);
        entrenadorSeleccionado = null;
        tablaEntrenadores.getSelectionModel().clearSelection();
    }

    @FXML
    private void seleccionarEntrenador() {
        entrenadorSeleccionado = tablaEntrenadores.getSelectionModel().getSelectedItem();
        if (entrenadorSeleccionado != null) {
            txtCedula.setText(entrenadorSeleccionado.getCedula());
            txtNombre.setText(entrenadorSeleccionado.getNombre());
            txtApellido.setText(entrenadorSeleccionado.getApellido());
            txtTelefono.setText(entrenadorSeleccionado.getTelefono());
            cbEspecialidad.setValue(entrenadorSeleccionado.getEspecialidad());
        }
    }

    private void cargarEntrenadores() {
        try {
            lista.clear();
            lista.addAll(dao.listar());
            tablaEntrenadores.setItems(lista);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    private boolean validarCampos() {

        if (txtCedula.getText().isBlank()
                || txtNombre.getText().isBlank()
                || txtApellido.getText().isBlank()
                || txtTelefono.getText().isBlank()
                || txtUsuario.getText().isBlank()
                || txtContrasena.getText().isBlank()
                || cbEspecialidad.getValue() == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Complete todos los campos.");
            alert.showAndWait();

            return false;
        }

        return true;
    }
}