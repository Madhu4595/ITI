/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  DBC.DBConnection
 *  javax.servlet.http.HttpServletRequest
 */
package DBC;

import DBC.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;

public class GenReports {
    public static String testReport(String headt, String headc, String headr, String value, Connection con, String rsql, int colcount, HttpServletRequest request) throws SQLException {
        con = DBConnection.getConnection((HttpServletRequest)request);
        Object rs = null;
        Object psmt = null;
        ResultSet rsq = null;
        PreparedStatement psq = null;
        ResultSet rsh = null;
        PreparedStatement psh = null;
        String cols = "";
        String space="";
        String center = "center";
        String classtr = "tableheader";
        String classtd = "tddata";
        String result = "";
        try {
            if (headt == null || headr == null || value == null || con == null) {
                return null;
            }
            psh = con.prepareStatement("select " + headc + " from " + headt + " where " + headr + "=" + value + " order by order_by");
            rsh = psh.executeQuery();
            
     
            cols = "<br></br>"+"<table align='" + center + "'  border=" + 1 + " bgcolor=\"#e4eeb9\" id=\"tot\"><thead> <tr bgcolor=\"lightblue\">";
             
            while (rsh.next()) {
                cols = cols + "<td>" + rsh.getString(1) + "</td>";
            }
            cols = cols + "</tr></thead><tbody><tr>";
            int k = 1;
            psq = con.prepareStatement(rsql);
            rsq = psq.executeQuery();
            while (rsq.next()) {
                for (k = 1; k <= colcount; ++k) {
                    cols = cols + "<td>" + rsq.getString(k) + "</td>";
                }
                cols = cols + "</tr>";
            }
            cols = cols + "</tbody>";
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally{
        if(con!=null){
        con.close();
        }
        }
        return cols;
    }
   
}