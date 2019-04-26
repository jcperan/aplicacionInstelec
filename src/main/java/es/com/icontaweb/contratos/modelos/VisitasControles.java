package es.com.icontaweb.contratos.modelos;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the visitas_controles database table.
 * 
 */
@Entity
@Table(name="visitas_controles")
public class VisitasControles implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private VisitasControlesPK id;

	private byte valor;

	public VisitasControles() {
	}

	public VisitasControlesPK getId() {
		return this.id;
	}

	public void setId(VisitasControlesPK id) {
		this.id = id;
	}

	public byte getValor() {
		return this.valor;
	}

	public void setValor(byte valor) {
		this.valor = valor;
	}

}