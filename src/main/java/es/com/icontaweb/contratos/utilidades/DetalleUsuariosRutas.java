/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.com.icontaweb.contratos.utilidades;

/**
 *
 * @author jcperan
 */
public class DetalleUsuariosRutas {

    private Integer id;
    private String ruta;
    private boolean permitida;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public boolean isPermitida() {
        return permitida;
    }

    public void setPermitida(boolean permitida) {
        this.permitida = permitida;
    }

}
