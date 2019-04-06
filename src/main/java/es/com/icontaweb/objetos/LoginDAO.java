package es.com.icontaweb.objetos;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import es.com.icontaweb.modelos.Contadores;
import es.com.icontaweb.modelos.Horario;
import es.com.icontaweb.modelos.Usuarios;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.persistence.Query;

public class LoginDAO implements Serializable {

    public LoginDAO() {

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

    private EntityManager entityManager() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("contratos");
        }
        return emf.createEntityManager();
    }

    /**
     * *******************************************************************************************
     * Clase para el acceso al fichero de contadores
	 ********************************************************************************************
     */
    private Contadores contadores = new Contadores();

    public Contadores getContadores() {
        return contadores;
    }

    public void setContadores(Contadores contadores) {
        this.contadores = contadores;
    }

    /**
     * *******************************************************************************************
     * Clase para el acceso al fichero de usuarios
	 ********************************************************************************************
     */
    private Usuarios usuarios = new Usuarios();

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    /**
     * *******************************************************************************************
     * Contador de Visitas a la P�gina
	 ********************************************************************************************
     */
    private Double contador = new Double("0");

    public Double getContador() {
        em = entityManager();
        em.getTransaction().begin();
        try {
            contadores = em.find(Contadores.class, "pagina");
            if (contadores == null) {
                contadores = new Contadores();
                contadores.setContador("pagina");
                contadores.setValor(contador);
                em.persist(contadores);
            } else {
                contador = contadores.getValor();
            }
        } catch (Exception e) {
            System.out.println("icontaweb " + (new Date()).toString() + " " + e.getMessage());
        }
        contador++;
        contadores.setContador("pagina");
        contadores.setValor(contador);
        em.flush();
        em.getTransaction().commit();
        em.close();
        return contador;
    }

    public void setContador(Double contador) {
        this.contador = contador;
    }

    public boolean ValidaLogin(String usuario, String clave) {

        boolean resultado = false;

        em = entityManager();
        try {
            usuarios = em.find(Usuarios.class, usuario);
            if (usuarios == null) {
            } else {
                if (usuarios.getClave().equals(clave)) {
                    resultado = true;
                }
            }
        } catch (Exception e) {
            System.out.println("icontaweb " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            em.close();
        }

        return resultado;

    }
    
    public boolean validaHorario(String usuario) {
        
        boolean resultado = false;
        
        int hora = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int dias = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
        if (dias<=0) dias=7;
        
        em = entityManager();
        try {
            String sqlquery = "select h from Horario h where h.usuario = :usuario ";
            sqlquery += "and h.dia = :diaSemana ";
            sqlquery += "and h.desde <= :hora1 ";
            sqlquery += "and h.hasta >  :hora2 ";
            
            Query q = em.createQuery(sqlquery);
            q.setParameter("usuario", usuario);
            q.setParameter("diaSemana", dias);
            q.setParameter("hora1",hora);
            q.setParameter("hora2",hora);
            
            List<Horario> listaHorario = q.getResultList();
            if (listaHorario.size()==0) {
                resultado = false;
            } else {
                resultado = true;
            }
            
        } catch (Exception e) {
            System.out.println("icontaweb " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            if (em.isOpen()) em.close();
        }
        
        return resultado;
        
    }
    
}
