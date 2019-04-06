package es.com.icontaweb.contratos.modelos;

public class ListaMantenimiento {

	private int id_mantenimiento = 0;
	private int id_grupo = 0;
	private int id_detalle = 0;
	
	private String grupo = "";
	private String datos = "";
	private boolean valor = false;
	private boolean verlo = false;
	
	public ListaMantenimiento() {
		
	}
	
	public ListaMantenimiento(int id_mantenimiento, int id_grupo, int id_detalle, String grupo, String datos, boolean valor, boolean verlo) {
		this.id_mantenimiento = id_mantenimiento;
		this.id_grupo = id_grupo;
		this.id_detalle = id_detalle;
		this.grupo = grupo;
		this.datos = datos;
		this.valor = valor;
		this.verlo = verlo;
	}
	
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public String getDatos() {
		return datos;
	}
	public void setDatos(String datos) {
		this.datos = datos;
	}
	public boolean isValor() {
		return valor;
	}
	public void setValor(boolean valor) {
		this.valor = valor;
	}
	public boolean isVerlo() {
		return verlo;
	}
	public void setVerlo(boolean verlo) {
		this.verlo = verlo;
	}

	public int getId_mantenimiento() {
		return id_mantenimiento;
	}

	public void setId_mantenimiento(int id_mantenimiento) {
		this.id_mantenimiento = id_mantenimiento;
	}

	public int getId_grupo() {
		return id_grupo;
	}

	public void setId_grupo(int id_grupo) {
		this.id_grupo = id_grupo;
	}

	public int getId_detalle() {
		return id_detalle;
	}

	public void setId_detalle(int id_detalle) {
		this.id_detalle = id_detalle;
	}
	
	
	
}
