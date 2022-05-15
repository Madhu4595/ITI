/* Decompiler 18ms, total 1090ms, lines 92 */
package applicationForm;

import DBC.DBConnection;
import beans.MyUtil;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class Api_datainsert extends Action {

    Connection con = null;
    boolean commit = true;
    PreparedStatement st = null;
    ResultSet rs = null;
    String redirect = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("InMETHOD...........");

        HttpSession session = request.getSession(true);
        
        String stored_token = beans.MyUtil.filterBad(""+session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }

        try {

            con = DBConnection.getConnectionGen();
            Api_missingdata_from formvariable = (Api_missingdata_from) form;
            String admission_num = beans.MyUtil.filterBad(formvariable.getAdmission_number());
            String ssc_no =  beans.MyUtil.filterBad(formvariable.getSsc_regno());
            String name =  beans.MyUtil.filterBad(formvariable.getName());
            String fname =  beans.MyUtil.filterBad(formvariable.getF_name());
            String mname =  beans.MyUtil.filterBad(formvariable.getM_name());
            int i = 0;
            String aadharno =  beans.MyUtil.filterBad(formvariable.getAadhar_num());
            boolean aadharcheck = MyUtil.validateVerhoeff(aadharno);
            if (!aadharcheck) {
                this.redirect = "no_data";
                request.setAttribute("success", "Invalid Aadhar No.");
                return mapping.findForward(this.redirect);
            }

            String dob =  beans.MyUtil.filterBad(formvariable.getDateofbirth());
            String emailid =  beans.MyUtil.filterBad(formvariable.getEmail_id());
            String shift =  beans.MyUtil.filterBad(formvariable.getShift());
            String unit =  beans.MyUtil.filterBad(formvariable.getUnit());
            String pwd_category =  beans.MyUtil.filterBad(formvariable.getPwd_category());
            String ewc =  beans.MyUtil.filterBad(formvariable.getEwc());
            String is_trainee_dual_mode =  beans.MyUtil.filterBad(formvariable.getDual_mode());
            
            int duplicatemail = DBC.MyUtil.DuplicateCheck("admissions.iti_admissions", "email_id='" + emailid + "' and adm_num!='" + admission_num + "'", "");
            System.err.println("duplicatemail--->" + duplicatemail);
            if (duplicatemail > 0) {
                this.redirect = "no_data";
                request.setAttribute("success", "MailId Already Exists");
                return mapping.findForward(this.redirect);
            }

            String regid =  beans.MyUtil.filterBad(formvariable.getRegid());
            System.err.println("regid is--->" + regid);
            int reg_id = Integer.parseInt(regid);
            boolean itrainee_dual_mode = is_trainee_dual_mode.equals("yes");
            long phno = formvariable.getPhno();
            int duplicatephone = DBC.MyUtil.DuplicateCheck("admissions.iti_admissions", "phno='" + phno + "' and adm_num!='" + admission_num + "'", "");
            System.err.println("duplicatephone--->" + duplicatephone);
            if (duplicatephone > 0) {
                this.redirect = "no_data";
                request.setAttribute("success", "Phone Number Already Exists");
                return mapping.findForward(this.redirect);
            }

            boolean economic_weaker_section = ewc.equals("yes");
            System.out.println("fdlkg;fdkgdfg" + economic_weaker_section);
            HttpSession ses = request.getSession();
            String ins_code =  beans.MyUtil.filterBad("" + ses.getAttribute("iti_code"));
            System.err.println("ins_code is--->" + ins_code);
            String query = "Update admissions.iti_admissions set name=?, fname=?,mname=?,adarno=?,dob=?,email_id=?,shift=?,unit=?,pwd_category=?,economic_weaker_section=?,phno=?,is_trainee_dual_mode=?,rec_status='E' where adm_num=? and ssc_regno=?";
            this.st = this.con.prepareStatement(query);
            this.st.setString(1, name);
            this.st.setString(2, fname);
            this.st.setString(3, mname);
            this.st.setString(4, aadharno);
            this.st.setDate(5, Date.valueOf(MyUtil.ChDate1(dob)));
            this.st.setString(6, emailid);
            this.st.setString(7, shift);
            this.st.setString(8, unit);
            this.st.setString(9, pwd_category);
            this.st.setBoolean(10, economic_weaker_section);
            this.st.setLong(11, phno);
            this.st.setBoolean(12, itrainee_dual_mode);
            this.st.setString(13, admission_num);
            this.st.setString(14, ssc_no);
            System.err.println("st is---->" + query);
            i = this.st.executeUpdate();
            if (i > 0) {
                request.setAttribute("success", "Succesfully Updated");
            } else {
                request.setAttribute("success", "Failed to Update");
            }
        } catch (Exception var32) {
            var32.printStackTrace();
        }

        return mapping.findForward(this.redirect);
    }
}
