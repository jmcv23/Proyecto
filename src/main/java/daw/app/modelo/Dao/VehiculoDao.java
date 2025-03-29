package daw.app.modelo.Dao;

import daw.app.modelo.Vehiculo;
import jakarta.enterprise.context.ApplicationScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class VehiculoDao implements Serializable {


    private Map<String, Vehiculo> vehiculos;

    public VehiculoDao() {
        vehiculos = new HashMap<>();
    }

    public void crear(Vehiculo vehiculo) {
        if (vehiculo != null && vehiculo.getMatricula() != null) {
            vehiculos.put(vehiculo.getMatricula(), vehiculo);
        }
    }

    public Vehiculo buscar(String matricula) {
        return vehiculos.get(matricula);
    }


    public List<Vehiculo> buscarTodos() {
        return new ArrayList<>(vehiculos.values());
    }


    public void actualizar(Vehiculo vehiculo) {
        if (vehiculo != null && vehiculo.getMatricula() != null) {
            vehiculos.put(vehiculo.getMatricula(), vehiculo);
        }
    }


    public boolean eliminar(String matricula) {
        if (vehiculos.containsKey(matricula)) {
            vehiculos.remove(matricula);
            return true;
        }
        return false;
    }


    public int numVehiculos() {
        return vehiculos.size();
    }
}