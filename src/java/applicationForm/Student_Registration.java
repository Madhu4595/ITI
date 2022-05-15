/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationForm;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Azad
 */
public class Student_Registration extends org.apache.struts.action.Action {

    private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        System.out.println("regidedit........");

        EditForm uf = (EditForm) form;
        String regid = beans.MyUtil.filterBad(uf.getRegid());
        String ssc_regno =  beans.MyUtil.filterBad(uf.getSsc_regno());
        String dob =  beans.MyUtil.filterBad(uf.getDob());
        System.out.println("dateofbirth----" + dob);
        Date dateofbirth = Date.valueOf(DBC.MyUtil.ChDate1(dob));
        
        Connection con = null;
        Connection connect = null;
        
        Statement st = null;
        Statement stat = null;
        PreparedStatement ps = null;
        
        ResultSet rs = null, rs1 = null;
        ResultSet rst = null;
        
        
        String iticodes = null, itinames = null;
        String redirect = "success";
        
        HttpSession ses = request.getSession();
        
        String stored_token = beans.MyUtil.filterBad(""+ses.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
        
        System.out.println("after getting session  ");
        ses.invalidate();

        ses = request.getSession();

        System.out.println("regid" + regid);
        System.out.println("ssc_regno" + ssc_regno);
        System.out.println("dob" + dob);

        try {
            con = DBC.DBConnection.getConnectionGen();
            String sql = "select * from student_application where regid=? and dob=? and ssc_regno=?";
            System.out.println("ghrugrhgu" + sql);
            ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(regid));
            ps.setDate(2, dateofbirth);
            ps.setString(3, ssc_regno);
            rs1 = null;
            rs = ps.executeQuery();

            if (rs.next()) {
                ses.setAttribute("action", "update");
                sql = "Select * from student_cand_marks where regid=?";
                ps = con.prepareStatement(sql);
                ps.setString(1, regid);
                rs1 = ps.executeQuery();
                if (rs1.next()) {
                    ses.setAttribute("first_lang_marks", rs1.getString("ssc_first_lang_gpa"));
                    System.out.println("yfgfygfgf" + rs1.getString("ssc_first_lang_gpa"));
                    ses.setAttribute("second_lang_marks", rs1.getString("ssc_second_lang_gpa"));
                    ses.setAttribute("eng_marks", rs1.getString("ssc_eng_gpa"));
                    ses.setAttribute("maths_marks", rs1.getString("ssc_math_gpa"));
                    ses.setAttribute("science_marks", rs1.getString("ssc_sci_gpa"));
                    ses.setAttribute("social_marks", rs1.getString("ssc_social_gpa"));
                    ses.setAttribute("tot_gpa", rs1.getString("ssc_tot_gpa"));

                    ses.setAttribute("first_marks", rs1.getString("ssc_first_lang_marks"));
                    ses.setAttribute("second_marks", rs1.getString("ssc_second_lang_marks"));
                    ses.setAttribute("eng1_marks", rs1.getString("ssc_eng_marks"));
                    ses.setAttribute("maths1_marks", rs1.getString("ssc_math_marks"));
                    ses.setAttribute("science1_marks", rs1.getString("ssc_sci_marks"));
                    ses.setAttribute("social1_marks", rs1.getString("ssc_social_marks"));
                    ses.setAttribute("tot_marks_gpa", rs1.getString("ssc_tot_marks"));

                    ses.setAttribute("inter1s", rs1.getString("inter_first_sub"));
                    ses.setAttribute("inter1m", rs1.getString("inter_first_marks"));
                    ses.setAttribute("inter2s", rs1.getString("inter_second_sub"));
                    ses.setAttribute("inter2m", rs1.getString("inter_second_marks"));
                    ses.setAttribute("inter3s", rs1.getString("inter_third_sub"));
                    ses.setAttribute("inter3m", rs1.getString("inter_third_marks"));

                }
                ses.setAttribute("regid", regid);
                ses.setAttribute("ssc_type", rs.getString("ssc_type"));
                ses.setAttribute("ssc", rs.getString("ssc_passed"));
                System.out.println("gfhjfg" + regid);
                ses.setAttribute("phc", rs.getBoolean("phc") ? "yes" : "no");
                ses.setAttribute("exservice", rs.getBoolean("exservice") ? "yes" : "no");
                ses.setAttribute("ssc", rs.getBoolean("ssc_passed") ? "yes" : "no");
                ses.setAttribute("inter", rs.getBoolean("inter_passed") ? "yes" : "no");
                ses.setAttribute("name", rs.getString("name"));
                ses.setAttribute("fname", rs.getString("fname"));
                ses.setAttribute("mname", rs.getString("mname"));
                ses.setAttribute("addr", rs.getString("addr"));
                ses.setAttribute("phno", rs.getString("phno"));
                ses.setAttribute("gen", rs.getString("gender"));
                ses.setAttribute("ssc_regno", rs.getString("ssc_regno"));
                ses.setAttribute("ssc_board", rs.getString("ssc_board"));
                System.out.println("boardname" + rs.getString("ssc_board"));
                ses.setAttribute("ssc_year", rs.getString("ssc_year"));
                ses.setAttribute("ssc_month", rs.getString("ssc_month"));
                ses.setAttribute("caste", rs.getString("caste"));
                ses.setAttribute("adarno", rs.getString("adarno"));
                ses.setAttribute("local", rs.getString("local"));
                ses.setAttribute("pincode", rs.getString("pincode"));
                ses.setAttribute("dob", beans.MyUtil.ChDate(rs.getString("dob")));
                ses.setAttribute("intermarks", rs.getString("inter_marks"));
                System.out.println("email---->" + rs.getString("email"));
                ses.setAttribute("email", rs.getString("email"));
            } else {
                request.setAttribute("success", "No records Found");
                redirect = "nodata";
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.err.println("exception is--->" + ex);
            ex.printStackTrace();
            if (rs != null) {
                rs.close();
            }
            if (rs1 != null) {
                rs1.close();
            }
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }
        }
        try {
            connect = DBC.DBConnection.getConnectionGen();
            String sqls = "select iti.iti_code from student_trade_sel inner join iti on student_trade_sel.iti_code=iti.iti_code where student_trade_sel.regid=?";
            System.out.println("qqqqqqqqqqqqhyjyhj----" + sqls);
            ps = con.prepareStatement(sqls);
            ps.setString(1, regid);
            rst = ps.executeQuery();
            System.out.println("rgtght" + rst);
            int i = 0;
            while (rst.next()) {
                itinames = rst.getString("iti_code");
                if (iticodes == null) {
                    iticodes = itinames;

                } else {

                    iticodes = iticodes + "," + itinames;

                }

            }
            ses.setAttribute("iticodes", iticodes);
            System.out.println("iticodes" + iticodes);

        } catch (Exception ex) {
            System.out.println("iticodes" + ex);

            ex.printStackTrace();
        } finally {
            if (rst != null) {
                rst.close();
            }
            if (stat != null) {
                stat.close();
            }

        }

        return mapping.findForward(redirect);
    }
}
