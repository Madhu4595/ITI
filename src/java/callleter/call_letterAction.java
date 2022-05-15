package callleter;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import DBC.DBConnection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;

/**
 *
 * @author RAJU
 */
public class call_letterAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    private String main;

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
        ResultSet rs = null;
        PreparedStatement ps = null;
        
        HttpSession session = request.getSession(true);
        
        String stored_token = beans.MyUtil.filterBad(""+session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("fail"); }
        
        call_letterForm cf = (call_letterForm) form;
        String regid = beans.MyUtil.filterBad(cf.getRegid());
        try {
            con = DBC.DBConnection.getConnection(request);
            String query = "select regid,name,fname from application where regid=?";
            ps = con.prepareStatement(query);
            ps.setString(1, regid);
            rs = ps.executeQuery();
            ArrayList a = new ArrayList();

            if (rs.next()) {
                call_letterForm f = new call_letterForm();
                f.setRegid(rs.getString(1));
                f.setName(rs.getString(2));
                f.setFname(rs.getString(3));
                a.add(f);
            } else {
                return mapping.findForward("failure");
            }
            session.setAttribute("a", a);
        } catch (Exception e) {
            e.printStackTrace();
            return mapping.findForward("failure");

        } finally {
            try {
                ps.close();
                con.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(generic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return mapping.findForward(SUCCESS);
    }
}
