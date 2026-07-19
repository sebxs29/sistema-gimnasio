package controller;

import dao.ClienteDAO;
import dao.MembresiaDAO;
import dao.PlanDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import model.Cliente;
import model.Membresia;
import model.Plan;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class MembresiaController {

    @FXML
    private ComboBox<Cliente> cbCliente;

    @FXML
    private ComboBox<Plan> cbPlan;

    @FXML
    private DatePicker dpFechaInicio;

    @FXML
    private DatePicker dpFechaFin;

    @FXML
    private ComboBox<String> cbEstado;


    @FXML
    private TableView<Membresia> tblMembresias;

    @FXML
    private TableColumn<Membresia, Integer> colId;

    @FXML
    private TableColumn<Membresia, String> colCliente;

    @FXML
    private TableColumn<Membresia, String> colPlan;

    @FXML
    private TableColumn<Membresia, LocalDate> colFechaInicio;

    @FXML
    private TableColumn<Membresia, LocalDate> colFechaFin;

    @FXML
    private TableColumn<Membresia, String> colEstado;

    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final PlanDAO planDAO = new PlanDAO();
    private final MembresiaDAO membresiaDAO = new MembresiaDAO();

    @FXML
    void initialize() {
        cbEstado.getItems().addAll("ACTIVO", "INACTIVO");
        configurarComboBoxes();

        cargarClientes();
        cargarPLanes();

        configurarCalculoFechaFin();

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("clienteNombre"));
        colPlan.setCellValueFactory(new PropertyValueFactory<>("planNombre"));
        colFechaInicio.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
        colFechaFin.setCellValueFactory(new PropertyValueFactory<>("fechaFin"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        cargarMembresias();

        tblMembresias.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cargarFormulario(newValue);
            }
        });
    }

    private void configurarComboBoxes() {

        cbCliente.setConverter(new StringConverter<>() {

            @Override
            public String toString(Cliente cliente) {

                if (cliente == null) {
                    return "";
                }

                return cliente.getNombre() + " " + cliente.getApellido();

            }

            @Override
            public Cliente fromString(String string) {
                return null;
            }
        });

        cbPlan.setConverter(new StringConverter<>() {

            @Override
            public String toString(Plan plan) {

                if (plan == null) {
                    return "";
                }
                return plan.getNombre() + " - " + plan.getDuracionMeses() + " mes(es)";
            }

            @Override
            public Plan fromString(String string) {
                return null;
            }
        });

    }

    private void cargarClientes() {
        try {
            cbCliente.setItems(FXCollections.observableArrayList(clienteDAO.listar()));
        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudieron cargar los clientes\n" + e.getMessage());
        }
    }

    private void cargarPLanes() {
        try {
            cbPlan.setItems(FXCollections.observableArrayList(planDAO.listar()));
        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudieron cargar los planes\n" + e.getMessage());
        }
    }

    private void calcularFechaFin() {
        Plan plan = cbPlan.getValue();
        LocalDate fechaInicio = dpFechaInicio.getValue();

        if (plan == null || fechaInicio == null) {
            dpFechaFin.setValue(null);
            return;
        }

        LocalDate fechaFin = fechaInicio.plusMonths(plan.getDuracionMeses());

        dpFechaFin.setValue(fechaFin);
    }

    private void configurarCalculoFechaFin() {
        dpFechaFin.setDisable(true);

        cbPlan.valueProperty().addListener((observable, oldValue, newValue) -> calcularFechaFin());

        dpFechaInicio.valueProperty().addListener((observable, oldValue, newValue) -> calcularFechaFin());
    }

    @FXML
    void guardarMembresia() {

        try {
            Cliente cliente = cbCliente.getValue();
            Plan plan = cbPlan.getValue();

            Membresia membresiaGuardada = new Membresia(
                    cliente.getId(),
                    plan.getId(),
                    dpFechaInicio.getValue(),
                    dpFechaFin.getValue(),
                    cbEstado.getValue()
            );

            membresiaDAO.guardar(membresiaGuardada);

            mostrarAlerta(Alert.AlertType.INFORMATION, "Membresia guardada", "La membresia se guardo correctamente");

            cargarMembresias();
            limpiarCampos();

        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de base de datos", e.getMessage());
        } catch (IllegalArgumentException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validacion", e.getMessage());
        }
    }

    private void cargarMembresias() {

        try {
            List<Membresia> lista = membresiaDAO.listar();

            ObservableList<Membresia> data = FXCollections.observableArrayList(lista);

            tblMembresias.setItems(data);
        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de base de datos", e.getMessage());
        }
    }

    void cargarFormulario(Membresia membresiaCargada) {

        Cliente clienteSeleccionado = cbCliente.getItems().stream().filter(cliente -> cliente.getId() == membresiaCargada.getClienteId()).findFirst().orElse(null);

        Plan planSeleccionado = cbPlan.getItems().stream().filter(plan -> plan.getId() == membresiaCargada.getPlanId()).findFirst().orElse(null);

        cbCliente.setValue(clienteSeleccionado);
        cbPlan.setValue(planSeleccionado);

        dpFechaInicio.setValue(membresiaCargada.getFechaInicio());
        dpFechaFin.setValue(membresiaCargada.getFechaFin());

        cbEstado.setValue(membresiaCargada.getEstado());

    }

    @FXML
    void actualizarMembresia() {

        Membresia membresiaSeleccionada = tblMembresias.getSelectionModel().getSelectedItem();

        if (membresiaSeleccionada == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Membresia", "Seleccione una membresia para actualizar");
            return;
        }

        try {

            Cliente cliente = cbCliente.getValue();
            Plan plan = cbPlan.getValue();

            int clienteId = cliente.getId();
            int planId = plan.getId();
            LocalDate fechaInicio = dpFechaInicio.getValue();
            LocalDate fechaFin = dpFechaFin.getValue();
            String estado = cbEstado.getValue();

            Membresia membresiaActualizada = new Membresia(membresiaSeleccionada.getId(), clienteId, planId, fechaInicio, fechaFin, estado);

            membresiaDAO.actualizar(membresiaActualizada);

            mostrarAlerta(Alert.AlertType.INFORMATION, "Membresia actualizada", "Membresia actualizada correctamente");

            cargarMembresias();
            limpiarCampos();
        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de base de datos", e.getMessage());
        } catch (IllegalArgumentException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validacion", e.getMessage());
        }

    }

    @FXML
    void eliminarMembresia() {

        try {
            Membresia membresiaSeleccionada = tblMembresias.getSelectionModel().getSelectedItem();

            if (membresiaSeleccionada == null) {
                mostrarAlerta(Alert.AlertType.WARNING, "Membresia", "Seleccione una membresia para eliminar");
                return;
            }

            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminacion");
            confirmacion.setContentText("Estas seguro de eliminar esta membresia?");

            if (confirmacion.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
                return;
            }

            membresiaDAO.eliminar(membresiaSeleccionada.getId());
            mostrarAlerta(Alert.AlertType.INFORMATION, "Exito", "Membresia eliminada correctamente");

            cargarMembresias();
            limpiarCampos();


        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de base de datos", e.getMessage());
        }

    }

    @FXML
    void limpiarCampos() {

        cbCliente.getSelectionModel().clearSelection();
        cbPlan.setValue(null);

        dpFechaInicio.setValue(LocalDate.now());
        dpFechaFin.setValue(null);

        cbEstado.getSelectionModel().clearSelection();

        tblMembresias.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
