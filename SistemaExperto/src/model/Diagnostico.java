package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="DIAGNOSTICO")
public class Diagnostico  implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2828033788042866610L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="DIAGNOSTICO_ID")
	private Integer id_diagnostico;
	@Column(name="ESSPAX")
	private String esSPAX;
	@Column(name="RECOMENDACION")
	private String recomendacion;
	@Column(name="ENFERMEDAD")
	private String enfermedad;
	
	@OneToMany(cascade = {CascadeType.ALL},fetch= FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinColumn(name="ANALISIS_ID")
	private List<Analisis> analisis;
	@OneToMany(cascade = {CascadeType.ALL},fetch= FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinColumn(name="DOLOR_ID")
	private List<Dolor> dolores;
	@OneToMany(cascade = {CascadeType.ALL},fetch= FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinColumn(name="ANTECEDENTE_ID")
	private List<Antecedentes> antecedentes;
	
	public Diagnostico() {
		super();
	}

	public Diagnostico(Integer id_diagnostico, String esSPAX, String recomendacion, String enfermedad) {
		super();
		this.id_diagnostico = id_diagnostico;
		this.esSPAX = esSPAX;
		this.recomendacion = recomendacion;
		this.enfermedad = enfermedad;
		
		analisis = new ArrayList<Analisis>();
		dolores = new ArrayList<Dolor>();
		antecedentes = new ArrayList<Antecedentes>();
	}

	public Integer getId_diagnostico() {
		return id_diagnostico;
	}

	public void setId_diagnostico(Integer id_diagnostico) {
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

	public List<Analisis> getAnalisis() {
		return analisis;
	}

	public void setAnalisis(List<Analisis> analisis) {
		this.analisis = analisis;
	}

	public List<Dolor> getDolores() {
		return dolores;
	}

	public void setDolores(List<Dolor> dolores) {
		this.dolores = dolores;
	}

	public List<Antecedentes> getAntecedentes() {
		return antecedentes;
	}

	public void setAntecedentes(List<Antecedentes> antecedentes) {
		this.antecedentes = antecedentes;
	}

	
}
