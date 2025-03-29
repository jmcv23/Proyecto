package daw.app.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Reserva implements Serializable {
    private int idReserva;
    private EstadoReserva estadoReserva;
    private String dniReserva;
    private String matricula;
    private LocalDate fechaRecogida;
    private Ciudad lugarRecogida;
    private LocalTime horaRecogida;
    private LocalDate fechaDevolucion;
    private Ciudad lugarDevolucion;
    private LocalTime horaDevolucion;
    private Float precioReserva;

    public enum EstadoReserva {
        RESERVADO,
        COMPLETADO,
        CANCELADO
    }

    public enum Ciudad {
        MADRID,
        BARCELONA,
        VALENCIA,
        SEVILLA,
        BILBAO
    }

    public Reserva() {
    }

    public Reserva(int idReserva, EstadoReserva estadoReserva, String dniReserva, String matricula, LocalDate fechaRecogida, Ciudad lugarRecogida, LocalTime horaRecogida, LocalDate fechaDevolucion, Ciudad lugarDevolucion, LocalTime horaDevolucion, Float precioReserva) {
        this.idReserva = idReserva;
        this.estadoReserva = estadoReserva;
        this.dniReserva = dniReserva;
        this.matricula = matricula;
        this.fechaRecogida = fechaRecogida;
        this.lugarRecogida = lugarRecogida;
        this.horaRecogida = horaRecogida;
        this.fechaDevolucion = fechaDevolucion;
        this.lugarDevolucion = lugarDevolucion;
        this.horaDevolucion = horaDevolucion;
        this.precioReserva = precioReserva;
    }

    // Getters y Setters
    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public EstadoReserva getEstadoReserva() {
        return estadoReserva;
    }

    public void setEstadoReserva(EstadoReserva estadoReserva) {
        this.estadoReserva = estadoReserva;
    }

    public String getDniReserva() {
        return dniReserva;
    }

    public void setDniReserva(String dniReserva) {
        this.dniReserva = dniReserva;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public LocalDate getFechaRecogida() {
        return fechaRecogida;
    }

    public void setFechaRecogida(LocalDate fechaRecogida) {
        this.fechaRecogida = fechaRecogida;
    }

    public Ciudad getLugarRecogida() {
        return lugarRecogida;
    }

    public void setLugarRecogida(Ciudad lugarRecogida) {
        this.lugarRecogida = lugarRecogida;
    }

    public LocalTime getHoraRecogida() {
        return horaRecogida;
    }

    public void setHoraRecogida(LocalTime horaRecogida) {
        this.horaRecogida = horaRecogida;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public Ciudad getLugarDevolucion() {
        return lugarDevolucion;
    }

    public void setLugarDevolucion(Ciudad lugarDevolucion) {
        this.lugarDevolucion = lugarDevolucion;
    }

    public LocalTime getHoraDevolucion() {
        return horaDevolucion;
    }

    public void setHoraDevolucion(LocalTime horaDevolucion) {
        this.horaDevolucion = horaDevolucion;
    }

    public Float getPrecioReserva() {
        return precioReserva;
    }

    public void setPrecioReserva(Float precioReserva) {
        this.precioReserva = precioReserva;
    }
}