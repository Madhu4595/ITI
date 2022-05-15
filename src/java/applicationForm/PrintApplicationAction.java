package applicationForm;

import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.text.SimpleDateFormat;
import beans.MyUtil;
import DBC.DBConnection;
import java.sql.PreparedStatement;
import org.apache.struts.action.ActionForward;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.Action;

public class PrintApplicationAction extends Action {

    @Override
    public ActionForward execute(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        System.out.println("UR IN PrintApplicationAction.java->");

        final PrintApplicationForm formvariable = (PrintApplicationForm) form;

        final HttpSession ses = request.getSession();
        
        String regid = beans.MyUtil.filterBad(formvariable.getRegid());
 System.out.println("regid--->"+regid);
        if (regid.isEmpty() || regid.equalsIgnoreCase("null") || regid.equals("")) {
            regid = beans.MyUtil.filterBad("" + ses.getAttribute("regid"));
        }

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        String app_status = null;
        String user_id = null;

        String redirect = "success";

        //TOKEN MATCHING CODE
        String stored_token = beans.MyUtil.filterBad("" + ses.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
 
       String id = stored_token.substring(10, 20) + request.getSession().getId() + stored_token.substring(2, 6);
//        if (!token.equals(id)) { return mapping.findForward("failure"); }

        try {
            con = DBConnection.getConnectionGen();
          

            PreparedStatement ps = null;
            String sql1 = "select * from student_application where regid= ?";
            ps = con.prepareStatement(sql1);
            ps.setInt(1, Integer.parseInt(regid));
            rs = ps.executeQuery();

            final String ssc_board = beans.MyUtil.filterBad("" + ses.getAttribute("ssc_board"));
            System.out.println("ssc_ board is" + ssc_board);
            if (rs.next()) {
                ses.setAttribute("action", (Object) "update");
                sql1 = "select * from student_cand_marks where regid= ?";
                ps = null;
                ps = con.prepareStatement(sql1);
                ps.setString(1, regid);
                rs2 = ps.executeQuery();
                if (rs2.next()) {
                    ses.setAttribute("first_lang_marks", (Object) rs2.getString("ssc_first_lang_gpa"));
                    ses.setAttribute("second_lang_marks", (Object) rs2.getString("ssc_second_lang_gpa"));
                    ses.setAttribute("eng_marks", (Object) rs2.getString("ssc_eng_gpa"));
                    ses.setAttribute("maths_marks", (Object) rs2.getString("ssc_math_gpa"));
                    ses.setAttribute("science_marks", (Object) rs2.getString("ssc_sci_gpa"));
                    ses.setAttribute("social_marks", (Object) rs2.getString("ssc_social_gpa"));
                    ses.setAttribute("tot_gpa", (Object) rs2.getString("ssc_tot_gpa"));
                    ses.setAttribute("first_marks", (Object) rs2.getString("ssc_first_lang_marks"));
                    ses.setAttribute("second_marks", (Object) rs2.getString("ssc_second_lang_marks"));
                    ses.setAttribute("eng1_marks", (Object) rs2.getString("ssc_eng_marks"));
                    ses.setAttribute("maths1_marks", (Object) rs2.getString("ssc_math_marks"));
                    ses.setAttribute("science1_marks", (Object) rs2.getString("ssc_sci_marks"));
                    ses.setAttribute("social1_marks", (Object) rs2.getString("ssc_social_marks"));
                    ses.setAttribute("tot_marks_gpa", (Object) rs2.getString("ssc_tot_marks"));
                    ses.setAttribute("inter1s", (Object) rs2.getString("inter_first_sub"));
                    ses.setAttribute("inter1m", (Object) rs2.getString("inter_first_marks"));
                    ses.setAttribute("inter2s", (Object) rs2.getString("inter_second_sub"));
                    ses.setAttribute("inter2m", (Object) rs2.getString("inter_second_marks"));
                    ses.setAttribute("inter3s", (Object) rs2.getString("inter_third_sub"));
                    ses.setAttribute("inter3m", (Object) rs2.getString("inter_third_marks"));
                }
                ses.setAttribute("regid", (Object) regid);
                ses.setAttribute("phc", (Object) (rs.getBoolean("phc") ? "YES" : "NO"));
                final String sscboard_name = MyUtil.getOptionValue("admissions.ssc_examboard_mst", "board_code", "board_name", "" + rs.getString("ssc_board"), con);
                System.out.println("sdfk;sdkfsd" + sscboard_name);
                ses.setAttribute("exservice", (Object) (rs.getBoolean("exservice") ? "YES" : "NO"));
                ses.setAttribute("ssc", (Object) (rs.getBoolean("ssc_passed") ? "YES" : "NO"));
                ses.setAttribute("inter", (Object) (rs.getBoolean("inter_passed") ? "YES" : "NO"));
                ses.setAttribute("name", (Object) rs.getString("name"));
                ses.setAttribute("fname", (Object) rs.getString("fname"));
                ses.setAttribute("mname", (Object) rs.getString("mname"));
                ses.setAttribute("addr", (Object) rs.getString("addr"));
                ses.setAttribute("phno", (Object) rs.getString("phno"));
                ses.setAttribute("gen", (Object) rs.getString("gender"));
                ses.setAttribute("ssc_regno", (Object) rs.getString("ssc_regno"));
                ses.setAttribute("ssc_board", (Object) sscboard_name);
                ses.setAttribute("ssc_year", (Object) rs.getString("ssc_year"));
                ses.setAttribute("ssc_month", (Object) rs.getString("ssc_month"));
                ses.setAttribute("caste", (Object) rs.getString("caste"));
                ses.setAttribute("ssc_type", (Object) rs.getString("ssc_type"));
                ses.setAttribute("email", (Object) rs.getString("email"));
                ses.setAttribute("ews", (Object) (rs.getBoolean("economic_weaker_section") ? "YES" : "NO"));
                ses.setAttribute("itd", (Object) (rs.getBoolean("is_trainee_dual_mode") ? "YES" : "NO"));
                String aadhaar_no = rs.getString("adarno");
                final Timestamp edate = rs.getTimestamp("entry_date");
                final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                final String date = formatter.format(edate);
                ses.setAttribute("entry_date", (Object) date);
                if (aadhaar_no == null || aadhaar_no.equals("")) {
                    aadhaar_no = "NA";
                } else {
                    aadhaar_no = "XXXXXXXX".concat(aadhaar_no.substring(8, 12));
                }
                ses.setAttribute("email", (Object) rs.getString("email"));
                ses.setAttribute("ews", (Object) (rs.getBoolean("economic_weaker_section") ? "YES" : "NO"));
                ses.setAttribute("itd", (Object) (rs.getBoolean("is_trainee_dual_mode") ? "YES" : "NO"));
                ses.setAttribute("entry_date", (Object) date);
                ses.setAttribute("adarno", (Object) aadhaar_no);
                ses.setAttribute("local", (Object) rs.getString("local"));
                ses.setAttribute("pincode", (Object) rs.getString("pincode"));
                ses.setAttribute("dob", (Object) MyUtil.ChDate(rs.getString("dob")));
                ses.setAttribute("intermarks", (Object) rs.getString("inter_marks"));
                app_status = rs.getString("app_status");
                user_id = rs.getString("user_id");
                System.out.println("redirct in if-------->" + redirect);
            } else {
                redirect = "nodata";
                // return mapping.findForward("nodata");
                System.out.println("redirect-------->" + redirect);
            }
            rs.close();
            st.close();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            if (rs != null) {
                rs.close();
            }
            if (rs2 != null) {
                rs2.close();
            }
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return mapping.findForward(redirect);
    }
}
