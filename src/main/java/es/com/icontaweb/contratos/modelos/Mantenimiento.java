package es.com.icontaweb.contratos.modelos;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the mantenimientos database table.
 * 
 */
@Entity
@Table(name="mantenimientos")
public class Mantenimiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(updatable=false)
	private int id;

	private String denominacion;

    @Lob()
	private String observaciones;

	//bi-directional many-to-one association to MantenimientosGrupo
	@OneToMany(mappedBy="mantenimiento", cascade={CascadeType.ALL})
	@OrderBy("id.idGrupo")
	private Set<MantenimientosGrupo> mantenimientosGrupos;

    public Mantenimiento() {
    }

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDenominacion() {
		return this.denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Set<MantenimientosGrupo> getMantenimientosGrupos() {
		return this.mantenimientosGrupos;
	}

	public void setMantenimientosGrupos(Set<MantenimientosGrupo> mantenimientosGrupos) {
		this.mantenimientosGrupos = mantenimientosGrupos;
	}
	
}