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
public class RolesEntryFormAcion extends org.apache.struts.action.Action {

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

        HttpSession session = request.getSession();

        ResultSet rs = null;
        Connection con = null;
        PreparedStatement ps = null;

        boolean flag = false;
        try {
            con = DBC.DBConnection.getConnection(request);
            String sql = "select role_id,rolename from role_mast order by role_id";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            ArrayList list = new ArrayList();
            while (rs.next()) {
                RolesEntryForm ref = new RolesEntryForm();
                ref.setRoleid(rs.getString(1));
                ref.setRolename(rs.getString(2));

                list.add(ref);
            }
            System.out.println("the list size value is:" + list.size());

            session.setAttribute("list", list);

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

        return mapping.findForward("successroles");
    }
}
