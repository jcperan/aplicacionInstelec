package es.com.icontaweb.contratos.modelos;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the trabajos database table.
 * 
 */
@Entity
@Table(name="trabajos")
public class Trabajos implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String trabajo;

	//bi-directional many-to-one association to Visitas
	@OneToMany(mappedBy="trabajos")
	private Set<Visitas> visitas;

    public Trabajos() {
    }

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTrabajo() {
		return this.trabajo;
	}

	public void setTrabajo(String trabajo) {
		this.trabajo = trabajo;
	}

	public Set<Visitas> getVisitas() {
		return this.visitas;
	}

	public void setVisitas(Set<Visitas> visitas) {
		this.visitas = visitas;
	}
	
}