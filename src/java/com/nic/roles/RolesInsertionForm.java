/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nic.roles;

import DBC.Validations;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author theru hareesh
 */
public class RolesInsertionForm extends org.apache.struts.action.ActionForm {

    String name;
    String[] selectRole;
    String[] roleOption;
    String menuLink;
    boolean haschildren;
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

    public boolean isHaschildren() {
        return haschildren;
    }

    public void setHaschildren(boolean haschildren) {
        this.haschildren = haschildren;
    }

    public String getMenuLink() {
        return beans.MyUtil.filterBad(menuLink);
    }

    public void setMenuLink(String menuLink) {
        this.menuLink = menuLink;
    }

    public String[] getRoleOption() {
        return roleOption;
    }

    public void setRoleOption(String[] roleOption) {
        this.roleOption = roleOption;
    }

    public String[] getSelectRole() {
        return selectRole;
    }

    public void setSelectRole(String[] selectRole) {
        this.selectRole = selectRole;
    }

    public String getName() {
        return beans.MyUtil.filterBad(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return beans.MyUtil.filterBad(link);
    }

    public void setLink(String link) {
        this.link = link;
    }
    String link;

    public RolesInsertionForm() {
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
        ActionErrors aes = new ActionErrors();
        if ((name == null) || (name.equalsIgnoreCase(""))) {
            aes.add("name1", new ActionMessage("name.required"));
        } else if (!Validations.Validate(getName(), 3)) {
            aes.add("name2", new ActionMessage("name.alpha"));
        }

        if ((link == null) || (link.equalsIgnoreCase(""))) {
            aes.add("link1", new ActionMessage("link.required"));
        } else if (!Validations.Validate(getLink(), 12)) {
            //aes.add("name2", new ActionMessage("link.address"));
        }

        if ((menuLink == null) || (menuLink.equalsIgnoreCase(""))) {
            aes.add("link1", new ActionMessage("menuLink.required"));
        } else if (!Validations.Validate(getMenuLink(), 12)) {
            //aes.add("name2", new ActionMessage("menuLink.address"));
        }
        /* if(!Validations.Validate(roleOption.toString(),2)){
         aes.add("roleOption",new ActionMessage("roleOption.address"));
         }*/

        /* for(int i=0;i<roleOption.length;i++){
         if(!roleOption[i].equals("1") || !roleOption[i].equals("0") ){
         aes.add("roleOption",new ActionMessage("roleOption.address"));
         }
         } */
        return aes;
    }
}
