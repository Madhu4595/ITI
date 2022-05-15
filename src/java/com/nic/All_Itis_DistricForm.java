/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nic;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author SUBBAREDDY
 */
public class All_Itis_DistricForm extends org.apache.struts.action.ActionForm {

    String iti_code;
    String iti_name;
    String dist_code;

    public String getIti_code() {
        return beans.MyUtil.filterBad(iti_code);
    }

    public void setIti_code(String iti_code) {
        this.iti_code = iti_code;
    }

    public String getIti_name() {
        return beans.MyUtil.filterBad(iti_name);
    }

    public void setIti_name(String iti_name) {
        this.iti_name = iti_name;
    }

    public String getDist_code() {
        return beans.MyUtil.filterBad(dist_code);
    }

    public void setDist_code(String dist_code) {
        this.dist_code = dist_code;
    }

    public All_Itis_DistricForm() {
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
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();

        return errors;
    }
}
