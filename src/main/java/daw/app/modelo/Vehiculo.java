package daw.app.modelo;

import java.io.Serializable;

public class Vehiculo implements Serializable {
    private String matricula;
    private String marca;
    private String modelo;
    private String tipo;
    private String transmision;
    private String combustible;
    private int plazas;
    private String ciudad;
    private double precio;
    private String imagen;

    public Vehiculo() {
    }

    public Vehiculo(String matricula, String marca, String modelo, String tipo, String transmision,
                    String combustible, int plazas, String ciudad, double precio, String imagen) {
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.tipo = tipo;
        this.transmision = transmision;
        this.combustible = combustible;
        this.plazas = plazas;
        this.ciudad = ciudad;
        this.precio = precio;
        this.imagen = imagen;
    }


    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getTransmision() { return transmision; }
    public void setTransmision(String transmision) { this.transmision = transmision; }

    public String getCombustible() { return combustible; }
    public void setCombustible(String combustible) { this.combustible = combustible; }

    public int getPlazas() { return plazas; }
    public void setPlazas(int plazas) { this.plazas = plazas; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }
}
