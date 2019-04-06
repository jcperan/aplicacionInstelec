package es.com.icontaweb.modelos;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the contadores database table.
 *
 */
@Entity
@Table(name = "contadores")
public class Contadores implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String contador;

    private double valor;

    public Contadores() {
    }

    public String getContador() {
        return this.contador;
    }

    public void setContador(String contador) {
        this.contador = contador;
    }

    public double getValor() {
        return this.valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

}
