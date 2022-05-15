/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dashboard;

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
public class Show_dashboarditisAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String dist_code = beans.MyUtil.filterBad(request.getParameter("dist_code"));
        HttpSession session = request.getSession(true);
        String role_id = beans.MyUtil.filterBad("" + session.getAttribute("role_id"));
        String returnpath = "";
        if (role_id.equals("2")) {
            String sql = "select distinct iti_code from trade_sel where substring(iti_code,1,2)='" + dist_code + "' order by iti_code";
            returnpath = RoleBasedOperations.DashBoard_Showitis(sql, request, dist_code);
        }
        return mapping.findForward(returnpath);
    }
}
