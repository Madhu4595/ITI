/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package callleter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author GMPRSC
 */
public class generic {

    public static String getRank(String regid, String dist_code,HttpServletRequest request) {

        String return_val = "";
        // String regid=null;
        String sql = " Select rank from ranks where regid=" + regid + " and dist_code=" + dist_code + "";
        Connection con = DBC.DBConnection.getConnection(request);
        Statement st = null;
        ResultSet rs = null;
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                return_val += "" + rs.getString("rank");
            }
            st.close();
            rs.close();
            con.close();
        } catch (SQLException ex) {
        }

        return return_val;
    }
}
