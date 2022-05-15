/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author APDCA
 */
public class GovtITIReportForm extends org.apache.struts.action.ActionForm {

    private String iti_name;
    private String address;
    private String dist_name;

    public String getDist_name() {
        return dist_name;
    }

    public void setDist_name(String dist_name) {
        this.dist_name = dist_name;
        System.err.println("Enter in Form bean class of setter...");
    }
    
    
    public String getIti_name() {
        return iti_name;
    }

    public String getAddress() {
        return address;
    }

    public void setIti_name(String iti_name) {
        this.iti_name = iti_name;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        System.out.println("Form bean validate()...");
        return errors;
    }
    

}
