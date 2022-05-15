/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Ramesh Naidu
 */
public class VerificationActionEdit extends org.apache.struts.action.Action {

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
        VerificationForm vff = (VerificationForm) form;
        String itiCode = vff.getItiCodeEdit();
        String itiName = vff.getItiNameEdit();
        String officerCode = vff.getOfficerCodeEdit();
        String officerName = vff.getVerifyOfficerName();
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            String query = "update vo_iti_mapping set verificationofficer_code='" + officerCode + "' where iti_code='" + itiCode + "'";
            con = DBC.DBConnection.getConnection(request);
            ps = con.prepareStatement(query);
            ps.executeUpdate();
        } catch (Exception e) {
        } finally {

            if (ps != null) {
                ps.close();
                con.close();
            }

        }

        return mapping.findForward(SUCCESS);
    }
}
