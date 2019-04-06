package es.com.icontaweb.contratos.modelos;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the representantes database table.
 * 
 */
@Entity
@Table(name="representantes")
public class Representante implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String nombre;

	//bi-directional many-to-one association to Clientes
	@OneToMany(mappedBy="representante")
	private Set<Clientes> clientes;

    public Representante() {
    }

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<Clientes> getClientes() {
		return this.clientes;
	}

	public void setClientes(Set<Clientes> clientes) {
		this.clientes = clientes;
	}
	
}