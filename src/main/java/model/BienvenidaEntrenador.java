package model;

public class BienvenidaEntrenador extends BienvenidaRol {

    public BienvenidaEntrenador(String nombreUsuario) {
        super(nombreUsuario);
    }

    @Override
    public String obtenerNombreRol() {
        return "Entrenador";
    }

    @Override
    public String obtenerMensaje() {
        return "Consulta clientes, administra rutinas "
                + "y revisa las rutinas asignadas.";
    }
}