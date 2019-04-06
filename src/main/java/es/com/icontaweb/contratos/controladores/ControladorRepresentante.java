package es.com.icontaweb.contratos.controladores;

import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;

import es.com.icontaweb.contratos.objetos.Contratos;
import es.com.icontaweb.rutinas.Rutinas;

public class ControladorRepresentante {
	
	boolean swModificar = false;
	
	/**
	 * Objeto para el acceso a los datos y funciones de acceso a la base de datos.
	 */
	private Contratos objeto = new Contratos();
	public void setObjeto(Contratos objeto) { this.objeto = objeto; }
	public Contratos getObjeto() { return objeto; }
	
	/**
	 * Ver datos del registro actual
	 * @param event
	 */
	public void cmdVerRepresentante(ActionEvent event) {
		
		Integer codigo = new Integer(((UIParameter) event.getComponent().getFacet("codigo")).getValue().toString());
		if (objeto.LeerRepresentante(codigo)) {
			Rutinas.MuestraMensaje("Introduzca Datos del Representante y pulse Grabar...");
			swModificar=true;
		} else {
			Rutinas.MuestraMensaje("Error al acceder al dato");
		}
	}

	/**
	 * Generar nuevo
	 * @param event
	 */
	public void cmdNuevoRepresentante(ActionEvent event) {
		if (objeto.NuevoRepresentante()) {
			Rutinas.MuestraMensaje("Introduzca Datos del Representante y pulse Grabar...");
			swModificar=false;
		} else {
			Rutinas.MuestraMensaje("Error al Crear nuevo Representante");			
		}
	}
	
	/**
	 * Grabar nuevo motivo
	 * @param event
	 */
	public void cmdGrabaRepresentante(ActionEvent event) {
		if (swModificar) {
			objeto.ActualizaRepresentante(objeto.getRepresentante().getId(), objeto.getRepresentante().getNombre());
		} else {
			objeto.GrabaRepresentante();
		}
	}
	
	/**
	 * Borrar el motivo actual
	 * @param event
	 */
	public void cmdBorraRepresentante(ActionEvent event) {
		objeto.BorraRepresentante(objeto.getRepresentante().getId());
	}

}
