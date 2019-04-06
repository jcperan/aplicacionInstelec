/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.com.icontaweb.contratos.vistas;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author jcperan
 */
@Entity
@Table(name = "vistaUsuariosRutas")
public class VistaUsuariosRutas implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    private VistaUsuariosRutasPK clave;
    
    @Size(max = 30)
    @Column(name = "denominacion")
    private String denominacion;

    public VistaUsuariosRutas() {
    }

    public VistaUsuariosRutasPK getClave() {
        return clave;
    }

    public void setClave(VistaUsuariosRutasPK clave) {
        this.clave = clave;
    }
    
    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }
    
}
