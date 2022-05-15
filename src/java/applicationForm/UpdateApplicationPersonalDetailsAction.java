/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationForm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author SUBBAREDDY
 */
public class UpdateApplicationPersonalDetailsAction extends Action {

    private UpdateApplicationPersonalDetailsForm updateApplicationPersonalDetailsForm;

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
       HttpSession ses = request.getSession();
        
        String stored_token = beans.MyUtil.filterBad(""+ses.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
        
        Connection con = null;
        PreparedStatement st = null;
        
        try {
            String strval = "";
            String opr = "";
            updateApplicationPersonalDetailsForm = (UpdateApplicationPersonalDetailsForm) form;
            java.sql.Date date = java.sql.Date.valueOf(DBC.MyUtil.ChDate1(updateApplicationPersonalDetailsForm.getDate_of_birth()));
            FormFile pic = updateApplicationPersonalDetailsForm.getPhoto();
            String regid = updateApplicationPersonalDetailsForm.getRegid();
            
            con = DBC.DBConnection.getConnection(request);
            String val = beans.MyUtil.getOptionValue("iti_params", "code", "value", "14", con);
            ResultSet rs = null;
            try {
                String sql = "";
                if (val.equalsIgnoreCase("application")) {
                    sql = "select * from img_cand_photos where regid=?";
                } else {
                    sql = "select * from exams.img_cand_photos_exams where adm_num=?";
                }
                st = con.prepareStatement(sql);
                st.setString(1, regid);
                rs = st.executeQuery();

                if (rs.next()) {
                    opr = "Update";
                } else {
                    opr = "Insert";
                }
                if (!val.equalsIgnoreCase("application")) {
                     ses = request.getSession();
                    
                     String role_id = beans.MyUtil.filterBad("" + ses.getAttribute("role_id"));
                    
                     if (!pic.getFileName().equals("")) {
                        if (opr.equalsIgnoreCase("Insert")) {
                            DBC.DBConnection.insertPhoto("exams.img_cand_photos_exams", opr, "image", pic, "adm_num$" + regid, request);
                        } else {

                            DBC.DBConnection.insertPhoto("exams.img_cand_photos_exams", opr, "image", pic, "adm_num$" + regid, request);
                            strval = strval + " ";
                        }
                    }
                } else {
                      ses = request.getSession();
                    String role_id = beans.MyUtil.filterBad("" + ses.getAttribute("role_id"));
                    if (!pic.getFileName().equals("")) {
                        if (opr.equalsIgnoreCase("Insert")) {
                            DBC.DBConnection.insertPhoto("img_cand_photos", opr, "pic", pic, "regid" + regid, request);
                        } else {

                            DBC.DBConnection.insertPhoto("img_cand_photos", opr, "pic", pic, "regid" + regid, request);
                            strval = strval + " ";
                        }
                    }
                }

            } catch (Exception ex) {
            }
            String name = updateApplicationPersonalDetailsForm.getName();
            String fname = updateApplicationPersonalDetailsForm.getFname().trim();
            String dob = updateApplicationPersonalDetailsForm.getDate_of_birth();
            dob = beans.MyUtil.ChDate(dob);
            int trno = DBC.generic.logTrace((String) request.getSession().getAttribute("userName"), "application", "Update", request);

            String sql = ""; 

            if (val.equalsIgnoreCase("application")) {
                sql = "update application set name=?,fname=?,dob=?, trno=? where regid=?";
                st = con.prepareStatement(sql);
                st.setString(1, name);
                st.setString(2, fname);
                st.setString(3, dob);
                st.setInt(4, trno);
                st.setString(5, regid);
            } else {
                if(request.getSession().getAttribute("userName").toString().equalsIgnoreCase("TESTDIST")){
                sql = "update admissions.iti_admissions set name=?,fname=?,dob=? where adm_num=?";
                
                }else{
                    sql = "update admissions.iti_admissions set name=?,fname=?,dob=? where adm_num=?";//and  year_of_admission='2015'";
                }
                st = con.prepareStatement(sql);
                st.setString(1, name);
                st.setString(2, fname);
                st.setString(3, dob);
                st.setString(4, regid);
            }
            if (opr.equalsIgnoreCase("Insert")) {
                
                st.executeUpdate();
                strval = strval + "<center><font color=\"green\"><h2>Inserted Image Successfully</h2><html:errors/></font><br/>\n<a href=\"newjspAUpdate_AdmissionDetails_Interface.jsp\">Update Another Application</a>\n</center>";
                request.setAttribute("success", strval);
            } else {
                st.executeUpdate();
                strval = strval + "<center><font color=\"green\"><h2>Updated Success.</h2><html:errors/></font><br/>\n<a href=\"newjspAUpdate_AdmissionDetails_Interface.jsp\">Try Another Application</a>\n</center>";
                request.setAttribute("success", strval);
            }
            return mapping.findForward("success");
        } catch (Exception e) {
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return null;
    }
}
