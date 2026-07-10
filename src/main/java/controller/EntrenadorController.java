package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Entrenador;

public class EntrenadorController {

    @FXML private TextField txtCedula;

    @FXML private TextField txtNombre;

    @FXML private TextField txtApellido;

    @FXML private ComboBox<String> cbEspecialidad;

    @FXML private TextField txtTelefono;

    @FXML private TextField txtUsuario;

    @FXML private PasswordField txtContrasena;

    @FXML private TableView<Entrenador> tablaEntrenadores;

    @FXML private TableColumn<Entrenador, String> colCedula;

    @FXML private TableColumn<Entrenador, String> colNombre;

    @FXML private TableColumn<Entrenador, String> colApellido;

    @FXML private TableColumn<Entrenador, String> colEspecialidad;

    @FXML private TableColumn<String, String> colTelefono;

    @FXML
    public void initialize() {
        cbEspecialidad.getItems().addAll("Preparador Físico", "Director Técnico", "Entrenador de Porteros", "Asistente Técnico");
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

    }

    @FXML
    private void actualizar() {

    }

    @FXML
    private void eliminar() {

    }

    @FXML
    private void limpiar() {

    }

    @FXML
    private void seleccionarEntrenador() {

    }

    private void cargarEntrenadores() {

    }

    private boolean validarCampos() {
        return false;
    }


}