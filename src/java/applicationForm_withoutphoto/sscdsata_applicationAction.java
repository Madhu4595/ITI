/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationForm_withoutphoto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author Narendra
 */
public class sscdsata_applicationAction extends org.apache.struts.action.Action {

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
        sscdsata_applicationForm formvariable = (sscdsata_applicationForm) form;
        HttpSession session = request.getSession(true);
        String role_id = "" + session.getAttribute("role_id");
        String field_name = "dist_code";
        if (role_id.equalsIgnoreCase("4")) {
            field_name = "iti_code";
        }
        String ins_code = "" + session.getAttribute("ins_code");
        Connection con = DBC.DBConnection.getConnection(request);
        Statement st = con.createStatement();

        System.out.println("hello. ");
        String sscregno = request.getParameter("sscregno");
        String sscyear = request.getParameter("year");
        String state = sscregno.substring(2, 4);
        String regid = "";
        String addr = request.getParameter("addr");
        String pincode = request.getParameter("pincode");
        String phno = request.getParameter("phno");
        String adarno = request.getParameter("adarno");
        String caste = request.getParameter("caste");
        String phc = request.getParameter("phc");
        String exser = request.getParameter("exser");
        String local = request.getParameter("local");
        int trno = DBC.generic.logTrace((String) request.getSession().getAttribute("userName"), "application & cand marks", "Insert", request);
        String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "7", con);
        String phse = beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "phase", year, "current", "true", con);

        state = beans.MyUtil.getOptionValue("ssc.ssc_dist_mast", "dist_code", "state", state, con);
        //out.println("you given regd no is : " + sscregno + " for the year : " + year + " and state is " + state);
        //String sql = "select name,fname,mname,(case when (substring(dob,7,2))::integer>=50 then substring(dob,1,6)||'19'||substring(dob,7,2) else substring(dob,1,6)||'20'||substring(dob,7,2) end) as dob,(case when sex ilike '%g%' then 'FEMALE' else 'MALE' end) as sex,rollno,l1grade,l1points,l2grade,l2points,l3grade,l3points,matpoints,matgrade,scigrade,scipoints,socgrade,socpoints,gpa,result,dist_name,stream,dist_code from  where rollno='" + sscregno + "'";
        String sql = "INSERT INTO application(name, fname, addr, mname, phno, adarno, gender, caste,ssc_passed, inter_passed, inter_marks, local, pincode,phc, exservice, dob, trno, ssc_regno, ssc_board, ssc_year,ssc_month, phase, year)select name,fname,'" + addr + "',mname,'" + phno + "','" + adarno + "',sex,'" + caste + "','y','n'," + trno + ",'" + local + "','" + pincode + "','" + phc + "','" + exser + "',((substring(dob,7,4))||'-'||substring(dob,4,2)||'-'||substring(dob,1,2))::date,'" + trno + "',rollno,'1','" + sscyear + "','june',hstore(array['" + phse + "'],array['true']),'" + year + "' from ssc.ssc_data_" + sscyear + "_" + state + " where rollno='" + sscregno + "'";
        System.out.println(sql);
        int i = 0;
        try {
            i = st.executeUpdate(sql);
        } catch (Exception ex) {
            System.out.println("ex");
            String qry = "select regid from application where ssc_regno='" + sscregno + "'";
            Statement st2 = con.createStatement();
            ResultSet rs2 = st2.executeQuery(qry);
            while (rs2.next()) {
                regid = rs2.getString("regid");
                // x = false;
                //out.println("<font color=red>This Candidate already have Registration Number " + rs2.getString("regid") + " Click on Generate button to Apply this candidate into Your District Also<br><br></font>");
            }
            st2.close();
            rs2.close();
        }
        if (i > 0) {

            sql = "select regid from application where ssc_regno='" + sscregno + "'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                regid = rs.getString("regid");
                
            }
            System.out.println("Registration Number is :: " + regid);
        }
        request.setAttribute("regid", regid);
        sql = "INSERT INTO trade_sel(regid, " + field_name + ", temp_code,trno, phase, year)VALUES ('" + regid + "', '" + ins_code + "', '" + ins_code + "', " + trno + ", hstore(array['" + phse + "'],array['true']),'" + year + "')";
        try {
            st.executeUpdate(sql);
        } catch (Exception ex) {
            System.out.println("ex");
        }
        sql = "INSERT INTO cand_marks(regid, ssc_first_lang_marks, ssc_first_lang_gpa, ssc_first_lang_grade,  ssc_second_lang_marks, ssc_second_lang_gpa, ssc_second_lang_grade,  ssc_eng_marks, ssc_eng_gpa, ssc_eng_grade, ssc_math_marks, ssc_math_gpa,  ssc_math_grade, ssc_sci_marks, ssc_sci_gpa, ssc_sci_grade, ssc_social_marks,  ssc_social_gpa, ssc_social_grade, ssc_tot_gpa,trno, year)select '" + regid + "',l1mrk::integer,l1points,l1grade,l2mrk::integer,l2points,l2grade,l3mrk::integer,l3points,l3grade,matmrk::integer,matpoints,matgrade,scimrk::integer,scipoints,scigrade,socmrk::integer,socpoints,socgrade,gpa," + trno + ",'" + year + "' from ssc.ssc_data_" + sscyear + "_" + state + "  where rollno='" + sscregno + "'";
        try {
            st.executeUpdate(sql);
        } catch (Exception ex) {
            System.out.println("ex");
        }

        FormFile photo = formvariable.getPhoto();
        String fileName = photo.getFileName();
        PreparedStatement ps = null;
        try {
            if (!("").equals(fileName)) {


                String img_query = "insert into img_cand_photos values(?,?)";
                ps = con.prepareStatement(img_query);
                ps.setInt(1, Integer.parseInt(regid));
                ps.setBinaryStream(2, photo.getInputStream(), (int) photo.getFileSize());



                ps.executeUpdate();

                
            }
        } catch (Exception ex) {
              ps.close();
        con.close();
            System.out.println(ex);
        }
        try{
        ps.close();
        con.close();
        }catch(Exception ex){
            
        }finally{
            if(con!=null){
                con.close();
            }
        }
        return mapping.findForward(SUCCESS);
    }
}
