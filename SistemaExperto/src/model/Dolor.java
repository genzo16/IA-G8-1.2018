package model;

import java.io.Serializable;

import javax.persistence.*;
//"CREATE TABLE DOLOR "
//+ "(DOLOR_ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY "
//+ "CONSTRAINT DOLOR_PK PRIMARY KEY "
//+ "CONSTRAINT DOLOR_FK REFERENCES DIAGNOSTICO, "
//+ "ZONA VARCHAR(32) NOT NULL, "
//+ "INICIO_DOLOR VARCHAR(32) NOT NULL, "
//+ "CONDICION_ALIVIO VARCHAR(32) NOT NULL, "
//+ "RECURRENCIA INT NOT NULL, "
//+ "TIPO VARCHAR(32) NOT NULL) ";

@Entity
@Table(name="DOLOR")
public class Dolor  implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8106436433072455988L;
	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="DOLOR_ID")
	private Integer id_dolencia;
	@Column(name="TIPO")
	public String tipo;
	@Column(name="ZONA")
	public String zona;
	@Column(name="INICIO_DOLOR")
	public String incio_dolor;
	@Column(name="CONDICION_ALIVIO")
	public String condicion_alivio;
	@Column(name="RECURRENCIA")
	public int recurrencia;
	
	public Dolor() {
		super();
	}

	public Dolor(Integer id_dolencia, String tipo, String zona, String incio_dolor, String condicion_alivio,
			int recurrencia) {
		super();
		this.id_dolencia = id_dolencia;
		this.tipo = tipo;
		this.zona = zona;
		this.incio_dolor = incio_dolor;
		this.condicion_alivio = condicion_alivio;
		this.recurrencia = recurrencia;
	}

	public Integer getId_dolencia() {
		return id_dolencia;
	}

	public void setId_dolencia(Integer id_dolencia) {
		this.id_dolencia = id_dolencia;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public String getIncio_dolor() {
		return incio_dolor;
	}

	public void setIncio_dolor(String incio_dolor) {
		this.incio_dolor = incio_dolor;
	}

	public String getCondicion_alivio() {
		return condicion_alivio;
	}

	public void setCondicion_alivio(String condicion_alivio) {
		this.condicion_alivio = condicion_alivio;
	}

	public int getRecurrencia() {
		return recurrencia;
	}

	public void setRecurrencia(int recurrencia) {
		this.recurrencia = recurrencia;
	}
	
}
