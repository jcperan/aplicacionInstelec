package es.com.icontaweb.contratos.controladores;

import java.util.Date;

import es.com.icontaweb.contratos.objetos.Contratos;
import es.com.icontaweb.controladores.ControlLogin;
import es.com.icontaweb.modelos.Usuarios;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

@SessionScoped
@ManagedBean(name = "controladorAvisos")
public class ControladorAvisos implements Serializable {

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
    
    /**
     * Objeto para el acceso a los datos y funciones de acceso a la base de
     * datos.
     */
    private Contratos objeto = new Contratos();

    public void setObjeto(Contratos objeto) {
        this.objeto = objeto;
    }

    public Contratos getObjeto() {
        return objeto;
    }
    
    @ManagedProperty(value = "#{ControlLogin}")
    private ControlLogin controlLogin = new ControlLogin();
    public void setControlLogin(ControlLogin controlLogin) { this.controlLogin = controlLogin; }
    public ControlLogin getControlLogin() { return controlLogin; }
    
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

    @SuppressWarnings("deprecation")
    public String getHora() {
        setHora(new String((new Date().getHours()) + ":" + (new Date().getMinutes())));
        return hora;
    }
    
    /**
     * Lista de Usuarios/Operarios
     */
    private List<SelectItem> listaUsuarios = new ArrayList<>();

    public void setListaUsuarios(List<SelectItem> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }
    
    public List<SelectItem> getListaUsuarios() {
        
        em = entityManager();   
        try {
            listaUsuarios.clear();
            
            listaUsuarios.add(new SelectItem("null","TODOS"));
            
            String sqlquery = "select u from Usuarios u";
            Query q = em.createQuery(sqlquery);
            List<Usuarios> lista = q.getResultList();
            Iterator<Usuarios> it = lista.iterator();
            while (it.hasNext()) {
                Usuarios datos = (Usuarios) it.next();
                listaUsuarios.add(new SelectItem(datos.getUsuario(), datos.getNombre()));
            }
        } catch (Exception e) {
            System.out.println("icontaweb " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            if (em.isOpen()) em.close();
        }

        return listaUsuarios;
    }
    
}
