/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package upload_csv;

/**
 *
 * @author shobha
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
 
public class automateImport
{
    public static void main(String[] args) 
    {
        DBase db = new DBase();
        Connection conn = db.connect(
    "jdbc:mysql://localhost:3306/test","root","caspian");
        db.importData(conn,args[0]);
    }
 
}
 
class DBase
{
    public DBase()
    {
    }
 
    public Connection connect(String db_connect_str, 
  String db_userid, String db_password)
    {
        Connection conn;
        try
        {
            Class.forName(  
    "com.mysql.jdbc.Driver").newInstance();
 
            conn = DriverManager.getConnection(db_connect_str, 
    db_userid, db_password);
         
        }
        catch(Exception e)
        {
            e.printStackTrace();
            conn = null;
        }
 
        return conn;    
    }
     
    public void importData(Connection conn,String filename)
    {
        Statement stmt;
        String query;
 
        try
        {
            stmt = conn.createStatement(
    ResultSet.TYPE_SCROLL_SENSITIVE,
    ResultSet.CONCUR_UPDATABLE);
 
            query = "LOAD DATA INFILE '"+filename+
    "' INTO TABLE admissions.sample (ext_marks->S4A);";
 
            stmt.executeUpdate(query);
                 
        }
        catch(Exception e)
        {
            e.printStackTrace();
            stmt = null;
        }
    }
};
