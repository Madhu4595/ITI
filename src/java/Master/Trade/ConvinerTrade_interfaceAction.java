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

 
public class ConvinerTrade_interfaceAction extends org.apache.struts.action.Action {

    
    private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        convinerTrade_interfaceForm lf = (convinerTrade_interfaceForm) form;

        String iti_code_gen = beans.MyUtil.filterBad(lf.getIti_code());

        HttpSession session = request.getSession(true);
        
        
        session.setAttribute("iti_code_gen", iti_code_gen);
        
        String stored_token = beans.MyUtil.filterBad(""+session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
        
        String role_id = beans.MyUtil.filterBad("" + session.getAttribute("role_id"));
        String iti_code =  beans.MyUtil.filterBad(""+session.getAttribute("ins_code"));
        String returnpath = "";
        
        
        if (role_id.equals("3")) {
            String sql = "select * from ititrade WHERE iti_code='" + iti_code_gen + "'";
            returnpath = RoleBasedOperations.ConvinerTradeCodesRep(sql, request, iti_code_gen);
        }
        return mapping.findForward(returnpath);
    }
}
