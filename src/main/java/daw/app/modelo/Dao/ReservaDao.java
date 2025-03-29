package daw.app.modelo.Dao;

import daw.app.modelo.Reserva;
import jakarta.enterprise.context.ApplicationScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class ReservaDao implements Serializable {

    private Map<Integer, Reserva> reservas;
    private int ultimoIdReserva;

    public ReservaDao() {
        reservas = new HashMap<>();
        ultimoIdReserva = 1;
    }

    // Método para crear o registrar una nueva reserva
    public void crear(Reserva reserva) {
        if (reserva != null) {
            reserva.setIdReserva(ultimoIdReserva);
            reservas.put(ultimoIdReserva, reserva);
            ultimoIdReserva++;
        }
    }

    // Método para buscar una reserva por idReserva
    public Reserva buscar(int idReserva) {
        return reservas.get(idReserva);
    }

    // Método para obtener la lista de todas las reservas
    public List<Reserva> buscarTodos() {
        return new ArrayList<>(reservas.values());
    }

    // Método para actualizar los datos de una reserva
    public void actualizar(Reserva reserva) {
        if (reserva != null && reserva.getIdReserva() != 0) {
            reservas.put(reserva.getIdReserva(), reserva);
        }
    }

    // Método para eliminar una reserva por idReserva
    public boolean eliminar(int idReserva) {
        if (reservas.containsKey(idReserva)) {
            reservas.remove(idReserva);
            return true;
        }
        return false;
    }

    // Método para conocer el número total de reservas registradas
    public int numReservas() {
        return reservas.size();
    }
}
