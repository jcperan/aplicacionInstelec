package es.com.icontaweb.contratos.modelos;

import java.awt.Color;
import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the avisos database table.
 *
 */
@Entity
@Table(name = "avisos")
public class Avisos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String avisoDe;
    private String estado;

    @Temporal(TemporalType.DATE)
    private Date fecha;
    private String hora;
    private String observaciones;
    private String referencia;

    //bi-directional many-to-one association to Clientes
    @ManyToOne
    @JoinColumn(name = "idCliente")
    private Clientes cliente;

    //bi-directional many-to-one association to Motivos
    @ManyToOne
    @JoinColumn(name = "idMotivo")
    private Motivos motivo;
    
    private String usuario;
    private int prioridad;
    
    @Transient
    private String textoPrioridad;
    
    @Transient
    private String colorPrioridad;

    public Avisos() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvisoDe() {
        return this.avisoDe;
    }

    public void setAvisoDe(String avisoDe) {
        this.avisoDe = avisoDe;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return this.hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getObservaciones() {
        return this.observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getReferencia() {
        return this.referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Clientes getCliente() {
        return this.cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    public Motivos getMotivo() {
        return this.motivo;
    }

    public void setMotivo(Motivos motivo) {
        this.motivo = motivo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public String getTextoPrioridad() {
        switch (prioridad) {
            case 0: textoPrioridad = "Normal"; break;
            case 1: textoPrioridad = "Importante"; break;
            case 2: textoPrioridad = "Urgente"; break;
            case 3: textoPrioridad = "PRIORITARIO"; break;
            default: textoPrioridad = "";
        }
        return textoPrioridad;
    }

    public void setTextoPrioridad(String textoPrioridad) {
        this.textoPrioridad = textoPrioridad;
    }

    public String getColorPrioridad() {
        switch (prioridad) {
            case 0: colorPrioridad = "green"; break;
            case 1: colorPrioridad = "orange"; break;
            case 2: colorPrioridad = "red"; break;
            case 3: colorPrioridad = "white"; break;
            default: colorPrioridad = "black";
        }
        return colorPrioridad;
    }

    public void setColorPrioridad(String colorPrioridad) {
        this.colorPrioridad = colorPrioridad;
    }

}
