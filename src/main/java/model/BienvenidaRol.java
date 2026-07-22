package model;

public abstract class BienvenidaRol {

    private final String nombreUsuario;

    public BienvenidaRol(String nombreUsuario) {

        if (nombreUsuario == null || nombreUsuario.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre de usuario no puede estar vacío"
            );
        }

        this.nombreUsuario = nombreUsuario;
    }

    public String obtenerTitulo() {
        return "¡Bienvenido, " + nombreUsuario + "!";
    }

    public abstract String obtenerNombreRol();

    public abstract String obtenerMensaje();

    public static BienvenidaRol crear(
            String nombreUsuario,
            String rol
    ) {

        return switch (rol) {

            case "ADMINISTRADOR" ->
                    new BienvenidaAdministrador(nombreUsuario);

            case "ENTRENADOR" ->
                    new BienvenidaEntrenador(nombreUsuario);

            case "CLIENTE" ->
                    new BienvenidaCliente(nombreUsuario);

            default ->
                    throw new IllegalArgumentException(
                            "Rol no reconocido: " + rol
                    );
        };
    }
}