/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.sql.*; 
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
public class VerificationAction extends org.apache.struts.action.Action {

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
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = null;
        String itiCode = beans.MyUtil.filterBad(request.getParameter("tc"));
        try {
            con = DBC.DBConnection.getConnection(request);
            sql = "delete from vo_iti_mapping where iti_code=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, itiCode);
            ps.executeUpdate();
        } 
        catch (Exception e) { e.printStackTrace(); }
        return mapping.findForward(SUCCESS);
    }
}
