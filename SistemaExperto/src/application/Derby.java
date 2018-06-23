package application;

import java.sql.*;

public class Derby {

	private String driver;
	private String dbName;
	private String connectionURL;
	private Connection conn = null;
	static private Derby instance = null;
	
	static public Derby getInstance()
	{
		if(instance == null)
			instance = new Derby();
		return instance;
	}
	
	private Derby() {};
	
	public Connection getConnection() throws SQLException
	{
		if(conn == null)
			 conn = DriverManager.getConnection(connectionURL);
		return conn;
	}
	
	private boolean initDB()
	{
		driver = "org.apache.derby.jdbc.EmbeddedDriver";
		dbName="derbyDB";
		connectionURL = "jdbc:derby:"+dbName+";create=true";
		
        String psInsert= " INSERT INTO PACIENTE(ENTRY_DATE, NOMBRE, APELLIDO, DNI, EDAD, FECHA, SEXO) "
        		+ "VALUES(CURRENT_TIMESTAMP, 'TEST ENTRY','NONE','NONE',0, CURRENT_DATE,'MASCULINO')";
        
		String ctPaciente = "CREATE TABLE PACIENTE  "
		        + "(PACIENTE_ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY " 
		        + "CONSTRAINT PACIENTE_PK PRIMARY KEY, " 
		        + "ENTRY_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
		        + "NOMBRE VARCHAR(32) NOT NULL,"
		        + "APELLIDO VARCHAR(32) NOT NULL,"
		        + "DNI VARCHAR(32) NOT NULL,"
		        + "EDAD INTEGER NOT NULL,"
		        + "FECHA DATE NOT NULL,"
		        + "SEXO VARCHAR(32) NOT NULL)";
		String ctDolor = "CREATE TABLE DOLOR "
		        + "(DOLOR_ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY "
		        + "CONSTRAINT DOLOR_PK PRIMARY KEY, "
		        + "ZONA VARCHAR(32) NOT NULL, "
		        + "TIPO VARCHAR(32) NOT NULL) ";
		String ctAnalisis = "CREATE TABLE ANALISIS "
		        + "(ANALISIS_ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY "
		        + "CONSTRAINT ANALISIS_PK PRIMARY KEY, "
		        + "NOMBRE VARCHAR(32) NOT NULL, "
		        + "TIPO_ANALISIS VARCHAR(32) NOT NULL, "
		        + "RESULTADO VARCHAR(32) NOT NULL)";
		String ctAntecedente = "CREATE TABLE ANTECEDENTE "
		        + "(ANTECEDENTE_ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY "
		        + "CONSTRAINT ANTECEDENTE_PK PRIMARY KEY, "
		        + "ENFERMEDAD VARCHAR(32) NOT NULL, "
		        + "TIPO_ANTECEDENTE VARCHAR(32) NOT NULL)";
		String ctDiagnostico = "CREATE TABLE DIAGNOSTICO "
		        + "(DIAGNOSTICO_ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY "
		        + "CONSTRAINT DIAGNOSTICO_PK PRIMARY KEY, "
		        + "ESSPAX VARCHAR(32) NOT NULL, "
		        + "RECOMENDACION VARCHAR(32) NOT NULL, "
		        + "ENFERMEDAD VARCHAR(32) NOT NULL) ";
		
		// Cargar el driver
	
		try {
			Class.forName(driver);
		} catch(java.lang.ClassNotFoundException e) {
		}
		
		// Iniciar base de datos
		try {
		   conn =  getConnection();

		}  catch (Throwable e)  {  
		}
		if(conn == null)
			return false;
		
		// SQL
		Statement s;
		try {
			s = conn.createStatement();
			if (! DerbyUtils.wwdChk4Table(conn))
			{  
			   System.out.println (" . . . . creating table Paciente.");
			   s.execute(ctPaciente);
			   System.out.println (" . . . . creating table Analisis.");
			   s.execute(ctAnalisis);
			   System.out.println (" . . . . creating table Dolor.");
			   s.execute(ctDolor);
			   System.out.println (" . . . . creating table Antecedente.");
			   s.execute(ctAntecedente);
			   System.out.println (" . . . . creating table Diagnostico.");
			   s.execute(ctDiagnostico);
			   System.out.println (" . . . . inserting TEST ENTRY.");
			   s.executeUpdate(psInsert);		  
			   System.out.println ("DB succesfully initiated.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	protected boolean initialize()
	{
		boolean retValue = false;
		try {
			retValue = initDB();
		} catch (Exception e) {
			System.out.println("Derby initialize() failed.");
			e.printStackTrace();
			return false;
		}
		
		return retValue;
	}
	
	protected void shutdown()
	{
		if (driver.equals("org.apache.derby.jdbc.EmbeddedDriver")) 
		{
		   boolean gotSQLExc = false;
		   try {
		      DriverManager.getConnection("jdbc:derby:;shutdown=true");
		   } catch (SQLException se)  {
		      if ( se.getSQLState().equals("XJ015") ) {
		         gotSQLExc = true;
		      }
		   }
		   if (!gotSQLExc) {
		      System.out.println("Database did not shut down normally");
		   }  else  {
		      System.out.println("Database shut down normally");
		   }
		}
	}
	
	

}
