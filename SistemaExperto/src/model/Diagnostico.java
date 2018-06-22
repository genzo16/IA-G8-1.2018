package model;

public class Diagnostico 
{
	private int id_diagnostico;
	private String esSPAX;
	private String recomendacion;
	private String enfermedad;
	
	public Diagnostico(int id_diagnostico, String esSPAX, String recomendacion, String enfermedad) {
		super();
		this.id_diagnostico = id_diagnostico;
		this.esSPAX = esSPAX;
		this.recomendacion = recomendacion;
		this.enfermedad = enfermedad;
	}

	public int getId_diagnostico() {
		return id_diagnostico;
	}

	public void setId_diagnostico(int id_diagnostico) {
		this.id_diagnostico = id_diagnostico;
	}

	public String getEsSPAX() {
		return esSPAX;
	}

	public void setEsSPAX(String esSPAX) {
		this.esSPAX = esSPAX;
	}

	public String getRecomendacion() {
		return recomendacion;
	}

	public void setRecomendacion(String recomendacion) {
		this.recomendacion = recomendacion;
	}

	public String getEnfermedad() {
		return enfermedad;
	}

	public void setEnfermedad(String enfermedad) {
		this.enfermedad = enfermedad;
	}


}
