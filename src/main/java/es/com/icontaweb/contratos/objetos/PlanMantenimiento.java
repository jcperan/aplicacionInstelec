package es.com.icontaweb.contratos.objetos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import es.com.icontaweb.contratos.modelos.ListaMantenimiento;
import es.com.icontaweb.contratos.modelos.Mantenimiento;
import es.com.icontaweb.contratos.modelos.MantenimientosDetalle;
import es.com.icontaweb.contratos.modelos.MantenimientosDetallePK;
import es.com.icontaweb.contratos.modelos.MantenimientosGrupo;
import es.com.icontaweb.contratos.modelos.MantenimientosGrupoPK;
import es.com.icontaweb.contratos.modelos.VisitasControles;
import es.com.icontaweb.contratos.modelos.VisitasControlesPK;

public class PlanMantenimiento {

    public PlanMantenimiento() {

    }

    /**
     * *******************************************************************************************
     * Objetos de la Persistencia de Datos
	 ********************************************************************************************
     */
    @PersistenceUnit
    protected EntityManagerFactory emf;

    @PersistenceContext
    protected EntityManager em;

    public EntityManager entityManager() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("iContratos");
        }
        return emf.createEntityManager();
    }

    /**
     * *******************************************************************************************
     * Clase del mantenimiento actual
	 ********************************************************************************************
     */
    private Mantenimiento mantenimiento = new Mantenimiento();

    public void setMantenimiento(Mantenimiento mantenimiento) {
        this.mantenimiento = mantenimiento;
    }

    public Mantenimiento getMantenimiento() {
        return mantenimiento;
    }

    private MantenimientosGrupo mantenimientoGrupo = new MantenimientosGrupo();

    public MantenimientosGrupo getMantenimientoGrupo() {
        return mantenimientoGrupo;
    }

    public void setMantenimientoGrupo(MantenimientosGrupo mantenimientoGrupo) {
        this.mantenimientoGrupo = mantenimientoGrupo;
    }

    private MantenimientosDetalle mantenimientoDetalle = new MantenimientosDetalle();

    public MantenimientosDetalle getMantenimientoDetalle() {
        return mantenimientoDetalle;
    }

    public void setMantenimientoDetalle(MantenimientosDetalle mantenimientoDetalle) {
        this.mantenimientoDetalle = mantenimientoDetalle;
    }

    /**
     * Procedimiento para grabar un nuevo mantenimiento
     */
    public void Grabar(boolean modificando) {
        em = entityManager();
        try {
            em.getTransaction().begin();
            if (modificando) {
                em.merge(mantenimiento);
            } else {
                em.persist(mantenimiento);
            }
            em.flush();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
        }
    }

    /**
     * Procedimiento para eliminar un mantenimiento
     */
    public void Borrar() {

        em = entityManager();
        try {
            em.getTransaction().begin();
            Mantenimiento lectura = em.find(Mantenimiento.class, mantenimiento.getId());
            em.remove(lectura);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            em.close();
        }

    }

    /**
     * *******************************************************************************************
     * Lista de Consulta de Grupos
	 ********************************************************************************************
     */
    private List<MantenimientosGrupo> consultaGrupo = new ArrayList<MantenimientosGrupo>();

    public void setConsultaGrupo(List<MantenimientosGrupo> consultaGrupo) {
        this.consultaGrupo = consultaGrupo;
    }

    @SuppressWarnings("unchecked")
    public List<MantenimientosGrupo> getConsultaGrupo() {

        consultaGrupo.clear();
        em = entityManager();
        try {
            Query consulta = em.createQuery("select m from MantenimientosGrupo m where m.id.idGrupo = :id order by m.id.grupo ");
            consulta.setParameter("id", this.mantenimiento.getId());
            consultaGrupo = consulta.getResultList();
        } catch (Exception e) {
            System.out.println("contratos " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            em.close();
        }
        return consultaGrupo;

    }

    /**
     * Lectura del grupo
     *
     * @param id
     * @return
     */
    public boolean LeerGrupo(MantenimientosGrupoPK idGrupo) {

        boolean resultado = false;
        em = entityManager();
        try {
            this.mantenimientoGrupo = em.find(MantenimientosGrupo.class, idGrupo);
            resultado = true;
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
            resultado = false;
        } finally {
            em.close();
        }
        return resultado;

    }

    public void ActualizarGrupo() {
        em = entityManager();
        try {
            em.getTransaction().begin();
            em.merge(this.mantenimientoGrupo);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
        }
    }

    /**
     * Procedimiento para agregar un nuevo grupo al mantenimiento actual
     */
    public void GrabarGrupo() {
        em = entityManager();
        try {
            em.getTransaction().begin();
            int resultado;
            try {
                String sqlquery = "select max(g.id.grupo) from MantenimientosGrupo g where g.id.idGrupo = " + new Integer(mantenimiento.getId()).toString();
                Query consulta = em.createQuery(sqlquery);
                resultado = (Integer) consulta.getSingleResult();
            } catch (Exception e) {
                resultado = 0;
            }
            // componer la clave
            MantenimientosGrupoPK clave = new MantenimientosGrupoPK();
            clave.setIdGrupo(mantenimiento.getId());
            clave.setGrupo(resultado + 1);
            // componer el registro
            this.mantenimientoGrupo.setId(clave);
            // realizar la grabacion
            em.persist(mantenimientoGrupo);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
        }
    }

    /**
     * Procedimiento para eliminar un grupo
     */
    public void BorrarGrupo(MantenimientosGrupoPK idGrupo) {

        em = entityManager();
        try {
            em.getTransaction().begin();
            MantenimientosGrupo lectura = em.find(MantenimientosGrupo.class, idGrupo);
            em.remove(lectura);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            em.close();
        }

    }

    /**
     * *******************************************************************************************
     * Lista de Consulta de Detalles
	 ********************************************************************************************
     */
    private List<MantenimientosDetalle> consultaDetalle = new ArrayList<MantenimientosDetalle>();

    public void setConsultaDetalle(List<MantenimientosDetalle> consultaDetalle) {
        this.consultaDetalle = consultaDetalle;
    }

    @SuppressWarnings("unchecked")
    public List<MantenimientosDetalle> getConsultaDetalle() {

        consultaDetalle.clear();
        em = entityManager();
        try {
            Query consulta = em.createQuery("select m from MantenimientosDetalle m where m.id.idDetalle = :id and m.id.grupo = :grupo order by m.id.detalle ");
            consulta.setParameter("id", this.mantenimiento.getId());
            consulta.setParameter("grupo", this.mantenimientoGrupo.getId().getGrupo());
            consultaDetalle = consulta.getResultList();
        } catch (Exception e) {
            System.out.println("contratos " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            em.close();
        }
        return consultaDetalle;

    }

    /**
     * Lectura del grupo
     *
     * @param id
     * @return
     */
    public boolean LeerDetalle(MantenimientosDetallePK idDetalle) {

        boolean resultado = false;
        em = entityManager();
        try {
            this.mantenimientoDetalle = em.find(MantenimientosDetalle.class, idDetalle);
            resultado = true;
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
            resultado = false;
        } finally {
            em.close();
        }
        return resultado;

    }

    /**
     * Actualizar datos del detalle actual
     */
    public void ActualizarDetalle() {
        em = entityManager();
        try {
            em.getTransaction().begin();
            em.merge(this.mantenimientoDetalle);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
        }
    }

    /**
     * Procedimiento para agregar un nuevo detalle al grupo actual
     */
    public void GrabarDetalle() {
        em = entityManager();
        try {
            em.getTransaction().begin();
            int resultado;
            try {
                String sqlquery = "select max(d.id.detalle) from MantenimientosDetalle d where d.id.idDetalle = :id and d.id.grupo = :grupo ";
                Query consulta = em.createQuery(sqlquery);
                consulta.setParameter("id", this.getMantenimientoGrupo().getId().getIdGrupo());
                consulta.setParameter("grupo", this.getMantenimientoGrupo().getId().getGrupo());
                resultado = (Integer) consulta.getSingleResult();
            } catch (Exception e) {
                resultado = 0;
            }
            // componer la clave
            MantenimientosDetallePK clave = new MantenimientosDetallePK();
            clave.setIdDetalle(mantenimiento.getId());
            clave.setGrupo(this.getMantenimientoGrupo().getId().getGrupo());
            clave.setDetalle(resultado + 1);
            // componer el registro
            this.mantenimientoDetalle.setId(clave);
            // realizar la grabacion
            em.persist(mantenimientoDetalle);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
        }
    }

    /**
     * Procedimiento para eliminar un detalle
     */
    public void BorrarDetalle(MantenimientosDetallePK idDetalle) {

        em = entityManager();
        try {
            em.getTransaction().begin();
            MantenimientosDetalle lectura = em.find(MantenimientosDetalle.class, idDetalle);
            em.remove(lectura);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            em.close();
        }

    }

    /**
     * *******************************************************************************************
     * Lista de Consulta de Mantenimientos
	 ********************************************************************************************
     */
    private List<Mantenimiento> consultaMantenimientos = new ArrayList<Mantenimiento>();

    public void setConsultaMantenimientos(List<Mantenimiento> consultaMantenimientos) {
        this.consultaMantenimientos = consultaMantenimientos;
    }

    @SuppressWarnings("unchecked")
    public List<Mantenimiento> getConsultaMantenimientos() {

        consultaMantenimientos.clear();
        em = entityManager();
        try {
            Query consulta = em.createQuery("select m from Mantenimiento m order by m.id ");
            consultaMantenimientos = consulta.getResultList();
        } catch (Exception e) {
            System.out.println("contratos " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            em.close();
        }
        return consultaMantenimientos;

    }

    /**
     * Lectura del mantenimiento
     *
     * @param id
     * @return
     */
    public boolean LeerMantenimiento(Integer id) {

        boolean resultado = false;
        em = entityManager();
        try {
            this.mantenimiento = em.find(Mantenimiento.class, id);
            resultado = true;
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
            resultado = false;
        } finally {
            em.close();
        }
        return resultado;

    }

    public void GrabarVisita(int visita, ListaMantenimiento registro) {

        em = entityManager();
        try {
            em.getTransaction().begin();
            VisitasControlesPK clave = new VisitasControlesPK();
            clave.setIdVisita(visita);
            clave.setIdMantenimiento(registro.getId_mantenimiento());
            clave.setIdGrupo(registro.getId_grupo());
            clave.setIdDetalle(registro.getId_detalle());
            VisitasControles nuevoMantenimiento = new VisitasControles();
            nuevoMantenimiento.setId(clave);
            if (registro.isValor()) {
                nuevoMantenimiento.setValor((byte) 1);
            } else {
                nuevoMantenimiento.setValor((byte) 0);
            }
            em.persist(nuevoMantenimiento);
            em.flush();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
        }

    }

}
