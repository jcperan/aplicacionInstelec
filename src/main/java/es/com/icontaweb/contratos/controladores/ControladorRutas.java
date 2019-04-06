package es.com.icontaweb.contratos.controladores;

import es.com.icontaweb.contratos.modelos.Rutas;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
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
 * Controlador para la gestión de rutas
 * @author jcperan
 */
@ManagedBean
@SessionScoped
public class ControladorRutas implements Serializable {
    
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
     * Lista de Rutas
     */
    private List<Rutas> listaRutas = new ArrayList<>();

    public void setListaRutas(List<Rutas> listaRutas) {
        this.listaRutas = listaRutas;
    }
    
    public List<Rutas> getListaRutas() {
        
        em = entityManager();
        try {
            listaRutas.clear();
            
            String sqlquery = "select r from Rutas r";
            Query q = em.createQuery(sqlquery);
            listaRutas = q.getResultList();
        } catch (Exception e) {
            System.out.println("icontaweb " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            if (em.isOpen()) em.close();
        }

        return listaRutas;
    }
    
    private Rutas ruta;

    public Rutas getRuta() {
        return ruta;
    }

    public void setRuta(Rutas ruta) {
        this.ruta = ruta;
    }
    
    private boolean swNuevo = false;

    public boolean isSwNuevo() {
        return swNuevo;
    }

    public void setSwNuevo(boolean swNuevo) {
        this.swNuevo = swNuevo;
    }
    
    public void rutasEditar(ActionEvent event) {
        Integer id = new Integer(((UIParameter) event.getComponent().getFacet("id")).getValue().toString());
        swNuevo = false;
        em = entityManager();
        try {
            ruta = em.find(Rutas.class, id);
        } catch (Exception e) {
            System.out.println("icontaweb " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            if (em.isOpen()) em.close();
        }
    }
    
    public String rutasNuevo() {
        this.ruta = new Rutas();
        swNuevo = true;
        return "/contratos/tablas/rutasDatos.xhtml";
    }

    public String rutasGrabar() {
        em = entityManager();
        try {            
            em.getTransaction().begin();
            if (swNuevo) {
            em.persist(ruta);
                em.flush();
            } else {
                Integer id = ruta.getId();
                Rutas rutaModificar = em.find(Rutas.class, id);
                rutaModificar = ruta;
                em.merge(rutaModificar);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("icontaweb " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            if (em.isOpen()) em.close();
        }
        
        return "/contratos/tablas/rutasLista.xhtml";
    }
    
    public void rutasBorrar() {
        em = entityManager();
        try {            
            em.getTransaction().begin();
            Integer id = ruta.getId();
            ruta = em.find(Rutas.class, id);
            em.remove(ruta);
            em.flush();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("icontaweb " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            if (em.isOpen()) em.close();
        }
        
    }
    
}
