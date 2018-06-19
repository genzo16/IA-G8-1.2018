package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.Derby;

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

	static public Paciente Load(Integer idAnalisis) throws SQLException
	{
		Connection conn = Derby.getInstance().getConnection();
		Statement s;
		ResultSet rs=null;
		try {
			s = conn.createStatement();
			rs = s.executeQuery("select * from analisis where paciente_id = 1");
			rs.next();
			System.out.println (rs.getString("NOMBRE"));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	static public int Save(Analisis analisis) throws SQLException
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
