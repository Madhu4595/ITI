/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caste_master.info;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Gopi Daggumalle
 */
public class Edit_iti_paramsAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

   
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        HttpSession session = request.getSession(true);
        
        String stored_token = beans.MyUtil.filterBad(""+session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }

        Iti_params_interfaceform ip = (Iti_params_interfaceform) form;
        String code = beans.MyUtil.filterBad(ip.getCode());
        String description = beans.MyUtil.filterBad(ip.getDescription());
        String value = beans.MyUtil.filterBad(ip.getValue());
                    
        Connection con = null;
        PreparedStatement ps = null;

        try {
             con = DBC.DBConnection.getConnection(request);
            String sql = "update iti_params set description=?,value=? where code=?";
            ps = con.prepareStatement(sql); 
            ps.setString(1, description);
            ps.setString(2, value);
            ps.setString(3, code);
            ps.executeUpdate(); 
        } 
        catch (Exception e) { e.printStackTrace(); }
        finally{ 
            if(con!=null){con.close();} 
        }
        
        return mapping.findForward(SUCCESS);
    }
}
