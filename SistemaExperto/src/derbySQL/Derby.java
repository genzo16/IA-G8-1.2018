package derbySQL;

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
		
//        String psInsert= " INSERT INTO PACIENTE(ENTRY_DATE, NOMBRE, APELLIDO, DNI, EDAD, FECHA, SEXO) "
//        		+ "VALUES(CURRENT_TIMESTAMP, 'TEST ENTRY','NONE','NONE',0, CURRENT_DATE,'MASCULINO')";
        
		String ctPaciente = "CREATE TABLE PACIENTE  "
		        + "(PACIENTE_ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY " 
		        + "CONSTRAINT PACIENTE_PK PRIMARY KEY, "
		        + "NOMBRE VARCHAR(32) ,"
		        + "APELLIDO VARCHAR(32) ,"
		        + "DNI VARCHAR(32) ,"
		        + "EDAD INTEGER ,"
		        + "FECHA DATE ,"
		        + "SEXO VARCHAR(32) )";
		String ctDolor = "CREATE TABLE DOLOR "
		        + "(DOLOR_ID INTEGER NOT NULL "//GENERATED ALWAYS AS IDENTITY "
		        + "CONSTRAINT DOLOR_PK PRIMARY KEY "
		        + "CONSTRAINT DOLOR_FK REFERENCES DIAGNOSTICO, "
		        + "ZONA VARCHAR(32) , "
		        + "INICIO_DOLOR VARCHAR(32) , "
		        + "CONDICION_ALIVIO VARCHAR(32) , "
		        + "RECURRENCIA INT , "
		        + "TIPO VARCHAR(32) ) ";
		String ctAnalisis = "CREATE TABLE ANALISIS "
		        + "(ANALISIS_ID INTEGER NOT NULL "//GENERATED ALWAYS AS IDENTITY "
		        + "CONSTRAINT ANALISIS_PK PRIMARY KEY "
		        + "CONSTRAINT ANALISIS_FK REFERENCES DIAGNOSTICO, "
		        + "NOMBRE VARCHAR(32) , "
		        + "TIPO_ANALISIS VARCHAR(32) , "
		        + "RESULTADO VARCHAR(32))";
		String ctAntecedente = "CREATE TABLE ANTECEDENTE "
		        + "(ANTECEDENTE_ID INTEGER NOT NULL "//GENERATED ALWAYS AS IDENTITY "
		        + "CONSTRAINT ANTECEDENTE_PK PRIMARY KEY "
		        + "CONSTRAINT ANTECEDENTE_FK REFERENCES DIAGNOSTICO, "
		        + "ENFERMEDAD VARCHAR(32) , "
		        + "TIPO_ANTECEDENTE VARCHAR(32))";
		String ctDiagnostico = "CREATE TABLE DIAGNOSTICO "
		        + "(DIAGNOSTICO_ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY "
		        + "CONSTRAINT DIAGNOSTICO_PK PRIMARY KEY, "
		        + "PACIENTE_ID INTEGER NOT NULL "
		        + "CONSTRAINT PACIENTE_FK REFERENCES PACIENTE, "
		        + "ESSPAX VARCHAR(32) , "
		        + "RECOMENDACION VARCHAR(32) , "
		        + "ENFERMEDAD VARCHAR(32)) ";
		
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
			   System.out.println ("La BD no existe, creandola...");
			   System.out.println (" . . . . creando tabla Paciente.");
			   s.execute(ctPaciente);
			   System.out.println (" . . . . creando tabla Diagnostico.");
			   s.execute(ctDiagnostico);
			   System.out.println (" . . . . creando tabla Analisis.");
			   s.execute(ctAnalisis);
			   System.out.println (" . . . . creando tabla Dolor.");
			   s.execute(ctDolor);
			   System.out.println (" . . . . creando tabla Antecedente.");
			   s.execute(ctAntecedente);
			   System.out.println ("DB creada.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	// Crea la base de datos si no existe
	public boolean initialize()
	{
		boolean retValue = false;
		try {
			retValue = initDB();
		} catch (Exception e) {
			System.out.println("Derby initialize() failed.");
			e.printStackTrace();
			return false;
		}
		shutdown();
		return retValue;
	}
	
	public void shutdown()
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
