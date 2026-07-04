package controller;

import dao.UsuarioDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Usuario;

public class LoginController {
    @FXML
    private PasswordField txtContrasena;

    @FXML
    private TextField txtUsuario;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML
    private void ingresar() {
        String usuario = txtUsuario.getText();
        String contrasena = txtContrasena.getText();

        if (usuario.isBlank() || contrasena.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Complete todos los campos");
            alert.showAndWait();
            return;
        }

        Usuario usuarioEnconrado = usuarioDAO.validarLogin(usuario, contrasena);

        // FALTA CONEECTAR CON EL DASHBOARD
        if (usuarioEnconrado != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Bienvenido " + usuarioEnconrado.getUsuario() + "\nRol: " + usuarioEnconrado.getRol());
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Usuario o contraseña incorrectos");
            alert.showAndWait();
        }
    }

}
