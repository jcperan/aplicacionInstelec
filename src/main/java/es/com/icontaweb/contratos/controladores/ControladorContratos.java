package es.com.icontaweb.contratos.controladores;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.ServletContext;

import es.com.icontaweb.contratos.modelos.Avisos;
import es.com.icontaweb.contratos.modelos.ListaMantenimiento;
import es.com.icontaweb.contratos.modelos.MantenimientosDetalle;
import es.com.icontaweb.contratos.modelos.MantenimientosGrupo;
import es.com.icontaweb.contratos.modelos.Visitas;
import es.com.icontaweb.contratos.objetos.Contratos;
import es.com.icontaweb.contratos.objetos.PlanMantenimiento;
import es.com.icontaweb.contratos.utilidades.EnviarCorreo;
import es.com.icontaweb.contratos.utilidades.GenerarImagen;
import es.com.icontaweb.contratos.utilidades.GenerarJustificante;
import es.com.icontaweb.controladores.ControlLogin;
import es.com.icontaweb.rutinas.Rutinas;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "ControladorContratos")
@SessionScoped
public class ControladorContratos implements Serializable {
    
    @ManagedProperty(value = "#{ControlLogin}")
    private ControlLogin controlLogin = new ControlLogin();
    public void setControlLogin(ControlLogin controlLogin) { this.controlLogin = controlLogin; }
    public ControlLogin getControlLogin() { return controlLogin; }

    /**
     * Objeto para el acceso a los datos y funciones de acceso a la base de
     * datos.
     */
    private Contratos objeto = new Contratos();
    public void setObjeto(Contratos objeto) { this.objeto = objeto; }
    public Contratos getObjeto() { return objeto; }
    
    
    private String cmdVolver = "";
    public String getCmdVolver() { return cmdVolver; }
    public void setCmdVolver(String cmdVolver) { this.cmdVolver = cmdVolver; }
    
    
    private Date fecha = new Date();

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFecha() {
        return fecha;
    }

    private String hora;

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getHora() {
        setHora(((new Date().getHours()) + ":" + (new Date().getMinutes())));
        DateFormat format = new SimpleDateFormat("HH:mm");
        hora = format.format(new Date());
        return hora;
    }

    private Integer representante;

    public Integer getRepresentante() {
        return representante;
    }

    public void setRepresentante(Integer representante) {
        this.representante = representante;
    }

    private Integer cliente;
    private Integer motivo;
    private Integer trabajo;
    private String descripcion = "";
    private String sp = "";
    
    private String descripcionOperario;
    private String descripcionAviso;

    private boolean mantenimiento;
    private boolean cargo = true;
    
    private String linea1 = "";
    private String linea2 = "";

    private String firma;
    private String firmado1;
    private String firmado2;
    private Integer idAviso = 0;
    
    private boolean swMantenimiento = false;

    public Integer getCliente() {
        return cliente;
    }

    public void setCliente(Integer cliente) {
        this.cliente = cliente;
    }

    public Integer getMotivo() {
        return motivo;
    }

    public void setMotivo(Integer motivo) {
        this.motivo = motivo;
    }

    public Integer getTrabajo() {
        return trabajo;
    }

