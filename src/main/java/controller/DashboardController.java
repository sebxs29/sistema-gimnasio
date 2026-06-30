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

    @FXML private Button btnClientes;
    @FXML private Button btnEntrenadores;
    @FXML private Button btnPlanes;
    @FXML private Button btnMembresias;
    @FXML private Button btnRutinas;
    @FXML private Button btnAsignarRutinas;
    @FXML private Button btnMiRutina;
    @FXML private Button btnMiMembresia;
    @FXML private Button btnReportes;
    @FXML private AnchorPane paneContenido;



    @FXML
    public void abrirClientes(){



    }

    @FXML
    public void abrirEntrenadores(){



    }

    @FXML
    public void abrirPlanes(){



    }

    @FXML
    public void abrirRutinas(){



    }

    @FXML
    public void abrirMembresias(){

    }

    @FXML
    private void cerrarSesion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) btnClientes.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}