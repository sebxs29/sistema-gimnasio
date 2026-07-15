package model;

public class Cliente extends Persona{

    private int usuarioId;
    private String correo;
    private String estado;

    // CONSTRUCTOR PARA LEER
    public Cliente(int id, int usuarioId, String cedula, String nombre, String apellido, String telefono, String correo, String estado) {

        super(id, usuarioId, cedula, nombre, apellido, telefono);

        setCorreo(correo);
        setEstado(estado);
    }

    // CONSTRUCTOR PARA INSERTAR
    public Cliente(String cedula, String nombre, String apellido, String telefono, String correo, String estado) {

        super(cedula, nombre, apellido, telefono);

        setCorreo(correo);
        setEstado(estado);
    }

    public void setCorreo(String correo) {

        if (correo == null || correo.isBlank()) {
            throw new IllegalArgumentException("El correo no puede estar vacio");
        }

        if (correo.trim().length() > 50)  {
            throw new IllegalArgumentException("El correo no puede superar los 50 caracteres");
        }

        this.correo = correo;
    }

    public void setEstado(String estado) {

        if (estado == null || (!estado.equals("ACTIVO") && !estado.equals("INACTIVO"))) {
            throw new IllegalArgumentException("El estado debe ser ACTIVO o INACTIVO");
        }

        this.estado = estado;
    }



    public String getCorreo() {
        return correo;
    }

    public String getEstado() {
        return estado;
    }
}
