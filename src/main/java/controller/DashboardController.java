package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Usuario;

import java.io.IOException;

public class DashboardController {

    @FXML private AnchorPane paneContenido;
    @FXML private Button btnClientes;
    @FXML private Button btnEntrenadores;
    @FXML private Button btnPlanes;
    @FXML private Button btnMembresias;
    @FXML private Button btnRutinas;
    @FXML private Button btnAsignarRutinas;
    @FXML private Button btnMiRutina;
    @FXML private Button btnMiMembresia;
    @FXML private Button btnReportes;
    @FXML private Button btnCerrarSesion;

    private Usuario usuario;

    //Recibe el usuario que inició sesión.
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        configurarMenu();
    }


    private void ocultarTodo() {
        ocultarBoton(btnClientes);
        ocultarBoton(btnEntrenadores);
        ocultarBoton(btnPlanes);
        ocultarBoton(btnMembresias);
        ocultarBoton(btnRutinas);
        ocultarBoton(btnAsignarRutinas);
        ocultarBoton(btnMiRutina);
        ocultarBoton(btnMiMembresia);
        ocultarBoton(btnReportes);
    }


    private void configurarMenu() {
        ocultarTodo();
        String rol = usuario.getRol();
        switch (rol) {
            case "ADMINISTRADOR":
                mostrarBoton(btnClientes);
                mostrarBoton(btnEntrenadores);
                mostrarBoton(btnPlanes);
                mostrarBoton(btnMembresias);
                mostrarBoton(btnReportes);
                break;
            case "ENTRENADOR":
                mostrarBoton(btnClientes);
                mostrarBoton(btnRutinas);
                mostrarBoton(btnAsignarRutinas);
                break;
            case "CLIENTE":
                mostrarBoton(btnMiRutina);
                mostrarBoton(btnMiMembresia);
                break;
        }
    }

    private void mostrarBoton(Button boton) {
        boton.setVisible(true);
        boton.setManaged(true);
    }

    private void ocultarBoton(Button boton) {
        boton.setVisible(false);
        boton.setManaged(false);
    }

    private void cargarVista(String vista) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/" + vista));
            Node node = loader.load();
            paneContenido.getChildren().setAll(node);
            AnchorPane.setTopAnchor(node, 0.0);
            AnchorPane.setBottomAnchor(node, 0.0);
            AnchorPane.setLeftAnchor(node, 0.0);
            AnchorPane.setRightAnchor(node, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirClientes() {
        cargarVista("clientes.fxml");
    }

    @FXML
    private void abrirEntrenadores() {
        cargarVista("entrenadores.fxml");
    }

    @FXML
    private void abrirPlanes() {
        cargarVista("planes.fxml");
    }

    @FXML
    private void abrirMembresias() {
        cargarVista("membresias.fxml");
    }

    @FXML
    private void abrirRutinas() {
        cargarVista("rutinas.fxml");
    }

    @FXML
    private void abrirAsignacionRutinas() {
        cargarVista("asignacionRutina.fxml");
    }

    @FXML
    private void cerrarSesion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) btnCerrarSesion.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}