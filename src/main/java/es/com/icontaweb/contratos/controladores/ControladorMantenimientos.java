package es.com.icontaweb.contratos.controladores;

import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;

import es.com.icontaweb.contratos.modelos.Mantenimiento;
import es.com.icontaweb.contratos.modelos.MantenimientosDetalle;
import es.com.icontaweb.contratos.modelos.MantenimientosDetallePK;
import es.com.icontaweb.contratos.modelos.MantenimientosGrupo;
import es.com.icontaweb.contratos.modelos.MantenimientosGrupoPK;
import es.com.icontaweb.contratos.objetos.Contratos;
import es.com.icontaweb.contratos.objetos.PlanMantenimiento;

public class ControladorMantenimientos {
	
	private boolean modificando = false;
	private Integer idMantenimiento = 0;
	private Integer idGrupo = 0;
	private Integer idDetalle = 0;

	public ControladorMantenimientos() {
		
	}
	
	/*************************************************************************************************
	 * Objetos para el acceso a los datos y funciones de acceso a la base de datos.
	 *************************************************************************************************/
	private Contratos objeto = new Contratos();
	public void setObjeto(Contratos objeto) { this.objeto = objeto; }
	public Contratos getObjeto() { return objeto; }
	
	private PlanMantenimiento mantenimiento = new PlanMantenimiento();
	public void setMantenimiento(PlanMantenimiento mantenimiento) { this.mantenimiento = mantenimiento; }
	public PlanMantenimiento getMantenimiento() { return this.mantenimiento; }
	
	

	/*************************************************************************************************
	 * Datos del mantenimiento seleccionado
	 *************************************************************************************************/

	
	/**
	 * Boton para crear un mantenimiento en blanco
	 * @param event
	 */
	public void cmdNuevoMantenimiento(ActionEvent event) {
		this.mantenimiento.setMantenimiento(new Mantenimiento());
		modificando=false;
	}

	/**
	 * Boton para grabar un nuevo mantenimiento
	 * @param event
	 */
	public void cmdGrabaMantenimiento(ActionEvent event) {
		mantenimiento.Grabar(modificando);
	}
	
	/**
	 * Boton para borrar un mantenimiento existente
	 * @param event
	 */
	public void cmdBorraMantenimiento(ActionEvent event) {
		mantenimiento.Borrar();
	}
	
	/**
	 * Boton para asignar el mantenimiento en curso
	 * @param event
	 */
	public void cmdAsignaMantenimiento(ActionEvent event) {
		idMantenimiento = new Integer(((UIParameter) event.getComponent().getFacet("codigo")).getValue().toString());
		mantenimiento.LeerMantenimiento(idMantenimiento);
		modificando = true;
	}
	
	
	
	/*************************************************************************************************
	 * Datos del grupo seleccionado
	 *************************************************************************************************/
	
	
	/**
	 * Boton para generar un nuevo grupo
	 * @param event
	 */
	public void cmdNuevoGrupo(ActionEvent event) {
		mantenimiento.setMantenimientoGrupo(new MantenimientosGrupo());
		modificando=false;
	}
	
	/**
	 * Boton para grabar un nuevo grupo
	 * @param event
	 */
	public void cmdGrabaGrupo(ActionEvent event) {
		if (modificando) {
			mantenimiento.ActualizarGrupo();
		} else { 
			mantenimiento.GrabarGrupo();
		}
	}
	
	/**
	 * Boton para grabar un nuevo grupo
	 * @param event
	 */
	public void cmdEditaGrupo(ActionEvent event) {
		idGrupo = new Integer(((UIParameter) event.getComponent().getFacet("codigo")).getValue().toString());
		mantenimiento.LeerGrupo(new MantenimientosGrupoPK(idMantenimiento,idGrupo));
		modificando=true;
	}
	
	/**
	 * Boton para asignar el grupo en curso
	 * @param event
	 */
	public void cmdAsignaGrupo(ActionEvent event) {
		idGrupo = new Integer(((UIParameter) event.getComponent().getFacet("codigo")).getValue().toString());
		mantenimiento.LeerGrupo(new MantenimientosGrupoPK(idMantenimiento,idGrupo));
	}
	
	/**
	 * Boton para borrar un grupo existente
	 * @param event
	 */
	public void cmdBorraGrupo(ActionEvent event) {
		mantenimiento.BorrarGrupo(new MantenimientosGrupoPK(idMantenimiento,idGrupo));
	}
	

	
	
	/*************************************************************************************************
	 * Datos del detalle seleccionado
	 *************************************************************************************************/
	
	
	/**
	 * Boton para generar un nuevo grupo
	 * @param event
	 */
	public void cmdNuevoDetalle(ActionEvent event) {
		mantenimiento.setMantenimientoDetalle(new MantenimientosDetalle());
		modificando=false;
	}
	
	/**
	 * Boton para grabar un nuevo grupo
	 * @param event
	 */
	public void cmdGrabaDetalle(ActionEvent event) {
		if (modificando) {
			mantenimiento.ActualizarDetalle();
		} else { 
			mantenimiento.GrabarDetalle();
		}
	}
	
	/**
	 * Boton para grabar un nuevo grupo
	 * @param event
	 */
	public void cmdEditaDetalle(ActionEvent event) {
		idDetalle = new Integer(((UIParameter) event.getComponent().getFacet("codigo")).getValue().toString());
		mantenimiento.LeerDetalle(new MantenimientosDetallePK(idMantenimiento,idGrupo,idDetalle));
		modificando=true;
	}
	
	/**
	 * Boton para asignar el grupo en curso
	 * @param event
	 */
	public void cmdAsignaDetalle(ActionEvent event) {
		idDetalle = new Integer(((UIParameter) event.getComponent().getFacet("codigo")).getValue().toString());
		mantenimiento.LeerDetalle(new MantenimientosDetallePK(idMantenimiento,idGrupo,idDetalle));
	}
	
	/**
	 * Boton para borrar un grupo existente
	 * @param event
	 */
	public void cmdBorraDetalle(ActionEvent event) {
		mantenimiento.BorrarDetalle(new MantenimientosDetallePK(idMantenimiento,idGrupo,idDetalle));
	}
		
}
