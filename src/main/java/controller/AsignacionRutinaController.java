package controller;

import dao.AsignacionRutinaDAO;
import dao.ClienteDAO;
import dao.EntrenadorDAO;
import dao.RutinaDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import model.AsignacionRutina;
import model.Cliente;
import model.Entrenador;
import model.Rutina;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AsignacionRutinaController {

    @FXML
    private ComboBox<Cliente> cmbCliente;

    @FXML
    private ComboBox<Rutina> cmbRutina;

    @FXML
    private ComboBox<Entrenador> cmbEntrenador;

    @FXML
    private DatePicker dpFechaAsignacion;

    @FXML
    private TableView<AsignacionRutina> tblAsignaciones;

    @FXML
    private TableColumn<AsignacionRutina, Integer> colId;

    @FXML
    private TableColumn<AsignacionRutina, String> colCliente;

    @FXML
    private TableColumn<AsignacionRutina, String> colRutina;

    @FXML
    private TableColumn<AsignacionRutina, String> colEntrenador;

    @FXML
    private TableColumn<AsignacionRutina, LocalDate> colFecha;

    private final AsignacionRutinaDAO asignacionRutinaDAO = new AsignacionRutinaDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final RutinaDAO rutinaDAO = new RutinaDAO();
    private final EntrenadorDAO entrenadorDAO = new EntrenadorDAO();

    @FXML
    void initialize() {

        configurarCombos();
        cargarCombos();
        cargarAsignaciones();

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("clienteNombre"));
        colRutina.setCellValueFactory(new PropertyValueFactory<>("rutinaNombre"));
        colEntrenador.setCellValueFactory(new PropertyValueFactory<>("entrenadorNombre"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaAsignacion"));

        tblAsignaciones.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cargarFormulario(newValue);
            }
        });
    }

    private void configurarCombos() {

        cmbCliente.setConverter(new StringConverter<>() {
            @Override
            public String toString(Cliente cliente) {
                return cliente == null ? "" : cliente.getNombre() + " " + cliente.getApellido();
            }

            @Override
            public Cliente fromString(String string) {
                return null;
            }
        });

        cmbRutina.setConverter(new StringConverter<>() {
            @Override
            public String toString(Rutina rutina) {
                return rutina == null ? "" : rutina.getNombre();
            }

            @Override
            public Rutina fromString(String string) {
                return null;
            }
        });

        cmbEntrenador.setConverter(new StringConverter<>() {
            @Override
            public String toString(Entrenador entrenador) {
                return entrenador == null ? "" : entrenador.getNombre() + " " + entrenador.getApellido();
            }

            @Override
            public Entrenador fromString(String string) {
                return null;
            }
        });
    }

    private void cargarCombos() {
        try {
            List<Cliente> clientes = clienteDAO.listar();
            List<Rutina> rutinas = rutinaDAO.listar();
            List<Entrenador> entrenadores = entrenadorDAO.listar();

            cmbCliente.setItems(FXCollections.observableArrayList(clientes));
            cmbRutina.setItems(FXCollections.observableArrayList(rutinas));
            cmbEntrenador.setItems(FXCollections.observableArrayList(entrenadores));

        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de base de datos", e.getMessage());
        }
    }

    void cargarAsignaciones() {
        try {
            List<AsignacionRutina> lista = asignacionRutinaDAO.listar();

            ObservableList<AsignacionRutina> data = FXCollections.observableArrayList(lista);

            tblAsignaciones.setItems(data);

        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de base de datos", e.getMessage());
        }
    }

    void cargarFormulario(AsignacionRutina asignacionCargada) {

        seleccionarClientePorId(asignacionCargada.getClienteId());
        seleccionarRutinaPorId(asignacionCargada.getRutinaId());
        seleccionarEntrenadorPorId(asignacionCargada.getEntrenadorId());
        dpFechaAsignacion.setValue(asignacionCargada.getFechaAsignacion());
    }

    private void seleccionarClientePorId(int id) {
        for (Cliente cliente : cmbCliente.getItems()) {
            if (cliente.getId() == id) {
                cmbCliente.setValue(cliente);
                return;
            }
        }
    }

    private void seleccionarRutinaPorId(int id) {
        for (Rutina rutina : cmbRutina.getItems()) {
            if (rutina.getId() == id) {
                cmbRutina.setValue(rutina);
                return;
            }
        }
    }

    private void seleccionarEntrenadorPorId(int id) {
        for (Entrenador entrenador : cmbEntrenador.getItems()) {
            if (entrenador.getId() == id) {
                cmbEntrenador.setValue(entrenador);
                return;
            }
        }
    }

    @FXML
    void guardarAsignacion() {
        try {

            if (!validarCampos()) {
                return;
            }

            int clienteId = cmbCliente.getValue().getId();
            int rutinaId = cmbRutina.getValue().getId();
            int entrenadorId = cmbEntrenador.getValue().getId();
            LocalDate fecha = dpFechaAsignacion.getValue();

            AsignacionRutina asignacionGuardada = new AsignacionRutina(clienteId, rutinaId, entrenadorId, fecha);
            asignacionRutinaDAO.guardar(asignacionGuardada);

            cargarAsignaciones();
            limpiarCampos();

            mostrarAlerta(Alert.AlertType.INFORMATION, "Exito", "Rutina asignada correctamente");
        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de base de datos", e.getMessage());
        } catch (IllegalArgumentException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validacion", e.getMessage());
        }
    }

    @FXML
    void actualizarAsignacion() {

        try {
            AsignacionRutina asignacionSeleccionada = tblAsignaciones.getSelectionModel().getSelectedItem();

            if (asignacionSeleccionada == null) {
                mostrarAlerta(Alert.AlertType.ERROR, "Asignacion", "Seleccione una asignacion para actualizar");
                return;
            }

            if (!validarCampos()) {
                return;
            }

            int id = asignacionSeleccionada.getId();
            int clienteId = cmbCliente.getValue().getId();
            int rutinaId = cmbRutina.getValue().getId();
            int entrenadorId = cmbEntrenador.getValue().getId();
            LocalDate fecha = dpFechaAsignacion.getValue();

            AsignacionRutina asignacionActualizada = new AsignacionRutina(id, clienteId, rutinaId, entrenadorId, fecha);
            asignacionRutinaDAO.actualizar(asignacionActualizada);

            mostrarAlerta(Alert.AlertType.INFORMATION, "Exito", "Asignacion actualizada correctamente");

            cargarAsignaciones();
            limpiarCampos();

        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de base de datos", e.getMessage());
        } catch (IllegalArgumentException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validacion", e.getMessage());
        }
    }

    @FXML
    void eliminarAsignacion() {
        try {
            AsignacionRutina asignacionSeleccionada = tblAsignaciones.getSelectionModel().getSelectedItem();

            if (asignacionSeleccionada == null) {
                mostrarAlerta(Alert.AlertType.ERROR, "Asignacion", "Seleccione una asignacion para eliminar");
                return;
            }

            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminacion");
            confirmacion.setContentText("Estas seguro de eliminar esta asignacion de rutina?");

            if (confirmacion.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
                return;
            }

            asignacionRutinaDAO.eliminar(asignacionSeleccionada.getId());
            mostrarAlerta(Alert.AlertType.INFORMATION, "Exito", "Asignacion eliminada correctamente");
            cargarAsignaciones();
            limpiarCampos();

        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de base de datos", e.getMessage());
        }
    }

    @FXML
    void limpiarCampos() {
        cmbCliente.getSelectionModel().clearSelection();
        cmbRutina.getSelectionModel().clearSelection();
        cmbEntrenador.getSelectionModel().clearSelection();
        dpFechaAsignacion.setValue(null);

        tblAsignaciones.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private boolean validarCampos() {

        if (cmbCliente.getValue() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validacion", "Debe seleccionar un cliente");
            return false;
        }

        if (cmbRutina.getValue() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validacion", "Debe seleccionar una rutina");
            return false;
        }

        if (cmbEntrenador.getValue() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validacion", "Debe seleccionar un entrenador");
            return false;
        }

        if (dpFechaAsignacion.getValue() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validacion", "Debe seleccionar la fecha de asignacion");
            return false;
        }

        return true;
    }
}