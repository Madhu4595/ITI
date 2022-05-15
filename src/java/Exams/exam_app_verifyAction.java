/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Exams;

import DBC.RoleBasedOperations;
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
public class exam_app_verifyAction extends org.apache.struts.action.Action {

    private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession session = request.getSession(true);
        String iti_code = (String) session.getAttribute("ins_code");
        //String iti_code = (String) session.getAttribute(sidc);
        String returnpath = null;
        String role_id = "" + session.getAttribute("role_id");
        if (role_id.equals("4")) {
            String sql = "SELECT * FROM ititrade WHERE iti_code='" + iti_code + "' and trade_code='" + "" + "'";
            returnpath = RoleBasedOperations.EditConvinerTradeCodes(sql, request, "");
        } else if (role_id.equals("3")) {

            String sql = "select * from iti_admissions";
            returnpath = RoleBasedOperations.EditConvinerTradeCodes(sql, request, "");
        }
        return mapping.findForward(returnpath);
    }
}
