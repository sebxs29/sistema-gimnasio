package model;

public class Cliente {

    private int id;
    private int usuarioId;
    private String cedula;
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private String estado;

    // CONSTRUCTOR PARA LEER
    public Cliente(int id, int usuarioId, String cedula, String nombre, String apellido, String telefono, String correo, String estado) {
        setId(id);
        setUsuarioId(usuarioId);
        setCedula(cedula);
        setNombre(nombre);
        setApellido(apellido);
        setTelefono(telefono);
        setCorreo(correo);
        setEstado(estado);
    }

    // CONSTRUCTOR PARA INSERTAR
    public Cliente(String cedula, String nombre, String apellido, String telefono, String correo, String estado) {
        setCedula(cedula);
        setNombre(nombre);
        setApellido(apellido);
        setTelefono(telefono);
        setCorreo(correo);
        setEstado(estado);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public void setCedula(String cedula) {

        if (cedula == null || !cedula.matches("\\d{10}")) {
            throw new IllegalArgumentException("La cedula debe tener 10 digitos");
        }

        this.cedula = cedula;
    }

    public void setNombre(String nombre) {

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio");
        }
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {

        if (apellido == null || apellido.isBlank()) {
            throw new IllegalArgumentException("El apellido no puede estar vacio");
        }

        this.apellido = apellido;
    }

    public void setTelefono(String telefono) {

        if (telefono == null || !telefono.matches("\\d{10}")) {
            throw new IllegalArgumentException("El telefono debe tener 10 digitos");
        }

        this.telefono = telefono;
    }

    public void setCorreo(String correo) {

        if (correo == null || correo.isBlank()) {
            throw new IllegalArgumentException("El correo no puede estar vacio");
        }

        this.correo = correo;
    }

    public void setEstado(String estado) {

        if (estado == null || estado.isBlank()) {
            throw new IllegalArgumentException("El estado no puede estar vacio");
        }

        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public String getEstado() {
        return estado;
    }
}
