/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationForm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Admin
 */
public class approve_rejectAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        approve_rejectForm uf = (approve_rejectForm) form;
        String regid = beans.MyUtil.filterBad(uf.getRegid());
        System.out.println("fdkjgldfjgfdgd" + regid);
        
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null, rs1 = null;
        
        String app_status = null;
        String user_id = null;
        String redirect = "success";
        
        try {
            con = DBC.DBConnection.getConnection(request);
            String sql = "select * from student_application where regid=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, regid);
            System.out.println("ygyfgftdd" + sql);
            rs1 = null;
            rs = ps.executeQuery();

            HttpSession ses = request.getSession();

            String stored_token = beans.MyUtil.filterBad("" + ses.getAttribute("generated_token"));
            String token = beans.MyUtil.filterBad(request.getParameter("token"));

            String id = stored_token.substring(10, 20) + request.getSession().getId() + stored_token.substring(2, 6);
            if (!token.equals(id)) { return mapping.findForward("failure"); }

            String ssc_board = beans.MyUtil.filterBad("" + ses.getAttribute("ssc_board"));
            System.out.println("ssc_ board is" + ssc_board);

            if (rs.next()) {
                ses.setAttribute("action", "update");
                sql = "Select * from student_cand_marks where regid=?";
                ps = con.prepareStatement(sql);
                ps.setString(1, regid);
                rs1 = ps.executeQuery();
                if (rs1.next()) {
                    ses.setAttribute("first_lang_marks", rs1.getString("ssc_first_lang_gpa"));
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
                ses.setAttribute("ssc_year", rs.getString("ssc_year"));
                ses.setAttribute("ssc_month", rs.getString("ssc_month"));
                ses.setAttribute("caste", rs.getString("caste"));
                ses.setAttribute("adarno", rs.getString("adarno"));
                ses.setAttribute("local", rs.getString("local"));
                System.out.println("local======" + rs.getString("local"));
                ses.setAttribute("pincode", rs.getString("pincode"));
                ses.setAttribute("dob", beans.MyUtil.ChDate(rs.getString("dob")));
                ses.setAttribute("intermarks", rs.getString("inter_marks"));
                ses.setAttribute("ssc_type", rs.getString("ssc_type"));
                app_status = rs.getString("app_status");
                user_id = rs.getString("user_id");
            }
            if (app_status.equalsIgnoreCase("A") || app_status.equalsIgnoreCase("R")) {
                ses.setAttribute("app_status", rs.getString("app_status"));
                System.out.println("TFYFYYYT" + app_status);
                ses.setAttribute("user_id", rs.getString("user_id"));
                redirect = "approved";
            }
            rs.close();
            rs1.close();
            ps.close();
            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            if (rs != null) {
                rs.close();
            }
            if (rs1 != null) {
                rs1.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return mapping.findForward(redirect);
    }
}
