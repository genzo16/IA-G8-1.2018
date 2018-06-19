package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.Derby;

public abstract class Antecedente 
{
	private int id_antecedente;
	public Integer idPaciente;
	public String enfermedad;
	public TIPO_ANTECEDENTE tipoDeAntecedente;
	
	public Antecedente(int id_antecedente, Integer idPaciente, String enfermedad, TIPO_ANTECEDENTE tipoDeAntecedente) {
		super();
		this.id_antecedente = id_antecedente;
		this.idPaciente = idPaciente;
		this.enfermedad = enfermedad;
		this.tipoDeAntecedente = tipoDeAntecedente;
	}

	public int getId_antecedente() {
		return id_antecedente;
	}

	public void setId_antecedente(int id_antecedente) {
		this.id_antecedente = id_antecedente;
	}

	public Integer getIdPaciente() {
		return idPaciente;
	}

	public void setIdPaciente(Integer idPaciente) {
		this.idPaciente = idPaciente;
	}

	public String getEnfermedad() {
		return enfermedad;
	}

	public void setEnfermedad(String enfermedad) {
		this.enfermedad = enfermedad;
	}

	public TIPO_ANTECEDENTE getTipoDeAntecedente() {
		return tipoDeAntecedente;
	}

	public void setTipoDeAntecedente(TIPO_ANTECEDENTE tipoDeAntecedente) {
		this.tipoDeAntecedente = tipoDeAntecedente;
	}

	static public Paciente Load(Integer idAntecedente) throws SQLException
	{
		Connection conn = Derby.getInstance().getConnection();
		Statement s;
		ResultSet rs=null;
		try {
			s = conn.createStatement();
			rs = s.executeQuery("select * from antecedente where paciente_id = 1");
			rs.next();
			System.out.println (rs.getString("NOMBRE"));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	static public int Save(Antecedente antecedente) throws SQLException
	{
		Connection conn = Derby.getInstance().getConnection();
		Statement s;
		int rs=0;
		try {
			s = conn.createStatement();
			rs = s.executeUpdate("select * from paciente where paciente_id = 1");
		} catch (SQLException e) {
			e.printStackTrace();
			return rs;
		}
		return rs;
	}
}
