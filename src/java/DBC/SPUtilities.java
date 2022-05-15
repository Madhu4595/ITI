/*
 * To change this resultlate, choose Tools | Templates
 * and open the resultlate in the editor.
 */
package DBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Admin
 */
public class SPUtilities {

    public static String getPostgres_Array(String[] arr) {
        String result = "{";
        for (int i = 0; i < arr.length; i++) {
            result = result + "" + arr[i] + ",";
        }
        result = result.substring(0, result.length() - 1) + " }";
        return result;
    }
     public static String getPostgres_Array2(String[] arr) {
        String result = "[";
        for (int i = 0; i < arr.length; i++) {
            result = result + "'false',";
        }
        result = result.substring(0, result.length() - 1) + " ]";
        return result;
    }

    public static String getHstoreStatement(String[] array, boolean isnull, String[] values) {
        String ar = "array" + getPostgres_Array(array);
        ar = ar.replace(",", "','");
        ar = ar.replace("{", "['");
        ar = ar.replace("}", "']");
        ar = ar.replace(" ", "");
        if (isnull) {
            ar = "hstore(" + ar + ",null)";
        } else {
            String ar1 = "array" + getPostgres_Array2(array); ar = "hstore(" + ar + ","+ar1+")";
        }
        return ar;
    }

    public static String getDistricts(String region_code,HttpServletRequest request) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String district = "('";
        if (region_code != null) {
            try {
                con = DBC.DBConnection.getConnection(request);
                String qry1 = "select dist_code from conviner_login where region_code='" + region_code + "'";
                ps = con.prepareStatement(qry1);
                rs = ps.executeQuery();
                while (rs.next()) {
                    district = district + rs.getString("dist_code") + "','";
                }
                district = district.substring(0, district.length() - 3) + "')";
            } catch (SQLException e) {
            }
        }
        return district;
    }
}
