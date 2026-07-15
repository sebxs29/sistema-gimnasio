package model;

public abstract class Persona {

    private int id;
    private int usuarioId;
    private String cedula;
    private String nombre;
    private String apellido;
    private String telefono;

    public Persona() {
    }

    // CONSTRUCTOR PARA LEER
    public Persona(int id, int usuarioId, String cedula, String nombre, String apellido, String telefono) {
        setId(id);
        setUsuarioId(usuarioId);
        setCedula(cedula);
        setNombre(nombre);
        setApellido(apellido);
        setTelefono(telefono);
    }

    // CONSTRUCTOR PARA GUARDAR
    public Persona(String cedula, String nombre, String apellido, String telefono) {
        setCedula(cedula);
        setNombre(nombre);
        setApellido(apellido);
        setTelefono(telefono);
    }


    public void setId(int id){
        if (id < 0) {
            throw new IllegalArgumentException("El id no es válido");
        }

        this.id = id;

    }

    public void setUsuarioId(int usuarioId){

        if (usuarioId <= 0) {
            throw new IllegalArgumentException("El ID de usuario no es valido");
        }
        this.usuarioId = usuarioId;
    }

    public void setCedula(String cedula){

        if (cedula == null || !cedula.matches("\\d{10}")) {
            throw new IllegalArgumentException("El cedula debe tener 10 digitos");
        }
        this.cedula = cedula;

    }

    public void setNombre(String nombre){

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio");
        }

        if (nombre.trim().length() > 50) {
            throw new IllegalArgumentException("El nombre no puede superar los 50 caracteres");
        }

        this.nombre = nombre;

    }

    public void setApellido(String apellido){

        if(apellido == null || apellido.isBlank()){
            throw new IllegalArgumentException("El apellido no puede estar vacio");
        }

        if (apellido.trim().length() > 50){
            throw new IllegalArgumentException("El apellido no puede superar los 50 caracteres");
        }
        this.apellido = apellido;

    }

    public void setTelefono(String telefono){

        if (telefono == null || !telefono.matches("\\d{10}")) {
            throw new IllegalArgumentException("El telefono debe tener 10 digitos");
        }

        this.telefono = telefono;

    }

    public int getId() {
        return id;
    }

    public String getCedula() {
        return cedula;
    }

    public int getUsuarioId() {
        return usuarioId;
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
}


