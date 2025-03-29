package daw.app.controller;

import daw.app.modelo.Dao.VehiculoDao;
import daw.app.modelo.Vehiculo;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@jakarta.faces.view.ViewScoped
@Named(value = "vehiculoCtrl")
public class VehiculoCtrl implements Serializable {

    @Inject
    private VehiculoDao vehiculoDao;

    private Vehiculo vehiculo = new Vehiculo();
    private Part file;


    public void cargarVehiculo(String matricula) {
        this.vehiculo = vehiculoDao.buscar(matricula);
    }


    public String actualizarVehiculo() {

        if (file != null && file.getSize() > 0) {
            String imagenPath = guardarImagen(file);
            if (imagenPath != null) {
                vehiculo.setImagen(imagenPath);
            }
        }

        vehiculoDao.actualizar(vehiculo);
        return "gestionVehiculos?faces-redirect=true";
    }

    // Método para guardar la imagen en el servidor
    private String guardarImagen(Part file) {
        if (file != null && file.getSize() > 0) {
            try {

                String uploadPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/img");
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }


                String fileName = UUID.randomUUID().toString() + "_" + file.getSubmittedFileName();
                String filePath = uploadPath + File.separator + fileName;


                try (InputStream input = file.getInputStream()) {
                    Files.copy(input, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
                }


                return "/resources/img/" + fileName;
            } catch (IOException e) {
                e.printStackTrace();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error al subir la imagen"));
            }
        }
        return null;
    }

    public VehiculoCtrl() {
        this.vehiculo = new Vehiculo();
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }


    public void validarMatricula(FacesContext fcontext, UIComponent inputMatricula, Object value) {
        String matricula = (String) value;
        if (!matricula.matches("^[0-9]{4}-?[A-Z]{3}$")) {
            ((UIInput) inputMatricula).setValid(false);
            FacesMessage msg = new FacesMessage("Formato de matrícula no válido: 1234-ABC");
            fcontext.addMessage(inputMatricula.getClientId(fcontext), msg);
        }
    }


    public String registrar() {

        if (file == null || file.getSize() <= 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debes subir una imagen del vehículo."));
            return null;
        }


        String imagenPath = guardarImagen(file);
        if (imagenPath != null) {
            vehiculo.setImagen(imagenPath);
        }


        vehiculoDao.crear(vehiculo);


        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("gestionVehiculos.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public String borrarVehiculo(Vehiculo vehiculo) {
        boolean eliminado = vehiculoDao.eliminar(vehiculo.getMatricula());
        if (eliminado) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Vehículo eliminado correctamente"));
            return "gestionVehiculos";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar el vehículo"));
            return null;
        }
    }


    public List<Vehiculo> listarVehiculos() {
        return vehiculoDao.buscarTodos();
    }
}