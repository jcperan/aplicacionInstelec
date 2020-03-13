package es.com.icontaweb.modelos;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the usuarios database table.
 *
 */
@Entity
@Table(name = "usuarios")
public class Usuarios implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String usuario;
    private String nombre;
    private String clave;
    private int nivel;
    private String proceso;
    private int idRepresentante;

    public Usuarios() {
    }

    public String getUsuario() {
        return this.usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return this.clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getNivel() {
        return this.nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    public String getProceso() {
        return proceso;
    }

    public int getIdRpresentante() {
        return idRepresentante;
    }

    public void setIdRpresentante(int idRpresentante) {
        this.idRepresentante = idRpresentante;
    }

}
