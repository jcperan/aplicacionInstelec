/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.com.icontaweb.contratos.controladores;

/**
 *
 * @author jcperan
 */

import es.com.icontaweb.contratos.modelos.Clientes;
import es.com.icontaweb.contratos.modelos.Representante;
import es.com.icontaweb.contratos.modelos.Visitas;
import es.com.icontaweb.controladores.ControlLogin;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.servlet.ServletContext;

/**
 * Controlador para la gestión de la agenda
 * @author jcperan
 */
@ManagedBean
@SessionScoped
public class ControladorIntranet implements Serializable {
    
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
    
    @ManagedProperty(value = "#{ControlLogin}")
    private ControlLogin controlLogin = new ControlLogin();
    public void setControlLogin(ControlLogin controlLogin) { this.controlLogin = controlLogin; }
    public ControlLogin getControlLogin() { return controlLogin; }
    
    @ManagedProperty(value = "#{ControladorContratos}")
    private ControladorContratos controladorContratos = new ControladorContratos();
    
    public void setControladorContratos(ControladorContratos controladorContratos) {
        this.controladorContratos = controladorContratos;
    }
    
    public ControladorContratos getControladorContratos() {
        return controladorContratos;
    }
        
    private List<Clientes> listaClientes = new ArrayList<Clientes>();

    public List<Clientes> getListaClientes() {

        em = entityManager();   
        try {
            listaClientes.clear();
            
            int idRepresentante = getControlLogin().getLogin().getUsuarios().getIdRpresentante();
            String sqlquery  = "select c from Clientes c where c.idRepresentante = :representante ";
                   sqlquery += "and c.visible = 1 ";
                   sqlquery += "order by c.nombre";
            Query q = em.createQuery(sqlquery);
            q.setParameter("representante", idRepresentante);
            listaClientes = q.getResultList();
        } catch (Exception e) {
            System.out.println("INTRANET " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            if (em.isOpen()) em.close();
        }
        
        return listaClientes;
    }

    public void setListaClientes(List<Clientes> listaClientes) {
        this.listaClientes = listaClientes;
    }
    
    public void botonCliente(ActionEvent event) {

        Integer id = new Integer(((UIParameter) event.getComponent().getFacet("id")).getValue().toString());

        em = entityManager();
        try {
            this.cliente = em.find(Clientes.class, id);
            this.controladorContratos.getObjeto().LeerCliente(id);            
        } catch (Exception e) {
            System.out.println("INTRANET " + (new Date()).toString() + " " + e.getMessage());
        }

    }
    
    /*
    *       Objeto para el acceso a los datos del representante
    */
    private String nombreRepresentante;
    public String getNombreRepresentante() {
        
        Integer idRepresentante = getControlLogin().getLogin().getUsuarios().getIdRpresentante();
        Representante representante;

        em = entityManager();
        try {
            representante = em.find(Representante.class, idRepresentante);
            nombreRepresentante = representante.getNombre();
        } catch (Exception e) {
            System.out.println("INTRANET " + (new Date()).toString() + " " + e.getMessage());
        }
        return nombreRepresentante;
    }
    
    /*
    *       Objeto para el acceso a los datos del cliente
    */
    private Clientes cliente = new Clientes();
    public Clientes getCliente() {
        
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String realPath = (String) servletContext.getRealPath("/");
        
        if (cliente.getFoto() == null) {
            File fi = new File(realPath + "/resources/no_disponible.jpg");
            byte[] foto = null;
            try {            
                foto = Files.readAllBytes(fi.toPath());
            } catch (IOException ex) {
                Logger.getLogger(ControladorIntranet.class.getName()).log(Level.SEVERE, null, ex);
            }
            cliente.setFoto(foto);
        }
        return cliente; 
    }
    public void setCliente(Clientes cliente) { this.cliente = cliente; }
    

    private List<Visitas> listaTrabajos = new ArrayList<Visitas>();

    public List<Visitas> getListaTrabajos() {

        em = entityManager();   
        try {
            listaTrabajos.clear();
            
            String sqlquery  = "select v from Visitas v where v.clientes.id = :cliente ";
                   sqlquery += "and v.visible = 1 ";
                   sqlquery += "order by v.fecha desc";
            Query q = em.createQuery(sqlquery);
            q.setParameter("cliente", this.cliente.getId());
            listaTrabajos = q.getResultList();
        } catch (Exception e) {
            System.out.println("INTRANET " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            if (em.isOpen()) em.close();
        }
        
        return listaTrabajos;
    }

    public void setListaTrabajos(List<Visitas> listaTrabajos) {
        this.listaTrabajos = listaTrabajos;
    }

    public void botonTrabajo(ActionEvent event) {

    }
    
}
