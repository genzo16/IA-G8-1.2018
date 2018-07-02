package model;

import java.io.Serializable;

import javax.persistence.*;

//"CREATE TABLE ANTECEDENTE "
//+ "(ANTECEDENTE_ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY "
//+ "CONSTRAINT ANTECEDENTE_PK PRIMARY KEY "
//+ "CONSTRAINT ANTECEDENTE_FK REFERENCES DIAGNOSTICO, "
//+ "ENFERMEDAD VARCHAR(32) NOT NULL, "
//+ "TIPO_ANTECEDENTE VARCHAR(32) NOT NULL)";
@Entity
@Table(name="ANTECEDENTE")
public class Antecedentes   implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3991634440237184165L;
	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ANTECEDENTE_ID")
	private Integer antecedente_id;
	@Column(name="ENFERMEDAD")
	private String enfermedad;
	@Column(name="TIPO_ANTECEDENTE")
	private String tipo_antecedente;
	
	public Antecedentes() {
		super();
	}
	
	public Antecedentes(Integer antecedente_id, String enfermedad, String tipo_antecedente) {
		super();
		this.antecedente_id = antecedente_id;
		this.enfermedad = enfermedad;
		this.tipo_antecedente = tipo_antecedente;
	}
	public Integer getAntecedente_id() {
		return antecedente_id;
	}
	public void setAntecedente_id(Integer antecedente_id) {
		this.antecedente_id = antecedente_id;
	}
	public String getEnfermedad() {
		return enfermedad;
	}
	public void setEnfermedad(String enfermedad) {
		this.enfermedad = enfermedad;
	}
	public TIPO_ANTECEDENTE getTipo_antecedente() {
		return TIPO_ANTECEDENTE.valueOf(tipo_antecedente);
	}
	public void setTipo_antecedente(TIPO_ANTECEDENTE tipo_antecedente) {
		this.tipo_antecedente = tipo_antecedente.name();
	}
}
