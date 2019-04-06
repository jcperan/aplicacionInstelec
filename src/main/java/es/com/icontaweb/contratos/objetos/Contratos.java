package es.com.icontaweb.contratos.objetos;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import es.com.icontaweb.contratos.modelos.Agenda;
import es.com.icontaweb.contratos.modelos.Avisos;
import es.com.icontaweb.contratos.modelos.Clientes;
import es.com.icontaweb.contratos.modelos.Mantenimiento;
import es.com.icontaweb.contratos.modelos.Motivos;
import es.com.icontaweb.contratos.modelos.Representante;
import es.com.icontaweb.contratos.modelos.Trabajos;
import es.com.icontaweb.contratos.modelos.Visitas;
import es.com.icontaweb.rutinas.Rutinas;
import java.text.DecimalFormat;

public class Contratos {

    public Contratos() {

    }

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
     * Lista para el acceso a los clientes
     */
    private List<SelectItem> listaClientes = new ArrayList<SelectItem>();

    public List<SelectItem> getListaClientes() {
        this.CreaListaClientes();
        return listaClientes;
    }

    public void setListaClientes(List<SelectItem> listaClientes) {
        this.listaClientes = listaClientes;
    }

    @SuppressWarnings("unchecked")
    public void CreaListaClientes() {

        listaClientes.clear();
        em = entityManager();
        try {
            Query q = em.createQuery("select c from Clientes c order by c.nombre");
            List<Clientes> lista = q.getResultList();
            Iterator<Clientes> it = lista.iterator();
            while (it.hasNext()) {
                Clientes cli = (Clientes) it.next();
                listaClientes.add(new SelectItem(cli.getId(), cli.getNombre()));
            }
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            em.close();
        }

    }

    /**
     * Lista para el acceso a los Motivos
     */
    private List<SelectItem> listaMotivos = new ArrayList<SelectItem>();

    public List<SelectItem> getListaMotivos() {
        this.CreaListaMotivos();
        return listaMotivos;
    }

    public void setListaMotivos(List<SelectItem> listaMotivos) {
        this.listaMotivos = listaMotivos;
    }

    @SuppressWarnings("unchecked")
    public void CreaListaMotivos() {

        listaMotivos.clear();
        em = entityManager();
        try {
            Query q = em.createQuery("select m from Motivos m order by m.motivo");
            List<Motivos> lista = q.getResultList();
            Iterator<Motivos> it = lista.iterator();
            while (it.hasNext()) {
                Motivos datos = (Motivos) it.next();
                listaMotivos.add(new SelectItem(datos.getId(), datos.getMotivo()));
            }
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            em.close();
        }

    }

    /**
     * *******************************************************************************************
     * Lista para el acceso a los Trabajos
     * *******************************************************************************************
     */
    private List<SelectItem> listaTrabajos = new ArrayList<SelectItem>();

    public List<SelectItem> getListaTrabajos() {
        this.CreaListaTrabajos();
        return listaTrabajos;
    }

    public void setListaTrabajos(List<SelectItem> listaTrabajos) {
        this.listaTrabajos = listaTrabajos;
    }

    @SuppressWarnings("unchecked")
    public void CreaListaTrabajos() {

        listaTrabajos.clear();
        em = entityManager();
        try {
            Query q = em.createQuery("select t from Trabajos t order by t.id");
            List<Trabajos> lista = q.getResultList();
            Iterator<Trabajos> it = lista.iterator();
            while (it.hasNext()) {
                Trabajos datos = (Trabajos) it.next();
                listaTrabajos.add(new SelectItem(datos.getId(), datos.getTrabajo()));
            }
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            em.close();
        }

    }

    /**
     * Objetos para el acceso a clientes
     */
    private Clientes clientes = new Clientes();

    public Clientes getClientes() {
        return clientes;
    }

    public void setClientes(Clientes clientes) {
        this.clientes = clientes;
    }

    public boolean LeerCliente(Integer id) {
        boolean resultado = false;
        em = entityManager();
        try {
            this.clientes = em.find(Clientes.class, id);
            resultado = true;
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
            resultado = false;
        } finally {
            em.close();
        }
        return resultado;
    }

