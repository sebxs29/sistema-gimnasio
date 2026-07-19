package model;

public class Rutina {

    private int id;
    private String nombre;
    private String descripcion;
    private String nivel;

    // CONSTRUCTOR PARA LEER
    public Rutina(int id, String nombre, String descripcion, String nivel) {
        setId(id);
        setNombre(nombre);
        setDescripcion(descripcion);
        setNivel(nivel);
    }

    // CONSTRUCTOR PARA INSERTAR
    public Rutina(String nombre, String descripcion, String nivel) {
        setNombre(nombre);
        setDescripcion(descripcion);
        setNivel(nivel);
    }

    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("El id no es valido");
        }
        this.id = id;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio");
        }
        if (nombre.trim().length() > 50) {
            throw new IllegalArgumentException("El nombre no puede superar los 50 caracteres");
        }
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("La descripcion no puede estar vacia");
        }
        this.descripcion = descripcion;
    }

    public void setNivel(String nivel) {
        if (nivel == null ||
                (!nivel.equals("PRINCIPIANTE") &&
                        !nivel.equals("INTERMEDIO") &&
                        !nivel.equals("AVANZADO"))) {

            throw new IllegalArgumentException(
                    "El nivel debe ser PRINCIPIANTE, INTERMEDIO o AVANZADO");
        }
        this.nivel = nivel;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getNivel() {
        return nivel;
    }
}