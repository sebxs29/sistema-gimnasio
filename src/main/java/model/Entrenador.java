package model;

public class Entrenador extends Persona {

    private String especialidad;


    public Entrenador() {
        super();
    }


    public Entrenador(int id, int usuarioId, String cedula, String nombre, String apellido, String especialidad, String telefono) {

        super(id, usuarioId,cedula, nombre, apellido, telefono);

        setEspecialidad(especialidad);
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


}