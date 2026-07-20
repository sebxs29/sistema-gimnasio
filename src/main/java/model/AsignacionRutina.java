package model;

import java.time.LocalDate;

public class AsignacionRutina {

    private int id;
    private int clienteId;
    private int rutinaId;
    private int entrenadorId;
    private LocalDate fechaAsignacion;

    // CAMPOS EXTRA SOLO PARA MOSTRAR EN LA TABLA (NO SE INSERTAN, SE LLENAN CON UN JOIN)
    private String clienteNombre;
    private String rutinaNombre;
    private String entrenadorNombre;

    // CAMPOS EXTRA SOLO PARA MOSTRAR EN "MI RUTINA" (NO SE INSERTAN, SE LLENAN CON UN JOIN)
    private String rutinaDescripcion;
    private String rutinaNivel;

    // CONSTRUCTOR PARA INSERTAR
    public AsignacionRutina(int clienteId, int rutinaId, int entrenadorId, LocalDate fechaAsignacion) {
        setClienteId(clienteId);
        setRutinaId(rutinaId);
        setEntrenadorId(entrenadorId);
        setFechaAsignacion(fechaAsignacion);
    }

    // CONSTRUCTOR PARA LEER (SIN NOMBRES)
    public AsignacionRutina(int id, int clienteId, int rutinaId, int entrenadorId, LocalDate fechaAsignacion) {
        setId(id);
        setClienteId(clienteId);
        setRutinaId(rutinaId);
        setEntrenadorId(entrenadorId);
        setFechaAsignacion(fechaAsignacion);
    }

    // CONSTRUCTOR PARA LEER CON JOIN (PARA MOSTRAR EN LA TABLA)
    public AsignacionRutina(int id, int clienteId, String clienteNombre,
                            int rutinaId, String rutinaNombre,
                            int entrenadorId, String entrenadorNombre,
                            LocalDate fechaAsignacion) {
        setId(id);
        setClienteId(clienteId);
        setRutinaId(rutinaId);
        setEntrenadorId(entrenadorId);
        setFechaAsignacion(fechaAsignacion);
        this.clienteNombre = clienteNombre;
        this.rutinaNombre = rutinaNombre;
        this.entrenadorNombre = entrenadorNombre;
    }

    // CONSTRUCTOR PARA LEER CON JOIN COMPLETO (VISTA "MI RUTINA" DEL CLIENTE)
    public AsignacionRutina(int id, int rutinaId, String rutinaNombre, String rutinaDescripcion,
                            String rutinaNivel, String entrenadorNombre, LocalDate fechaAsignacion) {
        setId(id);
        this.rutinaId = rutinaId;
        this.rutinaNombre = rutinaNombre;
        this.rutinaDescripcion = rutinaDescripcion;
        this.rutinaNivel = rutinaNivel;
        this.entrenadorNombre = entrenadorNombre;
        setFechaAsignacion(fechaAsignacion);
    }

    public void setId(int id) {

        if (id < 0) {
            throw new IllegalArgumentException("El id no es valido");
        }
        this.id = id;
    }

    public void setClienteId(int clienteId) {

        if (clienteId <= 0) {
            throw new IllegalArgumentException("Debe seleccionar un cliente valido");
        }
        this.clienteId = clienteId;
    }

    public void setRutinaId(int rutinaId) {

        if (rutinaId <= 0) {
            throw new IllegalArgumentException("Debe seleccionar una rutina valida");
        }
        this.rutinaId = rutinaId;
    }

    public void setEntrenadorId(int entrenadorId) {

        if (entrenadorId <= 0) {
            throw new IllegalArgumentException("Debe seleccionar un entrenador valido");
        }
        this.entrenadorId = entrenadorId;
    }

    public void setFechaAsignacion(LocalDate fechaAsignacion) {

        if (fechaAsignacion == null) {
            throw new IllegalArgumentException("La fecha de asignacion es obligatoria");
        }
        this.fechaAsignacion = fechaAsignacion;
    }

    public int getId() {
        return id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public int getRutinaId() {
        return rutinaId;
    }

    public int getEntrenadorId() {
        return entrenadorId;
    }

    public LocalDate getFechaAsignacion() {
        return fechaAsignacion;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public String getRutinaNombre() {
        return rutinaNombre;
    }

    public String getEntrenadorNombre() {
        return entrenadorNombre;
    }

    public String getRutinaDescripcion() {
        return rutinaDescripcion;
    }

    public String getRutinaNivel() {
        return rutinaNivel;
    }
}