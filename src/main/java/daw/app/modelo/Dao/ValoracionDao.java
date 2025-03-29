package daw.app.modelo.Dao;

import daw.app.modelo.Valoracion;
import jakarta.enterprise.context.ApplicationScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class ValoracionDao implements Serializable {

    private Map<Integer, Valoracion> valoraciones;
    private int contadorId;

    public ValoracionDao() {
        valoraciones = new HashMap<>();
        contadorId = 1;
    }


    public void crear(Valoracion valoracion) {
        if (valoracion != null) {
            valoracion.setId(contadorId++);
            valoraciones.put(valoracion.getId(), valoracion);
        }
    }


    public Valoracion buscar(int id) {
        return valoraciones.get(id);
    }


    public List<Valoracion> buscarTodos() {
        return new ArrayList<>(valoraciones.values());
    }


    public void actualizar(Valoracion valoracion) {
        if (valoracion != null && valoraciones.containsKey(valoracion.getId())) {
            valoraciones.put(valoracion.getId(), valoracion);
        }
    }


    public boolean eliminar(int id) {
        if (valoraciones.containsKey(id)) {
            valoraciones.remove(id);
            return true;
        }
        return false;
    }


    public int numValoraciones() {
        return valoraciones.size();
    }
}
