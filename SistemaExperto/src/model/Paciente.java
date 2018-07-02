package model;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import org.hibernate.annotations.Cascade;
//import org.hibernate.annotations.CascadeType;
import org.hibernate.Session;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import hibernate.HibernateUtils;
//"CREATE TABLE PACIENTE  "
//+ "(PACIENTE_ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY " 
//+ "CONSTRAINT PACIENTE_PK PRIMARY KEY, "
//+ "NOMBRE VARCHAR(32) NOT NULL,"
//+ "APELLIDO VARCHAR(32) NOT NULL,"
//+ "DNI VARCHAR(32) NOT NULL,"
//+ "EDAD INTEGER NOT NULL,"
//+ "FECHA DATE NOT NULL,"
//+ "SEXO VARCHAR(32) NOT NULL)";
@Entity
@Table(name = "PACIENTE")
public class Paciente implements Serializable
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3930409348541533953L;
	/* Variables */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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

	@OneToMany(fetch= FetchType.EAGER, cascade={CascadeType.ALL})
        /*    CascadeType.PERSIST, 
            CascadeType.REMOVE})*/
//	@org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinColumn(name="PACIENTE_ID")
	private List<Diagnostico> diagnosticos;
	
	@Transient
	private static Session session;
	
	public Paciente(Integer id_paciente, String dNI, String nombre, String apellido, int edad, Date fecha,
			String sexo) {
		super();
		this.id_paciente = id_paciente;
		DNI = dNI;
		Nombre = nombre;
		Apellido = apellido;
		Edad = edad;
		Fecha = fecha;
		Sexo = sexo;
		
		diagnosticos = new ArrayList<Diagnostico>();
	}
	
	public Paciente() {
		super();
		diagnosticos = new ArrayList<Diagnostico>();
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

	public List<Diagnostico> getDiagnosticos() {
		return diagnosticos;
	}

	public void setDiagnosticos(List<Diagnostico> diagnosticos) {
		this.diagnosticos = diagnosticos;
	}

	@Override
	public String toString() {
		return getId_paciente()+" - "+getApellido()+", "+getNombre();
	}

	public static void saveOrUpdate(Paciente paciente) 
	{    
        try
        {
        	session = HibernateUtils.getSessionFactory().openSession();
        	session.beginTransaction();
        	session.saveOrUpdate(paciente);
        	session.getTransaction().commit();
            
        }finally {
        	session.close();
	      }
    }
	
	public static void persist(Paciente paciente) 
	{    
		 try
	        {
	        	session = HibernateUtils.getSessionFactory().openSession();
        	session.beginTransaction();
        	session.persist(paciente);
        	session.getTransaction().commit();
            
        }finally {
        	session.close();
	      }
    }
	
	public static Paciente load(int id_paciente) 
	{    
		Paciente p = null;
		 try
	        {
	        	session = HibernateUtils.getSessionFactory().openSession();
	        	session.beginTransaction();
            p = session.get(Paciente.class, id_paciente);
            session.getTransaction().commit();
       }finally {
        session.close();
	      }
        return p;
    }

	public static void update(Paciente actual) {
		try
        {
        	session = HibernateUtils.getSessionFactory().openSession();
    	session.beginTransaction();
    	session.update(actual);
    	session.getTransaction().commit();
        
    }finally {
    	session.close();
      }
		
	}
	
}
