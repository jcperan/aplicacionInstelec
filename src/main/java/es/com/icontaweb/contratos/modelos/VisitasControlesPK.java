package es.com.icontaweb.contratos.modelos;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the visitas_controles database table.
 * 
 */
@Embeddable
public class VisitasControlesPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_visita")
	private int idVisita;

	@Column(name="id_mantenimiento")
	private int idMantenimiento;

	@Column(name="id_grupo")
	private int idGrupo;

	@Column(name="id_detalle")
	private int idDetalle;

	public VisitasControlesPK() {
	}
	public int getIdVisita() {
		return this.idVisita;
	}
	public void setIdVisita(int idVisita) {
		this.idVisita = idVisita;
	}
	public int getIdMantenimiento() {
		return this.idMantenimiento;
	}
	public void setIdMantenimiento(int idMantenimiento) {
		this.idMantenimiento = idMantenimiento;
	}
	public int getIdGrupo() {
		return this.idGrupo;
	}
	public void setIdGrupo(int idGrupo) {
		this.idGrupo = idGrupo;
	}
	public int getIdDetalle() {
		return this.idDetalle;
	}
	public void setIdDetalle(int idDetalle) {
		this.idDetalle = idDetalle;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof VisitasControlesPK)) {
			return false;
		}
		VisitasControlesPK castOther = (VisitasControlesPK)other;
		return 
			(this.idVisita == castOther.idVisita)
			&& (this.idMantenimiento == castOther.idMantenimiento)
			&& (this.idGrupo == castOther.idGrupo)
			&& (this.idDetalle == castOther.idDetalle);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idVisita;
		hash = hash * prime + this.idMantenimiento;
		hash = hash * prime + this.idGrupo;
		hash = hash * prime + this.idDetalle;
		
		return hash;
	}
}