package model;

public class Entrenador {

    private int id;
    private int usuarioId;
    private String cedula;
    private String nombre;
    private String apellido;
    private String especialidad;
    private String telefono;


    public Entrenador() {
    }


    public Entrenador(int id, int usuarioId, String cedula, String nombre, String apellido, String especialidad, String telefono) {
        this.id = id;
        setUsuarioId(usuarioId);
        setCedula(cedula);
        setNombre(nombre);
        setApellido(apellido);
        setEspecialidad(especialidad);
        setTelefono(telefono);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("El ID no puede ser negativo.");
        }
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        if (usuarioId <= 0) {
            throw new IllegalArgumentException("El ID del usuario debe ser mayor a 0.");
        }
        this.usuarioId = usuarioId;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        if (cedula == null || !cedula.matches("\\d{10}")) {
            throw new IllegalArgumentException("La cédula debe tener exactamente 10 dígitos.");
        }
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido es obligatorio.");
        }
        this.apellido = apellido;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        if (especialidad == null ||
                (!especialidad.equals("Musculacion") &&
                        !especialidad.equals("Calistenia") &&
                        !especialidad.equals("Powerlifting"))) {

            throw new IllegalArgumentException(
                    "La especialidad debe ser Musculacion, Calistenia o Powerlifting.");
        }
        this.especialidad = especialidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        if (telefono == null || !telefono.matches("\\d{10}")) {
            throw new IllegalArgumentException("El teléfono debe tener exactamente 10 dígitos.");
        }
        this.telefono = telefono;
    }

}