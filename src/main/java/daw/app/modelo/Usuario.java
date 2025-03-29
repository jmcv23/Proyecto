package daw.app.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Usuario implements Serializable {
    private String nombre;
    private LocalDate fechaNacimiento;  // Cambiar Date por LocalDate
    private String dni;
    private String email;
    private String password;
    private String direccion;
    private String foto;

    public Usuario() {
    }

    public Usuario(String nombre, LocalDate fechaNacimiento, String dni, String email, String password, String direccion, String foto) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.dni = dni;
        this.email = email;
        this.password = password;
        this.direccion = direccion;
        this.foto = foto;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    // Usando LocalDate en vez de Date
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    // También puedes tener un getter que retorne la fecha en formato String
    public String getFechaNacimientoString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return fechaNacimiento != null ? fechaNacimiento.format(formatter) : null;
    }

    public void setFechaNacimientoString(String fecha) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.fechaNacimiento = LocalDate.parse(fecha, formatter);
    }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }

}
