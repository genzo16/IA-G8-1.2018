package application;
import java.sql.*;
import java.util.Properties;

public class Derby {

	private String driver;
	private String dbName;
	private String connectionURL;
	private Connection conn = null;
	
	public boolean initDB()
	{
		driver = "org.apache.derby.jdbc.EmbeddedDriver";
		dbName="derbyDB";
		connectionURL = "jdbc:derby:"+dbName+";create=true";
        
		String createString = "CREATE TABLE PACIENTE  "
		        +  "(PACIENTE_ID INT NOT NULL GENERATED ALWAYS AS IDENTITY " 
		        +  "   CONSTRAINT WISH_PK PRIMARY KEY, " 
		        +  " ENTRY_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
		        +  " WISH_ITEM VARCHAR(32) NOT NULL) " ;
		
		// Cargar el driver
	
		try {
			Class.forName(driver);
		} catch(java.lang.ClassNotFoundException e) {
		}
		
		// Iniciar base de datos
		try {
		   conn = DriverManager.getConnection(connectionURL);

		}  catch (Throwable e)  {  
		}
		
		// SQL
		Statement s;
		try {
			s = conn.createStatement();
			if (! DerbyUtils.wwdChk4Table(conn))
			{  
			   System.out.println (" . . . . creating table");
			   s.execute(createString);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	//	psInsert = conn.prepareStatement("insert into WISH_LIST(WISH_ITEM) values (?)");
		return true;
	}
	
	protected void OnShutdown() 
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
