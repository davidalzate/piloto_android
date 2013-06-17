package co.com.nuevaera.mobil.model;

public class AnuncioDto {

	private long idAnuncio;
	private long idEmpresa;
	private String fotoSmall;
	private String fotoBig;
	private int duracion;
	
	public long getIdAnuncio() {
		return idAnuncio;
	}
	public void setIdAnuncio(long idAnuncio) {
		this.idAnuncio = idAnuncio;
	}
	public long getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(long idEmpresa) {
		this.idEmpresa = idEmpresa;
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
	public int getDuracion() {
		return duracion;
	}
	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}	
	
}
