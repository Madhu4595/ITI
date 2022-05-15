 
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

 

public class Iti_params_interfaceaction extends org.apache.struts.action.Action {

     
    private static final String SUCCESS = "success";
    private String caste_code;
    private String FAILUR;

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        Iti_params_interfaceform ip = (Iti_params_interfaceform) form;
       
        String code         = beans.MyUtil.filterBad(ip.getCode());
        String description  = beans.MyUtil.filterBad(ip.getDescription());
        String value        = beans.MyUtil.filterBad(ip.getValue());
        
        HttpSession session = request.getSession(true);
        
        String stored_token = beans.MyUtil.filterBad(""+session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
        
        int i = 0;
        Connection con=null;
        PreparedStatement ps = null;
        
        try {
            con = DBC.DBConnection.getConnection(request);
            String sql = "insert into iti_params(code,description,value) values (?,?,?)";
             
            ps = con.prepareStatement(sql);
            ps.setString(1, code);
            ps.setString(2, description);
            ps.setString(3, value);
            ps.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("success", "<font color=red>Insertion Fail </font>");
            return mapping.findForward("success");
        }finally{if(con!=null){con.close();
        }}
        if (i <= 0) {
            request.setAttribute("success", "<font color=red>Insertion Fail </font>");
            return mapping.findForward("success");
        }
        request.setAttribute("success", "<font color=green>Insertion success </font>");
        return mapping.findForward(SUCCESS);

    }
}
