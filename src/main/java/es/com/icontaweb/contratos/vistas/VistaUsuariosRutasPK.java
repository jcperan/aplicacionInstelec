/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.com.icontaweb.contratos.vistas;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

/**
 *
 * @author jcperan
 */
@Embeddable
public class VistaUsuariosRutasPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Size(max = 20)
    @Column(name = "usuario")
    private String usuario;
    
    @Column(name = "ruta")
    private Integer ruta;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Integer getRuta() {
        return ruta;
    }

    public void setRuta(Integer ruta) {
        this.ruta = ruta;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.usuario);
        hash = 71 * hash + Objects.hashCode(this.ruta);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final VistaUsuariosRutasPK other = (VistaUsuariosRutasPK) obj;
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        if (!Objects.equals(this.ruta, other.ruta)) {
            return false;
        }
        return true;
    }
    
    
}
