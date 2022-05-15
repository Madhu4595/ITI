package applicationForm;

import DBC.MyUtil;
import DBC.Validations;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

 
public class EditForm extends org.apache.struts.action.ActionForm {
  
    private String regid, ssc_type,ssc_regno,dob,captcha;
    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getSsc_type() {
        return beans.MyUtil.filterBad(ssc_type);
    }

    public String getSsc_regno() {
        return ssc_regno;
    }

    public void setSsc_regno(String ssc_regno) {
        this.ssc_regno = ssc_regno;
    }

    public void setSsc_type(String ssc_type) {
        this.ssc_type = ssc_type;
    }

    public String getRegid() {
        return beans.MyUtil.filterBad(regid);
    }

    public void setRegid(String regid) {
        this.regid = regid;
    }
    public String getCaptcha() {
        return beans.MyUtil.filterBad(captcha);
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
    public EditForm(){
    super();
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
       
        ActionErrors errors = new ActionErrors();
        
        HttpSession session = request.getSession(true);
       
        String user_name =  beans.MyUtil.filterBad(""+session.getAttribute("edit_form session is created"));
        System.out.println(user_name +": from EditForm.java");
        
        String temp_captcha = beans.MyUtil.filterBad(""+session.getAttribute("captcha"));
        boolean insession = MyUtil.checkExistingSession(user_name);
        System.out.println("From EditForm session name is--->" + insession);
        
        if (temp_captcha.length() > 0) {
            if (!temp_captcha.equalsIgnoreCase(getCaptcha())) {
                errors.add("captcha", new ActionMessage("error.captcha.notmatch"));
            } 
            else 
            {
                    //ssc_regno
                    if(getSsc_regno() == null || getSsc_regno().length()<1){
                        errors.add("ssc_regno", new ActionMessage("error.ssc_regno.required"));
                    }else if(!Validations.Validate(getSsc_regno(), 26)){
                        errors.add("ssc_regno", new ActionMessage("error.ssc_regno.notmatch"));
                    }
                    //dob
                    if(getDob()== null || getDob().length()<1){
                        errors.add("dob", new ActionMessage("error.dob.required"));
                    }else if(!Validations.Validate(getDob(), 8)){
                        errors.add("dob", new ActionMessage("error.dob.notmatch"));
                    }
                    //regid
                    if (getRegid() == null || getRegid().length() < 1) {
                        errors.add("regid", new ActionMessage("error.regid.required"));
                    }else if (!Validations.Validate(getRegid(), 25)) {
                        errors.add("regid", new ActionMessage("error.regid.notmatch"));
                    } 
                    
            }
        } 
        else 
        {
            errors.add("captcha", new ActionMessage("error.captcha.required"));
        }
        return errors;

 
    }
    
}
