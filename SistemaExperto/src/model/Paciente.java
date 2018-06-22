package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;

import application.Derby;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class Paciente implements GUIFriendly {
	/* JavaFX links */
	
	@FXML // fx:id="dni"
	private static TextField dniFX;
	@FXML // fx:id="apellido"
	private static TextField apellidoFX;
	@FXML // fx:id="nombre"
	private static TextField nombreFX;
	@FXML // fx:id="edad"
	private static TextField edadFX;
	// fx:id="fecha"
	//private static TextField fechaFX;
	@FXML // fx:id="sexo"
	private ChoiceBox<String> sexoFX;
	
	/* Variables */
	// TODO: shouldn't these all be private, since we have getters and setters?
	
	private Integer id_paciente;
	public String DNI;
	public String Nombre;
	public String Apellido;
	public Integer Edad;
	public Date Fecha;// (de cuando fue guardado el perfil de paciente)
	public String Sexo;
	
	public Paciente(int id_paciente, String dNI, String nombre, String apellido, int edad, Date fecha,
			String sexo) {
		super();
		this.id_paciente = id_paciente;
		DNI = dNI;
		Nombre = nombre;
		Apellido = apellido;
		Edad = edad;
		Fecha = fecha;
		Sexo = sexo;
	}
	
	public void grabFromGUI() {
		DNI = dniFX.getText();
		Nombre = nombreFX.getText();
		Apellido = apellidoFX.getText();
		try {
			Edad = Integer.parseInt( edadFX.getText() );
		} catch(NumberFormatException e) {
			Edad = -1;
		}
		
		//String tempDate = fechaFX.getText();
		// TODO: do date conversion
		//Fecha = Date.valueOf("");
		
		Sexo = sexoFX.getValue();
	}
	
	public void pushToGUI() {
		dniFX.setText(DNI);
		nombreFX.setText(Nombre);
		apellidoFX.setText(Apellido);
		edadFX.setText(String.valueOf(Edad));
		//fechaFX.setText(Fecha.toString());
		sexoFX.setValue(Sexo);
	}

	static public Paciente Load(Integer idPaciente) throws SQLException
	{
		Connection conn = Derby.getInstance().getConnection();
		PreparedStatement s;
		ResultSet rs=null;
		Paciente rPac = null;
		try {
			s = conn.prepareStatement("select * from paciente where paciente_id = ?");
			s.setInt(1, idPaciente);
			rs = s.executeQuery();
			rs.next();
			rPac = new Paciente(rs.getInt("paciente_id"), rs.getString("dni"), rs.getString("nombre"), rs.getString("apellido"),
					rs.getInt("edad"), rs.getDate("fecha"), rs.getString("sexo"));
			System.out.println (rs.getString("NOMBRE"));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return rPac;
	}
	
	static public int Save(Paciente paciente) throws SQLException
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
	
	/* Getters & Setters */
	

	public Integer getId_paciente() {
		return id_paciente;
	}

	public void setId_paciente(Integer id_paciente) {
		this.id_paciente = id_paciente;
	}

	public String getDNI() {
		return DNI;
	}

	public void setDNI(String dNI) {
		DNI = dNI;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	public String getApellido() {
		return Apellido;
	}

	public void setApellido(String apellido) {
		Apellido = apellido;
	}

	public Integer getEdad() {
		return Edad;
	}

	public void setEdad(Integer edad) {
		Edad = edad;
	}

	public Date getFecha() {
		return Fecha;
	}

	public void setFecha(Date fecha) {
		Fecha = fecha;
	}

	public String getSexo() {
		return Sexo;
	}

	public void setSexo(String sexo) {
		Sexo = sexo;
	}

}
