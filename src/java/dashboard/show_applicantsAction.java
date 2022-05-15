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
 * @author GOPI DAGGUMALLE
 */
public class show_applicantsAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        HttpSession session = request.getSession(true);
        
        String stored_token = beans.MyUtil.filterBad(""+session.getAttribute("generated_token"));
        String token = beans.MyUtil.filterBad(request.getParameter("token"));
    
        String id=stored_token.substring(10, 20)+request.getSession().getId()+stored_token.substring(2, 6);
        if(!token.equals(id)){ return mapping.findForward("failure"); }
        
        String sidc = (String) session.getAttribute("sidc");
        String iti_code = (String) session.getAttribute("ins_code");

        //String role_id = "" + session.getAttribute("role_id");
        String returnpath = "";

        String iti_code_v = request.getParameter("iti_code");
        // //System.out.println(iti_code_v);
        //String sql = "select distinct regid from trade_sel where phase2e=true and iti_code='" + iti_code_v + "' order by regid";
        returnpath = RoleBasedOperations.ShowApplicants("", request, iti_code_v);
        return mapping.findForward(SUCCESS);
    }
}
