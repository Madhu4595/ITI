/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Master.Trade;

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
public class TradeAction extends org.apache.struts.action.Action {

    private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        HttpSession session = request.getSession(true);
        //String type= (String)request.getAttribute("type");
        //String sidc = (String) session.getAttribute("sidc");
        String iti_code = (String) session.getAttribute("ins_code");

        String role_id = beans.MyUtil.filterBad("" + session.getAttribute("role_id"));
        String returnpath = "";

        if (role_id.equals("4")) {
            String sql = "select * from ititrade WHERE iti_code='" + iti_code + "'";
            returnpath = RoleBasedOperations.TradeCodes(sql, request, iti_code);
        } else if (role_id.equals("3")) {
            String sql = "select * from iti WHERE dist_code='" + iti_code + "'";
            returnpath = RoleBasedOperations.selectitiCodes(sql, request, iti_code);
        }

        return mapping.findForward(returnpath);
    }
}
