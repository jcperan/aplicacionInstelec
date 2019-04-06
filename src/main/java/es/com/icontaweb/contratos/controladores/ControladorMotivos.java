package es.com.icontaweb.contratos.controladores;

import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;

import es.com.icontaweb.contratos.objetos.Contratos;
import es.com.icontaweb.rutinas.Rutinas;

public class ControladorMotivos {
	
	boolean swModificar = false;
	
	/**
	 * Objeto para el acceso a los datos y funciones de acceso a la base de datos.
	 */
	private Contratos objeto = new Contratos();
	public void setObjeto(Contratos objeto) { this.objeto = objeto; }
	public Contratos getObjeto() { return objeto; }
	
	/**
	 * Ver datos del motivo
	 * @param event
	 */
	public void cmdVerMotivo(ActionEvent event) {
		
		Integer codigo = new Integer(((UIParameter) event.getComponent().getFacet("codigo")).getValue().toString());
		if (objeto.LeerMotivo(codigo)) {
			Rutinas.MuestraMensaje("Introduzca Datos del Motivo y pulse Grabar...");
			swModificar=true;
		} else {
			Rutinas.MuestraMensaje("Error al acceder al dato");
		}
	}

	/**
	 * Generar nuevo motivo
	 * @param event
	 */
	public void cmdNuevoMotivo(ActionEvent event) {
		if (objeto.NuevoMotivo()) {
			Rutinas.MuestraMensaje("Introduzca Datos del Motivo y pulse Grabar...");
			swModificar=false;
		} else {
			Rutinas.MuestraMensaje("Error al Crear nuevo motivo");			
		}
	}
	
	/**
	 * Grabar nuevo motivo
	 * @param event
	 */
	public void cmdGrabaMotivo(ActionEvent event) {
		if (swModificar) {
			objeto.ActualizaMotivo(objeto.getMotivos().getId(), objeto.getMotivos().getMotivo());
		} else {
			objeto.GrabaMotivo();
		}
	}
	
	/**
	 * Borrar el motivo actual
	 * @param event
	 */
	public void cmdBorraMotivo(ActionEvent event) {
		objeto.BorraMotivo(objeto.getMotivos().getId());
	}

}
