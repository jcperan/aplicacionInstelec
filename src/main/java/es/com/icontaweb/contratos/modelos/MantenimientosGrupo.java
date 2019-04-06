package es.com.icontaweb.contratos.modelos;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Set;


/**
 * The persistent class for the mantenimientos_grupos database table.
 * 
 */
@Entity
@Table(name="mantenimientos_grupos")
public class MantenimientosGrupo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MantenimientosGrupoPK id;

    @Lob()
	private String descripcion;

	//bi-directional many-to-one association to Mantenimiento
    @ManyToOne
	@JoinColumn(name="idGrupo", insertable=false, updatable=false, referencedColumnName="id")
	private Mantenimiento mantenimiento;

	//bi-directional many-to-one association to MantenimientosDetalle
	@OneToMany(mappedBy="mantenimientosGrupo", cascade={CascadeType.ALL})
	@OrderBy("id.idDetalle")
	private Set<MantenimientosDetalle> mantenimientosDetalles;

    public MantenimientosGrupo() {
    }

	public MantenimientosGrupoPK getId() {
		return this.id;
	}

	public void setId(MantenimientosGrupoPK id) {
		this.id = id;
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Mantenimiento getMantenimiento() {
		return this.mantenimiento;
	}

	public void setMantenimiento(Mantenimiento mantenimiento) {
		this.mantenimiento = mantenimiento;
	}
	
	public Set<MantenimientosDetalle> getMantenimientosDetalles() {
		return this.mantenimientosDetalles;
	}

	public void setMantenimientosDetalles(Set<MantenimientosDetalle> mantenimientosDetalles) {
		this.mantenimientosDetalles = mantenimientosDetalles;
	}
	
}