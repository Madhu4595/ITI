/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBC;

import java.sql.Connection;
import java.sql.DriverManager;


/**
 *
 * @author APDCA
 */
public class Test {
  public static void main(String[] args) throws ClassNotFoundException, Exception{
      DBConnection db=new DBConnection();
      Connection con=null;
Test t=new Test();
      System.out.println("aaaa-->"+db.getconnection_jsp());
  } 
 
}
