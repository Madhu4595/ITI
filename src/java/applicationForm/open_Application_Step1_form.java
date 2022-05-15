package applicationForm;

import DBC.MyUtil;
import DBC.Validations;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

 
public class open_Application_Step1_form extends org.apache.struts.action.ActionForm {

    private String name, dob, fname, math, ssc_type,dist;
    private String iti_code,ssc_board;
    private String ssc_regno,passed_year;
    private String captcha;
 
    public String getSsc_regno() { return ssc_regno; }
    public void setSsc_regno(String ssc_regno) { this.ssc_regno = ssc_regno; }
    
    public String getPassed_year() { return passed_year; }
    public void setPassed_year(String passed_year) { this.passed_year = passed_year; }

    public String getIti_code() { return iti_code; }
    public void setIti_code(String iti_code) { this.iti_code = iti_code; }

    public String getSsc_board() { return ssc_board; }
    public void setSsc_board(String ssc_board) { this.ssc_board = ssc_board; }
    
    public String getMath() { return beans.MyUtil.filterBad(math); }
    public void setMath(String math) { this.math = math; }

    public String getDist() { return dist; }
    public void setDist(String dist) { this.dist = dist; }

    

    public String getSsc_type() {
        return beans.MyUtil.filterBad(ssc_type);
    }

    public void setSsc_type(String ssc_type) {
        this.ssc_type = ssc_type;
    }

    public String getName() {
        return name;
    }

    public String getFname() {
        return beans.MyUtil.filterBad(fname);
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return beans.MyUtil.filterBad(dob);
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
    public String getCaptcha() {
        return beans.MyUtil.filterBad(captcha);
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
    public open_Application_Step1_form() {
        super();
         
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        
        ActionErrors errors = new ActionErrors();
        
        HttpSession ses = request.getSession(true);
        
        String user_name = beans.MyUtil.filterBad("" + ses.getAttribute("userName"));
        String temp_captcha = beans.MyUtil.filterBad("" + ses.getAttribute("captcha"));
        boolean insession = MyUtil.checkExistingSession(user_name);
 
        if (temp_captcha.length() > 0) {
            if (!temp_captcha.equalsIgnoreCase(getCaptcha())) { 
                errors.add("captcha", new ActionMessage("error.captcha.notmatch"));
            } 
            else { 
                //ssc_board
                if(getSsc_board().length() <1 || getSsc_board() == null){ 
                    errors.add("ssc_board", new ActionMessage("error.ssc_board.required")); 
                }
                //ssc_regno
                if(getSsc_regno() == null || getSsc_regno().length()<1){
                    errors.add("ssc_regno", new ActionMessage("error.ssc_regno.required"));
                }
                else if (!Validations.Validate(getSsc_regno(), 24)) { 
                    errors.add("ssc_regno", new ActionMessage("error.ssc_regno.notmatch")); 
                }
                //passed_year
                if(getPassed_year() == null || getPassed_year().length()<1){
                    errors.add("passed_year", new ActionMessage("error.passed_year.required"));
                }
                else if(!Validations.Validate(getPassed_year(), 27)) {
                    errors.add("passed_year", new ActionMessage("error.passed_year.notmatch"));
                }
                //ssc_type
                if(getSsc_type() == null || getSsc_type().length()<1){
                    errors.add("ssc_type", new ActionMessage("error.ssc_type.required"));
                }
                else if (!Validations.Validate(getSsc_type(), 6)) { 
                    errors.add("ssc_type", new ActionMessage("error.ssc_type.notmatch")); }
                
                if (getSsc_type().equals("open")) {
                    try {
                        if (!Validations.Validate(getMath(), 6)) { 
                            errors.add("math", new ActionMessage("error.math.notmatch")); 
                        }
                        if (getMath().equals("no")) { 
                            errors.add("math", new ActionMessage("error.math.eligible")); 
                        }
                    }
                    catch (NullPointerException ex) {
                        errors.add("math", new ActionMessage("error.math.required"));
                    }
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
