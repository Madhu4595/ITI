package admissions;

import javax.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import org.apache.struts.action.ActionMessage;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import DBC.DBConnection;
import org.apache.struts.action.ActionErrors;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import beans.MyUtil;
import java.sql.PreparedStatement;
import org.apache.struts.action.ActionForm;


public class Admission_InterfaceForm extends ActionForm
{
    String rank;
    
    public String getRank() {
        return MyUtil.filterBad(this.rank);
    }
    
    public void setRank(final String rank) {
        this.rank = rank;
    }
    
    public ActionErrors validate(final ActionMapping mapping, final HttpServletRequest request) {
        System.out.println("UR in Admission_InterfaceForm.java");
        
        final ActionErrors errors = new ActionErrors();
       //rank
       if(getRank() == null || getRank().length()<1){
           errors.add("rank", new ActionMessage("error.rank.required"));
       }
        
        
        
        Connection con = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
       
        Statement st1 = null;
        ResultSet rs1 = null;
        String regid = "";
        String sscregno="";
        
        Boolean merit_list_generated = false;
        Boolean seat_matrix_generated = false;
        Boolean already_admitted = false;
        String adm_iti_code = null;
        
        final HttpSession session = request.getSession();
        final String iti_code = beans.MyUtil.filterBad("" + session.getAttribute("ins_code"));
        final String role_id = beans.MyUtil.filterBad("" + session.getAttribute("role_id"));
        
        String field = "";
        if (role_id.equals("3")) {
            field = "dist_code";
        }
        else if (role_id.equals("4")) {
            field = "iti_code";
        }
        String year = "";
        String phase = "";
        try {
            con = DBConnection.getConnectionGen();
            year = MyUtil.getOptionValue("iti_params", "code", "value", "7", con);
            //phase="2";
            phase = MyUtil.getOptionValue("admissions.admission_phase", "year", "phase", year, "current", "true", con);
        }
        catch (Exception g) {
            System.out.println("Exception iss  " + g);
            if (con != null) {
                try {
                    con.close();
                }
                catch (SQLException ex) {
                    Logger.getLogger(Admission_InterfaceForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (con != null) {
                try {
                    con.close();
                }
                catch (SQLException ex2) {
                    Logger.getLogger(Admission_InterfaceForm.class.getName()).log(Level.SEVERE, null, ex2);
                }
            }
            if (con != null) {
                try {
                    con.close();
                }
                catch (SQLException ex3) {
                    Logger.getLogger(Admission_InterfaceForm.class.getName()).log(Level.SEVERE, null, ex3);
                }
            }
        }
        finally {
            if (con != null) {
                try {
                    con.close();
                }
                catch (SQLException ex4) {
                    Logger.getLogger(Admission_InterfaceForm.class.getName()).log(Level.SEVERE, null, ex4);
                }
            }
        }
        final String merit_report = "select regid from ranks where ?=? and rank='" + this.rank + "' and phase=?";
        try {
            con = DBConnection.getConnectionGen();
            //st = con.createStatement();
            ps = con.prepareStatement(merit_report);
            ps.setString(1, field);
            ps.setString(2, iti_code);
            ps.setString(3, phase);
            rs = ps.executeQuery(merit_report);
            if (rs.next()) {
                merit_list_generated = true;
                regid = rs.getString("regid");
            }
            rs.close();
            if (merit_list_generated) {
                final String iti_total = "select (select sum(strength) from ititrade where " + field + "='" + iti_code + "')=(select sum(strength) from iti_seatmatrix where iti_code='" + iti_code + "') as result";
                seat_matrix_generated = true;
                System.out.println("regid" + regid);
                 final String hallticket="select ssc_regno from admissions.iti_admissions where regid=?";
                con = DBConnection.getConnectionGen();
                
            //st1 = con.createStatement();
            ps = con.prepareStatement(hallticket);
            ps.setString(1, regid);
            rs1 = ps.executeQuery(hallticket);
            if (rs1.next()) {
               
             sscregno=rs.getString("ssc_regno");
            }
               
                
                
                final String already_admitted_query = "select adm_num,iti_code from adm_year2020 where ssc_regno =?";
               // final String already_admitted_query = "select adm_num,iti_code from adm_year2020 where regid ='" + regid + "'";
               
               System.out.println("afterquery" + regid);
               ps=con.prepareStatement(already_admitted_query);
               ps.setString(1, sscregno);
                rs = ps.executeQuery(already_admitted_query);
                if (rs.next()) {
                    adm_iti_code = rs.getString("iti_code");
                    System.out.println("adm_iti_code" + adm_iti_code);
                    already_admitted = true;
                }
                if (seat_matrix_generated && already_admitted) {
                    errors.add("rank", new ActionMessage("error.already_admitted", (Object)("" + adm_iti_code)));
                }
            }
            else {
                errors.add("rank", new ActionMessage("error.merit_list_not_generated"));
            }
            System.out.println("ifelseclose");
        }
        catch (Exception ex9) {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            }
            catch (Exception ex5) {
                System.out.println("Exception in connection close is " + ex5);
            }
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            }
            catch (Exception ex6) {
                System.out.println("Exception in connection close is " + ex6);
            }
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            }
            catch (Exception ex7) {
                System.out.println("Exception in connection close is " + ex7);
            }
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            }
            catch (Exception ex8) {
                System.out.println("Exception in connection close is " + ex8);
            }
        }
        return errors;
    }
}