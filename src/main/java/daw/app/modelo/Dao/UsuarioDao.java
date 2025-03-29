package daw.app.modelo.Dao;
import daw.app.modelo.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class UsuarioDao implements Serializable {

    // Usamos el DNI como clave
    private Map<String, Usuario> usuarios;

    public UsuarioDao() {
        usuarios = new HashMap<>();
    }

    // Método para crear o registrar un nuevo usuario
    public void crear(Usuario usuario) {
        if (usuario != null && usuario.getDni() != null) {
            usuarios.put(usuario.getDni(), usuario);
        }
    }

    // Método para buscar un usuario por DNI
    public Usuario buscar(String dni) {
        return usuarios.get(dni);
    }

    // Método para obtener la lista de todos los usuarios
    public List<Usuario> buscarTodos() {
        return new ArrayList<>(usuarios.values());
    }

    // Método para actualizar los datos de un usuario
    public void actualizar(Usuario usuario) {
        if (usuario != null && usuario.getDni() != null) {
            usuarios.put(usuario.getDni(), usuario);
        }
    }

    // Método para eliminar un usuario por DNI
    public boolean eliminar(String dni) {
        if (usuarios.containsKey(dni)) {
            usuarios.remove(dni);
            return true;
        }
        return false;
    }


    // Método para conocer el número total de usuarios registrados
    public int numUsuarios() {
        return usuarios.size();
    }
}
