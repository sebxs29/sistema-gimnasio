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
    }

    @FXML
    void guardarCliente() {

        try {

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

        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de base de datos", e.getMessage());
        } catch (IllegalArgumentException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validacion", e.getMessage());
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


    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
