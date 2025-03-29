package daw.app.modelo;

import java.io.Serializable;

public class Valoracion implements Serializable {
        private static int contadorId = 1;

        private int id;
        private String matricula;
        private String dniUsuario;
        private int estrellas;
        private String descripcion;


        public Valoracion() {
            this.id = contadorId++;
        }

        public Valoracion(String matricula, String dniUsuario, int estrellas, String descripcion) {
            this();
            this.matricula = matricula;
            this.dniUsuario = dniUsuario;
            this.estrellas = estrellas;
            this.descripcion = descripcion;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMatricula() {
            return matricula;
        }

        public void setMatricula(String matricula) {
            this.matricula = matricula;
        }

        public String getDniUsuario() {
            return dniUsuario;
        }

        public void setDniUsuario(String dniUsuario) {
            this.dniUsuario = dniUsuario;
        }

        public int getEstrellas() {
            return estrellas;
        }

        public void setEstrellas(int estrellas) {
            this.estrellas = estrellas;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }
}
