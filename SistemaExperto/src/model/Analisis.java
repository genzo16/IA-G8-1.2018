package model;

import java.io.Serializable;

import javax.persistence.*;

//"CREATE TABLE ANALISIS "
//+ "(ANALISIS_ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY "
//+ "CONSTRAINT ANALISIS_PK PRIMARY KEY "
//+ "CONSTRAINT ANALISIS_FK REFERENCES DIAGNOSTICO, "
//+ "NOMBRE VARCHAR(32) NOT NULL, "
//+ "TIPO_ANALISIS VARCHAR(32) NOT NULL, "
//+ "RESULTADO VARCHAR(32) NOT NULL)";
@Entity
@Table(name="ANALISIS")
public class Analisis  implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7422857790964899147L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ANALISIS_ID")
	private int id_analisis;
	@Column(name="NOMBRE")
	private String nombre;
	@Column(name="TIPO_ANALISIS")
	private TIPO_ANALISIS tipo_analisis;
	@Column(name="RESULTADO")
	private String resultado;
	
	public Analisis() {
		super();
	}

	public Analisis(int id_analisis, String nombre, TIPO_ANALISIS tipo_analisis, String resultado) {
		super();
		this.id_analisis = id_analisis;
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
