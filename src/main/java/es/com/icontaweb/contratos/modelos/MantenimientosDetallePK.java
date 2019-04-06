package es.com.icontaweb.contratos.modelos;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the mantenimientos_detalle database table.
 * 
 */
@Embeddable
public class MantenimientosDetallePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int idDetalle;

	private int grupo;

	private int detalle;

    public MantenimientosDetallePK() {
    }
	public MantenimientosDetallePK(Integer idMantenimiento, Integer idGrupo, Integer idDetalle) {
		this.idDetalle = idMantenimiento;
		this.grupo = idGrupo;
		this.detalle = idDetalle;
	}
	public int getIdDetalle() {
		return this.idDetalle;
	}
	public void setIdDetalle(int idDetalle) {
		this.idDetalle = idDetalle;
	}
	public int getGrupo() {
		return this.grupo;
	}
	public void setGrupo(int grupo) {
		this.grupo = grupo;
	}
	public int getDetalle() {
		return this.detalle;
	}
	public void setDetalle(int detalle) {
		this.detalle = detalle;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MantenimientosDetallePK)) {
			return false;
		}
		MantenimientosDetallePK castOther = (MantenimientosDetallePK)other;
		return 
			(this.idDetalle == castOther.idDetalle)
			&& (this.grupo == castOther.grupo)
			&& (this.detalle == castOther.detalle);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idDetalle;
		hash = hash * prime + this.grupo;
		hash = hash * prime + this.detalle;
		
		return hash;
    }
}