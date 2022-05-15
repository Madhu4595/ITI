/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nic.roles;

import java.sql.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author theru hareesh
 */
public class RolesEditFormAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    /**
     * This is the action called from the Struts framework.
     *
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        HttpSession session = request.getSession();
        
        String stored_token = beans.MyUtil.filterBad(""+session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
        
        RolesEditForm ref = (RolesEditForm) form;
        String name = ref.getName();
        String link = ref.getLink();
        String[] role = ref.getRole();
        String menulink = ref.getMenulink();
        int mid = ref.getMid();
        System.out.println("mid&&&&&:" + mid);
        int weight = ref.getWeight();
        System.out.println("weight&:" + weight);
        System.out.println("Name:" + name);
        System.out.println("Link:" + link);
        System.out.println("Role:" + role);
        System.out.println("MenuLink:" + menulink);
        String lid = (String) request.getSession().getAttribute("lid");
        System.out.println("the Lid:" + lid);
        boolean children = false;
        try {
            String y = "";
            for (int i = 0; i < role.length; i++) {
                y += role[i];

            }
            System.out.println("the update role are:" + y);
            con = DBC.DBConnection.getConnection(request);

            int trno = DBC.generic.logTrace("" + session.getAttribute("userName"), "role_menus", "update", request);
            // System.out.println("the trno value is :" + trno);
            //DBC.RoleBasedOperations.arche_data_entry("role_menus", "lid");
            String update = "update role_menus set name='" + name + "',link='" + link + "',menulink='" + menulink + "',role='" + y + "',trno=" + trno + ",mid=" + mid + ",weight=" + weight + ",haschildren=" + children + " where lid=" + lid + "";
            ps = con.prepareStatement(update);
            //System.out.println("update query is" + update);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }

        }
        return mapping.findForward(SUCCESS);
    }
}
