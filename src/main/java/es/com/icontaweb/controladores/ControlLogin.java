package es.com.icontaweb.controladores;

import java.text.DecimalFormat;

import javax.faces.event.ActionEvent;

import es.com.icontaweb.objetos.LoginDAO;
import es.com.icontaweb.rutinas.Rutinas;
import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "ControlLogin")
@SessionScoped
public class ControlLogin implements Serializable {

    /**
     * Variables para el control del Usuario y Contraseña
     */
    private String USER = "";
    private String PASS = "";

    public String getUSER() {
        return USER;
    }

    public void setUSER(String USER) {
        this.USER = USER;
    }

    public String getPASS() {
        return PASS;
    }

    public void setPASS(String PASS) {
        this.PASS = PASS;
    }

    /**
     * Control del destino del login si correcto
     */
    private String destino = "";

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    /**
     * Objeto para el acceso a los datos
     */
    private LoginDAO login = new LoginDAO();

    public LoginDAO getLogin() {
        return login;
    }

    public void setLogin(LoginDAO login) {
        this.login = login;
    }

    /**
     * Contador de visitas a la pagina
     */
    private String contador = "";

    public void setContador(String contador) {
        this.contador = contador;
    }

    public String getContador() {
        DecimalFormat df = new DecimalFormat("00000000");
        Double numero = login.getContador();
        contador = "   " + df.format(numero) + "   ";
        Rutinas.MuestraMensaje("Introduzca Datos de Conexion (demo/demo)");
        return contador;
    }

    /**
     * Evento de la pulsacion del boton de login.
     * @param event
     */
    public void cmdLOGIN(ActionEvent event) {
        if (login.ValidaLogin(USER, PASS)) {
            if (this.getNivelAdministrador() || login.validaHorario(USER)) {
                this.setDestino(login.getUsuarios().getProceso());
                Rutinas.Log("LOGIN USUARIO : " + USER + " - " + this.getDestino());
            } else {
                Rutinas.MuestraMensaje("Horario incorrecto");
                Rutinas.Log("LOGIN USUARIO : " + USER + " - Horario Incorrecto");
                this.setDestino("");                
            }
        } else {
            Rutinas.MuestraMensaje("Login incorrecto");
            Rutinas.Log("LOGIN USUARIO : " + USER + " - Login Incorrecto");
            this.setDestino("");
        }
    }

    private boolean nivelAdministrador = false;
    
    public void setNivelAdministrador(boolean nivelAdministrador) {
        this.nivelAdministrador = nivelAdministrador;
    }
    
    public boolean getNivelAdministrador() {
        
        this.nivelAdministrador = login.getUsuarios().getNivel() == 9;
        return nivelAdministrador;
    }
    
    private String fechaActual = "";

    public String getFechaActual() {
        fechaActual = new Date().toString();
        return fechaActual;
    }

    public void setFechaActual(String fechaActual) {
        this.fechaActual = fechaActual;
    }
     
    
}
