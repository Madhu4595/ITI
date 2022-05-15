/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import DBC.Validations;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author Ramesh Naidu
 */
public class Verify_Vofficer_Form extends org.apache.struts.action.ActionForm {

    private String vid, pwd;

    public String getVid() {
        return beans.MyUtil.filterBad(vid);
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getPwd() {
        return beans.MyUtil.filterBad(pwd);
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Verify_Vofficer_Form() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * This is the action called from the Struts framework.
     *
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        System.out.println("UR in Verify_Vofficer_Form.java");
        
        ActionErrors errors = new ActionErrors();
        
        HttpSession session = request.getSession(true);
        DBC.generic g = new DBC.generic(); 
        String iti_code = (String) session.getAttribute("ins_code");
        
        //vid
        if(getVid() == null || getVid().length()<1){
            errors.add("vid",new ActionMessage("error.vid.required"));
        }
        //pwd
        if(getPwd()== null || getPwd().length()<1){
            errors.add("pwd",new ActionMessage("error.pwd.required"));
        }
        

        return errors;
    }
}
