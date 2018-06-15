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