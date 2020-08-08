package es.com.icontaweb.contratos.controladores;

import es.com.icontaweb.contratos.modelos.Mantenimiento;
import es.com.icontaweb.contratos.modelos.Representante;
import es.com.icontaweb.contratos.modelos.Rutas;
import es.com.icontaweb.contratos.objetos.ficheros;
import es.com.icontaweb.controladores.ControlLogin;
import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.servlet.ServletContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 * Controlador para la gestión de la agenda
 *
 * @author jcperan
 */
@ManagedBean
@SessionScoped
public class ControladorClientes implements Serializable {

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
    
    public void setControladorContratos(ControladorContratos controladorContratos) {
        this.controladorContratos = controladorContratos;
    }
    
    public ControladorContratos getControladorContratos() {
        return controladorContratos;
    }
    
    @ManagedProperty(value = "#{ControlLogin}")
    private ControlLogin controlLogin = new ControlLogin();
    
    public void setControlLogin(ControlLogin controlLogin) {
        this.controlLogin = controlLogin;
    }
    
    public ControlLogin getControlLogin() {
        return controlLogin;
    }
    
    public void subirFoto(FileUploadEvent event) {
        
        UploadedFile uploadedPhoto = event.getFile();
        
        byte[] bytes = null;
        if (null != uploadedPhoto) {
            bytes = uploadedPhoto.getContents();
            this.controladorContratos.getObjeto().getClientes().setFoto(bytes);
            this.controladorContratos.cmdGrabaCliente(null);
        }
        
        FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Carga de Foto Ejecutada.", ""));
        
    }

    /**
     * Lista de Rutas
     */
    private List<SelectItem> listaRutas = new ArrayList<>();
    
    public void setListaRutas(List<SelectItem> listaRutas) {
        this.listaRutas = listaRutas;
    }
    
    public List<SelectItem> getListaRutas() {
        
        em = entityManager();
        try {
            listaRutas.clear();
            listaRutas.add(new SelectItem(0, "SIN ASIGNAR RUTA"));
            
            String sqlquery = "select r from Rutas r";
            Query q = em.createQuery(sqlquery);
            List<Rutas> lista = q.getResultList();
            Iterator<Rutas> it = lista.iterator();
            while (it.hasNext()) {
                Rutas datos = (Rutas) it.next();
                listaRutas.add(new SelectItem(datos.getId(), datos.getDenominacion()));
            }
        } catch (Exception e) {
            System.out.println("icontaweb " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        
        return listaRutas;
    }

    /**
     * Lista de Mantenimientos
     */
    private List<SelectItem> listaMantenimientos = new ArrayList<>();
    
    public void setListaMantenimiento(List<SelectItem> listaMantenimientos) {
        this.listaMantenimientos = listaMantenimientos;
    }
    
    public List<SelectItem> getListaMantenimiento() {
        
        em = entityManager();
        try {
            listaMantenimientos.clear();
            listaMantenimientos.add(new SelectItem(0, "SIN ASIGNAR MANTENIMIENTO"));
            
            String sqlquery = "select m from Mantenimiento m";
            Query q = em.createQuery(sqlquery);
            List<Mantenimiento> lista = q.getResultList();
            Iterator<Mantenimiento> it = lista.iterator();
            while (it.hasNext()) {
                Mantenimiento datos = (Mantenimiento) it.next();
                listaMantenimientos.add(new SelectItem(datos.getId(), datos.getDenominacion()));
            }
        } catch (Exception e) {
            System.out.println("icontaweb " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        
        return listaMantenimientos;
    }

    /**
     * Lista para el acceso a los Representantes
     */
    private List<SelectItem> listaRepresentantes = new ArrayList<SelectItem>();
    
    public List<SelectItem> getListaRepresentantes() {
        
        em = entityManager();
        try {
            listaRepresentantes.clear();
            listaRepresentantes.add(new SelectItem(0, "SIN ASIGNAR REPRESENTANTE"));
            
            Query q = em.createQuery("select r from Representante r order by r.nombre");
            List<Representante> lista = q.getResultList();
            Iterator<Representante> it = lista.iterator();
            while (it.hasNext()) {
                Representante datos = (Representante) it.next();
                listaRepresentantes.add(new SelectItem(datos.getId(), datos.getNombre()));
            }
        } catch (Exception e) {
            System.out.println("CONTRATOS " + (new Date()).toString() + " " + e.getMessage());
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return listaRepresentantes;
    }
    
    public void setListaRepresentantes(List<SelectItem> listaRepresentantes) {
        this.listaRepresentantes = listaRepresentantes;
    }
    
    private String obtenerDirectorio() {
        
        DecimalFormat df = new DecimalFormat("00000000");
        String directorio = df.format(this.controladorContratos.getObjeto().getClientes().getId());
        
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String realPath = ctx.getRealPath("") + "/../ficheros/" + directorio;
        
        return realPath;
    }
    
    public void upload(FileUploadEvent event) {
        
        UploadedFile nombreFichero = event.getFile();
        
        if (nombreFichero != null) {
            FacesMessage message;
            try {
                File dir = new File(obtenerDirectorio());
                dir.mkdirs();                
                nombreFichero.write(obtenerDirectorio() + "/" + nombreFichero.getFileName());
                message = new FacesMessage("Correcto", nombreFichero.getFileName() + " ha sido obtenido");
            } catch (Exception e) {
                message = new FacesMessage("Incorrecto", nombreFichero.getFileName() + " no ha sido obtenido");
            }
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    
    private List<ficheros> listaFicheros = new ArrayList<ficheros>();
    
    public List<ficheros> getListaFicheros() {
        
        listaFicheros.clear();
        
        File carpeta = new File(obtenerDirectorio());
        File[] listado = carpeta.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return true;
            }
        });
        if (listado == null || listado.length == 0) {
            
        } else {
            for (int i = 0; i < listado.length; i++) {
                DecimalFormat df = new DecimalFormat("00000000");
                String directorio = df.format(this.controladorContratos.getObjeto().getClientes().getId());
                
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                ficheros fichero = new ficheros();
                fichero.setNombre(listado[i].getName());
                fichero.setFecha(sdf.format(listado[i].lastModified()));
                fichero.setTamano(new Double(listado[i].length()));
                fichero.setUrl("/../ficheros/" + directorio + "/" + listado[i].getName());
                listaFicheros.add(fichero);
            }
        }
        
        return listaFicheros;
    }
 
}
