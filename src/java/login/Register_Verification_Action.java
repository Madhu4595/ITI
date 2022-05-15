/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession; 
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Ramesh Naidu
 */
public class Register_Verification_Action extends org.apache.struts.action.Action {

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

        Register_Verification_Form rvf = (Register_Verification_Form) form;
        
        String username = beans.MyUtil.filterBad(rvf.getUsername());
        String hash_password = beans.MyUtil.filterBad(rvf.getPwd());
        String desig_code = beans.MyUtil.filterBad(rvf.getDesignation());
        String email_id = beans.MyUtil.filterBad(rvf.getEid());
        String mobile_no = beans.MyUtil.filterBad(rvf.getMbno());
        String u_name = beans.MyUtil.filterBad(rvf.getEname());
        String roleid = "7";
        int slno = 555;
        
        HttpSession session = request.getSession(true);
        String stored_token = beans.MyUtil.filterBad(""+session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id = stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
        
        
        String inscode = beans.MyUtil.filterBad(rvf.getOfficer_iti());
        int trno = DBC.generic.logTrace((String) request.getSession().getAttribute("userName"), "application", "Update", request);

        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBC.DBConnection.getConnection(request);
            String query = "insert into login_users(username,hash_password,email_id,mobile_no,u_name,roleid,slno,trno,ins_code,tablename) values(?,md5(md5(?)),?,?,?,?,?,?,?,'iti')";
            ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, hash_password);
            ps.setString(3, email_id);
            ps.setString(4, mobile_no);
            ps.setString(5, u_name);
            ps.setString(6, roleid);
            ps.setInt(7, slno);
            ps.setInt(8, trno);
            ps.setString(9, inscode);
            ps.executeUpdate();
        } 
        catch (Exception e) { e.printStackTrace(); }
        finally {
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
