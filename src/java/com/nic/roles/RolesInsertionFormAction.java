/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nic.roles;

import java.sql.*;
import java.util.ArrayList;
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
public class RolesInsertionFormAction extends org.apache.struts.action.Action {

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
        ResultSet rs = null;
        HttpSession session = request.getSession();
        
        String stored_token = beans.MyUtil.filterBad(""+session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
        
        RolesInsertionForm rif = (RolesInsertionForm) form;
        String menuName = rif.getName();
        System.out.println("the menuname is:" + menuName);
        String link = rif.getLink();
        System.out.println("the link value is:" + link);
        String menuLink = rif.getMenuLink();
        int mid = rif.getMid();
        int weight = rif.getWeight();
        String[] roleOption = rif.getRoleOption();
        boolean children = false;
        // System.out.println("the check box value is:" + role.length);
        //ArrayList data=(ArrayList)session.getAttribute("list");
        //System.out.println("the value is:"+data.size());
        try {
            con = DBC.DBConnection.getConnection(request);
            String x = "";
            for (int i = 0; i < roleOption.length; i++) {
                x += roleOption[i];
            }
            System.out.println("x value is:" + x);
            int trno = DBC.generic.logTrace("" + session.getAttribute("userName"), "role_menus", "Insert", request);
            System.out.println("the trno value is:" + trno);
            String sql = "insert into role_menus (name,link,role,menulink,haschildren,mid,weight,trno)values(?,?,?,?,?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, menuName);
            ps.setString(2, link);
            ps.setString(3, x);
            ps.setString(4, menuLink);
            ps.setBoolean(5, children);
            ps.setInt(6, mid);
            ps.setInt(7, weight);
            ps.setInt(8, trno);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
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
