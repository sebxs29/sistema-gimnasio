package controller;

import dao.ClienteDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Cliente;

import java.sql.SQLException;
import java.util.List;

public class ClienteController {

    @FXML
    private ComboBox<String> cbEstado;

    @FXML
    private TableColumn<Cliente, String> colApellido;

    @FXML
    private TableColumn<Cliente, String> colCedula;

    @FXML
    private TableColumn<Cliente, String> colCorreo;

    @FXML
    private TableColumn<Cliente, String> colEstado;

    @FXML
    private TableColumn<Cliente, Integer> colId;

    @FXML
    private TableColumn<Cliente, String> colNombre;

    @FXML
    private TableColumn<Cliente, String> colTelefono;

    @FXML
    private TableView<Cliente> tblClientes;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtCedula;

    @FXML
    private PasswordField txtContrasena;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtUsuario;

    private final ClienteDAO clienteDAO = new ClienteDAO();

    @FXML
    void initialize() {
        cbEstado.getItems().addAll("ACTIVO", "INACTIVO");

        cargarClientes();

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        tblClientes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cargarFormulario(newValue);
            }
        });

    }

    @FXML
    void guardarCliente() {

        try {

            if (!validarCamposCliente()) {
                return;
            }

            if (!validarCredenciales()) {
                return;
            }

            String cedula = txtCedula.getText();
            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            String telefono = txtTelefono.getText();
            String correo = txtCorreo.getText();
            String estado = cbEstado.getValue();

            String usuario = txtUsuario.getText();
            String contrasena = txtContrasena.getText();

            Cliente clienteGuardado = new Cliente(cedula, nombre, apellido, telefono, correo, estado);
            clienteDAO.guardar(clienteGuardado, usuario, contrasena);

            mostrarAlerta(Alert.AlertType.INFORMATION, "Exito", "Cliente guardado correctamente");

            cargarClientes();
            limpiarCampos();

        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de base de datos", e.getMessage());
        } catch (IllegalArgumentException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validacion", e.getMessage());
        }
    }

    @FXML
    void actualizarCliente() {
        try {


            Cliente clienteSeleccionado = tblClientes.getSelectionModel().getSelectedItem();

            if (clienteSeleccionado == null) {
                mostrarAlerta(Alert.AlertType.ERROR, "Cliente", "Seleccione un cliente para actualizar");
                return;
            }

            if (!validarCamposCliente()) {
                return;
            }

            int id = clienteSeleccionado.getId();
            int usuarioId = clienteSeleccionado.getUsuarioId();
            String cedula = txtCedula.getText();
            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            String telefono = txtTelefono.getText();
            String correo = txtCorreo.getText();
            String estado = cbEstado.getValue();

            Cliente clienteActualizado = new Cliente(id, usuarioId, cedula, nombre, apellido, telefono, correo, estado);
            clienteDAO.actualizar(clienteActualizado);

            mostrarAlerta(Alert.AlertType.INFORMATION,"Exito", "Cliente actualizado correctamente");
            cargarClientes();
            limpiarCampos();

        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de base de datos", e.getMessage());
        } catch (IllegalArgumentException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validacion", e.getMessage());
        }
    }


    @FXML
    void eliminarCliente() {

        try {
            Cliente clienteSeleccionado = tblClientes.getSelectionModel().getSelectedItem();

            if (clienteSeleccionado == null) {
                mostrarAlerta(Alert.AlertType.ERROR, "Cliente", "Seleccione un cliente para eliminar");
                return;
            }

            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminacion");
            confirmacion.setContentText("Estas seguro de eliminar este cliente? " + clienteSeleccionado.getNombre() + " " + clienteSeleccionado.getApellido());

            if (confirmacion.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
                return;
            }

            clienteDAO.eliminar(clienteSeleccionado.getId(), clienteSeleccionado.getUsuarioId());
            mostrarAlerta(Alert.AlertType.INFORMATION,"Exito", "Cliente eliminado correctamente");

            cargarClientes();
            limpiarCampos();
        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de base de datos", e.getMessage());
        }

    }

    void cargarClientes() {

        try {
            // Se convierte a una observable para qeu se actualize la tabla
            List<Cliente> lista = clienteDAO.listar();

            ObservableList<Cliente> data = FXCollections.observableArrayList(lista);

            tblClientes.setItems(data);

        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de base de datos", e.getMessage());
        }

    }

    void cargarFormulario(Cliente clienteCargado) {
        txtCedula.setText(clienteCargado.getCedula());
        txtNombre.setText(clienteCargado.getNombre());
        txtApellido.setText(clienteCargado.getApellido());
        txtTelefono.setText(clienteCargado.getTelefono());
        txtCorreo.setText(clienteCargado.getCorreo());
        cbEstado.setValue(clienteCargado.getEstado());

        txtUsuario.setDisable(true);
        txtContrasena.setDisable(true);
    }

    @FXML
    void limpiarCampos() {
        txtCedula.clear();
        txtNombre.clear();
        txtApellido.clear();
        txtTelefono.clear();
        txtCorreo.clear();
        txtUsuario.clear();
        txtContrasena.clear();
        cbEstado.getSelectionModel().clearSelection();
        tblClientes.getSelectionModel().clearSelection();
        txtUsuario.setDisable(false);
        txtContrasena.setDisable(false);

    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private boolean validarCamposCliente() {
        if (txtCedula.getText().isBlank()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validacion", "La cedula es obligatoria");
            return false;
        }

        if (txtNombre.getText().isBlank()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validacion", "El nombre es obligatorio");
            return false;
        }

        if (txtApellido.getText().isBlank()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validacion", "El apellido es obligatorio");
            return false;
        }

        if (txtTelefono.getText().isBlank()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validacion", "El telefono es obligatorio");
            return false;
        }

        if (txtCorreo.getText().isBlank()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validacion", "El correo es obligatorio");
            return false;
        }

        if (cbEstado.getValue() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validacion", "Debe seleccionar un estado");
            return false;
        }

        return true;
    }

    private boolean validarCredenciales() {

        if (txtUsuario.getText().isBlank()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validacion", "El usuario es obligatorio");
            return false;
        }

        if (txtContrasena.getText().isBlank()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validacion", "La contraseña es obligatoria");
            return false;
        }

        return true;
    }
}
