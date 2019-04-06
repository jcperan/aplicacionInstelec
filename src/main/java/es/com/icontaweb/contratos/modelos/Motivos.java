package es.com.icontaweb.contratos.modelos;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the motivos database table.
 * 
 */
@Entity
@Table(name="motivos")
public class Motivos implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String motivo;

	//bi-directional many-to-one association to Visitas
	@OneToMany(mappedBy="motivos")
	private Set<Visitas> visitas;

	//bi-directional many-to-one association to Aviso
	@OneToMany(mappedBy="motivo")
	private Set<Avisos> avisos;

    public Motivos() {
    }

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMotivo() {
		return this.motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public Set<Visitas> getVisitas() {
		return this.visitas;
	}

	public void setVisitas(Set<Visitas> visitas) {
		this.visitas = visitas;
	}
	
	public Set<Avisos> getAvisos() {
		return this.avisos;
	}

	public void setAvisos(Set<Avisos> avisos) {
		this.avisos = avisos;
	}
	
}