package model;

import java.sql.Date;
import java.util.List;
import javax.persistence.*;
import org.hibernate.Session;
import hibernate.HibernateUtils;

@Entity
@Table(name = "PACIENTE")
public class Paciente implements GUIFriendly 
{	
	/* Variables */
	@Id
	@Column(name ="PACIENTE_ID")
	private Integer id_paciente;
	private String DNI;
	@Column(name ="NOMBRE")
	private String Nombre;
	@Column(name ="APELLIDO")
	private String Apellido;
	@Column(name ="EDAD")
	private Integer Edad;
	@Column(name ="FECHA")
	private Date Fecha;// (de cuando fue guardado el perfil de paciente)
	@Column(name ="SEXO")
	private String Sexo;
	
//	private List<Diagnostico> diagnosticos;
	
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
	
	public Paciente() {
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

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getId_paciente()+" - "+getApellido()+", "+getNombre();
	}

	public static void save(Paciente paciente) 
	{    
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(paciente);
            session.getTransaction().commit();
        }
    }

	@Override
	public void grabFromGUI() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pushToGUI() {
		// TODO Auto-generated method stub
		
	}
	
}
