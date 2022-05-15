/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nic.roles;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author theru hareesh
 */
public class RolesEntryForm extends org.apache.struts.action.ActionForm {

    String roleid;
    private int mid;
    private int weight;

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getRoleid() {
        return beans.MyUtil.filterBad(roleid);
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }
    String rolename;

    public String getRolename() {
        return beans.MyUtil.filterBad(rolename);
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public RolesEntryForm() {
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
        ActionErrors errors = new ActionErrors();

        return errors;
    }
}
