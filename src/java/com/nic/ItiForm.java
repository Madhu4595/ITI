/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nic;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author SUBBAREDDY
 */
public class ItiForm extends org.apache.struts.action.ActionForm {

    String iti_code;
    String iti_name;

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

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        return errors;
    }
}
