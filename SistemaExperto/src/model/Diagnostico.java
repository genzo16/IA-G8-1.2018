package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.Derby;

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

	static public Paciente Load(Integer idDiagnostico) throws SQLException
	{
		Connection conn = Derby.getInstance().getConnection();
		Statement s;
		ResultSet rs=null;
		try {
			s = conn.createStatement();
			rs = s.executeQuery("select * from paciente where paciente_id = 1");
			rs.next();
			System.out.println (rs.getString("NOMBRE"));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	static public int Save(Diagnostico diagnostico) throws SQLException
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
