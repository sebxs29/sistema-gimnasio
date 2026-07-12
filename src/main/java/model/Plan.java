package model;

public class Plan {
    private int id;
    private String nombre;
    private double precio;
    private int duracionMeses;


    // CONSTRUCTOR PARA LEER
    public Plan(int id, String nombre, double precio, int duracionMeses) {
        setId(id);
        setNombre(nombre);
        setPrecio(precio);
        setDuracionMeses(duracionMeses);
    }

    // CONSTRUCTOR PARA INSERTAR
    public Plan(String nombre, double precio, int duracionMeses) {
        setNombre(nombre);
        setPrecio(precio);
        setDuracionMeses(duracionMeses);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio");
        }
        this.nombre = nombre;
    }

    public void setPrecio(double precio) {

        if (precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor que 0");
        }
        this.precio = precio;
    }

    public void setDuracionMeses(int duracionMeses) {

        if (duracionMeses <= 0) {
            throw  new IllegalArgumentException("La duracion en meses debe ser mayor que 0");
        }
        this.duracionMeses = duracionMeses;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getDuracionMeses() {
        return duracionMeses;
    }
}
