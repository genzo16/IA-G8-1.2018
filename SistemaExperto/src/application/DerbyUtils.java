package application;
	/*
    Derby - WwdUtils.java - utilitity methods used by WwdEmbedded.java

       Licensed to the Apache Software Foundation (ASF) under one
          or more contributor license agreements.  See the NOTICE file
          distributed with this work for additional information
          regarding copyright ownership.  The ASF licenses this file
          to you under the Apache License, Version 2.0 (the
          "License"); you may not use this file except in compliance
          with the License.  You may obtain a copy of the License at

            http://www.apache.org/licenses/LICENSE-2.0

          Unless required by applicable law or agreed to in writing,
          software distributed under the License is distributed on an
          "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
          KIND, either express or implied.  See the License for the
          specific language governing permissions and limitations
          under the License.    

*/

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Paciente;

public class DerbyUtils {

/*****************
**  Asks user to enter a list item or 'exit' to exit the loop - returns 
**       the string entered - loop should exit when the string 'exit' is returned
******************/
  public static String getItem() {
     BufferedReader br = new BufferedReader( new InputStreamReader(System.in));
     String ans = "";
     try
     {
        while ( ans.length() == 0 ) {
           System.out.println("Enter list item (enter exit to end): ");
           ans = br.readLine();
           if ( ans.length() == 0 ) 
              System.out.print("Nothing entered: ");
        }
     } catch (java.io.IOException e) {
        System.out.println("Could not read response from stdin");	
        }
        return ans;
   }  /**  END  getItem  ***/

/***      Check for table    ****/
  public static boolean wwdChk4Table (Connection conTst ) throws SQLException {
     try {
        Statement s = conTst.createStatement();
        s.execute("update PACIENTE set ENTRY_DATE = CURRENT_TIMESTAMP, NOMBRE = 'TEST ENTRY' where PACIENTE_ID=1");
     }  catch (SQLException sqle) {
        String theError = (sqle).getSQLState();
        //   System.out.println("  Utils GOT:  " + theError);
        /** If table exists will get -  WARNING 02000: No row was found **/
        if (theError.equals("42X05"))   // Table does not exist
        {  return false;
         }  else if (theError.equals("42X14") || theError.equals("42821"))  {
            System.out.println("WwdChk4Table: Incorrect table definition. Drop table derbyDB and rerun this program");
            throw sqle;   
         } else { 
            System.out.println("WwdChk4Table: Unhandled SQLException" );
            throw sqle; 
         }
     }
     System.out.println("TEST ENTRY updated.");
     return true;
  }  /*** END wwdInitTable  **/

	static public Paciente LoadPaciente(Integer idPaciente) throws SQLException
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
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return rPac;
	}
	
	static public List<Paciente> LoadPacientes() throws SQLException
	{
		List<Paciente> lPacientes = new ArrayList<Paciente>();
		Connection conn = Derby.getInstance().getConnection();
		PreparedStatement s;
		ResultSet rs=null;
		Paciente rPac = null;
		try {
			s = conn.prepareStatement("select * from paciente");
			rs = s.executeQuery();
			while(rs.next())
			{
				lPacientes.add(new Paciente(rs.getInt("paciente_id"), rs.getString("dni"), rs.getString("nombre"), rs.getString("apellido"),
					rs.getInt("edad"), rs.getDate("fecha"), rs.getString("sexo")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return lPacientes;
	}
	
	static public int SavePaciente(Paciente p) throws SQLException
	{
		Connection conn = Derby.getInstance().getConnection();
		PreparedStatement ps;
		int rs = 0;
		try {
			if(p.getId_paciente()==0)
			{
				// insert
				ps = conn.prepareStatement("INSERT INTO Paciente (dni,nombre,apellido,edad,fecha,sexo) VALUES (?,?,?,?,?,?)");
				System.out.println(ps.toString());
				ps.setString(1, p.getDNI());//dni
				ps.setString(2, p.getNombre());//nombre
				ps.setString(3, p.getApellido());//apellido
				ps.setInt(4, p.getEdad());//edad
				ps.setDate(5, p.getFecha());//fecha
				ps.setString(6, p.getSexo());//sexo
				rs = ps.executeUpdate();
			}else{
				// update
				ps = conn.prepareStatement("UPDATE Paciente SET dni=?,nombre=?,apellido=?,edad=?,fecha=?,sexo=? WHERE paciente_id = ?");
				ps.setString(1, p.getDNI());//dni
				ps.setString(2, p.getNombre());//nombre
				ps.setString(3, p.getApellido());//apellido
				ps.setInt(4, p.getEdad());//edad
				ps.setDate(5, p.getFecha());//fecha
				ps.setString(6, p.getSexo());//sexo
				ps.setInt(7, p.getId_paciente());//nro afiliado
				rs = ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return rs;
		}
		return rs;
	}
  

  public static void main  (String[] args) {
  // This method allows stand-alone testing of the getItem method
     String answer;
     do {
        answer = getItem();
        if (! answer.equals("exit"))  {
           System.out.println ("You said: " + answer);
        }
     } while (! answer.equals("exit")) ;
  }
}
