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
public class EditTrade_InterfaceAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        EditTrade_InterfaceForm fl = (EditTrade_InterfaceForm) form;
        String strength = beans.MyUtil.filterBad(fl.getStrength());
        String strength_fill = beans.MyUtil.filterBad(fl.getStrength_fill());
        String strength_vacant = beans.MyUtil.filterBad(fl.getStrength_vacant());
        String available = beans.MyUtil.filterBad(fl.getAvailable());
        String trade_code = beans.MyUtil.filterBad(fl.getTrade_code1());
        
        String year = beans.MyUtil.getOptionValue("iti_params", "code", "value", "7",request);
        int phase = Integer.parseInt(beans.MyUtil.getOptionValue("admissions.admission_phase", "year", "phase",year,request));

        HttpSession session = request.getSession(true);
        
        String stored_token = beans.MyUtil.filterBad(""+session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id = stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
       
// String sidc = (String) session.getAttribute("sidc");
        String iti_code = beans.MyUtil.filterBad(""+session.getAttribute("ins_code"));
        String role_id = beans.MyUtil.filterBad("" + session.getAttribute("role_id"));
        
        String returnpath = "";
        
        if (role_id.equals("4")) {
            // String iti_code_conv=(String)session.getAttribute("iti_code_conv");
            String sql = "update ititrade set strength='" + strength + "' , available_for_year='" + available + "' WHERE iti_code='" + iti_code + "' and trade_code='" + trade_code + "' ";
            returnpath = RoleBasedOperations.EditConTradeCodesSuccess(sql, request, iti_code);
        } else if (role_id.equals("3")) {
            String iti_code_conv = (String) session.getAttribute("iti_code_conv");
            //String sql = "update ititrade set strength='" + strength + "' , available_for_year='" + available + "' WHERE iti_code='" + iti_code_conv + "' and trade_code='" + trade_code + "'";
            String sql = "update ititrade set strength='" + strength + "' , available_for_year='" + available + "' WHERE iti_code='" + iti_code_conv + "' and trade_code='" + trade_code + "' ";
            System.out.println("sql "+sql);
            returnpath = RoleBasedOperations.EditConTradeCodesSuccess(sql, request, iti_code);
        }
        return mapping.findForward(returnpath);
    }
}
