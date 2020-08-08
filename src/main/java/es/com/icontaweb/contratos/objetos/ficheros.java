/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.com.icontaweb.contratos.objetos;

/**
 *
 * @author jcperan
 */
public class ficheros {
        
    private String nombre;
    private Double tamano;
    private String fecha;
    private String url;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getTamano() {
        return tamano;
    }

    public void setTamano(Double tamano) {
        this.tamano = tamano;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
