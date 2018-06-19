package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.Derby;

public class Dolor 
{
	private int id_dolor;
	private String zona;
	private String tipo;
	
	public Dolor(int id_dolor, String zona, String tipo) {
		super();
		this.id_dolor = id_dolor;
		this.zona = zona;
		this.tipo = tipo;
	}

	public int getId_dolor() {
		return id_dolor;
	}

	public void setId_dolor(int id_dolor) {
		this.id_dolor = id_dolor;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	static public Paciente Load(Integer idDolor) throws SQLException
	{
		Connection conn = Derby.getInstance().getConnection();
		Statement s;
		ResultSet rs=null;
		try {
			s = conn.createStatement();
			rs = s.executeQuery("select * from dolor where paciente_id = 1");
			rs.next();
			System.out.println (rs.getString("NOMBRE"));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	static public int Save(Dolor dolor) throws SQLException
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
