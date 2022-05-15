/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nic;

import org.apache.struts.action.ActionForm;

/**
 *
 * @author SUBBAREDDY
 */
public class DistrictForm extends ActionForm {

    private String dist_code;

    public DistrictForm() {
        super();
    }
    private String dist_name;

    public String getDist_code() {
        return beans.MyUtil.filterBad(dist_code);
    }

    public void setDist_code(String dist_code) {
        this.dist_code = dist_code;
    }

    public void setDist_name(String dist_name) {
        this.dist_name = dist_name;
    }

    public String getDist_name() {
        return beans.MyUtil.filterBad(dist_name);
    }
}
