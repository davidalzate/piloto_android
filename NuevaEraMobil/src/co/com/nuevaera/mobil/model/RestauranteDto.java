package co.com.nuevaera.mobil.model;

public class RestauranteDto {
	private long idRestaurante;
	private String nombre;
	private String fondo;
	private String banner;
	
	public long getIdRestaurante() {
		return idRestaurante;
	}
	public void setIdRestaurante(long idRestaurante) {
		this.idRestaurante = idRestaurante;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getFondo() {
		return fondo;
	}
	public void setFondo(String fondo) {
		this.fondo = fondo;
	}
	public String getBanner() {
		return banner;
	}
	public void setBanner(String banner) {
		this.banner = banner;
	}
}
