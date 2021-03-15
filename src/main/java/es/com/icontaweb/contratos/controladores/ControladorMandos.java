package es.com.icontaweb.contratos.controladores;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

/**
 * Controlador para la gestión de la agenda
 * @author jcperan
 */
@ManagedBean
@SessionScoped
public class ControladorMandos implements Serializable {
    
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
     * Lista de Codificaciones
     */
    private final List<SelectItem> listaCodificaciones = new ArrayList<>();    
    public List<SelectItem> getListaCodificaciones() {
        
        listaCodificaciones.clear();
        listaCodificaciones.add(new SelectItem(0,"No Procede"));
        listaCodificaciones.add(new SelectItem(1,"Antigua"));
        listaCodificaciones.add(new SelectItem(2,"Nueva"));
        
        return listaCodificaciones;
    }

    /**
     * Lista de Instalador
     */
    private final List<SelectItem> listaInstalador = new ArrayList<>();    
    public List<SelectItem> getListaInstalador() {
        
        listaInstalador.clear();
        listaInstalador.add(new SelectItem(  0,"No Procede"));
        listaInstalador.add(new SelectItem( 10," 10"));
        listaInstalador.add(new SelectItem( 25," 25"));
        listaInstalador.add(new SelectItem( 50," 50"));
        listaInstalador.add(new SelectItem( 75," 75"));
        listaInstalador.add(new SelectItem(100,"100"));
        
        return listaInstalador;
    }

    /**
     * Lista de Candado
     */
    private final List<SelectItem> listaCandado = new ArrayList<>();    
    public List<SelectItem> getListaCandado() {
        
        listaCandado.clear();
        listaCandado.add(new SelectItem(0,"No Procede"));
        listaCandado.add(new SelectItem(1,"Cerrado"));
        listaCandado.add(new SelectItem(2,"Abierto"));
        
        return listaCandado;
    }
    
}
