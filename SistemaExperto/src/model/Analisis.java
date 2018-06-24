package model;

public abstract class Analisis 
{
	private int id_analisis;
	private String idPaciente;
	private String nombre;
	private TIPO_ANALISIS tipo_analisis;
	private String resultado;
	
	public Analisis(int id_analisis, String idPaciente, String nombre, TIPO_ANALISIS tipo_analisis, String resultado) {
		super();
		this.id_analisis = id_analisis;
		this.idPaciente = idPaciente;
		this.nombre = nombre;
		this.tipo_analisis = tipo_analisis;
		this.resultado = resultado;
	}

	public int getId_analisis() {
		return id_analisis;
	}

	public void setId_analisis(int id_analisis) {
		this.id_analisis = id_analisis;
	}

	public String getIdPaciente() {
		return idPaciente;
	}

	public void setIdPaciente(String idPaciente) {
		this.idPaciente = idPaciente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public TIPO_ANALISIS getTipo_analisis() {
		return tipo_analisis;
	}

	public void setTipo_analisis(TIPO_ANALISIS tipo_analisis) {
		this.tipo_analisis = tipo_analisis;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

}
