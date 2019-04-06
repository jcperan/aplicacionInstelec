package es.com.icontaweb.contratos.modelos;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the mantenimientos_grupos database table.
 * 
 */
@Embeddable
public class MantenimientosGrupoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int idGrupo;

	private int grupo;

    public MantenimientosGrupoPK() {
    }
	public MantenimientosGrupoPK(int idGrupo, Integer grupo) {
		this.idGrupo=idGrupo;
		this.grupo=grupo;
	}
	public int getIdGrupo() {
		return this.idGrupo;
	}
	public void setIdGrupo(int idGrupo) {
		this.idGrupo = idGrupo;
	}
	public int getGrupo() {
		return this.grupo;
	}
	public void setGrupo(int grupo) {
		this.grupo = grupo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MantenimientosGrupoPK)) {
			return false;
		}
		MantenimientosGrupoPK castOther = (MantenimientosGrupoPK)other;
		return 
			(this.idGrupo == castOther.idGrupo)
			&& (this.grupo == castOther.grupo);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idGrupo;
		hash = hash * prime + this.grupo;
		
		return hash;
    }
}