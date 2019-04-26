package es.com.icontaweb.contratos.controladores;

import es.com.icontaweb.contratos.modelos.Visitas;
import es.com.icontaweb.contratos.objetos.Contratos;
import es.com.icontaweb.contratos.utilidades.EnviarCorreo;
import es.com.icontaweb.contratos.utilidades.GenerarImagen;
import es.com.icontaweb.contratos.utilidades.GenerarJustificante;
import es.com.icontaweb.controladores.ControlLogin;
import es.com.icontaweb.rutinas.Rutinas;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

/**
 * Controlador para la gestión de Visitas
 * @author jcperan
 */
@ManagedBean
@SessionScoped
public class ControladorVisitas implements Serializable {
    
    /**
     * Objetos de la Persistencia de Datos
     */
    @PersistenceUnit
    protected EntityManagerFactory emf;

    @PersistenceContext
    protected EntityManager em;

    public EntityManager entityManager() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("contratos");
        }
        return emf.createEntityManager();
    }
    
    
    
    
    @ManagedProperty(value = "#{ControladorContratos}")
    private ControladorContratos controladorContratos = new ControladorContratos();
    public void setControladorContratos(ControladorContratos controladorContratos) { this.controladorContratos = controladorContratos; }
    public ControladorContratos getControladorContratos() { return controladorContratos; }

    @ManagedProperty(value = "#{ControlLogin}")
    private ControlLogin controlLogin = new ControlLogin();
    public void setControlLogin(ControlLogin controlLogin) { this.controlLogin = controlLogin; }
    public ControlLogin getControlLogin() { return controlLogin; }
    
    
    private boolean pendientes = true;
    public boolean isPendientes() { return pendientes; }
    public void setPendientes(boolean pendientes) { this.pendientes = pendientes; }
    
    private String datoConsulta = "";
    public String getDatoConsulta() { return datoConsulta; }
    public void setDatoConsulta(String datoConsulta) { this.datoConsulta = datoConsulta; }
    
    /**
     * Lista de Visitas
     */
    private List<Visitas> listaVisitas = new ArrayList<>();

    public void setListaRutas(List<Visitas> listaVisitas) {
        this.listaVisitas = listaVisitas;
    }
    
    public List<Visitas> getListaVisitas() {
        this.cmdBuscarVisitas();
        return listaVisitas;
    }

    public void cmdBuscarVisitas() {
        em = entityManager();
        try {
            listaVisitas.clear();
            if (datoConsulta.equals(null)) datoConsulta = "";
            String sqlquery = "select v from Visitas v where v.clientes.nombre like :nombre ";
            if (pendientes) sqlquery += "and v.pendiente = 1";
            sqlquery += " order by v.fecha desc ";
            Query q = em.createQuery(sqlquery);
            q.setParameter("nombre", "%" + datoConsulta + "%");
            listaVisitas = q.getResultList();
        } catch (Exception e) {
            System.out.println("icontaweb " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            if (em.isOpen()) em.close();
        }
    }
    
    private Visitas visita;

    public Visitas getVisita() {
        return visita;
    }

    public void setVisita(Visitas visita) {
        this.visita = visita;
    }
    
    private boolean swNuevo = false;

    public boolean isSwNuevo() {
        return swNuevo;
    }

    public void setSwNuevo(boolean swNuevo) {
        this.swNuevo = swNuevo;
    }
    
    public void visitasEditar(ActionEvent event) {
        Integer id = new Integer(((UIParameter) event.getComponent().getFacet("id")).getValue().toString());
        swNuevo = false;
        em = entityManager();
        try {
            visita = em.find(Visitas.class, id);
        } catch (Exception e) {
            System.out.println("icontaweb " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            if (em.isOpen()) em.close();
        }
    }
    
    public String visitasGrabar() {
        
        Rutinas.Log("VISITA CONFIRMADA : " + new DecimalFormat("########").format(visita.getId()) );
        
        em = entityManager();
        try {
            em.getTransaction().begin();
            if (swNuevo) {
                em.persist(visita);
                em.flush();
            } else {
                Integer id = visita.getId();
                Visitas rutaModificar = em.find(Visitas.class, id);
                rutaModificar = visita;
                em.merge(rutaModificar);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("icontaweb " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            if (em.isOpen()) em.close();
        }
        
        return "/contratos/visitas/visitasLista.xhtml";
    }
    
    public String visitasEnviar() {
        
        visita.setPendiente(false);
        this.visitasGrabar();
        
        Contratos objeto = controladorContratos.getObjeto();
        objeto.LeerVisita(visita.getId());
        objeto.LeerCliente(visita.getClientes().getId());
        boolean swMantenimiento = false;
        
        GenerarImagen generador = new GenerarImagen();
        byte[] imagen = generador.ImagenJson(visita.getFirma());

        GenerarJustificante informe = new GenerarJustificante();
        informe.GeneraInforme(visita.getId(), imagen);

        String destino;
        EnviarCorreo mensaje = new EnviarCorreo("instelec@puertaautomatica.es", objeto, swMantenimiento);
        if (visita.getConCargo())  mensaje.EnviarMensaje("cobrosinstelec@gmail.com", objeto, swMantenimiento);
        mensaje.EnviarMensaje("partesinstelec@gmail.com", objeto, swMantenimiento);
        // EnviarCorreo mensaje = new EnviarCorreo("jcperan@gmail.com", objeto, false);

        destino = objeto.getClientes().getCorreo1();
        if (!destino.equals("") && controladorContratos.isEnviar1()) mensaje.EnviarMensaje(destino, objeto, swMantenimiento);

        destino = objeto.getClientes().getCorreo2();
        if (!destino.equals("") && controladorContratos.isEnviar2()) mensaje.EnviarMensaje(destino, objeto, swMantenimiento);
        
        destino = objeto.getClientes().getCorreo3(); 
        if (!destino.equals("") && controladorContratos.isEnviar3()) mensaje.EnviarMensaje(destino, objeto, swMantenimiento);
        
        destino = objeto.getClientes().getCorreo4();
        if (!destino.equals("") && controladorContratos.isEnviar4()) mensaje.EnviarMensaje(destino, objeto, swMantenimiento);
        
        destino = objeto.getClientes().getCorreo5();
        if (!destino.equals("") && controladorContratos.isEnviar5()) mensaje.EnviarMensaje(destino, objeto, swMantenimiento);
        
        return "/contratos/opciones.xhtml";
    }
    
    private String visitasFirma;

    public String getVisitasFirma() {
        this.visitasFirma = visita.getFirma().substring(22);
        return visitasFirma;
    }

    public void setVisitasFirma(String visitasFirma) {
        this.visitasFirma = visitasFirma;
    }
    
    
}
