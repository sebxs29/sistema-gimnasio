package model;

public class BienvenidaAdministrador extends BienvenidaRol {

    public BienvenidaAdministrador(String nombreUsuario) {
        super(nombreUsuario);
    }

    @Override
    public String obtenerNombreRol() {
        return "Administrador";
    }

    @Override
    public String obtenerMensaje() {
        return "Gestiona clientes, entrenadores, planes, "
                + "membresías y reportes del gimnasio.";
    }
}
