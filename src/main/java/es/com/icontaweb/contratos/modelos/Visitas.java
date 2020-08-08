package es.com.icontaweb.contratos.modelos;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the visitas database table.
 *
 */
@Entity
@Table(name = "visitas")
public class Visitas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private boolean conCargo;
    private boolean enMantenimiento;
    private boolean facturado;
    private boolean pendiente;

    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String firma;
    private String firmado1;
    private String firmado2;
    private String hora;
    private String observaciones;
    private Integer idAviso;
    
    private String descripcionOperario;
    private String descripcionAviso;
    private String sp;
    
    private String linea1;
    private String linea2;
    
    private boolean visible;

    //bi-directional many-to-one association to Clientes
    @ManyToOne
    @JoinColumn(name = "idCliente")
    private Clientes clientes;

    //bi-directional many-to-one association to Motivos
    @ManyToOne
    @JoinColumn(name = "idMotivo")
    private Motivos motivos;

    //bi-directional many-to-one association to Trabajos
    @ManyToOne
    @JoinColumn(name = "idTrabajo")
    private Trabajos trabajos;

    public Visitas() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getConCargo() {
        return this.conCargo;
    }

    public void setConCargo(boolean conCargo) {
        this.conCargo = conCargo;
    }

    public boolean getEnMantenimiento() {
        return this.enMantenimiento;
    }

    public void setEnMantenimiento(boolean mantenimiento) {
        this.enMantenimiento = mantenimiento;
    }

    public boolean getFacturado() {
        return this.facturado;
    }

    public void setFacturado(boolean facturado) {
        this.facturado = facturado;
    }

    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getFirma() {
        return this.firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public String getFirmado1() {
        return this.firmado1;
    }

    public void setFirmado1(String firmado1) {
        this.firmado1 = firmado1;
    }

    public String getFirmado2() {
        return this.firmado2;
    }

    public void setFirmado2(String firmado2) {
        this.firmado2 = firmado2;
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

    public Clientes getClientes() {
        return this.clientes;
    }

    public void setClientes(Clientes clientes) {
        this.clientes = clientes;
    }

    public Motivos getMotivos() {
        return this.motivos;
    }

    public void setMotivos(Motivos motivos) {
        this.motivos = motivos;
    }

    public Trabajos getTrabajos() {
        return this.trabajos;
    }

    public void setTrabajos(Trabajos trabajos) {
        this.trabajos = trabajos;
    }

    public Integer getIdAviso() {
        return idAviso;
    }

    public void setIdAviso(Integer idAviso) {
        this.idAviso = idAviso;
    }

    public boolean isPendiente() {
        return pendiente;
    }

    public void setPendiente(boolean pendiente) {
        this.pendiente = pendiente;
    }

    public String getDescripcionOperario() {
        return descripcionOperario;
    }

    public void setDescripcionOperario(String descripcionOperario) {
        this.descripcionOperario = descripcionOperario;
    }

    public String getDescripcionAviso() {
        return descripcionAviso;
    }

    public void setDescripcionAviso(String descripcionAviso) {
        this.descripcionAviso = descripcionAviso;
    }

    public String getSp() {
        return sp;
    }

    public void setSp(String sp) {
        this.sp = sp;
    }

    public String getLinea1() {
        return linea1;
    }

    public void setLinea1(String linea1) {
        this.linea1 = linea1;
    }

    public String getLinea2() {
        return linea2;
    }

    public void setLinea2(String linea2) {
        this.linea2 = linea2;
    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

}
