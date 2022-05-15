package applicationForm;

import DBC.MyUtil;
import DBC.Validations;
import java.sql.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class ForgotRegidForm  extends org.apache.struts.action.ActionForm {
    
    private String ssc_regno,dob,regid;
    private String captcha;
    
    public String getCaptcha() {
        return beans.MyUtil.filterBad(captcha);
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }


    public String getRegid() {
        return regid;
    }

    public void setRegid(String regid) {
        this.regid = regid;
    }

    public String getSsc_regno() {
        return ssc_regno;
    }

    public void setSsc_regno(String ssc_regno) {
        this.ssc_regno = ssc_regno;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
    
    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        
        ActionErrors errors = new ActionErrors();
       
        HttpSession ses = request.getSession(true);
        
        String user_name = beans.MyUtil.filterBad("" + ses.getAttribute("userName"));
        System.out.println("user_name from session" + user_name);
         
        String temp_captcha = beans.MyUtil.filterBad("" + ses.getAttribute("captcha"));
        boolean insession = MyUtil.checkExistingSession(user_name);
        System.out.println("in session is--->" + insession);

        if (temp_captcha.length() > 0) {
            if (!temp_captcha.equalsIgnoreCase(getCaptcha())) {
                errors.add("captcha", new ActionMessage("error.captcha.notmatch"));
            } 
            else {
                //ssc_regno
                if (getSsc_regno() == null || getSsc_regno().length() < 1) {
                    errors.add("ssc_regno", new ActionMessage("error.ssc_regno.required"));
                }
                else if(!Validations.Validate(getSsc_regno(), 4)){
                    errors.add("ssc_regno", new ActionMessage("error.ssc_regno.notmatch"));
                }
                
                //dob
                if(getDob() == null || getDob().length()<10){
                    errors.add("dob", new ActionMessage("error.dob.required"));
                }
                else if(!Validations.Validate(getDob(), 8)){
                    errors.add("dob", new ActionMessage("error.dob.notmatch"));
                }   
            }

        } 
        else {
            errors.add("captcha", new ActionMessage("error.captcha.required"));
        }
        
        return errors;
    }
    
}
