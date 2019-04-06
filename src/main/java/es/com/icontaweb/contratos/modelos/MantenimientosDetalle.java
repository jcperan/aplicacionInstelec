package es.com.icontaweb.contratos.modelos;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the mantenimientos_detalle database table.
 * 
 */
@Entity
@Table(name="mantenimientos_detalle")
public class MantenimientosDetalle implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MantenimientosDetallePK id;
	
	private String descripcion;

	//bi-directional many-to-one association to MantenimientosGrupo
    @ManyToOne
	@JoinColumns({
		@JoinColumn(name="idDetalle", referencedColumnName="idGrupo", insertable=false, updatable=false),
		@JoinColumn(name="grupo",     referencedColumnName="grupo"  , insertable=false, updatable=false)
		})
	private MantenimientosGrupo mantenimientosGrupo;

    public MantenimientosDetalle() {
    }

	public MantenimientosDetallePK getId() {
		return this.id;
	}

	public void setId(MantenimientosDetallePK id) {
		this.id = id;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public MantenimientosGrupo getMantenimientosGrupo() {
		return this.mantenimientosGrupo;
	}

	public void setMantenimientosGrupo(MantenimientosGrupo mantenimientosGrupo) {
		this.mantenimientosGrupo = mantenimientosGrupo;
	}
	
}