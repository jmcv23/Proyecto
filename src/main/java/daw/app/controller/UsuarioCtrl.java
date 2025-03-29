package daw.app.controller;

import daw.app.modelo.Dao.UsuarioDao;
import daw.app.modelo.Usuario;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
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
@Named(value = "usuCtrl")
public class UsuarioCtrl implements Serializable{
    @Inject
    private UsuarioDao usuarioDao;
    private Part file;

    private Usuario usuario = new Usuario();

    public UsuarioCtrl() {
        this.usuario = new Usuario();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public void validarDNI(FacesContext fcontext, UIComponent inputDni, Object value) {
        String dni = (String) value;
        if (!dni.matches("\\d{8}[A-Z]")) {
            ((UIInput) inputDni).setValid(false);
            FacesMessage msg = new FacesMessage("Formato DNI no válido: (Ejemplo: 12345678A)");
            fcontext.addMessage(inputDni.getClientId(fcontext), msg);
        }
    }

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

    public String registrar() {
        String imagenPath = guardarImagen(file);
        if (imagenPath != null) {
            usuario.setFoto(imagenPath);
        }

        usuarioDao.crear(usuario);

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("gestionUsuarios.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String actualizar() {
        String imagenPath = guardarImagen(file);
        if (imagenPath != null) {
            usuario.setFoto(imagenPath);
        }

        usuarioDao.actualizar(usuario);

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("gestionUsuarios.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String borrarUsuario(Usuario usuario) {
        boolean eliminado = usuarioDao.eliminar(usuario.getDni());
        if (eliminado) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuario eliminado correctamente"));
            return "gestionUsuarios";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar el usuario"));
            return null;
        }
    }

    public void cargarUsuario() {
        String dni = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("dni");
        if (dni != null && !dni.isEmpty()) {
            usuario = usuarioDao.buscar(dni);
        }
    }

    public List<Usuario> listarUsuarios() {
        return usuarioDao.buscarTodos();
    }


}
