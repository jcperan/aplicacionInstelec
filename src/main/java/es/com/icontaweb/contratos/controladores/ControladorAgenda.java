package es.com.icontaweb.contratos.controladores;

import es.com.icontaweb.contratos.modelos.Agenda;
import es.com.icontaweb.contratos.vistas.VistaUsuariosRutas;
import es.com.icontaweb.controladores.ControlLogin;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

/**
 * Controlador para la gestión de la agenda
 * @author jcperan
 */
@ManagedBean
@SessionScoped
public class ControladorAgenda implements Serializable {
    
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
    
    /**
     * Lista de Rutas segun usuario
     */
    private List<SelectItem> listaRutasUsuario = new ArrayList<>();

    public void setListaRutasUsuario(List<SelectItem> listaRutasUsuario) {
        this.listaRutasUsuario = listaRutasUsuario;
    }
    
    public List<SelectItem> getListaRutasUsuario() {
        
        em = entityManager();   
        try {
            listaRutasUsuario.clear();
            if (controlLogin.getNivelAdministrador()) listaRutasUsuario.add(new SelectItem(0, "TODOS"));
            
            String sqlquery = "select r from VistaUsuariosRutas r where r.clave.usuario = :usuario ";
            sqlquery += "order by r.denominacion";
            Query q = em.createQuery(sqlquery);
            q.setParameter("usuario", controlLogin.getUSER());
            List<VistaUsuariosRutas> lista = q.getResultList();
            Iterator<VistaUsuariosRutas> it = lista.iterator();
            while (it.hasNext()) {
                VistaUsuariosRutas datos = (VistaUsuariosRutas) it.next();
                listaRutasUsuario.add(new SelectItem(datos.getClave().getRuta(), datos.getDenominacion()));
                if ((!controlLogin.getNivelAdministrador()) && (rutaSeleccionada==0)) rutaSeleccionada = datos.getClave().getRuta();
            }
        } catch (Exception e) {
            System.out.println("icontaweb " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            if (em.isOpen()) em.close();
        }

        return listaRutasUsuario;
    }

    private Integer rutaSeleccionada = 0;
    public Integer getRutaSeleccionada() { return rutaSeleccionada; }
    public void setRutaSeleccionada(Integer rutaSeleccionada) { this.rutaSeleccionada = rutaSeleccionada; }
    
    private String datoConsulta = "";
    public String getDatoConsulta() { return datoConsulta; }
    public void setDatoConsulta(String datoConsulta) { this.datoConsulta = datoConsulta; }

    /**
     * Lista de Agenda
     */
    private List<Agenda> listaAgenda = new ArrayList<>();

    public List<Agenda> getListaAgenda() {
        this.ObtenerAgenda();
        return listaAgenda;
    }

    public void setListaAgenda(List<Agenda> listaAgenda) {
        this.listaAgenda = listaAgenda;
    }
    
    /**
     * Procedimiento para obtener la lista de la agenda pendiente
     */
    public void ObtenerAgenda() {
        
        em = entityManager();
        try {
            
            String sqlquery = "select a from Agenda a where a.cliente.nombre like :nombre ";
            if (rutaSeleccionada>0) sqlquery = sqlquery + "and a.cliente.idRuta = :ruta ";
            if (statusAgenda) {
                sqlquery = sqlquery + "and a.fechaProxima <= CURRENT_DATE ";
            } else {
                if (controlLogin.getNivelAdministrador()) {
                    
                } else {
                    Calendar cal = Calendar.getInstance(); 
                    cal.add(Calendar.DAY_OF_YEAR, 89);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    sqlquery = sqlquery + "and a.fechaProxima <= '" + sdf.format(cal.getTime()) + "' " ;
                }
            }
            sqlquery = sqlquery + "order by a.fechaProxima ";

            Query lista = em.createQuery(sqlquery);

            lista.setParameter("nombre", "%" + datoConsulta + "%");
            if (rutaSeleccionada>0) lista.setParameter("ruta", rutaSeleccionada);

            listaAgenda = lista.getResultList();
        } catch (Exception e) {
            System.out.println("icontaweb " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            if (em.isOpen()) em.close();
        }

    }

    private boolean swAgenda = false;

    public boolean isSwAgenda() {
        return swAgenda;
    }

    public void setSwAgenda(boolean swAgenda) {
        this.swAgenda = swAgenda;
    }

    private boolean statusAgenda = true;

    public boolean isStatusAgenda() {
        return statusAgenda;
    }

    public void setStatusAgenda(boolean statusAgenda) {
        this.statusAgenda = statusAgenda;
    }

    private Agenda agenda = new Agenda();

    public Agenda getAgenda() {
        return agenda;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }

    /**
     * Procedimiento para obtener la cita seleccionada
     * @param event
     */
    public void cmdVERAGENDA(ActionEvent event) {

        Integer id = new Integer(((UIParameter) event.getComponent().getFacet("id")).getValue().toString());

        em = entityManager();
        try {
            this.agenda = em.find(Agenda.class, id);
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
        }

        swAgenda = true;

    }

    /**
     * Procedimiento para grabar los datos de la cita actual
     * @return
     */
    public String AgendaAceptar() {

        em = entityManager();
        try {
            em.getTransaction().begin();
            if (swAgenda) {
                em.merge(agenda);
            } else {
                em.persist(agenda);
                em.flush();
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
        }

        return "agendaLista.xhtml";
    }

    /**
     * Procedimiento para borrar la cita de agenda actual
     * @return
     */
    public String AgendaBorrar() {

        em = entityManager();
        try {
            em.getTransaction().begin();
            Integer id = agenda.getId();
            Agenda dato = em.find(Agenda.class, id);
            em.remove(dato);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
        }

        return "agendaLista.xhtml";
    }

    /**
     * Procedimiento para ejecutar la cita seleccionada
     * @return
     */
    public String AgendaRealizar() {

        // --- Asignar los valores por defecto de la nueva visita --------------------------------
        this.controladorContratos.setCliente(this.agenda.getIdCliente());
        this.controladorContratos.getObjeto().LeerCliente(this.agenda.getIdCliente());
        this.controladorContratos.setDescripcion(this.agenda.getDescripcion());
        this.controladorContratos.setSp(this.agenda.getSp());
        this.controladorContratos.setMotivo(1);
        this.controladorContratos.setTrabajo(1);
        this.controladorContratos.setMantenimiento(true);
        this.controladorContratos.setCargo(false);

        // --- Procesar la cita actual y crear nueva entrada -------------------------------------
        Calendar calendar = new GregorianCalendar();
        Date currentDate = calendar.getTime();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_YEAR, this.getAgenda().getPeriodicidad());
        this.getAgenda().setFechaProxima(calendar.getTime());
        this.getAgenda().setFechaUltima(currentDate);
        this.AgendaAceptar();

        // --- Procesa el paso a generar visita --------------------------------------------------
        return "/contratos/visitas/visitasDatos.xhtml";

    }

    /**
     * Procedimiento para crear una nueva entrada en la agenda
     * @return
     */
    public String AgendaNueva() {
        this.setAgenda(new Agenda());
        this.agenda.setId(0);
        this.agenda.setIdCliente(controladorContratos.getCliente());
        this.agenda.setCliente(this.controladorContratos.getObjeto().getClientes());
        this.agenda.setFechaAlta(new Date());
        this.setSwAgenda(false);
        return "/contratos/agenda/agendaDatos.xhtml";
    }

}
