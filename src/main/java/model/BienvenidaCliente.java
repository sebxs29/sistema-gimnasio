package model;

public class BienvenidaCliente extends BienvenidaRol {

    public BienvenidaCliente(String nombreUsuario) {
        super(nombreUsuario);
    }

    @Override
    public String obtenerNombreRol() {
        return "Cliente";
    }

    @Override
    public String obtenerMensaje() {
        return "Consulta tu membresía y la rutina "
                + "que tienes asignada.";
    }
}