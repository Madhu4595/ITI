/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package State.Reports;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Gopi Daggumalle
 */
public class StatepwideRepAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String x = DBC.RoleBasedOperations.GenerateReport("a.dist_code#a.dist_name#b.region_code", "where a.dist_code=b.dist_code and a.statecode='28'", "dist_mst a,conviner_login b", "", request);
        return mapping.findForward(SUCCESS);
    }
}
