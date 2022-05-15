/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationForm;

import beans.MyUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
 
public class Api_missingimage extends org.apache.struts.action.Action {
    
    Connection con = DBC.DBConnection.getConnectionGen();        
    boolean commit = true;
    
    PreparedStatement st = null;
    ResultSet rs = null;
    String redirect="success";
    
    @Override    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("Api_missingimage.java");
            
        Api_missingdata_from formvariable = (Api_missingdata_from) form;
        String admission_num    = beans.MyUtil.filterBad(formvariable.getAdmission_number().trim()); 
        String ssc_no           = beans.MyUtil.filterBad(formvariable.getSsc_regno().trim());
        
        HttpSession ses = request.getSession();
        String ins_code = beans.MyUtil.filterBad(""+ses.getAttribute("ins_code"));
        System.err.println("ins_code is--->"+ins_code);
        
        String stored_token = beans.MyUtil.filterBad(""+ses.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
	
        
        String sql = "Select name,regid,fname,mname,adarno,dob,email_id,shift,unit,economic_weaker_section,pwd_category,phno,is_trainee_dual_mode from image_not_found where adm_num=? and ssc_regno=? and year_of_admission='2020' and iti_code=? and (rec_status ='E' or rec_status is null)";
       
        try {
            st = con.prepareStatement(sql);
            st.setString(1, admission_num);
            st.setString(2, ssc_no);
            st.setString(3, ins_code);
      
            rs = st.executeQuery();
                     
                if (rs.next()) {
                     ses.setAttribute("email_id", rs.getString("email_id")==null?"":rs.getString("email_id"));
                    ses.setAttribute("shift", rs.getString("shift"));
                    ses.setAttribute("unit", rs.getString("unit"));
                    ses.setAttribute("name", rs.getString("name"));                    
                    ses.setAttribute("fname", rs.getString("fname"));
                    ses.setAttribute("mname", rs.getString("mname"));
                    ses.setAttribute("adarno", rs.getString("adarno"));
                    ses.setAttribute("reg_id", rs.getInt("regid"));
                    if(rs.getString("dob")!=null){
                        ses.setAttribute("dob", MyUtil.ChDate(rs.getString("dob")));
                    }
                    
                    ses.setAttribute("admission_num", admission_num);
                    ses.setAttribute("ssc_no", ssc_no);
                    ses.setAttribute("economic_weaker_section", rs.getBoolean("economic_weaker_section"));
                    ses.setAttribute("pwd_category", rs.getString("pwd_category"));
                    ses.setAttribute("phno", rs.getString("phno"));
                    ses.setAttribute("is_trainee_dual_mode", rs.getBoolean("is_trainee_dual_mode"));
                    System.err.println("in if condition");
                     redirect="success";
                }
                else{
                    System.err.println("dkflbgfdbgdfgdfgfd");
                    request.setAttribute("success", "No Data Found");
                    redirect="no_data";
                }
        } catch (Exception ex) {
            System.out.println("exception in api missing data" + ex);
        }
             
             
           return mapping.findForward(redirect);
        }
}
