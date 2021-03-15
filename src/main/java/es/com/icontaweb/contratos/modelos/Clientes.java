package es.com.icontaweb.contratos.modelos;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 * The persistent class for the clientes database table.
 *
 */
@Entity
@Table(name = "clientes")
public class Clientes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String cif;
    private String codpostal;

    private String correo1;
    private String correo2;
    private String correo3;
    private String correo4;
    private String correo5;

    private String cuenta;
    private String direccion;
    private String nombre;
    private String poblacion;
    private String provincia;
    private String telefono1;
    private String telefono2;
    
    private int idRepresentante;
    private int idRuta;
    private int idMantenimiento;
    
    private Double longitud;
    private Double latitud;

    @Transient
    private String localizacion = "";

    private byte[] foto;
    private String claveMantenimiento;
    private String observaciones;
    
    private boolean visible;
    
    private String ultimoCodigo;
    private String marca;
    private String modelo;
    private Double pvpMando;
    private String llaveManual;
    private String modeloLlave;
    private Double pvpLlave;
    private int codificacion;
    private int instalador;
    private int candado;
    private String reservaAntiguosDesde;
    private String reservaAntiguosHasta;
    private String reservaNuevosDesde;
    private String reservaNuevosHasta;
    private String reservaTarjetaMemo;
    private String mandosIniciales;
    private String observacionesMandos;

    @Transient
    private StreamedContent imagen;

    //bi-directional many-to-one association to Avisos
    @OneToMany(mappedBy = "cliente")
    private Set<Avisos> avisos;

    //bi-directional many-to-one association to Visitas
    @OneToMany(mappedBy = "clientes")
    private Set<Visitas> visitas;

    //bi-directional many-to-one association to Visitas
    @OneToMany(mappedBy = "cliente")
    private Set<Agenda> agenda;

    public Clientes() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCif() {
        return this.cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getCodpostal() {
        return this.codpostal;
    }

    public void setCodpostal(String codpostal) {
        this.codpostal = codpostal;
    }

    public String getCorreo1() {
        return this.correo1;
    }

    public void setCorreo1(String correo1) {
        this.correo1 = correo1;
    }

    public String getCorreo2() {
        return this.correo2;
    }

    public void setCorreo2(String correo2) {
        this.correo2 = correo2;
    }

    public String getCorreo3() {
        return this.correo3;
    }

    public void setCorreo3(String correo3) {
        this.correo3 = correo3;
    }

    public String getCorreo4() {
        return this.correo4;
    }

    public void setCorreo4(String correo4) {
        this.correo4 = correo4;
    }

    public String getCorreo5() {
        return this.correo5;
    }

    public void setCorreo5(String correo5) {
        this.correo5 = correo5;
    }

    public String getCuenta() {
        return this.cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPoblacion() {
        return this.poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String getProvincia() {
        return this.provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getTelefono1() {
        return this.telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return this.telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public Set<Avisos> getAvisos() {
        return this.avisos;
    }

    public void setAvisos(Set<Avisos> avisos) {
        this.avisos = avisos;
    }

    public Set<Visitas> getVisitas() {
        return this.visitas;
    }

    public void setVisitas(Set<Visitas> visitas) {
        this.visitas = visitas;
    }

    public int getIdRepresentante() {
        return idRepresentante;
    }

    public void setIdRepresentante(int idRepresentante) {
        this.idRepresentante = idRepresentante;
    }
    
    public int getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
    }

    public int getIdMantenimiento() {
        return idMantenimiento;
    }

    public void setIdMantenimiento(int idMantenimiento) {
        this.idMantenimiento = idMantenimiento;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public String getLocalizacion() {
        if (longitud !=null) {
            localizacion = "http://maps.google.com/maps?q=" + latitud.toString() + "," + longitud.toString() + "&ll=" + latitud.toString() + "," + longitud.toString() + "&z=17";
        } else {
            localizacion = "";
        }
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public StreamedContent getImagen() {
        if (foto == null) {
            imagen = null;
        } else {
            imagen = new DefaultStreamedContent(new ByteArrayInputStream(foto));
        }
        return imagen;
    }

    public void setImagen(StreamedContent imagen) {
        this.imagen = imagen;
    }

    public String getClaveMantenimiento() {
        return claveMantenimiento;
    }

    public void setClaveMantenimiento(String claveMantenimiento) {
        this.claveMantenimiento = claveMantenimiento;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getUltimoCodigo() {
        return ultimoCodigo;
    }

    public void setUltimoCodigo(String ultimoCodigo) {
        this.ultimoCodigo = ultimoCodigo;
    }

    public Double getPvpMando() {
        return pvpMando;
    }

    public void setPvpMando(Double pvpMando) {
        this.pvpMando = pvpMando;
    }

    public String getLlaveManual() {
        return llaveManual;
    }

    public void setLlaveManual(String llaveManual) {
        this.llaveManual = llaveManual;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getCodificacion() {
        return codificacion;
    }

    public void setCodificacion(int codificacion) {
        this.codificacion = codificacion;
    }

    public int getInstalador() {
        return instalador;
    }

    public void setInstalador(int instalador) {
        this.instalador = instalador;
    }

    public int getCandado() {
        return candado;
    }

    public void setCandado(int candado) {
        this.candado = candado;
    }

    public String getReservaAntiguosDesde() {
        return reservaAntiguosDesde;
    }

    public void setReservaAntiguosDesde(String reservaAntiguosDesde) {
        this.reservaAntiguosDesde = reservaAntiguosDesde;
    }

    public String getReservaAntiguosHasta() {
        return reservaAntiguosHasta;
    }

    public void setReservaAntiguosHasta(String reservaAntiguosHasta) {
        this.reservaAntiguosHasta = reservaAntiguosHasta;
    }

    public String getReservaNuevosDesde() {
        return reservaNuevosDesde;
    }

    public void setReservaNuevosDesde(String reservaNuevosDesde) {
        this.reservaNuevosDesde = reservaNuevosDesde;
    }

    public String getReservaNuevosHasta() {
        return reservaNuevosHasta;
    }

    public void setReservaNuevosHasta(String reservaNuevosHasta) {
        this.reservaNuevosHasta = reservaNuevosHasta;
    }

    public String getReservaTarjetaMemo() {
        return reservaTarjetaMemo;
    }

    public void setReservaTarjetaMemo(String reservaTarjetaMemo) {
        this.reservaTarjetaMemo = reservaTarjetaMemo;
    }

    public String getMandosIniciales() {
        return mandosIniciales;
    }

    public void setMandosIniciales(String mandosIniciales) {
        this.mandosIniciales = mandosIniciales;
    }

    public String getObservacionesMandos() {
        return observacionesMandos;
    }

    public void setObservacionesMandos(String observacionesMandos) {
        this.observacionesMandos = observacionesMandos;
    }

    public String getModeloLlave() {
        return modeloLlave;
    }

    public void setModeloLlave(String modeloLlave) {
        this.modeloLlave = modeloLlave;
    }

    public Double getPvpLlave() {
        return pvpLlave;
    }

    public void setPvpLlave(Double pvpLlave) {
        this.pvpLlave = pvpLlave;
    }
    
}
