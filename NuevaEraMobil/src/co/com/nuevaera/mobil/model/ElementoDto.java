package co.com.nuevaera.mobil.model;

public class ElementoDto {

	private long idElemento;
	private long idCategoria;
	private String nombre;
	private String descripcionCorta;
	private String descripcionLarga;
	private String fotoSmall;
	private String fotoBig;
	private String precio;
	private boolean disponible;
	
	public long getIdElemento() {
		return idElemento;
	}
	public void setIdElemento(long idElemento) {
		this.idElemento = idElemento;
	}
	public long getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(long idCategoria) {
		this.idCategoria = idCategoria;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcionCorta() {
		return descripcionCorta;
	}
	public void setDescripcionCorta(String descripcionCorta) {
		this.descripcionCorta = descripcionCorta;
	}
	public String getDescripcionLarga() {
		return descripcionLarga;
	}
	public void setDescripcionLarga(String descripcionLarga) {
		this.descripcionLarga = descripcionLarga;
	}
	public String getFotoSmall() {
		return fotoSmall;
	}
	public void setFotoSmall(String fotoSmall) {
		this.fotoSmall = fotoSmall;
	}
	public String getFotoBig() {
		return fotoBig;
	}
	public void setFotoBig(String fotoBig) {
		this.fotoBig = fotoBig;
	}
	public String getPrecio() {
		return precio;
	}
	public void setPrecio(String precio) {
		this.precio = precio;
	}
	public boolean isDisponible() {
		return disponible;
	}
	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}
	
}
