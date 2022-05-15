package applicationForm;

import beans.MyUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author APDCA
 */
public class verification_reject_print_action extends org.apache.struts.action.Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        PrintApplicationForm formvariable = (PrintApplicationForm) form;
       
        HttpSession ses = request.getSession();
        
        String stored_token = beans.MyUtil.filterBad(""+ses.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
        
        String regid = beans.MyUtil.filterBad("" + ses.getAttribute("regid"));
        String role_id = beans.MyUtil.filterBad("" + ses.getAttribute("role_id"));
        String username = "";
        String principal_name = "";

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null, rs1 = null;
        String app_status = null;
        String user_id = null;
        String redirect = "success";
        
        try {
            con = DBC.DBConnection.getConnection(request);
            String sql = "select * from student_application where regid=?";
            System.out.println("ygyfgftdd" + sql);
            ps = con.prepareStatement(sql);
            ps.setString(1, regid);
            rs1 = null;
            rs = ps.executeQuery();

            String ssc_board = beans.MyUtil.filterBad("" + ses.getAttribute("ssc_board"));
            System.out.println("ssc_ board is" + ssc_board);

            if (rs.next()) {
                ses.setAttribute("action", "update");
                sql = "Select * from cand_marks where regid=?";
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
                ses.setAttribute("phc", rs.getBoolean("phc") ? "YES" : "NO");

                String sscboard_name = beans.MyUtil.getOptionValue("admissions.ssc_examboard_mst", "board_code", "board_name", "" + rs.getString("ssc_board"), con);
                System.out.println("sdfk;sdkfsd" + sscboard_name);
                ses.setAttribute("exservice", rs.getBoolean("exservice") ? "YES" : "NO");
                ses.setAttribute("ssc", rs.getBoolean("ssc_passed") ? "YES" : "NO");
                ses.setAttribute("inter", rs.getBoolean("inter_passed") ? "YES" : "NO");
                ses.setAttribute("name", rs.getString("name"));
                ses.setAttribute("fname", rs.getString("fname"));
                ses.setAttribute("mname", rs.getString("mname"));
                ses.setAttribute("addr", rs.getString("addr"));
                ses.setAttribute("phno", rs.getString("phno"));
                ses.setAttribute("gen", rs.getString("gender"));
                ses.setAttribute("ssc_regno", rs.getString("ssc_regno"));
                ses.setAttribute("ssc_board", sscboard_name);
                ses.setAttribute("ssc_year", rs.getString("ssc_year"));
                ses.setAttribute("ssc_month", rs.getString("ssc_month"));
                ses.setAttribute("caste", rs.getString("caste"));
                ses.setAttribute("ssc_type", rs.getString("ssc_type"));
                String aadhaar_no = rs.getString("adarno");
                if (aadhaar_no == null || aadhaar_no == "" || aadhaar_no.equals("")) {
                    aadhaar_no = "NA";
                } else {
                    aadhaar_no = "XXXXXXXX".concat(aadhaar_no.substring(8, 12));
                }
                ses.setAttribute("adarno", aadhaar_no);
                ses.setAttribute("local", rs.getString("local"));
                ses.setAttribute("pincode", rs.getString("pincode"));
                ses.setAttribute("dob", beans.MyUtil.ChDate(rs.getString("dob")));
                ses.setAttribute("intermarks", rs.getString("inter_marks"));
                Timestamp edate = rs.getTimestamp("verified_date");
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String date = formatter.format(edate);
                ses.setAttribute("verified_date", date);
                ses.setAttribute("reason", rs.getString("reason"));
                ses.setAttribute("status", rs.getString("app_status"));
                System.out.println("vvvvvvvvvvvvvvvvvvv-->" + date);

                app_status = rs.getString("app_status");
                user_id = rs.getString("user_id");
                ses.setAttribute("user_id", user_id);
                System.out.println("user id-------->" + user_id);
                ses.setAttribute("ssc_eq", rs.getString("ssc_eq_marks"));
                ses.setAttribute("tc", rs.getString("tc"));
                ses.setAttribute("cc", rs.getString("caste_certificate"));
                ses.setAttribute("pc", rs.getString("ph_challenge"));
                ses.setAttribute("exsv", rs.getString("ex_service"));
                ses.setAttribute("sc", rs.getString("study_certificate"));
                ses.setAttribute("nc", rs.getString("nativity_certificate"));
                ses.setAttribute("uidv", rs.getString("uidv"));
                ses.setAttribute("ewsv", rs.getString("ewsv"));

                if (role_id.equalsIgnoreCase("3")) {
                    username = MyUtil.getOptionValue("dist_mst", "dist_code", "dist_name", rs.getString("user_id"), con);

                } else if (role_id.equalsIgnoreCase("4")) {
                    username = MyUtil.getOptionValue("iti", "iti_code", "iti_name", rs.getString("user_id"), con);
                }
                ses.setAttribute("username", username);

            }

            rs.close();
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
        return mapping.findForward("success");
    }
}
