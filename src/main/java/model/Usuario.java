package model;

public class Usuario {
    private int id;
    private String usuario;
    private String contrasena;
    private String rol;

    public Usuario(int id, String usuario, String contrasena, String rol) {
        setId(id);
        setUsuario(usuario);
        setContrasena(contrasena);
        setRol(rol);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {

        if (usuario == null || usuario.isBlank()) {
            throw new IllegalArgumentException("El usuario no puede estar vacio");
        }
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {

        if (contrasena == null || contrasena.isBlank()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacia");
        }
        this.contrasena = contrasena;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {

        if (rol == null || rol.isBlank()) {
            throw new IllegalArgumentException("El rol no puede estar vacio");
        }
        this.rol = rol;
    }
}
