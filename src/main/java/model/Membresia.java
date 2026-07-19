package model;

import java.time.LocalDate;

public class Membresia {

    private int id;
    private int clienteId;
    private int planId;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado;

    private String clienteNombre;
    private String planNombre;


    // CONSTRUCTOR PARA LEER
    public Membresia(int id, int clienteId, int planId, String clienteNombre, String planNombre, LocalDate fechaInicio, LocalDate fechaFin, String estado) {
        setId(id);
        setClienteId(clienteId);
        setPlanId(planId);
        setClienteNombre(clienteNombre);
        setPlanNombre(planNombre);
        setFechaInicio(fechaInicio);
        setFechaFin(fechaFin);
        setEstado(estado);
    }

    // CONSTRUCTOR PARA GUARDAR
    public Membresia(int clienteId, int planId, LocalDate fechaInicio, LocalDate fechaFin, String estado) {
        setClienteId(clienteId);
        setPlanId(planId);
        setFechaInicio(fechaInicio);
        setFechaFin(fechaFin);
        setEstado(estado);
    }

    // CONSTRUCTOR PAR ACTUALIZAR
    public Membresia(int id, int clienteId, int planId, LocalDate fechaInicio, LocalDate fechaFin, String estado) {
        setId(id);
        setClienteId(clienteId);
        setPlanId(planId);
        setFechaInicio(fechaInicio);
        setFechaFin(fechaFin);
        setEstado(estado);
    }

    public void setId(int id) {

        if (id <= 0) {
            throw new IllegalArgumentException("El ID de la membresia no es valido");
        }
        this.id = id;
    }

    public void setClienteId(int clienteId) {

        if (clienteId <= 0) {
            throw new IllegalArgumentException("Debe seleccionar un cliente valido");
        }
        this.clienteId = clienteId;
    }

    public void setPlanId(int planId) {

        if (planId <= 0) {
            throw new IllegalArgumentException("Debe seleccionar un plan valido");
        }

        this.planId = planId;

    }

    public void setFechaInicio(LocalDate fechaInicio) {

        if (fechaInicio == null) {
            throw new IllegalArgumentException("La fecha de inicio no puede estar vacia");
        }

        this.fechaInicio = fechaInicio;

    }

    public void setFechaFin(LocalDate fechaFin) {

        if (fechaFin == null) {
            throw new IllegalArgumentException("La fecha de fin no puede estar vacia");
        }

        if (fechaInicio != null && fechaFin.isBefore(fechaInicio)) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio");
        }

        this.fechaFin = fechaFin;

    }

    public void setEstado(String estado) {

        if (estado == null || estado.isBlank()) {
            throw new IllegalArgumentException("El estado no puede estar vacio");
        }

        if (!estado.equals("ACTIVO") && !estado.equals("INACTIVO")) {
            throw new IllegalArgumentException("El estado debe ser ACTIVO O INACTIVO");
        }

        this.estado = estado;

    }

    public void setClienteNombre(String clienteNombre) {

        if (clienteNombre == null || clienteNombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del cliente no puede estar vacio");
        }
        this.clienteNombre = clienteNombre;

    }

    public void setPlanNombre(String planNombre) {

        if (planNombre == null || planNombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del plan no puede estar vacio");
        }
        this.planNombre = planNombre;

    }

    public int getId() {
        return id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public int getPlanId() {
        return planId;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public String getPlanNombre() {
        return planNombre;
    }
}
