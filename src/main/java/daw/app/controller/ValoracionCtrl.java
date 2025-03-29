package daw.app.controller;

import daw.app.modelo.Dao.UsuarioDao;
import daw.app.modelo.Dao.ValoracionDao;
import daw.app.modelo.Dao.VehiculoDao;
import daw.app.modelo.Reserva;
import daw.app.modelo.Usuario;
import daw.app.modelo.Valoracion;
import daw.app.modelo.Vehiculo;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@ViewScoped
@Named(value = "valoracionCtrl")
public class ValoracionCtrl implements Serializable {

    @Inject
    private ValoracionDao valoracionDao;

    @Inject
    private VehiculoDao vehiculoDao;

    @Inject
    private UsuarioDao usuarioDao;

    private Vehiculo vehiculo = new Vehiculo();
    private Usuario usuario = new Usuario();

    private Valoracion valoracion = new Valoracion();

    public ValoracionCtrl() {
        this.valoracion = new Valoracion();
    }

    public Valoracion getValoracion() {
        return valoracion;
    }

    public void setValoracion(Valoracion valoracion) {
        this.valoracion = valoracion;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void validarDNI(FacesContext fcontext, UIComponent inputDni, Object value) {
        String dni = (String) value;
        if (!dni.matches("\\d{8}[A-Z]")) {
            ((UIInput) inputDni).setValid(false);
            FacesMessage msg = new FacesMessage("Formato DNI no válido: (Ejemplo: 12345678A)");
            fcontext.addMessage(inputDni.getClientId(fcontext), msg);
        }
    }

    public void validarMatricula(FacesContext fcontext, UIComponent inputMatricula, Object value) {
        String matricula = (String) value;
        if (!matricula.matches("^[0-9]{4}-?[A-Z]{3}$")) {
            ((UIInput) inputMatricula).setValid(false);
            FacesMessage msg = new FacesMessage("Formato de matrícula no válido: 1234-ABC");
            fcontext.addMessage(inputMatricula.getClientId(fcontext), msg);
        }
    }

    /**
     * Crea una nueva valoración.
     *
     * @return Redirige a la página de listado de valoraciones.
     */
    public String crearValoracion() {
        valoracionDao.crear(valoracion);

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Valoración creada correctamente"));

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("misalquileres.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Actualiza una valoración existente.
     *
     * @return Redirige a la página de listado de valoraciones.
     */
    public String actualizarValoracion() {
        valoracionDao.actualizar(valoracion);


        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Valoración actualizada correctamente"));


        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("gestionValoraciones.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public String eliminarValoracion(Valoracion valoracion) {
        boolean eliminado = valoracionDao.eliminar(valoracion.getId());

        if (eliminado) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Valoración eliminada correctamente"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar la valoración"));
        }

        return "listadoValoraciones";
    }


    public void cargarValoracion() {
        String idStr = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        if (idStr != null && !idStr.isEmpty()) {
            int id = Integer.parseInt(idStr);
            valoracion = valoracionDao.buscar(id);
        }
    }

    public void cargarValoracionCompleta() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String idStr = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        int id = Integer.parseInt(idStr);
        if (valoracion.getId() > 0) {
            Valoracion valoracionEncontrada = valoracionDao.buscar(id);

            if (valoracionEncontrada != null) {
                this.valoracion = valoracionEncontrada;

                this.vehiculo = vehiculoDao.buscar(valoracion.getMatricula());

                if (this.vehiculo == null) {
                    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "No se encontró el vehículo asociado a la matrícula: " + valoracion.getMatricula()));
                }

                this.usuario = usuarioDao.buscar(valoracion.getDniUsuario());

                if (this.usuario == null) {
                    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "No se encontró el usuario asociado al DNI: " + valoracion.getDniUsuario()));
                }
            } else {
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se encontró la reserva con ID: " + valoracion.getId()));
            }
        } else {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "ID de reserva no válido"));
        }
    }


    public List<Valoracion> listarValoraciones() {
        return valoracionDao.buscarTodos();
    }
}
