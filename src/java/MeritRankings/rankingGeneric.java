/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MeritRankings;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Admin
 */
public class rankingGeneric {

    public static String getList(String iti_code, String qual, String category,HttpServletRequest request) {
        String output = "";
        String fieldname = "caste";
        if ("phc".equals(category)) {
            fieldname = "phc=true";
        } else if ("exs".equals(category)) {
            fieldname = "exs=true";
        } else {
            fieldname = fieldname + "='" + category + "'";
        }

        String sql = "Select * from merit_report where " + fieldname + " and dist_code='" + iti_code + "' and qual='" + qual + "' order by cast(rank as integer) asc";
        Connection con = DBC.DBConnection.getConnection(request);
        System.out.println("sql query " + sql);
        Statement st = null, st2 = null;
        ResultSet rs = null, rs2 = null;
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
            String regid = "";
            String rank = "";
            String caste = "";
            int i = 0;
            while (rs.next()) {
                //System.out.println("loaded");
                regid = rs.getString("regid");
                rank = rs.getString("rank");
                caste = rs.getString("caste");
                //System.out.println("regid " + regid);
                output += "<tr><td>" + (++i) + "</td>";
                output += "<td align='center'>" + rank + "</td><td>" + regid + "</td>";
                output += "<td>" + caste + "</td>";
                output += "<td>" + rs.getString("name") + "</td>";
                output += "<td align='center'>" + rs.getString("dob") + "</td>";
                output += "<td align='center'>" + rs.getString("ssc_tot_gpa") + "</td>";
                output += "<td align='center'>" + rs.getString("ssc_math_gpa") + "</td>";
                output += "<td align='center'>" + rs.getString("ssc_sci_gpa") + "</td>";
                output += "<td align='center'>" + rs.getString("ssc_social_gpa") + "</td>";
                output += "<td align='center'>" + rs.getString("ssc_eng_gpa") + "</td>";
                output += "<td align='center'>" + rs.getString("ssc_first_lang_gpa") + "</td>";
                output += "<td align='center'>" + rs.getString("ssc_second_lang_gpa") + "</td>";
                output += "</tr>";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return output;
    }

    public static String getList(String iti_code, String qual,HttpServletRequest request) {
        //ssc_tot_marks desc, ssc_math_gpa desc, ssc_sci_gpa desc, ssc_social_gpa desc, ssc_eng_gpa desc, ssc_first_lang_gpa desc, ssc_second_lang_gpa desc, dob desc, regid";
        String sql = "Select * from merit_report where dist_code='" + iti_code + "' and qual='" + qual + "' order by rank::integer asc ";
        Connection con = DBC.DBConnection.getConnection(request);
        Statement st = null;
        ResultSet rs = null;
        String output = "";
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                output += "<tr><td align='center'>" + rs.getString("rank") + "</td><td>" + rs.getString("regid") + "</td>";
                output += "<td>" + rs.getString("name") + "</td>";
                output += "<td>" + rs.getString("fname") + "</td>";
                output += "<td>" + rs.getString("caste") + "</td>";
                output += "<td align='center'>" + rs.getString("dob") + "</td>";
                output += "<td align='center'>" + rs.getString("ssc_tot_gpa") + "</td>";
                output += "<td align='center'>" + rs.getString("ssc_math_gpa") + "</td>";
                output += "<td align='center'>" + rs.getString("ssc_sci_gpa") + "</td>";
                output += "<td align='center'>" + rs.getString("ssc_social_gpa") + "</td>";
                output += "<td align='center'>" + rs.getString("ssc_eng_gpa") + "</td>";
                output += "<td align='center'>" + rs.getString("ssc_first_lang_gpa") + "</td>";
                output += "<td align='center'>" + rs.getString("ssc_second_lang_gpa") + "</td>";
                output += "</tr>";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
                st.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(rankingGeneric.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return output;
    }
}