    public boolean NuevoCliente() {
        boolean resultado = false;
        try {
            clientes = new Clientes();
            resultado = true;
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
        }
        return resultado;
    }

    public void GrabaCliente() {

        em = entityManager();
        try {
            em.getTransaction().begin();
            em.persist(clientes);
            em.flush();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
        }

    }

    public void ActualizaCliente(Integer id) {

        em = entityManager();
        try {
            em.getTransaction().begin();
            Clientes actualizar = em.find(Clientes.class, id);
            actualizar = clientes;
            em.merge(actualizar);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            em.close();
        }

    }

    /**
     * Registro Motivos
     */
    private Motivos motivos = new Motivos();

    public Motivos getMotivos() {
        return motivos;
    }

    public void setMotivos(Motivos motivos) {
        this.motivos = motivos;
    }

    public boolean LeerMotivo(Integer id) {
        boolean resultado = false;
        em = entityManager();
        try {
            this.motivos = em.find(Motivos.class, id);
            resultado = true;
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
            resultado = false;
        } finally {
            em.close();
        }
        return resultado;
    }

    public boolean NuevoMotivo() {
        boolean resultado = false;
        try {
            motivos = new Motivos();
            resultado = true;
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
        }
        return resultado;
    }

    public void GrabaMotivo() {

        em = entityManager();
        try {
            em.getTransaction().begin();
            em.persist(motivos);
            em.flush();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void ActualizaMotivo(Integer id, String denominacion) {

        em = entityManager();
        try {
            em.getTransaction().begin();
            motivos = em.find(Motivos.class, id);
            motivos.setMotivo(denominacion);
            em.merge(motivos);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void BorraMotivo(Integer id) {

        em = entityManager();
        try {
            em.getTransaction().begin();
            motivos = em.find(Motivos.class, id);
            em.remove(motivos);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            em.close();
        }

    }

    private Trabajos trabajos = new Trabajos();

    public Trabajos getTrabajos() {
        return trabajos;
    }

    public void setTrabajos(Trabajos trabajos) {
        this.trabajos = trabajos;
    }

    public boolean LeerTrabajo(Integer id) {

        boolean resultado = false;

        em = entityManager();
        try {
            this.trabajos = em.find(Trabajos.class, id);
            resultado = true;
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
        }
        return resultado;
    }

    public boolean NuevoTrabajo() {
        boolean resultado = false;
        try {
            trabajos = new Trabajos();
            resultado = true;
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
        }
        return resultado;
    }

    public void GrabaTrabajo() {

        em = entityManager();
        try {
            em.getTransaction().begin();
            em.persist(trabajos);
            em.flush();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
        }

    }

    public void ActualizaTrabajo(Integer id, String denominacion) {

        em = entityManager();
        try {
            em.getTransaction().begin();
            trabajos = em.find(Trabajos.class, id);
            trabajos.setTrabajo(denominacion);
            em.merge(trabajos);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void BorraTrabajo(Integer id) {

        em = entityManager();
        try {
            em.getTransaction().begin();
            trabajos = em.find(Trabajos.class, id);
            em.remove(trabajos);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            em.close();
        }

    }

    /**
     * Registro Motivos
     */
    private Representante representante = new Representante();

    public Representante getRepresentante() {
        return representante;
    }

    public void setRepresentante(Representante representante) {
        this.representante = representante;
    }

    public boolean LeerRepresentante(Integer id) {
        boolean resultado = false;
        em = entityManager();
        try {
            this.representante = em.find(Representante.class, id);
            resultado = true;
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
            resultado = false;
        } finally {
            em.close();
        }
        return resultado;
    }

    public boolean NuevoRepresentante() {
        boolean resultado = false;
        try {
            representante = new Representante();
            resultado = true;
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
        }
        return resultado;
    }

    public void GrabaRepresentante() {

        em = entityManager();
        try {
            em.getTransaction().begin();
            em.persist(representante);
            em.flush();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void ActualizaRepresentante(Integer id, String nombre) {

        em = entityManager();
        try {
            em.getTransaction().begin();
            representante = em.find(Representante.class, id);
            representante.setNombre(nombre);
            em.merge(representante);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void BorraRepresentante(Integer id) {

        em = entityManager();
        try {
            em.getTransaction().begin();
            representante = em.find(Representante.class, id);
            em.remove(representante);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            em.close();
        }

    }

    /**
     * **********************************************************************************************************
     * Operaciones sobre el control de visitas
     * **********************************************************************************************************
     */
    private Visitas visitas = new Visitas();

    public Visitas getVisitas() {
        return visitas;
    }

    public void setVisitas(Visitas visitas) {
        this.visitas = visitas;
    }

    public boolean LeerVisita(Integer id) {

        boolean resultado = false;

        em = entityManager();
        try {
            this.visitas = em.find(Visitas.class, id);
            resultado = true;
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
        }
        return resultado;
    }

    public void GrabaVisita() {

        em = entityManager();
        try {
            em.getTransaction().begin();
            em.persist(visitas);
            em.flush();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
        }

        Rutinas.Log("VISITA GRABADA : " + new DecimalFormat("########").format(visitas.getId()));

    }

    public void ActualizarVisita(Visitas visita, int id) {

        em = entityManager();
        try {
            em.getTransaction().begin();
            Visitas actualizar = em.find(Visitas.class, id);
            actualizar = visita;
            em.merge(actualizar);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
        }

    }

    /**
     * Lista de Consulta de Visitas
     */
    private List<Visitas> ConsultaVisitas = new ArrayList<Visitas>();

    public void setConsultaVisitas(List<Visitas> consultaVisitas) {
        this.ConsultaVisitas = consultaVisitas;
    }

    public List<Visitas> getConsultaVisitas() {
        return ConsultaVisitas;
    }

    @SuppressWarnings("unchecked")
    public void ObtenerConsultaVisitas(Integer busqueda) {

        em = entityManager();
        try {
            Query consulta = em.createQuery("select v from Visitas v where v.clientes.id = :buscar order by v.fecha desc");
            consulta.setParameter("buscar", busqueda);
            // consulta.setMaxResults(50);
            ConsultaVisitas = consulta.getResultList();
        } catch (Exception e) {
            System.out.println("icontaweb " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            em.close();
        }

    }

    /**
     * *******************************************************************************************
     * Lista de Consulta de Motivos
     * *******************************************************************************************
     */
    private List<Motivos> consultaMotivos = new ArrayList<Motivos>();

    public void setConsultaMotivos(List<Motivos> consultaMotivos) {
        this.consultaMotivos = consultaMotivos;
    }

    @SuppressWarnings("unchecked")
    public List<Motivos> getConsultaMotivos() {
        em = entityManager();
        try {
            Query consulta = em.createQuery("select m from Motivos m order by m.motivo");
            consultaMotivos = consulta.getResultList();
        } catch (Exception e) {
            System.out.println("icontaweb " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            em.close();
        }
        return consultaMotivos;
    }

    /**
     * *******************************************************************************************
     * Lista de Consulta de Trabajos
     * *******************************************************************************************
     */
    private List<Trabajos> consultaTrabajos = new ArrayList<Trabajos>();

    public void setConsultaTrabajos(List<Trabajos> consultaTrabajos) {
        this.consultaTrabajos = consultaTrabajos;
    }

    @SuppressWarnings("unchecked")
    public List<Trabajos> getConsultaTrabajos() {
        em = entityManager();
        try {
            Query consulta = em.createQuery("select t from Trabajos t order by t.trabajo");
            consultaTrabajos = consulta.getResultList();
        } catch (Exception e) {
            System.out.println("icontaweb " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            em.close();
        }
        return consultaTrabajos;
    }

    /**
     * *******************************************************************************************
     * Lista de Consulta de Representantes
     * *******************************************************************************************
     */
    private List<Representante> consultaRepresentantes = new ArrayList<Representante>();

    public void setConsultaRepresentantes(List<Representante> consultaRepresentantes) {
        this.consultaRepresentantes = consultaRepresentantes;
    }

    @SuppressWarnings("unchecked")
    public List<Representante> getConsultaRepresentantes() {
        em = entityManager();
        try {
            Query consulta = em.createQuery("select r from Representante r order by r.nombre");
            consultaRepresentantes = consulta.getResultList();
        } catch (Exception e) {
            System.out.println("icontaweb " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            em.close();
        }
        return consultaRepresentantes;
    }

    /**
     * *******************************************************************************************
     * Lista de Consulta de Clientes
     * *******************************************************************************************
     */
    private List<Clientes> consultaClientes = new ArrayList<Clientes>();

    public void setConsultaClientes(List<Clientes> consultaClientes) {
        this.consultaClientes = consultaClientes;
    }

    public List<Clientes> getConsultaClientes() {
        return consultaClientes;
    }

    @SuppressWarnings("unchecked")
    public void BuscaClientes(String dato) {
        em = entityManager();
        try {
            Query consulta = em.createQuery("select c from Clientes c where c.nombre like :busca order by c.nombre");
            consulta.setParameter("busca", "%" + dato + "%");
            // consulta.setMaxResults(100);
            consultaClientes = consulta.getResultList();
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            em.close();
        }
    }

    private Avisos avisos = new Avisos();

    public Avisos getAvisos() {
        return avisos;
    }

    public void setAvisos(Avisos avisos) {
        this.avisos = avisos;
    }

    /**
     * Lectura de los datos de un aviso
     *
     * @param id
     * @return
     */
    public boolean LeerAviso(Integer id) {

        boolean resultado = false;

        em = entityManager();
        try {
            this.avisos = em.find(Avisos.class, id);
            resultado = true;
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
        }
        return resultado;
    }

    /**
     * Graba nuevo aviso
     */
    public void GrabaAviso() {

        em = entityManager();
        try {
            em.getTransaction().begin();
            em.persist(avisos);
            em.flush();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
        }

        Rutinas.Log("AVISO GENERADO : " + new DecimalFormat("########").format(avisos.getId()));

    }

    public void ActualizarAviso(Integer id) {

        em = entityManager();
        try {
            em.getTransaction().begin();
            Avisos actualizar = em.find(Avisos.class, id);
            actualizar = avisos;
            em.merge(actualizar);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
        }

        Rutinas.Log("AVISO ACTUALIZADO : " + new DecimalFormat("########").format(avisos.getId()));

    }

    /**
     * Lista de Consulta de Avisos
     */
    private List<Avisos> consultaAvisos = new ArrayList<Avisos>();

    public void setConsultaAvisos(List<Avisos> consultaAvisos) {
        this.consultaAvisos = consultaAvisos;
    }

    public List<Avisos> getConsultaAvisos() {
        return consultaAvisos;
    }

    
    public void ObtenerConsultaAvisos(Integer cliente, String estado, String usuario, String nombre, Integer prioridad) {
        this.ObtenerConsultaAvisos(cliente, estado, usuario, nombre, prioridad, 0);
    }
    
    @SuppressWarnings("unchecked")
    public void ObtenerConsultaAvisos(Integer cliente, String estado, String usuario, String nombre, Integer prioridad, Integer ruta) {

        em = entityManager();
        try {
            String sqlquery = "select a from Avisos a where a.cliente.nombre like :nombre ";
            if (cliente != 0)  sqlquery += "and a.cliente.id = :cliente ";
            if (estado != "")  sqlquery += "and a.estado = :estado ";
            if (ruta > 0)      sqlquery += "and a.cliente.idRuta = :ruta ";
            if (!usuario.equals("null"))  sqlquery += "and a.usuario = :usuario ";

            switch (prioridad) {
                case 1:
                    sqlquery += "and a.prioridad = 0 ";
                    break;
                case 2:
                    sqlquery += "and a.prioridad = 1 ";
                    break;
                case 3:
                    sqlquery += "and a.prioridad = 2 ";
                    break;
            }

            sqlquery = sqlquery + "order by a.fecha desc ";
            Query consulta = em.createQuery(sqlquery);
            consulta.setParameter("nombre", "%" + nombre + "%");
            if (cliente != 0) consulta.setParameter("cliente", cliente);
            if (estado != "") consulta.setParameter("estado", estado);
            if (ruta > 0)     consulta.setParameter("ruta", ruta);
            if (!usuario.equals("null")) consulta.setParameter("usuario", usuario);
            // consulta.setMaxResults(50);
            consultaAvisos = consulta.getResultList();
        } catch (Exception e) {
            System.out.println("icontaweb " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            em.close();
        }

    }

    /**
     * *******************************************************************************************
     * Lista de Consulta de Visitas
     * *******************************************************************************************
     */
    private List<Visitas> listaVisitas = null;

    public void setListaVisitas(List<Visitas> listaVisitas) {
        this.listaVisitas = listaVisitas;
    }

    public List<Visitas> getListaVisitas() {
        if (listaVisitas == null) {
            ObtenerListaVisitas(0, false);
        }
        return listaVisitas;
    }

    @SuppressWarnings("unchecked")
    public void ObtenerListaVisitas(Integer representante, boolean estado) {

        em = entityManager();
        try {
            String sqlquery = "select a from Visitas a where 1=1 ";
            if (representante != 0) {
                sqlquery = sqlquery + "and a.clientes.representante.id = :representante ";
            }
            sqlquery = sqlquery + "and a.conCargo = :estado ";
            sqlquery = sqlquery + "order by a.fecha ";
            Query consulta = em.createQuery(sqlquery);
            if (representante != 0) {
                consulta.setParameter("representante", representante);
            }
            consulta.setParameter("estado", estado);
            listaVisitas = consulta.getResultList();
        } catch (Exception e) {
            System.out.println("icontaweb " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            em.close();
        }

    }

    /**
     * *******************************************************************************************
     * Lista de Acceso a los Mantenimientos
     * *******************************************************************************************
     */
    private List<Mantenimiento> listaMantenimientos = new ArrayList<Mantenimiento>();

    @SuppressWarnings("unchecked")
    public List<Mantenimiento> getListaMantenimientos() {

        em = entityManager();
        try {
            String sqlquery = "select m from Mantenimiento m ";
            Query lista = em.createQuery(sqlquery);
            listaMantenimientos = lista.getResultList();
        } catch (Exception e) {
            System.out.println("icontaweb " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            em.close();
        }

        return listaMantenimientos;
    }

    public void setListaMantenimientos(List<Mantenimiento> listaMantenimientos) {
        this.listaMantenimientos = listaMantenimientos;
    }

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
     *
     * @return
     */
    public List<Agenda> ObtenerAgenda() {
        em = entityManager();
        try {
            String sqlquery = "select a from Agenda a where 1=1 ";
            // if (cliente!=0) 	sqlquery = sqlquery + "and a.clientes.id = :cliente ";
            if (statusAgenda) {
                sqlquery = sqlquery + "and a.fechaProxima <= CURRENT_DATE ";
            }
            sqlquery = sqlquery + "order by a.fechaProxima ";
            Query consulta = em.createQuery(sqlquery);
            // if (cliente!=0) consulta.setParameter("cliente", cliente);
            Query lista = em.createQuery(sqlquery);
            listaAgenda = lista.getResultList();
        } catch (Exception e) {
            System.out.println("icontaweb " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            em.close();
        }

        return listaAgenda;
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
     * *******************************************************************************************
     * Procedimiento para obtener la cita seleccionada
     *
     * @return
     * *******************************************************************************************
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
     * *******************************************************************************************
     * Procedimiento para grabar los datos de la cita actual
     *
     * @return
     * *******************************************************************************************
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

        Rutinas.Log("AGENDA GRABADA : " + new DecimalFormat("########").format(agenda.getId()));

        return "agendaLista.xhtml";
    }

    /**
     * Procedimiento para borrar la cita de agenda actual
     *
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

        return "buscaAgenda";
    }

}
