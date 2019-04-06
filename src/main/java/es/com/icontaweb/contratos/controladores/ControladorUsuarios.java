package es.com.icontaweb.contratos.controladores;

import es.com.icontaweb.contratos.modelos.Rutas;
import es.com.icontaweb.contratos.modelos.UsuarioRuta;
import es.com.icontaweb.contratos.utilidades.DetalleUsuariosRutas;
import es.com.icontaweb.contratos.vistas.VistaUsuariosRutas;
import es.com.icontaweb.contratos.vistas.VistaUsuariosRutasPK;
import es.com.icontaweb.modelos.Horario;
import es.com.icontaweb.modelos.Usuarios;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
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
 * Controlador para la gestión de usuarios
 *
 * @author jcperan
 */
@ManagedBean
@SessionScoped
public class ControladorUsuarios implements Serializable {

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
     * Lista de Usuarios
     */
    private List<Usuarios> listaUsuarios = new ArrayList<>();

    public void setListaUsuarios(List<Usuarios> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public List<Usuarios> getListaUsuarios() {

        em = entityManager();
        try {
            listaUsuarios.clear();
            String sqlquery = "select u from Usuarios u";
            Query q = em.createQuery(sqlquery);
            listaUsuarios = q.getResultList();
        } catch (Exception e) {
            System.out.println("icontaweb " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }

        return listaUsuarios;
    }

    private Usuarios usuario;

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    private boolean swNuevo = false;

    public boolean isSwNuevo() {
        return swNuevo;
    }

    public void setSwNuevo(boolean swNuevo) {
        this.swNuevo = swNuevo;
    }

    public void usuariosEditar(ActionEvent event) {
        String id = ((UIParameter) event.getComponent().getFacet("id")).getValue().toString();
        swNuevo = false;
        em = entityManager();
        try {
            usuario = em.find(Usuarios.class, id);
        } catch (Exception e) {
            System.out.println("icontaweb " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    public String usuariosNuevo() {
        this.usuario = new Usuarios();
        swNuevo = true;
        return "/contratos/tablas/usuariosDatos.xhtml";
    }

    public String usuariosGrabar() {
        em = entityManager();
        try {
            em.getTransaction().begin();
            if (swNuevo) {
                em.persist(usuario);
                em.flush();
            } else {
                em.merge(usuario);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("icontaweb " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }

        this.validarPermitida();
        return "/contratos/tablas/usuariosLista.xhtml";
    }

    public void usuariosBorrar() {
        
        String sqlquery = "";
        Query q;
        
        em = entityManager();
        try {
            em.getTransaction().begin();
            String id = usuario.getUsuario();
            usuario = em.find(Usuarios.class, id);

            sqlquery = "delete from Horario h where h.usuario = :usuario";
            q = em.createQuery(sqlquery);
            q.setParameter("usuario", usuario.getUsuario());
            q.executeUpdate();
            
            sqlquery = "delete from UsuarioRuta ur where ur.usuario=:usuario";
            q = em.createQuery(sqlquery);
            q.setParameter("usuario", usuario.getUsuario());
            q.executeUpdate();

            em.remove(usuario);
            em.flush();
            
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("icontaweb " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }

    }

    /**
     * Lista de Rutas segun Usuario
     */
    private List<DetalleUsuariosRutas> listaUsuariosRutas = new ArrayList<>();

    public void setListaUsuariosRutas(List<DetalleUsuariosRutas> listaUsuariosRutas) {
        this.listaUsuariosRutas = listaUsuariosRutas;
    }

    public List<DetalleUsuariosRutas> getListaUsuariosRutas() {

        em = entityManager();
        try {
            listaUsuariosRutas.clear();
            String sqlquery = "select r from Rutas r";
            Query q = em.createQuery(sqlquery);
            List<Rutas> listaRutas = q.getResultList();
            for (Rutas ruta : listaRutas) {
                DetalleUsuariosRutas detalle = new DetalleUsuariosRutas();
                detalle.setId(ruta.getId());
                detalle.setRuta(ruta.getDenominacion());

                try {
                    sqlquery = "select ur from UsuarioRuta ur where ur.ruta=:ruta and ur.usuario=:usuario";
                    q = em.createQuery(sqlquery);
                    q.setParameter("ruta", ruta.getId());
                    q.setParameter("usuario", usuario.getUsuario());
                    UsuarioRuta usuarioRuta = (UsuarioRuta) q.getSingleResult();
                    if (usuarioRuta == null) {
                        detalle.setPermitida(false);
                    } else {
                        detalle.setPermitida(true);
                    }
                } catch (Exception e) {
                    detalle.setPermitida(false);
                }

                listaUsuariosRutas.add(detalle);
            }
        } catch (Exception e) {
            System.out.println("icontaweb " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }

        return listaUsuariosRutas;
    }

    public void validarPermitida() {

        for (DetalleUsuariosRutas detalle : listaUsuariosRutas) {

            Integer ruta = detalle.getId();
            boolean permitida = detalle.isPermitida();

            em = entityManager();
            try {
                em.getTransaction().begin();
                if (permitida) {
                    VistaUsuariosRutasPK clave = new VistaUsuariosRutasPK();
                    clave.setRuta(ruta);
                    clave.setUsuario(usuario.getUsuario());
                    VistaUsuariosRutas usuariosRutas = em.find(VistaUsuariosRutas.class, clave);
                    if (usuariosRutas == null) {
                        UsuarioRuta usuarioRuta = new UsuarioRuta();
                        usuarioRuta.setRuta(ruta);
                        usuarioRuta.setUsuario(usuario.getUsuario());
                        em.persist(usuarioRuta);
                        em.flush();
                    }
                } else {
                    String sqlquery = "delete from UsuarioRuta ur where ur.ruta=:ruta and ur.usuario=:usuario";
                    Query q = em.createQuery(sqlquery);
                    q.setParameter("ruta", ruta);
                    q.setParameter("usuario", usuario.getUsuario());
                    q.executeUpdate();
                }
                em.getTransaction().commit();
            } catch (Exception e) {
                System.out.println("icontaweb " + (new Date()).toString() + " " + e.getMessage());
            } finally {
                if (em.isOpen()) {
                    em.clear();
                    em.close();
                }
            }

        }
    }
    
    
    
    /**
     * Lista de Rutas segun Usuario
     */
    private List<Horario> listaHorario= new ArrayList<>();

    public void setListaHorario(List<Horario> listaHorario) {
        this.listaHorario = listaHorario;
    }

    public List<Horario> getListaHorario() {

        em = entityManager();
        try {
            listaHorario.clear();
            String sqlquery = "select h from Horario h where h.usuario = :usuario order by h.dia, h.desde";
            Query q = em.createQuery(sqlquery);
            q.setParameter("usuario", usuario.getUsuario());
            listaHorario = q.getResultList();
        } catch (Exception e) {
            System.out.println("icontaweb " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            if (em.isOpen()) em.close();
        }

        return listaHorario;
    }
    
    private List<SelectItem> listaDias = new ArrayList<>();

    public List<SelectItem> getListaDias() {
        listaDias.clear();
        listaDias.add(new SelectItem(1,"Lunes"));
        listaDias.add(new SelectItem(2,"Martes"));
        listaDias.add(new SelectItem(3,"Miercoles"));
        listaDias.add(new SelectItem(4,"Jueves"));
        listaDias.add(new SelectItem(5,"Viernes"));
        listaDias.add(new SelectItem(6,"Sabado"));
        listaDias.add(new SelectItem(7,"Domingo"));
        return listaDias;
    }

    public void setListaDias(List<SelectItem> listaDias) {
        this.listaDias = listaDias;
    }
    
    private Horario nuevoHorario = new Horario();
    public Horario getNuevoHorario() { return nuevoHorario; }
    public void setNuevoHorario(Horario nuevoHorario) { this.nuevoHorario = nuevoHorario; }

    /**
     * Graba la nueva linea de horario
     * @param event 
     */
    public void horarioAgregar(ActionEvent event) {

        em = entityManager();
        try {
            em.getTransaction().begin();
            nuevoHorario.setUsuario(usuario.getUsuario());
            em.persist(nuevoHorario);
            em.flush();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("icontaweb " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            if (em.isOpen()) em.close();
        }
        
        nuevoHorario = new Horario();
        
    }
    
    /**
     * Eliminar la linea de horario seleccionada
     * @param event 
     */
    public void horarioEliminar(ActionEvent event) {

        Integer id = new Integer(event.getComponent().getAttributes().get("data-id").toString());
        
        em = entityManager();
        try {
            em.getTransaction().begin();
            Horario horario = em.find(Horario.class, id);
            em.remove(horario);
            em.flush();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("icontaweb " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }

    }

}
