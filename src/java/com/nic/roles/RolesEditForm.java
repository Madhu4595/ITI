/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nic.roles;


import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author theru hareesh
 */
public class RolesEditForm extends org.apache.struts.action.ActionForm {

    private String name;
    private String[] role;
    private String roleid;
    private String rolename;
    private int mid;
    private int weight;
    private String menulink;
    private String link;

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

    public String getRolename() {
        return beans.MyUtil.filterBad(rolename);
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String[] getRole() {
        return role;
    }

    public void setRole(String[] role) {
        this.role = role;
    }
   

    public String getName() {
        return beans.MyUtil.filterBad(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMenulink() {
        return beans.MyUtil.filterBad(menulink);
    }

    public void setMenulink(String menulink) {
        this.menulink = menulink;
    }

    public String getLink() {
        return beans.MyUtil.filterBad(link);
    }

    public void setLink(String link) {
        this.link = link;
    }

    public RolesEditForm() {
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
        /*if(!Validations.Validate(getMid(), 2)){
         errors.add("mid",new ActionMessage("mid.numeric"));
         }
         if(!Validations.Validate(getWeight(), 2)){
         errors.add("weight",new ActionMessage("weight.numeric"));
         }*/
        return errors;
    }
}
