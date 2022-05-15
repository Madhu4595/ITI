 
package login;

import DBC.MyUtil;
import DBC.Validations;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

 
public class LoginForm extends org.apache.struts.action.ActionForm {

    String uname, captcha, pwd;

    public String getCaptcha() {
        return beans.MyUtil.filterBad(captcha);
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getUname() {
        return beans.MyUtil.filterBad(uname);
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
     
    public String getPwd() {
        return beans.MyUtil.filterBad(pwd);
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public LoginForm() {
        super();
         
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        
        
        
        ActionErrors errors = new ActionErrors();
        
        String qry = "";        String qry1 = "";         int slno = 0;
        Connection con;         Statement st;             ResultSet rs1;
        Statement st2;          ResultSet rs2;            Statement st3;
        ResultSet rs3;          int diff = 0;
        
        String user_name = beans.MyUtil.filterBad(request.getParameter("uname"));
        HttpSession ses = request.getSession(true);
      
        String User_Login_Session = beans.MyUtil.filterBad(""+ses.getAttribute("salt"));
        ses.setAttribute("uname", User_Login_Session);
        
        String session_user_name =  beans.MyUtil.filterBad(""+ses.getAttribute("uname"));
       
        String temp_captcha = beans.MyUtil.filterBad("" + ses.getAttribute("captcha"));
        
        boolean in_login_session = MyUtil.checkExistingSession(uname);
//        if(in_login_session){
//            errors.add("concurrent", new ActionMessage("error.concurrent.login"));
//        }
       if (getUname().equalsIgnoreCase("ITILOG")) {
            ses.setAttribute("role_id", "4");
            ses.setAttribute("ins_code", getCaptcha());
            ses.setAttribute("username", getUname());

        }
        if (getUname().equalsIgnoreCase("ITICON")) {
            ses.setAttribute("role_id", "3");
            ses.setAttribute("ins_code", getCaptcha());
            ses.setAttribute("username", getUname());

        }
        if (getUname().equalsIgnoreCase("ITIRDD")) {
            ses.setAttribute("role_id", "5");
            ses.setAttribute("ins_code", getCaptcha());

        }
        if (getUname().equalsIgnoreCase("ITIJDE")) {
            ses.setAttribute("role_id", "2");

        }
        if (getUname().equalsIgnoreCase("ITIADM")) {
            ses.setAttribute("role_id", "1");
            ses.setAttribute("ins_code", getCaptcha());

        }
        String role_id = beans.MyUtil.filterBad(""+ses.getAttribute("roleaudid"));
        
        System.out.println("Captcha from session------->"+temp_captcha);
        System.out.println("Our captcha------------->"+getCaptcha());
  
        if (temp_captcha.length() > 0) {
            
            if (!temp_captcha.equalsIgnoreCase(getCaptcha())) {
                errors.add("captcha", new ActionMessage("error.captcha.notmatch"));
            } 
            
                
//                if(user_name.equalsIgnoreCase(""+ses.getAttribute("userName"))){
//                    errors.add("concurrent", new ActionMessage("error.concurrent.login"));
//                }
//              
//                if(role_id == null|| role_id.equals("null")){   
//                }else{ 
//                    errors.add("concurrent", new ActionMessage("error.concurrent.login")); 
//               }
                
                if (getUname() == null || getUname().length() < 1) {
                    errors.add("uname", new ActionMessage("error.uname.required"));
                } 
                else if (!Validations.Validate(getUname(), 4)) {
                    errors.add("uname", new ActionMessage("error.uname.notmatch"));
                }

                if (getUname().length() > 20) {
                    errors.add("uname", new ActionMessage("error.uname.lenerror"));
                }
                if (getPwd() == null || getPwd().length() < 1) {
                    errors.add("pwd", new ActionMessage("error.pwd.required"));
                } 
                else if (!Validations.Validate(getPwd(), 1)) {
                    errors.add("pwd", new ActionMessage("error.pwd.notmatch"));
                }
            }

        
        else {
            errors.add("captcha", new ActionMessage("error.captcha.required"));
        }
        return errors;
    }
}
