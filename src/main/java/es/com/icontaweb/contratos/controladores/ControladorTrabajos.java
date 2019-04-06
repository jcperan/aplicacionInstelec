package es.com.icontaweb.contratos.controladores;

import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;

import es.com.icontaweb.contratos.objetos.Contratos;
import es.com.icontaweb.rutinas.Rutinas;

public class ControladorTrabajos {
	
	boolean swModificar = false;
	
	/**
	 * Objeto para el acceso a los datos y funciones de acceso a la base de datos.
	 */
	private Contratos objeto = new Contratos();
	public void setObjeto(Contratos objeto) { this.objeto = objeto; }
	public Contratos getObjeto() { return objeto; }
	
	/**
	 * Ver datos del trabajo
	 * @param event
	 */
	public void cmdVerTrabajo(ActionEvent event) {
		Integer codigo = new Integer(((UIParameter) event.getComponent().getFacet("codigo")).getValue().toString());
		if (objeto.LeerTrabajo(codigo)) {
			Rutinas.MuestraMensaje("Introduzca Datos del Trabajo y pulse Grabar...");
			swModificar=true;
		} else {
			Rutinas.MuestraMensaje("Error al acceder al dato");
		}
	}

	/**
	 * Generar nuevo trabajo
	 * @param event
	 */
	public void cmdNuevoTrabajo(ActionEvent event) {
		if (objeto.NuevoTrabajo()) {
			Rutinas.MuestraMensaje("Introduzca Datos del Trabajo y pulse Grabar...");
			swModificar=false;
		} else {
			Rutinas.MuestraMensaje("Error al Crear nuevo trabajo");			
		}
	}
	
	/**
	 * Grabar nuevo trabajo
	 * @param event
	 */
	public void cmdGrabaTrabajo(ActionEvent event) {
		if (swModificar) {
			objeto.ActualizaTrabajo(objeto.getTrabajos().getId(), objeto.getTrabajos().getTrabajo());
		} else {
			objeto.GrabaTrabajo();
		}
	}
	
	/**
	 * Borrar el motivo actual
	 * @param event
	 */
	public void cmdBorraTrabajo(ActionEvent event) {
		objeto.BorraTrabajo(objeto.getTrabajos().getId());
	}
	
}
