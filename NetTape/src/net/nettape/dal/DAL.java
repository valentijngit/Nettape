package net.nettape.dal;

import java.sql.*;

public class DAL {
	String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	String connectionURL = "jdbc:derby:nettape";

	public DAL()
	{
		
	}
	public void SimpleSelect()
	{

	      Connection conn = null;
	      Statement s;

	      ResultSet myWishes;
	      String printLine = "  __________________________________________________";
	      String selectString = "select * from User_ " ;

	      try	        {
	          /*
	          **  Load the Derby driver. 
	          **     When the embedded Driver is used this action start the Derby engine.
	          **  Catch an error and suggest a CLASSPATH problem
	           */
	          Class.forName(driver); 
	          System.out.println(driver + " loaded. ");
	      } catch(java.lang.ClassNotFoundException e)     {
	          System.err.print("ClassNotFoundException: ");
	          System.err.println(e.getMessage());
	          System.out.println("\n    >>> Please check your CLASSPATH variable   <<<\n");
	      }
	      try {
	            // Create (if needed) and connect to the database
	            conn = DriverManager.getConnection(connectionURL);		 
	            s = conn.createStatement();
	            myWishes = s.executeQuery(selectString);
	            System.out.println(printLine);
                while (myWishes.next())
                {
                       System.out.println("On " + myWishes.getString(2));
                }
              System.out.println(printLine);
              //  Close the resultSet 
              myWishes.close();
              s.close();
              conn.close();
	      }
	      catch (Throwable e)  {   
	            /*       Catch all exceptions and pass them to 
	            **       the exception reporting method             */
	            System.out.println(" . . . exception thrown:" + e.getMessage());

	         }
	      
	}
}
