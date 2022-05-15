/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationForm_withoutphoto;

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
public class Application_Open_Iti_Action extends org.apache.struts.action.Action {

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
        Application_Open_Iti_Form aop = (Application_Open_Iti_Form) form;
        String iti = aop.getIti_code();
        String operation = aop.getOperation();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBC.DBConnection.getConnection(request);
            String query = "";
            if (operation.equals("insert")) {
                query = "insert into application_open_itis(iti_code) values(?);";
            } else {
                query = "delete from application_open_itis where iti_code=?";
            }
            ps = con.prepareStatement(query);
            ps.setString(1, iti);
            ps.executeUpdate();
        } catch (Exception e) {
            // e.printStackTrace();
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
