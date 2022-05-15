/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nic.confirmation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author theru hareesh
 */
public class ApplicationAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    /**
     * This is the action called from the Struts framework.
     *
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession session = request.getSession();
        
        String stored_token = beans.MyUtil.filterBad(""+session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
        
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        int reg_id = Integer.parseInt(request.getParameter("reg_id"));
        System.out.println("the reg_id value is:" + reg_id);
        session.setAttribute("reg_id", reg_id);

        try {
            con = DBC.DBConnection.getConnection(request);
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT  name,fname,dob,inter_marks,caste,ssc_first_lang_marks,ssc_first_lang_gpa, ssc_first_lang_grade, ssc_second_lang_marks,ssc_second_lang_gpa, ssc_second_lang_grade, ssc_eng_marks, ssc_eng_gpa,ssc_eng_grade, ssc_math_marks, ssc_math_gpa, ssc_math_grade,ssc_sci_marks, ssc_sci_gpa, ssc_sci_grade, ssc_social_marks,ssc_social_gpa, ssc_social_grade,ssc_tot_marks, ssc_tot_gpa FROM print_application where regid=" + reg_id + " limit 1");
            ArrayList list = new ArrayList();

            while (rs.next()) {
                ConfirmationFormBean verify = new ConfirmationFormBean();

                verify.setName(rs.getString(1));
                verify.setFather_name(rs.getString(2));
                verify.setDob(rs.getString(3));
                verify.setInt_marks(rs.getInt(4));
                verify.setCategory(rs.getString(5));

                verify.setSsc_first_lang_marks(rs.getInt(6));
                verify.setSsc_first_lang_gpa(rs.getInt(7));
                verify.setSsc_first_lang_grade(rs.getString(8));

                verify.setSsc_second_lang_marks(rs.getInt(9));
                verify.setSsc_second_lang_gpa(rs.getInt(10));
                verify.setSsc_second_lang_grade(rs.getString(11));

                verify.setSsc_eng_marks(rs.getInt(12));

                verify.setSsc_eng_gpa(rs.getInt(13));
                verify.setSsc_eng_grade(rs.getString(14));

                verify.setSsc_math_marks(rs.getInt(15));
                verify.setSsc_math_gpa(rs.getInt(16));
                verify.setSsc_math_grade(rs.getString(17));

                verify.setSsc_sci_marks(rs.getInt(18));
                verify.setSsc_sci_gpa(rs.getInt(19));
                verify.setSsc_sci_grade(rs.getString(20));

                verify.setSsc_social_marks(rs.getInt(21));
                verify.setSsc_social_gpa(rs.getInt(22));
                verify.setSsc_social_grade(rs.getString(23));

                verify.setSsc_tot_marks(rs.getInt(24));
                verify.setSsc_tot_gpa(rs.getInt(25));

                list.add(verify);

            }
            session.setAttribute("list", list);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }

        }
        return mapping.findForward(SUCCESS);
    }
}
