package daw.app.controller;
import  daw.app.modelo.Dao.VehiculoDao;
import daw.app.modelo.Dao.ReservaDao;
import  daw.app.modelo.Dao.UsuarioDao;
import daw.app.modelo.Reserva;
import daw.app.modelo.Usuario;
import daw.app.modelo.Vehiculo;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.model.SelectItem;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@jakarta.faces.view.ViewScoped
@Named(value = "resCtrl")
public class ReservaCtrl implements Serializable {

    @Inject
    private ReservaDao reservaDao;

    @Inject
    private VehiculoDao vehiculoDao;

    @Inject
    private UsuarioDao usuarioDao;

    private Reserva reserva = new Reserva();
    private Vehiculo vehiculo = new Vehiculo();
    private Usuario usuario = new Usuario();

    public ReservaCtrl() {
        this.reserva = new Reserva();
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    // Método para obtener el vehículo asociado a una matrícula
    public Vehiculo obtenerVehiculoPorMatricula(String matricula) {
        return vehiculoDao.buscar(matricula);
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

    public ReservaDao getReservaDao() {
        return reservaDao;
    }

    // Método para validar el formato del ID de la reserva.
    public void validarIdReserva(FacesContext fcontext, UIComponent inputId, Object value) {
        int idReserva = (int) value;
        if (idReserva <= 0) {
            ((UIInput) inputId).setValid(false);
            FacesMessage msg = new FacesMessage("ID de reserva no válido: debe ser un número positivo");
            fcontext.addMessage(inputId.getClientId(fcontext), msg);
        }
    }

    public List<SelectItem> getMatriculasDisponibles() {
        List<SelectItem> opciones = new ArrayList<>();
        for (String matricula : vehiculoDao.buscarTodos()
                .stream()
                .map(Vehiculo::getMatricula)
                .collect(Collectors.toList())) {
            opciones.add(new SelectItem(matricula, matricula));
        }
        return opciones;
    }


    // Método para registrar una nueva reserva.
    public String registrar() {
        validarFechasYHoras(FacesContext.getCurrentInstance(),null);

        Float precioReserva = calcularPrecioReserva();
        if(precioReserva <= 0){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo calcular el precio de la reserva."));
            return null;
        }

        reserva.setPrecioReserva(precioReserva);

        reserva.setEstadoReserva(Reserva.EstadoReserva.RESERVADO);

        reservaDao.crear(reserva);

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("gestionReservas.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Método para eliminar una reserva.
    public String borrarReserva(Reserva reserva) {
        boolean eliminado = reservaDao.eliminar(reserva.getIdReserva());
        if (eliminado) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Reserva eliminada correctamente"));
            return "gestionReservas";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar la reserva"));
            return null;
        }
    }

    // Método para listar todas las reservas.
    public List<Reserva> listarReservas() {
        return reservaDao.buscarTodos();
    }

    // Método para validar un DNI
    public void validarDNI(FacesContext fcontext, UIComponent inputDni, Object value) {
        String dni = (String) value;

        if (dni == null || dni.trim().isEmpty()) {
            ((UIInput) inputDni).setValid(false);
            FacesMessage msg = new FacesMessage("El DNI es obligatorio");
            fcontext.addMessage(inputDni.getClientId(fcontext), msg);
            return;
        }

        if (!dni.matches("\\d{7,8}-?[a-zA-Z]")) {
            ((UIInput) inputDni).setValid(false);
            FacesMessage msg = new FacesMessage("Formato DNI no válido. Debe ser 12345678-A o 12345678A");
            fcontext.addMessage(inputDni.getClientId(fcontext), msg);
        }
    }

    // Método para validar una matrícula
    public void validarMatricula(FacesContext fcontext, UIComponent inputMatricula, Object value) {
        String matricula = (String) value;

        if (matricula == null || matricula.trim().isEmpty()) {
            ((UIInput) inputMatricula).setValid(false);
            FacesMessage msg = new FacesMessage("La matrícula es obligatoria");
            fcontext.addMessage(inputMatricula.getClientId(fcontext), msg);
            return;
        }

        String regex = "^[0-9]{4}[BCDFGHJKLMNPRSTVWXYZ]{3}$";

        if (!matricula.toUpperCase().matches(regex)) {
            ((UIInput) inputMatricula).setValid(false);
            FacesMessage msg = new FacesMessage("Formato de matrícula no válido. Debe ser 1234ABC (sin vocales ni Ñ, Q)");
            fcontext.addMessage(inputMatricula.getClientId(fcontext), msg);
        }
    }

    // Método para actualizar una reserva
    public String actualizarReserva() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        validarFechasYHoras(facesContext, null);

        if (facesContext.getMessageList().isEmpty()) {
            reservaDao.actualizar(reserva);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Reserva actualizada correctamente"));
            return "gestionReservas?faces-redirect=true";
        } else {
            return null;
        }
    }

    // Método para cargar una reserva
    public void cargarReserva() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        if (reserva.getIdReserva() > 0) {
            Reserva reservaEncontrada = reservaDao.buscar(reserva.getIdReserva());

            if (reservaEncontrada != null) {
                this.reserva = reservaEncontrada;

                this.vehiculo = vehiculoDao.buscar(reserva.getMatricula());

                if (this.vehiculo == null) {
                    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "No se encontró el vehículo asociado a la matrícula: " + reserva.getMatricula()));
                }

                this.usuario = usuarioDao.buscar(reserva.getDniReserva());

                if (this.usuario == null) {
                    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "No se encontró el usuario asociado al DNI: " + reserva.getDniReserva()));
                }
            } else {
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se encontró la reserva con ID: " + reserva.getIdReserva()));
            }
        } else {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "ID de reserva no válido"));
        }
    }

    // Método para validar las fechas y horas de recogida y devolución
    public void validarFechasYHoras(FacesContext fcontext, UIComponent component) {
        LocalDate fechaRecogida = reserva.getFechaRecogida();
        LocalTime horaRecogida = reserva.getHoraRecogida();
        LocalDate fechaDevolucion = reserva.getFechaDevolucion();
        LocalTime horaDevolucion = reserva.getHoraDevolucion();

        if (fechaRecogida == null || horaRecogida == null || fechaDevolucion == null || horaDevolucion == null) {
            FacesMessage msg = new FacesMessage("Todos los campos de fecha y hora son obligatorios");
            fcontext.addMessage("contact-form:form_fecha_recogida", msg);
            return;
        }

        LocalDate fechaActual = LocalDate.now();
        LocalTime horaActual = LocalTime.now();

        if (fechaRecogida.isBefore(fechaActual) || (fechaRecogida.isEqual(fechaActual) && horaRecogida.isBefore(horaActual))) {
            FacesMessage msg = new FacesMessage("La fecha y hora de recogida no pueden ser anteriores a la actual");
            fcontext.addMessage("contact-form:form_fecha_recogida", msg);
            return;
        }

        if (fechaRecogida.isAfter(fechaDevolucion)) {
            FacesMessage msg = new FacesMessage("La fecha de recogida debe ser anterior a la fecha de devolución");
            fcontext.addMessage("contact-form:form_fecha_devolucion", msg);
            return;
        }

        if (fechaRecogida.isEqual(fechaDevolucion)) {
            Duration duracion = Duration.between(horaRecogida, horaDevolucion);
            if (duracion.toHours() < 2) {
                FacesMessage msg = new FacesMessage("La hora de recogida debe ser al menos 2 horas antes de la hora de devolución");
                fcontext.addMessage("contact-form:form_hora_recogida", msg);
                return;
            }
        }
    }

    // Método para obtener las opciones de lugares de recogida
    public List<SelectItem> getLugaresRecogida() {
        return getOpcionesCiudad();
    }

    // Método para obtener las opciones de lugares de devolución
    public List<SelectItem> getLugaresDevolucion() {
        return getOpcionesCiudad();
    }

    // Método común para obtener las opciones del enum Ciudad
    private List<SelectItem> getOpcionesCiudad() {
        List<SelectItem> opciones = new ArrayList<>();
        for (Reserva.Ciudad ciudad : Reserva.Ciudad.values()) {
            opciones.add(new SelectItem(ciudad, ciudad.name()));
        }
        return opciones;
    }

    public List<Reserva> listarReservasPorEstado(String estado) {
        List<Reserva> todasReservas = reservaDao.buscarTodos();
        List<Reserva> reservasFiltradas = new ArrayList<>();

        for (Reserva reserva : todasReservas) {
            if (reserva.getEstadoReserva().name().equals(estado)) {
                reservasFiltradas.add(reserva);
            }
        }

        return reservasFiltradas;
    }

    public String cambiarEstadoReserva(int idReserva, String nuevoEstado) {
        try {
            Reserva reserva = reservaDao.buscar(idReserva);
            if (reserva != null) {
                try {
                    reserva.setEstadoReserva(Reserva.EstadoReserva.valueOf(nuevoEstado)); // Cambiar el estado
                } catch (IllegalArgumentException e) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Estado no válido"));
                    return null; // No redirigir
                }
                reservaDao.actualizar(reserva); // Actualizar en la base de datos
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Reserva cancelada correctamente"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se encontró la reserva"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ocurrió un error al cancelar la reserva"));
            return null; // No redirigir
        }
        return "historialReservas?faces-redirect=true"; // Recargar la página
    }

    // Método para calcular el precio de la reserva
    private Float calcularPrecioReserva() {
        if (reserva.getFechaRecogida() == null || reserva.getFechaDevolucion() == null ||
                reserva.getHoraRecogida() == null || reserva.getHoraDevolucion() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Las fechas y horas de recogida y devolución son obligatorias."));
            return 0f;
        }

        long horas = Duration.between(
                LocalDateTime.of(reserva.getFechaRecogida(), reserva.getHoraRecogida()),
                LocalDateTime.of(reserva.getFechaDevolucion(), reserva.getHoraDevolucion())
        ).toHours();

        if (horas <= 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La duración del alquiler debe ser de al menos 1 hora."));
            return 0f;
        }

        Vehiculo vehiculo = vehiculoDao.buscar(reserva.getMatricula());

        if (vehiculo == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se encontró el vehículo asociado a la matrícula: " + reserva.getMatricula()));
            return 0f;
        }

        double precioPorHora = vehiculo.getPrecio() / 24.0;

        return (float) (horas * precioPorHora);
    }

}