    public void setTrabajo(Integer trabajo) {
        this.trabajo = trabajo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSp() {
        return sp;
    }

    public void setSp(String sp) {
        this.sp = sp;
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

    public boolean isMantenimiento() {
        return mantenimiento;
    }

    public void setMantenimiento(boolean mantenimiento) {
        this.mantenimiento = mantenimiento;
    }

    public boolean isCargo() {
        return cargo;
    }

    public void setCargo(boolean cargo) {
        this.cargo = cargo;
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
    
    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public String getFirmado1() {
        return firmado1;
    }

    public void setFirmado1(String firmado1) {
        this.firmado1 = firmado1;
    }

    public String getFirmado2() {
        return firmado2;
    }

    public void setFirmado2(String firmado2) {
        this.firmado2 = firmado2;
    }

    public Integer getIdAviso() {
        return idAviso;
    }

    public void setIdAviso(Integer idAviso) {
        this.idAviso = idAviso;
    }

    /**
     * Aceptar datos y pedir confirmacion grabacion
     *
     * @return 
     */
    public String ACEPTAR() {

        String resultado = "/contratos/visitas/visitasFinal.xhtml";
        if (!objeto.LeerCliente(cliente)) resultado = "";
        // if (!objeto.LeerMotivo(motivo))   resultado = "";
        if (!objeto.LeerTrabajo(trabajo)) resultado = "";

        return resultado;
    }

    private boolean enviar1 = true;
    private boolean enviar2 = true;
    private boolean enviar3 = true;
    private boolean enviar4 = true;
    private boolean enviar5 = true;

    public boolean isEnviar1() {
        return enviar1;
    }

    public void setEnviar1(boolean enviar1) {
        this.enviar1 = enviar1;
    }

    public boolean isEnviar2() {
        return enviar2;
    }

    public void setEnviar2(boolean enviar2) {
        this.enviar2 = enviar2;
    }

    public boolean isEnviar3() {
        return enviar3;
    }

    public void setEnviar3(boolean enviar3) {
        this.enviar3 = enviar3;
    }

    public boolean isEnviar4() {
        return enviar4;
    }

    public void setEnviar4(boolean enviar4) {
        this.enviar4 = enviar4;
    }

    public boolean isEnviar5() {
        return enviar5;
    }

    public void setEnviar5(boolean enviar5) {
        this.enviar5 = enviar5;
    }

    /**
     * Generar un nuevo cliente
     *
     * @param event
     */
    public void cmdNuevoCliente(ActionEvent event) {
        swMODIFICAR = false;
        if (!objeto.NuevoCliente()) {
            Rutinas.MuestraMensaje("Error al Crear nuevo cliente");
        }
    }

    boolean swMODIFICAR = false;
    boolean swNUEVOAVISO = false;

    /**
     * Grabar datos del cliente
     *
     * @param event
     */
    public void cmdGrabaCliente(ActionEvent event) {
        if (swMODIFICAR) {
            objeto.ActualizaCliente(cliente);
        } else {
            objeto.GrabaCliente();
        }
        Rutinas.MuestraMensaje("Los datos han sido grabados.");
    }

    public String GRABAR() {

        objeto.ActualizaCliente(cliente);

        objeto.setVisitas(new Visitas());
        objeto.getVisitas().setFecha(fecha);
        objeto.getVisitas().setHora(hora);
        objeto.LeerCliente(cliente);
        objeto.getVisitas().setClientes(objeto.getClientes());
        objeto.LeerMotivo(motivo);
        objeto.getVisitas().setMotivos(objeto.getMotivos());
        objeto.LeerTrabajo(trabajo);
        objeto.getVisitas().setTrabajos(objeto.getTrabajos());
        objeto.getVisitas().setObservaciones(descripcion);
        objeto.getVisitas().setFirma(firma);
        objeto.getVisitas().setFirmado1(firmado1);
        objeto.getVisitas().setFirmado2(firmado2);
        objeto.getVisitas().setEnMantenimiento(mantenimiento);
        objeto.getVisitas().setConCargo(cargo);
        objeto.getVisitas().setIdAviso(idAviso);
        objeto.getVisitas().setFacturado(false);
        objeto.getVisitas().setPendiente(true);
        objeto.getVisitas().setDescripcionAviso(descripcionAviso);
        objeto.getVisitas().setDescripcionOperario(descripcionOperario);
        objeto.getVisitas().setSp(sp);
        objeto.getVisitas().setLinea1(linea1);
        objeto.getVisitas().setLinea2(linea2);
        objeto.GrabaVisita();
        
        if (idAviso != 0) {
            objeto.LeerAviso(idAviso);
            objeto.getAvisos().setEstado("1");
            objeto.ActualizarAviso(idAviso);
        }
        
        descripcion = "";
        sp = "";
        
        linea1 = "";
        linea2 = "";
    
        descripcionOperario = "";
        descripcionAviso = "";

        firma = "";
        firmado1 = "";
        firmado2 = "";
        
        return "/contratos/opciones.xhtml";
    }
    
    public String ENVIAR() {
        
        this.GRABAR();
        
        if (objeto.LeerVisita(objeto.getVisitas().getId())) {
            objeto.getVisitas().setPendiente(false);
            objeto.ActualizarVisita(objeto.getVisitas(), objeto.getVisitas().getId());
        }

        GenerarImagen generador = new GenerarImagen();
        // byte[] imagen = generador.ImagenJson(firma);
        byte[] imagen = generador.ImagenJson(objeto.getVisitas().getFirma());

        GenerarJustificante informe = new GenerarJustificante();
        informe.GeneraInforme(objeto.getVisitas().getId(), imagen);

        if (swMantenimiento) {
            this.GeneraMantenimiento(objeto.getVisitas().getId());
            informe.GeneraMantenimiento(objeto.getVisitas().getId(), imagen);
        }
        
        String destino = "";
        EnviarCorreo mensaje = new EnviarCorreo("instelec@puertaautomatica.es", objeto, swMantenimiento);
        if (cargo)   mensaje.EnviarMensaje("cobrosinstelec@gmail.com", objeto, swMantenimiento);
        mensaje.EnviarMensaje("partesinstelec@gmail.com", objeto, swMantenimiento);
        // EnviarCorreo mensaje = new EnviarCorreo("jcperan@gmail.com", objeto, swMantenimiento);

        destino = objeto.getClientes().getCorreo1(); if (!destino.equals("") && isEnviar1()) mensaje.EnviarMensaje(destino, objeto, swMantenimiento);
        destino = objeto.getClientes().getCorreo2(); if (!destino.equals("") && isEnviar2()) mensaje.EnviarMensaje(destino, objeto, swMantenimiento);
        destino = objeto.getClientes().getCorreo3(); if (!destino.equals("") && isEnviar3()) mensaje.EnviarMensaje(destino, objeto, swMantenimiento);
        destino = objeto.getClientes().getCorreo4(); if (!destino.equals("") && isEnviar4()) mensaje.EnviarMensaje(destino, objeto, swMantenimiento);
        destino = objeto.getClientes().getCorreo5(); if (!destino.equals("") && isEnviar5()) mensaje.EnviarMensaje(destino, objeto, swMantenimiento);
        
        fecha = new Date();
        cliente = 0;
        motivo = 0;
        trabajo = 0;
        mantenimiento = false;
        cargo = true;
        descripcion = "";
        descripcionAviso="";
        descripcionOperario="";
        firmado1 = "";
        firmado2 = "";
        idAviso = 0;
        
        sp = "";
        linea1 = "";
        linea2 = "";

        return "/contratos/opciones.xhtml";
    }

    public String COMPROBAR() {

        objeto.ObtenerConsultaVisitas(cliente);
        return "comprobar";

    }

    public String MODIFICAR() {

        swMODIFICAR = true;
        objeto.LeerCliente(cliente);
        return "clientes";

    }

    /**
     * Dato para el acceso a la consulta de datos
     */
    private String datoConsulta = "";

    public String getDatoConsulta() {
        if (datoConsulta.equals("")) objeto.BuscaClientes("");
        return datoConsulta;
    }

    public void setDatoConsulta(String datoConsulta) {
        this.datoConsulta = datoConsulta;
    }

    /**
     * Buscar datos del cliente
     * @param event
     */
    public void cmdBuscaCliente(ActionEvent event) {
        objeto.BuscaClientes(datoConsulta);
    }

    public void cmdAceptaCliente(ActionEvent event) {
        cliente = new Integer(((UIParameter) event.getComponent().getFacet("id")).getValue().toString());
        objeto.LeerCliente(cliente);
        swMantenimiento = false;
        swNUEVOAVISO = true;
        swMODIFICAR = true;
    }

    public void cmdCONSULTA(ActionEvent event) {
        Integer id = new Integer(((UIParameter) event.getComponent().getFacet("codigo")).getValue().toString());
        objeto.LeerVisita(id);

        GenerarImagen generador = new GenerarImagen();
        generador.Imagen64(objeto.getVisitas().getFirma());
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String realPath = (String) servletContext.getRealPath("/");
        rutaImagen = realPath + "/recursos/imgfirma.png";

    }

    private String rutaImagen = "";

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public String visitasNuevo() {
        this.cargo=true;
        this.mantenimiento=false;
        this.descripcion="";
        this.descripcionAviso="";
        this.descripcionOperario="";
        this.swMantenimiento=false;
        this.swMODIFICAR=false;
        return "/contratos/visitas/visitasDatos.xhtml";
    }
        

    
    
    
    
    
    
    
    
    
    



    private String avisoDe;
    private String referencia;
    private String observaciones;
    private String usuario;
    private Integer prioridad = 0;
    
    private Integer rutaSeleccionada = -1;

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getAvisoDe() {
        return avisoDe;
    }

    public void setAvisoDe(String avisoDe) {
        this.avisoDe = avisoDe;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Integer getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }

    public Integer getRutaSeleccionada() {
        return rutaSeleccionada;
    }

    public void setRutaSeleccionada(Integer rutaSelecionada) {
        this.rutaSeleccionada = rutaSelecionada;
    }
    
    

    private String usuarioLista = "";

    public String getUsuarioLista() {
        if (usuarioLista.equals("")) usuarioLista=controlLogin.getUSER();
        if (!controlLogin.getNivelAdministrador()) usuarioLista=controlLogin.getUSER();
        return usuarioLista;
    }

    public void setUsuarioLista(String usuarioLista) {
        this.usuarioLista = usuarioLista;
    }
    
    
    /**
     * Aceptar datos y pedir confirmacion grabacion
     *
     * @param event
     */
    public String AceptarAviso() {

        // Asignar valores a variables
        if (swNUEVOAVISO) {
            objeto.setAvisos(new Avisos());
            objeto.getAvisos().setFecha(fecha);
            objeto.getAvisos().setHora(hora);
            objeto.LeerCliente(cliente);
            objeto.getAvisos().setCliente(objeto.getClientes());
            objeto.LeerMotivo(motivo);
            objeto.getAvisos().setMotivo(objeto.getMotivos());
            objeto.getAvisos().setAvisoDe(avisoDe);
            objeto.getAvisos().setReferencia(referencia);
            objeto.getAvisos().setObservaciones(descripcion);
            objeto.getAvisos().setEstado("0");
            objeto.getAvisos().setUsuario(usuario);
            objeto.getAvisos().setPrioridad(prioridad);
            objeto.GrabaAviso();
        } else {
            objeto.getAvisos().setFecha(fecha);
            objeto.getAvisos().setHora(hora);
            objeto.LeerCliente(cliente);
            objeto.getAvisos().setCliente(objeto.getClientes());
            objeto.LeerMotivo(motivo);
            objeto.getAvisos().setMotivo(objeto.getMotivos());
            objeto.getAvisos().setAvisoDe(avisoDe);
            objeto.getAvisos().setReferencia(referencia);
            objeto.getAvisos().setObservaciones(descripcion);
            objeto.getAvisos().setEstado("0");
            objeto.getAvisos().setUsuario(usuario);
            objeto.getAvisos().setPrioridad(prioridad);
            objeto.ActualizarAviso(objeto.getAvisos().getId());
        }

        // Limpiar variables de acceso local
        avisoDe = "";
        referencia = "";
        descripcion = "";
        usuario = "";
        prioridad = 0;

        // Regresa para nuevo aviso/visita
        return "/contratos/avisos/avisosLista.xhtml";
    }

    public String ComprobarAvisos() {

        objeto.ObtenerConsultaAvisos(cliente, "", usuarioLista, datoConsulta, 0);
        return "comprobar";

    }

    private boolean verTodos = true;

    public boolean isVerTodos() {
        return verTodos;
    }

    public void setVerTodos(boolean verTodos) {
        this.verTodos = verTodos;
    }

    public void cmdBuscarAvisos() {
        BuscaAvisos();
    }

    public void cmdBuscarAvisos(ValueChangeEvent event) {
        BuscaAvisos();
    }

    public String BuscaAvisos() {
        if (verTodos) {
            objeto.ObtenerConsultaAvisos(0, "", usuarioLista, datoConsulta, 0);
        } else {
            objeto.ObtenerConsultaAvisos(0, "0", usuarioLista, datoConsulta, 0);
        }
        return "/contratos/avisos/avisosLista.xhtml";
    }
    
    public List<Avisos> avisosLista() {
        if (!verTodos) {
            objeto.ObtenerConsultaAvisos(0, "", usuarioLista, datoConsulta, this.prioridad, this.rutaSeleccionada);
        } else {
            objeto.ObtenerConsultaAvisos(0, "0", usuarioLista, datoConsulta, this.prioridad, this.rutaSeleccionada);
        }
        return objeto.getConsultaAvisos();
    }

    public void cmdVERAVISO(ActionEvent event) {
        Integer id = new Integer(((UIParameter) event.getComponent().getFacet("codigo")).getValue().toString());
        objeto.LeerAviso(id);

        this.idAviso = id;
        this.cliente = objeto.getAvisos().getCliente().getId();
        this.motivo = objeto.getAvisos().getMotivo().getId();
        this.avisoDe = objeto.getAvisos().getAvisoDe();
        this.referencia = objeto.getAvisos().getReferencia();
        this.descripcion = objeto.getAvisos().getObservaciones();
        this.descripcionAviso = objeto.getAvisos().getObservaciones();
        this.descripcionOperario = "";
        this.usuario = objeto.getAvisos().getUsuario();
        this.prioridad = objeto.getAvisos().getPrioridad();

        objeto.LeerCliente(cliente);
        swNUEVOAVISO = false;
    }

    /**
     * Procedimiento para crear una nueva entrada de aviso
     * @return
     */
    public String avisosNuevo() {
        swNUEVOAVISO = true;
        this.motivo = 0;
        this.avisoDe = "";
        this.referencia = "";
        this.descripcion = "";
        this.prioridad = 0;
        return "/contratos/avisos/avisosNuevo.xhtml";
    }
        
    public void cmdVISITAR(ActionEvent event) {
        Integer id = new Integer(((UIParameter) event.getComponent().getFacet("codigo")).getValue().toString());
        objeto.LeerAviso(id);

        this.idAviso = id;
        this.cliente = objeto.getAvisos().getCliente().getId();
        this.motivo = objeto.getAvisos().getMotivo().getId();
        this.descripcion = objeto.getAvisos().getObservaciones();
        this.descripcionAviso = objeto.getAvisos().getObservaciones();
        this.descripcionOperario = "";

        objeto.LeerCliente(cliente);
    }

    public String SolucionarAviso() {
        this.AceptarAviso();
        this.idAviso = objeto.getAvisos().getId();
        this.cliente = objeto.getAvisos().getCliente().getId();
        this.motivo = objeto.getAvisos().getMotivo().getId();
        this.descripcion = objeto.getAvisos().getObservaciones();
        this.descripcionAviso = objeto.getAvisos().getObservaciones();
        this.descripcionOperario = "";

        objeto.LeerCliente(cliente); 
        return "/contratos/visitas/visitasDatos.xhtml";
    }


    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * Listas de visualizacion de los datos del mantenimiento
     */
    private PlanMantenimiento mantenimientos = new PlanMantenimiento();

    public void setMantenimientos(PlanMantenimiento mantenimientos) {
        this.mantenimientos = mantenimientos;
    }

    public PlanMantenimiento getMantenimientos() {
        return this.mantenimientos;
    }

    private List<ListaMantenimiento> listaDatos = new ArrayList<ListaMantenimiento>();

    public List<ListaMantenimiento> getListaDatos() {
        return listaDatos;
    }

    public void setListaDatos(List<ListaMantenimiento> listaDatos) {
        this.listaDatos = listaDatos;
    }

    private List<MantenimientosGrupo> listaGrupos = new ArrayList<MantenimientosGrupo>();

    public List<MantenimientosGrupo> getListaGrupos() {
        listaGrupos = mantenimientos.getConsultaGrupo();
        return listaGrupos;
    }

    public void setListaGrupos(List<MantenimientosGrupo> listaGrupos) {
        this.listaGrupos = listaGrupos;
    }

    private List<MantenimientosDetalle> listaDetalle = new ArrayList<MantenimientosDetalle>();

    public List<MantenimientosDetalle> getListaDetalle() {
        listaDetalle = mantenimientos.getConsultaDetalle();
        return listaDetalle;
    }

    public void setListaDetalle(List<MantenimientosDetalle> listaDetalle) {
        this.listaDetalle = listaDetalle;
    }

    /**
     * Boton para la lectura y procesamiento del mantenimiento seleccionado
     *
     * @param event
     */
    public void cmdAsignaMantenimiento(ActionEvent event) {
        listaDatos.clear();
        listaGrupos.clear();
        Integer idMantenimiento = new Integer(((UIParameter) event.getComponent().getFacet("codigo")).getValue().toString());
        if (mantenimientos.LeerMantenimiento(idMantenimiento)) {
            Iterator<MantenimientosGrupo> i1 = mantenimientos.getMantenimiento().getMantenimientosGrupos().iterator();
            while (i1.hasNext()) {
                MantenimientosGrupo grupo = (MantenimientosGrupo) i1.next();
                listaDatos.add(new ListaMantenimiento(0, 0, 0, grupo.getDescripcion(), "", false, false));
                Iterator<MantenimientosDetalle> i2 = grupo.getMantenimientosDetalles().iterator();
                while (i2.hasNext()) {
                    MantenimientosDetalle detalle = (MantenimientosDetalle) i2.next();
                    listaDatos.add(new ListaMantenimiento(detalle.getId().getIdDetalle(), detalle.getId().getGrupo(), detalle.getId().getDetalle(), "", detalle.getDescripcion(), false, true));
                }
            }
        }
    }

    private void GeneraMantenimiento(int NumeroVisita) {
        PlanMantenimiento mantenimiento = new PlanMantenimiento();
        Iterator<ListaMantenimiento> it = listaDatos.iterator();
        while (it.hasNext()) {
            ListaMantenimiento registro = it.next();
            mantenimiento.GrabarVisita(NumeroVisita, registro);
        }
    }

    public String cmdAceptaMantenimiento() {
        swMantenimiento = true;
        return "confirma";
    }

}
