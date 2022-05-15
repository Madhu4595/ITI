/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Password;

import DBC.Validations;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author Gopi Daggumalle
 */


public class ChangePassword_InterfaceForm extends org.apache.struts.action.ActionForm {

    String oldpwd; 
    String newpwd;
    String token;
    

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOldpwd() {
        return beans.MyUtil.filterBad(oldpwd);
    }

    public void setOldpwd(String oldpwd) {
        this.oldpwd = oldpwd;
    }
 

    public String getNewpwd() {
        return beans.MyUtil.filterBad(newpwd);
    }

    public void setNewpwd(String newpwd) {
        this.newpwd = newpwd;
    }
    String retpwd;

    public String getRetpwd() {
        return beans.MyUtil.filterBad(retpwd);
    }

    public void setRetpwd(String retpwd) {
        this.retpwd = retpwd;
    }

    public ChangePassword_InterfaceForm() { super(); }

     
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        
        ActionErrors errors = new ActionErrors();
        
        System.err.println("dskfjsfsdf"+getOldpwd());
        if (getOldpwd() == null || getOldpwd().length() < 1) {
            errors.add("oldpwd", new ActionMessage("error.oldpwd.required"));
        } else if (!Validations.Validate(getOldpwd(), 1)) {
            errors.add("oldpwd", new ActionMessage("error.oldpwd.notmatch"));
        }

        if (getOldpwd().length() > 20) {
            errors.add("oldpwd", new ActionMessage("error.oldpwd.lenerror"));
        }

        if (getNewpwd() == null || getNewpwd().length() < 1) {
            errors.add("newpwd", new ActionMessage("error.newpwd.required"));
        } else if (!Validations.Validate(getNewpwd(), 1)) {
            errors.add("newpwd", new ActionMessage("error.newpwd.notmatch"));
        }

        if (getNewpwd().length() > 20) {
            errors.add("newpwd", new ActionMessage("error.newpwd.lenerror"));
        }

        if (getRetpwd() == null || getRetpwd().length() < 1) {
            errors.add("retpwd", new ActionMessage("error.retpwd.required"));
        } else if (!Validations.Validate(getRetpwd(), 1)) {
            errors.add("retpwd", new ActionMessage("error.retpwd.notmatch"));
        }

        if (getRetpwd().length() > 20) {
            errors.add("retpwd", new ActionMessage("error.retpwd.lenerror"));
        }

        if (!getNewpwd().equals(getRetpwd())) {
            errors.add("newpwd", new ActionMessage("error.pass.error"));
        }
        if(getNewpwd().length()>7){
          
            String specialChars = "/*!@#$%^&*()\"{}_[]|\\?/*<>,.";
            if(checkpass(getNewpwd())){ 
                 Pattern specailCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
                  if (!specailCharPatten.matcher(getNewpwd()).find()) {
                       errors.add("newpwd", new ActionMessage("error.pass.containcharacters"));
                  }
        }
            else{
                errors.add("newpwd", new ActionMessage("error.pass.containcharacters"));
            }
        }
        else{
            errors.add("newpwd", new ActionMessage("error.pass.minlength"));
        }
        return errors;
        
        
    }
    
    public static boolean checkpass(String password){
        boolean hasNum=false;
        boolean hasCap=false;
        boolean hasLow=false;
        boolean special_character=false;
        char c;
        String specialChars = "/*!@#$%^&*()\"{}_[]|\\?/<>,.";
    for(int i=0;i<password.length();i++){
        c=password.charAt(i);
        if(Character.isDigit(c)){
            hasNum=true;
        }
        else if(Character.isUpperCase(c)){
            hasCap=true;
        }
        else if(Character.isLowerCase(c)){
            hasLow=true;
        }
        
        if(hasNum && hasCap && hasLow ){
            return true;
        }
        
    }
      return false;  
    }
}
