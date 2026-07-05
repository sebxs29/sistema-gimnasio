package model;

public class Entrenador {

    private int id;
    private int usuarioId;
    private String cedula;
    private String nombre;
    private String apellido;
    private String especialidad;
    private String telefono;

    // Datos de la tabla usuarios
    private String usuario;
    private String contrasena;

    public Entrenador() {
    }

    public Entrenador(int id, int usuarioId, String cedula, String nombre, String apellido, String especialidad, String telefono, String usuario, String contrasena) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
        this.telefono = telefono;
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    public Entrenador(int usuarioId, String cedula, String nombre, String apellido, String especialidad, String telefono, String usuario, String contrasena) {
        this.usuarioId = usuarioId;
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
        this.telefono = telefono;
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

}